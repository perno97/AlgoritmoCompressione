package it.unibs.compress;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LZCompressTest {

    private static final int[] expectedInt = {1,2,0,1,5,3,8,0,10,7,2};
    private static final String expectedString = "bcababbcbcbaaaabbc";
    private static final int[] expectedInt1 = {0,0,1,0,2,1,2,7,5,2};
    private static final String expectedString1 = "aabacbccbbac";

    @Test
    void compress() {
        LZCompress compress = createLZCompress();
        ArrayList<ArrayList<String>> result = null;
        try {
            result = compress.compress(expectedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isCorrect = true;
        ArrayList<String> outputResult = result.get(0);
        for(int i = 0; i< expectedInt.length; i++){
            if(expectedInt[i] != Integer.parseInt(outputResult.get(i))) isCorrect = false;
        }

        assertTrue(isCorrect);
    }

    @Test
    void decompress() {
        LZCompress compress = createLZCompress();
        ArrayList<Integer> input = new ArrayList<>();
        for (int value : expectedInt)
            input.add(value);

        ArrayList<ArrayList<String>> result = null;
        try {
            result = compress.decompress(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder resultString = new StringBuilder();
        for (String s: result.get(0))
            resultString.append(s);
        assertEquals(expectedString, resultString.toString());
    }

    @Test
    void compress1() {
        LZCompress compress = createLZCompress();
        ArrayList<ArrayList<String>> result = null;
        try {
            result = compress.compress(expectedString1);
        } catch (Exception e){
            e.printStackTrace();
        }
        boolean isCorrect = true;
        ArrayList<String> outputResult = result.get(0);
        for(int i=0; i<expectedInt1.length; i++) {
            if(expectedInt1[i] != Integer.parseInt(outputResult.get(i))) isCorrect = false;
        }

        assertTrue(isCorrect);
    }

    @Test
    void decompress1() {
        LZCompress compress = createLZCompress();
        ArrayList<Integer> input = new ArrayList<>();
        for (int value : expectedInt1)
            input.add(value);

        ArrayList<ArrayList<String>> result = null;
        try {
            result = compress.decompress(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder resultString = new StringBuilder();
        for (String s: result.get(0))
            resultString.append(s);
        assertEquals(expectedString1, resultString.toString());
    }

    private static LZCompress createLZCompress() {
        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");

        return new LZCompress(alphabet);
    }
}