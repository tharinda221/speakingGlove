package com.speakingglove.speakinggolve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menaka on 10/14/16.
 */
public class Constants {
    public static String[] words = {"hello", "I'm hungry", "I'm fine", "help me"};

    public static Map<String, String> wordImgMap = new HashMap<>();
    static {
        wordImgMap.put("hello", "hi");
        wordImgMap.put("I'm hungry", "hungry");
        wordImgMap.put("I'm fine", "fine");
        wordImgMap.put("help me", "help");
    }



}
