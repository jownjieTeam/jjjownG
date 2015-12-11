package com.example.jownjie.nihingo.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hebi5 on 12/11/2015.
 */
public class Random {
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static java.util.Random r;
    private static HashMap<Integer,Character> hashMapLetters;

    public Random(){
        hashMapLetters = new HashMap<>(26);
        r = new java.util.Random();
    }

    public void initLetters(){
    }

    public static String completeWord(String word, int max){
        String finalWord = word;
        for(int i = word.length(); i < max; i++){
            finalWord += (""+alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return finalWord;
    }

    public static String randomize(String word){
        String newWord = "";
        List<Integer> indexList = new ArrayList<>();
        for(int i = 0; i<word.length();){
            int randomIndex = r.nextInt(word.length());
            String letter = word.charAt(randomIndex)+"";
            if(!indexList.contains(randomIndex)) {
                indexList.add(randomIndex);
                newWord += letter;
                i++;
            }
        }
        return newWord;
    }

    public static int getRandomNumber(int upperLimit) {
        return r.nextInt(upperLimit);
    }

}
