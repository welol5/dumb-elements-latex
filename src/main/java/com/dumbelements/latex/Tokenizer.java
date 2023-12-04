package com.dumbelements.beans.latex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.spi.DirStateFactory.Result;


public class Tokenizer {

    public static final Map<Token, String> tokenMap;
    private static final Map<Token, Pattern> patterns;
    public static final Map<Token,Token> groupPairs;
    public static final List<Token> trigFunctions;

    public enum Token {
        //Basics
        NUMBER,
        VARIABLE,
        RESULT,
        EQUALITY,
        LT,
        GT,
        LTEQUAL,
        GTEQUAL,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        RPARAN,
        LPARAN,
        RCBRACKET,
        LCBRACKET,
        RBRACKET,
        LBRACKET,

        //functions
        SINE,
        COSINE,
        TANGENT,

        //Latex formatting
        SPACE,
        COLON,
        COMMA,
        FRAC,
        LEFT,
        RIGHT,
        PI
    }

    static {
        tokenMap = new HashMap<Token, String>();
        
        tokenMap.put(Token.NUMBER, "([0-9]+\\.[0-9]+|[0-9]+|\\.[0-9])");
        tokenMap.put(Token.VARIABLE, "[xt]");
        tokenMap.put(Token.RESULT, "y|(f\\(x\\))");
        tokenMap.put(Token.EQUALITY, "=");
        tokenMap.put(Token.LT, "<");
        tokenMap.put(Token.GT, ">");
        tokenMap.put(Token.LTEQUAL, "<=");
        tokenMap.put(Token.GTEQUAL, ">=");
        tokenMap.put(Token.PLUS, "[+]");
        tokenMap.put(Token.MINUS, "[-]");
        tokenMap.put(Token.MULTIPLY, "[*]");
        tokenMap.put(Token.DIVIDE, "[/]");
        tokenMap.put(Token.RPARAN, "(\\)|\\\\\\))");
        tokenMap.put(Token.LPARAN, "(\\(|\\\\\\()");
        tokenMap.put(Token.RCBRACKET, "(\\}|\\\\\\})");
        tokenMap.put(Token.LCBRACKET, "(\\{|\\\\\\{)");
        tokenMap.put(Token.RBRACKET, "(\\]|\\\\\\])");
        tokenMap.put(Token.LBRACKET, "(\\[|\\\\\\[)");
        
        tokenMap.put(Token.SINE, "\\\\sin");
        tokenMap.put(Token.COSINE, "\\\\cos");
        tokenMap.put(Token.TANGENT, "\\\\tan");

        tokenMap.put(Token.SPACE, "( |\\\\ )");
        tokenMap.put(Token.COLON, ":");
        tokenMap.put(Token.COMMA, ",");
        tokenMap.put(Token.FRAC, "\\\\frac");
        tokenMap.put(Token.LEFT, "\\\\left");
        tokenMap.put(Token.RIGHT, "\\\\right");
        tokenMap.put(Token.PI, "\\\\pi");

        patterns = new HashMap<Token, Pattern>();
        for(Map.Entry<Token, String> e : tokenMap.entrySet()){
            Pattern p = Pattern.compile(e.getValue());
            patterns.put(e.getKey(), p);
        }

        groupPairs = new HashMap<Token,Token>();
        groupPairs.put(Token.LBRACKET, Token.RBRACKET);
        groupPairs.put(Token.RBRACKET, Token.LBRACKET);
        groupPairs.put(Token.LCBRACKET, Token.RCBRACKET);
        groupPairs.put(Token.RCBRACKET, Token.LCBRACKET);
        groupPairs.put(Token.LPARAN, Token.RPARAN);
        groupPairs.put(Token.RPARAN, Token.LPARAN);

        trigFunctions = new ArrayList<Token>();
        trigFunctions.add(Token.SINE);
        trigFunctions.add(Token.COSINE);
        trigFunctions.add(Token.TANGENT);
    }
    
    public static List<Map.Entry<Token, String>> tokenize(String function) throws IllegalArgumentException{

        //create matchers
        Map<Token, Matcher> matchers = new HashMap<Token, Matcher>();
        for(Map.Entry<Token, Pattern> e : patterns.entrySet()){
            matchers.put(e.getKey(), e.getValue().matcher(function));
        }

        List<Map.Entry<Token, String>> tokenizedFunction = new ArrayList<Map.Entry<Token, String>>();
        int index = 0;
        while(index < function.length()){

            boolean validCharacter = false;;
            for(Map.Entry<Token, Matcher> e : matchers.entrySet()){
                if(e.getValue().find(index) && e.getValue().start() == index){
                    validCharacter = true;
                    index = e.getValue().end();
                    Map.Entry<Token, String> tokenized = Map.entry(e.getKey(), function.substring(e.getValue().start(), e.getValue().end()));
                    //ignore formatting tokens
                    if(!(e.getKey() == Token.SPACE || e.getKey() == Token.LEFT || e.getKey() == Token.RIGHT)){
                        tokenizedFunction.add(tokenized);
                    }
                    break;
                }
            }

            if(!validCharacter){
                System.out.println(function.substring(index));
                throw new IllegalArgumentException("unknown token at index: " + index + ". Character found: " + function.charAt(index));
            }
        }

        return tokenizedFunction;
    }
    
    
}
