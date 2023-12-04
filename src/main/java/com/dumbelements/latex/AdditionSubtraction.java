package com.dumbelements.beans.latex;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class AdditionSubtraction extends Function{

    private Function[] functions;
    private Token[] operators;

    public AdditionSubtraction(List<Map.Entry<Token,String>> tokens, int start, int end){
        this(tokens, start, end, findAdditionsAndSubtractions(tokens, start, end));
    }

    public AdditionSubtraction(List<Map.Entry<Token,String>> tokens, int start, int end, int[] asIndicies){

        operators = new Token[asIndicies.length];
        for(int i = 0; i < asIndicies.length; i++){
            operators[i] = tokens.get(asIndicies[i]).getKey();
        }

        functions = new Function[asIndicies.length+1];
        for(int i = 0; i < asIndicies.length; i++){
            System.out.println("Addition/Subtraction: ");
            if(i == 0){
                functions[i] = parse(tokens, start, asIndicies[i]-1);
            } else {
                functions[i] =  parse(tokens, asIndicies[i-1]+1, asIndicies[i]-1);
            }
        }
        System.out.println("Last function");
        functions[functions.length-1] = parse(tokens, asIndicies[asIndicies.length-1]+1, end);
    }

    @Override
    public String toString() {
        return "AdditionSubtraction [functions=" + Arrays.toString(functions) + ", operators="
                + Arrays.toString(operators) + "]";
    }

    public Function[] getFunctions() {
        return functions;
    }

    public void setFunctions(Function[] functions) {
        this.functions = functions;
    }

    public Token[] getOperators() {
        return operators;
    }

    public void setOperators(Token[] operators) {
        this.operators = operators;
    }
}
