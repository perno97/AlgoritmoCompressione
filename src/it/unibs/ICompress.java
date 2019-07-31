package it.unibs;

import java.util.ArrayList;

public interface ICompress {
    ArrayList<String> compress(String s) throws Exception;
    String decompress(ArrayList<Integer> s);
}
