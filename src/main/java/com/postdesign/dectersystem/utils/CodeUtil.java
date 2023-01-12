package com.postdesign.dectersystem.utils;

import java.util.Random;

public class CodeUtil {
    private Random random = new Random();
    private int gBound = 10;

    public CodeUtil(int gBound){
        this.gBound = gBound;
    }
    public CodeUtil(){

    }

    public String generateCode(){
        String code = "";
        for (int i = 0; i<=7; i++){
            int num = random.nextInt(0, gBound);
            code += Integer.toString(num);
        }
        return code;
    }
}
