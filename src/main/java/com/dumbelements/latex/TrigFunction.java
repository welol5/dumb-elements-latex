package com.dumbelements.beans.latex;

import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class TrigFunction extends Function{

    private Map.Entry<Token, String> trig;
    private Function function;

    public TrigFunction(List<Map.Entry<Token,String>> tokens, int start, int end){
        trig = tokens.get(start);
        function = parse(tokens, start+1, end);
    }

    @Override
    public String toString() {
        return "TrigFunction [trig=" + trig + ", function=" + function + "]";
    }

    public Map.Entry<Token, String> getTrig() {
        return trig;
    }

    public void setTrig(Map.Entry<Token, String> trig) {
        this.trig = trig;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
