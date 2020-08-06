package org.example;

import java.util.stream.IntStream;

public class Puzzle {

    static int numCombinationsFound;
    static int a, b, c, de, fg, mn;
    static String s;

    public static void solve() {
        //reduce the problem by first narrowing the results for the first 5 digits
        IntStream.rangeClosed(1234, 98765).forEach(n -> {
            s = String.valueOf(n);
            if (s.length() == 4) s = "0" + s;
            a = Integer.parseInt(s.substring(0, 1));
            b = Integer.parseInt(s.substring(1, 2));
            c = Integer.parseInt(s.substring(2, 3));
            de = Integer.parseInt(s.substring(3, 5));

            //for each first part that passes tentatively goto solve second part(last 4 digits)
            if (((a + b) * c == de) && isDigitsUnique(s)) {
                IntStream.rangeClosed(123, 9876).forEach(e -> {
                    String s2 = String.valueOf(e);
                    if (s2.length() == 3) s2 = "0" + s2;
                    s2 = s + s2;

                    fg = Integer.parseInt(s2.substring(5, 7));
                    mn = Integer.parseInt(s2.substring(7, 9));

                    if ((de + fg == mn) && isDigitsUnique(s2)) {
                        numCombinationsFound++;
                        System.out.println("abcdefgmn = " + s2);
                    }
                });
            }
        });
    }

    public static boolean isDigitsUnique(String word) {
        for (char c : word.toCharArray()) {
            if(word.indexOf(c)!=word.lastIndexOf(c))
                return false;
        }
        return true;
    }

    public static void main(String... args) {
        Long start = System.currentTimeMillis();
        solve();
        System.out.println("Number of combinations found = " + numCombinationsFound);
        System.out.println("Time taken: " + (System.currentTimeMillis() - start));
    }
}
