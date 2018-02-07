package com.tal.pseudo_share.data;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tal on 2/7/2018.
 */

public class  Colors {

    public static int getColorByString(String colorName){
     if(colorName.equals("Red"))
         return Color.RED;
     else if(colorName.equals("Green"))
         return Color.GREEN;
     else if(colorName.equals("White"))
         return Color.WHITE;
     else if(colorName.equals("Black"))
         return Color.BLACK;
     else if(colorName.equals("Blue"))
         return Color.BLUE;
     else if(colorName.equals("Gray"))
         return Color.GRAY;
    else throw new RuntimeException("Undefined color");
    }

    public static String[] generateColorArr(){
        return new String[]{
        "Red","Green","White","Black","Blue","Gray"
         };
    }



}
