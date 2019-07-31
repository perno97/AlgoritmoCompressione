package it.unibs;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    private static final int[] expectedInt = {1,2,0,1,5,3,8,0,10,7,2};
    private static final String expectedString = "bcababbcbcbaaaabbc";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Controller());
    }






    /*public static void main(String[] args) {
        LZCompress compress = createLZCompress();
        ArrayList<String> result1 = compress.compress(expectedString);

        System.out.println("Result1:");
        for(String s:result1) System.out.print(s + " ");

        ArrayList<Integer> input = new ArrayList<>();
        for (int value : expectedInt)
            input.add(value);

        String result2 = compress.decompress(input);

        System.out.println("\nResult2:\n" + result2);
    }

    private static LZCompress createLZCompress() {
        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");

        return new LZCompress(alphabet);
    }*/
}
