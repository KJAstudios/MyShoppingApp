package com.example.myshoppingapp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchQueryProcessor {

    public static List<String> processQuery(String inQuery) {
        List<String> returnList = new ArrayList<>();
        inQuery = inQuery.trim();
        String[] quoteSplit = inQuery.split("\"");
        if (quoteSplit[0].equals("")) {
            if(quoteSplit.length == 1){
                return null;
            }
            if (quoteSplit.length == 2) {
                return Arrays.asList(quoteSplit[1].trim());
            } else {
                String[] backSplit = quoteSplit[2].trim().split(" ");
                for (String word : backSplit) {
                    returnList.add(word);
                }
                returnList.add(quoteSplit[1].trim());
                return returnList;
            }
        }
        else if(quoteSplit.length == 2){
            String[] frontSplit = quoteSplit[0].trim().split(" ");
            for(String word: frontSplit){
                returnList.add(word);
            }
            returnList.add(quoteSplit[1].trim());
            return returnList;
        }
        else if (quoteSplit.length == 3) {
            String[] frontSplit = quoteSplit[0].trim().split(" ");
            String[] backSplit = quoteSplit[2].trim().split(" ");
            for (String word : frontSplit) {
                returnList.add(word);
            }
            for (String word : backSplit) {
                returnList.add(word);
            }
            returnList.add(quoteSplit[1].trim());
            return returnList;
        }
        else if (quoteSplit.length == 1 && !quoteSplit[0].equals("")){
            return Arrays.asList(quoteSplit[0].trim().split(" "));
        }
        return null;
    }


}
