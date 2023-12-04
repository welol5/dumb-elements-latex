package com.dumbelements.beans.latex;

import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class Condition extends Function{
    private Function left;
    private Function right;
    private Map.Entry<Token, String> equality;

    public Condition(List<Map.Entry<Token,String>> tokens, int start, int end){
        this(tokens,start,end, findLTGTEQ(tokens, start, end));
    }

    public Condition(List<Map.Entry<Token,String>> tokens, int start, int end, int ineqalityIndex){
        left = parse(tokens, start, ineqalityIndex-1);
        right = parse(tokens, ineqalityIndex+1, end);
    }

    public Function getLeft() {
        return left;
    }

    public void setLeft(Function left) {
        this.left = left;
    }

    public Function getRight() {
        return right;
    }

    public void setRight(Function right) {
        this.right = right;
    }

    public Map.Entry<Token, String> getEquality() {
        return equality;
    }

    public void setEquality(Map.Entry<Token, String> equality) {
        this.equality = equality;
    }

    @Override
    public String toString() {
        return "Condition [left=" + left + ", right=" + right + ", equality=" + equality + "]";
    }
}
