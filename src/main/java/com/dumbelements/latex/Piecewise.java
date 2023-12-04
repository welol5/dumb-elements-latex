package com.dumbelements.beans.latex;

import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class Piecewise  extends Function{
    private Function condition;
    private Function function;

    public Piecewise(List<Map.Entry<Token,String>> tokens, int start, int end){
        int[] functionGroups = findTopLevelGroups(tokens, 0, tokens.size()-1);
        int functionGroupIndex = 0;

        for (int i = 0; i <= tokens.size()-1; i++) {
            if(functionGroups.length > 0 && functionGroupIndex < functionGroups.length && i == functionGroups[functionGroupIndex]){
                i = functionGroups[functionGroupIndex+1];
                functionGroupIndex += 2;
            } else {
                if(tokens.get(i).getKey() == Token.COLON){
                    condition = parse(tokens, start, i-1);
                    function = parse(tokens, i+1, end);
                    return;
                }
            }
        }
    }

    public Function getCondition() {
        return condition;
    }

    public void setCondition(Function condition) {
        this.condition = condition;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "Piecewise [condition=" + condition + ", function=" + function + "]";
    }
}
