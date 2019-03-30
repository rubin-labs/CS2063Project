package com.rubinlabs.morsecode;

import java.util.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by smatt on 11/05/2017.
 */
public class MorseCodeTranslator {
    // static variable single_instance of type Singleton
    private static MorseCodeTranslator instance = null;

    private HashMap<String, String> englishToMorseLib = new HashMap<>(), morseToEnglishLib = new HashMap<>();

    {
        //fill up englishToMorseLib
        englishToMorseLib.put("A", ".-");
        englishToMorseLib.put("B", "-...");
        englishToMorseLib.put("C", "-.-.");
        englishToMorseLib.put("D", "-..");
        englishToMorseLib.put("E", ".");
        englishToMorseLib.put("F", "..-.");
        englishToMorseLib.put("G", "--.");
        englishToMorseLib.put("H", "....");
        englishToMorseLib.put("I", "..");
        englishToMorseLib.put("J", ".---");
        englishToMorseLib.put("K", "-.-");
        englishToMorseLib.put("L", ".-..");
        englishToMorseLib.put("M", "--");
        englishToMorseLib.put("N", "-.");
        englishToMorseLib.put("O", "---");
        englishToMorseLib.put("P", ".--.");
        englishToMorseLib.put("Q", "--.-");
        englishToMorseLib.put("R", ".-.");
        englishToMorseLib.put("S", "...");
        englishToMorseLib.put("T", "-");
        englishToMorseLib.put("U", "..-");
        englishToMorseLib.put("V", "...-");
        englishToMorseLib.put("W", ".--");
        englishToMorseLib.put("X", "-..-");
        englishToMorseLib.put("Y", "-.--");
        englishToMorseLib.put("Z", "--..");

        englishToMorseLib.put("0", "-----");
        englishToMorseLib.put("1", ".----");
        englishToMorseLib.put("2", "..---");
        englishToMorseLib.put("3", "...--");
        englishToMorseLib.put("4", "....-");
        englishToMorseLib.put("5", ".....");
        englishToMorseLib.put("6", "-....");
        englishToMorseLib.put("7", "--...");
        englishToMorseLib.put("8", "---..");
        englishToMorseLib.put("9", "----.");

        englishToMorseLib.put(".", ".-.-.-");
        englishToMorseLib.put(",", "--..--");
        englishToMorseLib.put("?", "..--..");
        englishToMorseLib.put(":", "---...");
        englishToMorseLib.put("-", "-....-");
        englishToMorseLib.put("@", ".--.-.");
        englishToMorseLib.put("error", "........");

        //fill the morseToEnglishLib
        List<Object> values = Arrays.asList(englishToMorseLib.values().toArray());
        List<Object> keys = Arrays.asList(englishToMorseLib.keySet().toArray());
        for(int i = 0; i < values.size(); i++) {
            morseToEnglishLib.put(values.get(i).toString(), keys.get(i).toString());
        }


    }

    private String info = "Morse Translator by Seun Matt (@SeunMatt2) \n" +
            "Note: \n" +
            "Morse code words are separated by / \n" +
            "Morse code alphabets are separated by single whitespace \n" +
            "Press space bar for auto translation";

    private MorseCodeTranslator() {

    }

    public static MorseCodeTranslator getInstance() {
        if (instance == null)
            instance = new MorseCodeTranslator();

        return instance;
    }



    public String englishWordToMorseWord(String englishWord) {
        StringBuffer buffer = new StringBuffer();
        Stream.of(englishWord.split("[ \n]"))
                .forEach( s -> {
//                    System.out.println("s = " + s);
                    for(char c: s.toCharArray()) {
//                        String v = englishToMorseLib.containsKey(String.valueOf(c).toUpperCase()) ? englishToMorseLib.get(String.valueOf(c).toUpperCase()) : " [error] ";
//                        System.out.println(c + "  == " + v);
                        buffer.append(englishToMorseLib.containsKey(String.valueOf(c).toUpperCase()) ? englishToMorseLib.get(String.valueOf(c).toUpperCase()) + " " : "?? ");
                    }
                    buffer.append(" / ");
                });
        return buffer.toString();
    }


    public String morseWordToEnglishWord(String morseWord) {
        StringBuffer buffer = new StringBuffer();
        Stream.of(morseWord.split("[\\s\\n]"))
                .filter((s) -> s != null && !s.isEmpty())
                .forEach( s -> {
//                        System.out.println("s == " + s);
                    if(s.equalsIgnoreCase("/") || s.equalsIgnoreCase("|")) {
                        buffer.append(" ");
                    } else {
//                            String v = morseToEnglishLib.containsKey(s) ? morseToEnglishLib.get(s) : "?? ";
//                            System.out.println(s + " === " + v);
                        buffer.append((morseToEnglishLib.containsKey(s) ? morseToEnglishLib.get(s) : "?? ").toLowerCase());
                    }
                });
        return buffer.toString();
    }



    public static void main(String[] args) {
    }

}
