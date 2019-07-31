package it.unibs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LZCompressTest {

    private static final int[] expectedInt = {1,2,0,1,5,3,8,0,10,7,2};
    private static final String expectedString = "bcababbcbcbaaaabbc";

    @Test
    void compress() {

        LZCompress compress = createLZCompress();
        ArrayList<String> result = null;
        try {
            result = compress.compress(expectedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isCorrect = true;
        for(int i = 0; i< expectedInt.length; i++){
            if(expectedInt[i] != Integer.parseInt(result.get(i))) isCorrect = false;
        }

        assertTrue(isCorrect);
    }

    @Test
    void decompress() {
        LZCompress compress = createLZCompress();
        ArrayList<Integer> input = new ArrayList<>();
        for (int value : expectedInt)
            input.add(value);

        String result = compress.decompress(input);
        assertEquals(expectedString, result);
    }

    private static LZCompress createLZCompress() {
        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");

        return new LZCompress(alphabet);
    }
}