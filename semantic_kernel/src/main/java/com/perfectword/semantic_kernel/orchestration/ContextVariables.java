package com.perfectword.semantic_kernel.orchestration;

import com.perfectword.semantic_kernel.Verify;

import java.util.HashMap;
import java.util.Map;

public class ContextVariables {
    final String MAIN_KEY = "INPUT";
    private final Map<String, String> _variables = new HashMap<>();

    private ContextVariables(ContextVariables src) {
        update(src);
    }

    private ContextVariables(String input) {
        _variables.put(MAIN_KEY, input == null ? "" : input);
    }

    public static ContextVariables of() {
        return new ContextVariables("");
    }

    public static ContextVariables of(String input) {
        return new ContextVariables(input);
    }


    public String getInput() {
        return this._variables.get(MAIN_KEY);
    }

    public void setInput(String input) {
        _variables.put(MAIN_KEY, input == null ? "" : input);
    }

    public void replace(ContextVariables newData) {
        if (newData == this)
            return;

        _variables.clear();
        _variables.putAll(newData._variables);
    }


    public void update(ContextVariables newData) {
        if (newData == this)
            return;
        _variables.putAll(newData._variables);
    }

    public void set(String name, String value) {
        Verify.notNullOrWhiteSpace(name);
        if (value != null) {
            _variables.put(name, value);
        } else {
            _variables.remove(name);
        }
    }

    public boolean containsKey(String key) {
        return _variables.containsKey(key);
    }

    public String get(String name) {
        return _variables.getOrDefault(name, null);
    }

    public ContextVariables copy() {
        return new ContextVariables(this);
    }

    @Override
    public String toString() {
        return getInput();
    }

}
