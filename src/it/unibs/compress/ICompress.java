package it.unibs.compress;

import java.util.ArrayList;

public interface ICompress {
    ArrayList<ArrayList<String>> compress(String s) throws Exception;
    ArrayList<ArrayList<String>> decompress(ArrayList<Integer> s) throws Exception;
}
