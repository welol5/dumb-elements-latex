package com.dumbelements.beans.latex;

import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class ResultFunction extends Function{
    private Function resultFunction;
    private Function inputFunction;

    public ResultFunction(List<Map.Entry<Token,String>> tokens, int start, int end) throws IllegalArgumentException{
        int equalityIndex = -1;
        int resultIndex = -1;
        for(int i = start; i < end; i++){
            if(tokens.get(i).getKey() == Token.EQUALITY && equalityIndex == -1){
                equalityIndex = i;
            } else if(tokens.get(i).getKey() == Token.EQUALITY && equalityIndex != -1){
                throw new IllegalArgumentException("error.illegalCharacter.multipleEquality");
            }
            if(tokens.get(i).getKey() == Token.RESULT && resultIndex == -1){
                resultIndex = i;
            } else if(tokens.get(i).getKey() == Token.RESULT && resultIndex != -1){
                throw new IllegalArgumentException("error.illegalCharacter.multipleResult");
            }
        }

        if(resultIndex > equalityIndex){
            resultFunction = parse(tokens, equalityIndex+1, end);
            inputFunction = parse(tokens, start, equalityIndex-1);
        } else {
            resultFunction = parse(tokens, start, equalityIndex-1);
            inputFunction = parse(tokens, equalityIndex+1, end);
        }
    }

    public Function getResultFunction() {
        return resultFunction;
    }

    public void setResultFunction(Function resultFunction) {
        this.resultFunction = resultFunction;
    }

    public Function getInputFunction() {
        return inputFunction;
    }

    public void setInputFunction(Function inputFunction) {
        this.inputFunction = inputFunction;
    }

    @Override
    public String toString() {
        return "ResultFunction [resultFunction=" + resultFunction + ", inputFunction=" + inputFunction + "]";
    }
}
