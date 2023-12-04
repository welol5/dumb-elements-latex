package com.dumbelements.beans.latex;

import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class Primitive extends Function{

    private Map.Entry<Token,String> value;

    public Primitive(Map.Entry<Token,String> value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "Primitive [value=" + value + "]";
    }

    public Map.Entry<Token, String> getValue() {
        return value;
    }

    public void setValue(Map.Entry<Token, String> value) {
        this.value = value;
    }
}
