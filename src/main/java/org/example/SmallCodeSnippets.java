package org.example;

public class SmallCodeSnippets {
    public static void generateArrayIndexOutOfBoundException() {
        int[] intArr = {2,5,9,10};
        int n = intArr[5];
    }
    public static void generateNullPointerException() {
        String s= null;
        s.trim();
    }
    public static String reverseString(String str) {
        int len = str.length();
        StringBuilder reverse = new StringBuilder(len);
        for (int i=len-1; i>=0; i--){
            reverse.append(str.charAt(i));
        }
        return reverse.toString();
    }
    public static String removeWhiteSpaces(String str) {
        return str.replaceAll("[ \\t\\r\\n]","");
    }

}
