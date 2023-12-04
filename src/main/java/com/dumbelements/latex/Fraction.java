package com.dumbelements.beans.latex;

import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class Fraction extends Function{

    private Function top;
    private Function bottom;

    public Fraction(List<Map.Entry<Token, String>> tokens, int start, int end){
        int[] splitIndicies = findTopLevelGroups(tokens, start, end);

        System.out.println("Fraction top: ");
        top = parse(tokens, splitIndicies[0]+1, splitIndicies[1]);
        System.out.println("Fraction Bottom: ");
        bottom = parse(tokens, splitIndicies[2]+1, splitIndicies[3]);
    }

    @Override
    public String toString() {
        return "Fraction [top=" + top + ", bottom=" + bottom + "]";
    }

    public Function getTop() {
        return top;
    }

    public void setTop(Function top) {
        this.top = top;
    }

    public Function getBottom() {
        return bottom;
    }

    public void setBottom(Function bottom) {
        this.bottom = bottom;
    }
}
