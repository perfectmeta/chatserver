package com.perfectword.semantic_kernal.orchestration;

import com.perfectword.semantic_kernal.abstractions.Verify;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ContextVariables implements Cloneable, Iterable<Map.Entry<String, String>>{
    final String MAIN_KEY = "INPUT";
    private final Map<String, String> _variables = new ConcurrentHashMap<>();

    public ContextVariables(String content) {
        _variables.put(MAIN_KEY, content == null ? "" : content);
    }

    public String getInput() {
        return this._variables.get(MAIN_KEY);
    }

    public ContextVariables update(String content) {
        _variables.put(MAIN_KEY, content == null ? "" : content);
        return this;
    }


    public ContextVariables update(ContextVariables newData) {
        return update(newData, true);
    }

    public ContextVariables update(ContextVariables newData, boolean merge) {
        if (newData == this)
            return this;
        if (!merge)
            _variables.clear();
        newData.forEach(e-> _variables.put(e.getKey(), e.getValue()));
        return this;
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

    @Override
    public String toString() {
        return getInput();
    }

    @Override
    public ContextVariables clone() throws CloneNotSupportedException {
        ContextVariables cloned = (ContextVariables) super.clone();
        cloned.update(this);
        return cloned;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this._variables.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, String>> action) {
        this._variables.entrySet().forEach(action);
    }
}
