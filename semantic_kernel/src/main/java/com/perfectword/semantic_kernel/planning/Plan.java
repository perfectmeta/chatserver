package com.perfectword.semantic_kernel.planning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.perfectword.semantic_kernel.KernelException;
import com.perfectword.semantic_kernel.ai.text_completion.CompleteRequestSettings;
import com.perfectword.semantic_kernel.orchestration.ContextVariables;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.skill_define.FunctionView;
import com.perfectword.semantic_kernel.skill_define.ISKFunction;

import java.util.List;
import java.util.regex.Pattern;

public class Plan implements ISKFunction {
    @JsonProperty("state")
    private ContextVariables state;

    @JsonIgnore
    private ISKFunction function;

    @JsonProperty("steps")
    private List<Plan> steps;

    @JsonProperty("parameters")
    private ContextVariables parameters;

    @JsonProperty("outputs")
    private List<String> outputs;

    @JsonProperty("next_step_index")
    private int nextStepIndex;

    @JsonProperty("name")
    private String name = "";

    @JsonProperty("skill_name")
    private String skillName = "";

    @JsonProperty("description")
    private String description = "";

    @JsonIgnore
    private boolean isSemantic;

    @JsonIgnore
    private CompleteRequestSettings requestSettings;

    @JsonIgnore
    static final Pattern variablesRegex = Pattern.compile("\\$(?<var>\\w+)");

    @JsonIgnore
    private final String defaultResultKey = "PLAN.RESULT";

    private FunctionView view;

    public Plan(String goal) {
        description = goal;
        skillName = this.getClass().getName();
        view = new FunctionView(this.getClass().getName(), skillName, description, false, List.of());
    }

    public Plan(String goal, Plan... steps) {
        addSteps(steps);
        view = new FunctionView(this.getClass().getName(), "", description, false, List.of());
    }

    public Plan(ISKFunction function) {
        this.setFunction(function);
        var fv = function.view();
        view = new FunctionView("Plan_" + fv.name(), fv.skillName(), fv.description(), fv.isSemantic(), fv.parameters());
    }

    public Plan(String name, String skillName, String description, int nextStepIndex,
                ContextVariables state, ContextVariables parameters, List<String> outputs, List<Plan> steps) {
        this.name = name;
        this.skillName = skillName;
        this.description = description;
        this.nextStepIndex = nextStepIndex;
        this.state = state;
        this.parameters = parameters;
        this.outputs = outputs;
        this.steps.clear();
        this.addSteps((Plan[]) steps.toArray());
    }

    public void addSteps(Plan... steps) {
        this.steps.addAll(List.of(steps));
    }

    public void setFunction(ISKFunction function) {
        this.function = function;
        this.name = function.view().name();
        this.skillName = function.view().skillName();
        this.description = function.view().description();
        this.isSemantic = function.view().isSemantic();
    }

    public boolean hasNextStep() {
        return nextStepIndex < steps.size();
    }

    @JsonProperty("parameters")
    public ContextVariables getParameters() {
        return parameters;
    }

    public void setParameters(ContextVariables parameters) {
        this.parameters = parameters;
    }

