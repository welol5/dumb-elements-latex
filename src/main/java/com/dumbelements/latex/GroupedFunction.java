package com.dumbelements.beans.latex;

import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class GroupedFunction extends Function{
    private Function function;

    public GroupedFunction(List<Map.Entry<Token, String>> tokens, int start, int end){
        System.out.println("Grouped function: ");
        function = parse(tokens, start+1, end-1);
    }

    @Override
    public String toString() {
        return "GroupedFunction [function=" + function + "]";
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
