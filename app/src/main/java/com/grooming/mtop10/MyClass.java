package com.grooming.mtop10;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyClass {
    private static final int A = 100;
    public static final String B = "dlkfsfsd.33343m,3df";
    private int a;
    private float b;
    public MyClass(int a, float b){
        this.a = a;
        this.b = b;
    }
    public String someMethod(String s){

        String tmp = a + s + b;
        try {
            return new String(MessageDigest.getInstance("SHA-256").digest(tmp.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String someMethod2(String s){
        String a = "eyuioa";
        for (char c: a.toCharArray()){
            s = s.replace(""+c,""+(int)c);
        }
        return s;
    }
    public float someMethod3(int c){
        if(c > 0) {
            return a + b + c * 2;
        } else {
            return a + b - c * 2;
        }
    }
    public void iA(){
        a++;
    }

}
