package com.dumbelements.beans.latex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public abstract class Function {

    public String className;

    // Testing functions
    // \frac{\left(\cos\left(2\pi x+2\pi t\right)+1\right)}{2}
    // \frac{\left(\cos\left(2\pi x-\frac{4\pi}{3}+2\pi t\right)+1\right)}{2}
    // \frac{\left(\cos\left(2\pi x-\frac{2\pi}{3}+2\pi t\right)+1\right)}{2}
    // x^{2\ }\left\{x>0\right\}
    // \left\{x>0\right\}\ x
    // y=\left\{\left(x+0.5\right)>\left\{y>1:x\right\}:x\right\}x
    // 2x\left\{y>.5:y\cos\left(x\right)\right\}\left\{x>0:x\right\}\ =\ \left\{x\ >\ 0:\frac{x}{y}+1\right\}

    public Function() {
        className = getClass().getName();
    }

    public static Function parse(List<Map.Entry<Token, String>> tokens) throws IllegalArgumentException {
        System.out.println("Start building tree: ");

        // result functions always need to be figured out first
        int[] functionGroups = findTopLevelGroups(tokens, 0, tokens.size()-1);
        int functionGroupIndex = 0;
        for (int i = 0; i <= tokens.size()-1; i++) {
            if(functionGroups.length > 0 && i == functionGroups[functionGroupIndex]){
                i = functionGroups[functionGroupIndex+1];
                functionGroupIndex += 2;
            } else {
                if(tokens.get(i).getKey() == Token.EQUALITY){
                    return new ResultFunction(tokens, i, tokens.size()-1);
                }
            }
        }
        return parse(tokens, 0, tokens.size() - 1);
    }

    protected static Function parse(List<Map.Entry<Token, String>> tokens, int start, int end)
            throws IllegalArgumentException {
        printTokens(tokens, start, end);

        // one token function
        if (start == end) {
            if (tokens.get(start).getKey() == Token.NUMBER) {
                return new Primitive(tokens.get(start));
            } else if (tokens.get(start).getKey() == Token.VARIABLE) {
                return new Variable(tokens.get(start));
            } else if (tokens.get(start).getKey() == Token.PI) {
                return new Primitive(tokens.get(start));
            }
        }

        int[] asIndicies = findAdditionsAndSubtractions(tokens, start, end);
        int[] topGroup = findTopLevelGroups(tokens, start, end);
        int ineqality = findLTGTEQ(tokens, start, end);

        if(ineqality > -1){
            return new Condition(tokens, start, end);
        }

        if (asIndicies.length > 0) {
            return new AdditionSubtraction(tokens, start, end, asIndicies);
        }

        if (tokens.get(start).getKey() == Token.FRAC) {
            return new Fraction(tokens, start, end);
        }

        if (topGroup.length > 0 && topGroup[0] == start && topGroup[1] == end) {
            return new GroupedFunction(tokens, start, end);
        }

        if (topGroup.length > 0 && topGroup[0] == start + 1 && topGroup[1] == end
                && Tokenizer.trigFunctions.contains(tokens.get(start).getKey())) {
            return new TrigFunction(tokens, start, end);
        }

        return new Multiplication(tokens, start, end);
    }

    protected static int[] findAdditionsAndSubtractions(List<Map.Entry<Token, String>> tokens, int start, int end) {
        int[] groups = findTopLevelGroups(tokens, start, end);
        int groupsUsedIndex = 0;

        ArrayList<Integer> additionSubtractionIndicies = new ArrayList<Integer>();
        for (int i = start; i < end; i++) {

            // TODO: This group is a it dumb
            // skip groups
            if (groups.length > 0) {
                for (int k = groupsUsedIndex; k < groups.length - 1; k += 2) {
                    if (i == groups[k]) {
                        i = groups[k + 1];
                        groupsUsedIndex += 2;
                    }
                }
            }

            if (tokens.get(i).getKey() == Token.PLUS || tokens.get(i).getKey() == Token.MINUS) {
                additionSubtractionIndicies.add(i);
            }
        }

        return additionSubtractionIndicies.stream().mapToInt(i -> i).toArray();
    }

    protected static int[] findTopLevelGroups(List<Map.Entry<Token, String>> tokens, int start, int end) {
        ArrayList<Integer> groups = new ArrayList<Integer>();
        int level = 0;
        Token groupStart = null;
        Token groupEnd = null;
        for (int i = start; i <= end; i++) {
            Token currentToken = tokens.get(i).getKey();
            if (level == 0 && Tokenizer.groupPairs.keySet().contains(currentToken)) {
                level++;
                groupStart = currentToken;
                groupEnd = Tokenizer.groupPairs.get(groupStart);
                groups.add(i);
            } else if (level != 0 && currentToken == groupStart) {
                level++;
            } else if (level != 0 && currentToken == groupEnd) {
                level--;
                if (level == 0) {
                    groupStart = null;
                    groupEnd = null;
                    groups.add(i);
                }
            }
        }

        if (level > 1) {
            System.out.println("Mismatched tokens group");
            printTokens(tokens, start, end);
            throw new IllegalArgumentException("mismatched groups");
        }

        return groups.stream().mapToInt(i -> i).toArray();
    }

    protected static int findLTGTEQ(List<Map.Entry<Token, String>> tokens, int start, int end){
        int[] functionGroups = findTopLevelGroups(tokens, 0, tokens.size()-1);
        int functionGroupIndex = 0;

        for (int i = 0; i <= tokens.size()-1; i++) {
            if(functionGroups.length > 0 && functionGroupIndex < functionGroups.length && i == functionGroups[functionGroupIndex]){
                i = functionGroups[functionGroupIndex+1];
                functionGroupIndex += 2;
            } else {
                if(tokens.get(i).getKey() == Token.LT || tokens.get(i).getKey() == Token.GT || tokens.get(i).getKey() == Token.LTEQUAL || tokens.get(i).getKey() == Token.GTEQUAL ){
                    return i;
                }
            }
        }
        return -1;
    }

    protected static void printTokens(List<Map.Entry<Token, String>> tokens, int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.print(tokens.get(i).getKey() + " : " + tokens.get(i).getValue() + ", ");
        }
        System.out.println("\nStart: " + start + ", End: " + end);
    }
}
