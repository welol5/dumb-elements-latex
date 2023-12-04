package com.dumbelements.beans.latex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.dumbelements.beans.latex.Tokenizer.Token;

public class Multiplication extends Function{
    private Function[] functions;

    static int halter = 0;

    public Multiplication(List<Map.Entry<Token,String>> tokens, int start, int end){
        List<Function> functions = new ArrayList<Function>();

        if(halter >= 20){
            return;
        }
        halter++;

        int[] groups = findTopLevelGroups(tokens, start, end);
        int groupsUsedIndex = 0;
        for(int i = start; i <= end; i++){
            if(groups.length > 0 && groupsUsedIndex < groups.length && i == groups[groupsUsedIndex]){
                functions.add(parse(tokens, i, groups[groupsUsedIndex+1]));
                i = groups[groupsUsedIndex+1];
                groupsUsedIndex += 2;
            } else {
                functions.add(parse(tokens,i,i));
            }
        }

        this.functions = functions.toArray(new Function[0]);
    }

    @Override
    public String toString() {
        return "Multiplication [functions=" + Arrays.toString(functions) + "]";
    }

    public Function[] getFunctions() {
        return functions;
    }

    public void setFunctions(Function[] functions) {
        this.functions = functions;
    }
}
