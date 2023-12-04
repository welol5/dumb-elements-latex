package com.dumbelements.beans.latex;

import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class Variable extends Function{
    private Map.Entry<Token, String> value;

    public Variable(Map.Entry<Token, String> variable){
        value = variable;
    }

    @Override
    public String toString() {
        return "Variable [value=" + value + "]";
    }

    public Map.Entry<Token, String> getValue() {
        return value;
    }

    public void setValue(Map.Entry<Token, String> value) {
        this.value = value;
    }
}
