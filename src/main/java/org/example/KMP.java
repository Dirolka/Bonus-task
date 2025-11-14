package org.example;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class KMP {
    private static int[] buildLps(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    private static List<Integer> searchAll(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        if (pattern == null || pattern.length() == 0) {
            for (int i = 0; i <= text.length(); i++) {
                result.add(i);
            }
            return result;
        }
        if (text == null || text.length() == 0 || pattern.length() > text.length()) {
            return result;
        }
        String textLower = text.toLowerCase();
        String patternLower = pattern.toLowerCase();
        int[] lps = buildLps(patternLower);
        int n = textLower.length();
        int m = patternLower.length();
        int i = 0;
        int j = 0;
        while (i < n) {
            if (textLower.charAt(i) == patternLower.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    result.add(i - j);
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    private static String positionsToString(List<Integer> pos) {
        if (pos.isEmpty()) {
            return "No occurrences";
        }
        StringBuilder sb = new StringBuilder();
        for (int p : pos) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(p);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileWriter("src/main/java/org/example/output.txt"))) {
            String text1 = "uhozhusdfauuhozhus";
            String pattern1 = "uhozhu";
            List<Integer> res1 = searchAll(text1, pattern1);
            out.println("Test 1");
            out.println("Text:    " + text1);
            out.println("Pattern: " + pattern1);
            out.println("Positions: " + positionsToString(res1));
            out.println();

            String text2 = "gffggggfgfggggfggg";
            String pattern2 = "fggg";
            List<Integer> res2 = searchAll(text2, pattern2);
            out.println("Test 2");
            out.println("Text:    " + text2);
            out.println("Pattern: " + pattern2);
            out.println("Positions: " + positionsToString(res2));
            out.println();

            StringBuilder sb = new StringBuilder();
            for (int rep = 0; rep < 2000; rep++) {
                sb.append("fggg");
            }
            String text3 = sb.toString();
            String pattern3 = "cab";
            List<Integer> res3 = searchAll(text3, pattern3);
            out.println("Test 3");
            out.println("Text length: " + text3.length());
            out.println("Pattern: " + pattern3);
            out.println("Number of occurrences: " + res3.size());
            for (int k = 0; k < Math.min(5, res3.size()); k++) {
                out.print(res3.get(k));
                if (k < Math.min(5, res3.size()) - 1) {
                    out.print(", ");
                }
            }
            if (res3.size() > 10) {
                out.print(", ..., ");
            }
            int start = Math.max(5, res3.size() - 5);
            for (int k = start; k < res3.size(); k++) {
                if (k > start) {
                    out.print(", ");
                }
                out.print(res3.get(k));
            }
            out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