    @JsonProperty("outputs")
    public List<String> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<String> outputs) {
        this.outputs = outputs;
    }

    @JsonProperty("next_step_index")
    public int getNextStepIndex() {
        return nextStepIndex;
    }

    public void setNextStepIndex(int nextStepIndex) {
        this.nextStepIndex = nextStepIndex;
    }

    @JsonProperty("state")
    public ContextVariables getState() {
        return state;
    }

    @JsonProperty("steps")
    public List<Plan> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public ISKFunction getFunction() {
        return function;
    }

    @Override
    public FunctionView view() {
        return view;
    }

    @Override
    public SKContext invoke(SKContext context) {
        if (this.function != null) {
            var result = this.function.invoke(context.copy());
            if (result.isErrorOccurred()) {
                System.out.printf("invoke error @%s:%s: %n", skillName, name);
                return result;
            }
        } else {
            while (hasNextStep()) {
                addVariablesToContext(state, context);
                invokeNextStep(context);
                updateContextWithOutputs(context);
            }
        }
        return context;
    }

    public Plan invokeNextStep(SKContext context) {
        if (!hasNextStep())
            return this;
        var step = steps.get(nextStepIndex);
        var functionVariable = getNextStepVariables(context.getVariables(), step);
        var functionContext = new SKContext(functionVariable, context.getMemory(), context.getSkills());
        var result = step.invoke(functionContext);
        var resultValue = result.toString(); // fixme 确认一下这么写行不行
        if (result.isErrorOccurred()) {
            throw new KernelException(KernelException.ErrorCodes.FunctionInvokeError,
                    "Error occurred while running plan step: %s".formatted(result.getLastException().getMessage()));
        }
        this.state.setInput(resultValue);
        if (outputs.stream().anyMatch(x -> step.outputs.contains(x))) {
            var value = state.get(defaultResultKey);
            if (value == null) {
                value = "";
            }
            state.set(defaultResultKey, String.join("\n", value.trim(), resultValue));
        }

        for (var item : step.outputs) {
            var v = result.getVariables().get(item);
            if (v != null) {
                state.set(item, v);
            } else {
                state.set(item, resultValue);
            }
        }
        this.nextStepIndex++;
        return this;
    }


    private ContextVariables getNextStepVariables(ContextVariables variables, Plan step) {
        var input = "";
        if (!isNullOrEmpty(step.parameters.getInput())) {
            input = expandFromVariables(variables, step.parameters.getInput());
        } else if (!isNullOrEmpty(variables.getInput())) {
            input = variables.getInput();
        } else if (!isNullOrEmpty(state.getInput())) {
            input = state.getInput();
        } else if (step.steps.size() > 0) {
            input = "";
        } else if (!isNullOrEmpty(description)) {
            input = description;
        }

        var stepVariables = ContextVariables.of(input);
        var functionParameters = step.view();
        for (var param : functionParameters.parameters()) {
            if (param.name().equalsIgnoreCase(ContextVariables.MAIN_KEY)) {
                continue;
            }

            if (variables.containsKey(param.name())) {
                stepVariables.set(param.name(), variables.get(param.name()));
            } else if (state.containsKey(param.name())) {
                var v = state.get(param.name());
                if (!isNullOrEmpty(v)) {
                    stepVariables.set(param.name(), v);
                }
            }
        }

        for (var item : step.parameters) {
            if (stepVariables.containsKey(item.getKey())) {
                continue;
            }

            var expandedValue = expandFromVariables(variables, item.getValue());
            if (!expandedValue.equalsIgnoreCase(item.getValue())) {
                stepVariables.set(item.getKey(), expandedValue);
            } else if (variables.containsKey(item.getKey())) {
                stepVariables.set(item.getKey(), variables.get(item.getKey()));
            } else if (state.containsKey(item.getKey())) {
                stepVariables.set(item.getKey(), state.get(item.getKey()));
            } else {
                stepVariables.set(item.getKey(), expandedValue);
            }
        }

        for (var entry : variables) {
            if (!stepVariables.containsKey(entry.getKey())) {
                stepVariables.set(entry.getKey(), entry.getValue());
            }
        }
        return stepVariables;
    }

    private String expandFromVariables(ContextVariables variables, String input) {
        var result = input;
        var matches = variablesRegex.matcher(input);
        if (matches.matches()) {
            for (int g = 0; g < matches.groupCount(); g++) {
                var varName = matches.group(g);
                if (variables.containsKey(varName)) {
                    result = result.replace("$" + varName, variables.get(varName));
                }
            }
        }
        return result;
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void addVariablesToContext(ContextVariables vars, SKContext context) {
        for (var v : vars) {
            if (!context.getVariables().containsKey(v.getKey())) {
                context.getVariables().set(v.getKey(), v.getValue());
            }
        }
    }

    private SKContext updateContextWithOutputs(SKContext context) {
        var resultString = state.get(defaultResultKey);
        if (resultString != null) {
            context.getVariables().setInput(resultString);
        } else {
            context.getVariables().setInput(state.toString());
        }

        for (var item : steps.get(nextStepIndex - 1).outputs) {
            var value = state.get(item);
            if (value != null) {
                context.getVariables().set(item, value);
            } else {
                context.getVariables().set(item, resultString);
            }
        }
        return context;
    }
}
