package it.unibs;

import java.util.ArrayList;

public class Controller implements Runnable, ProgressListener{
    static final boolean ENCODE = true;
    static final boolean DECODE = false;
    private static final String NOT_INTEGER_ERROR = "Uno o piu' numeri inseriti non sono interi";

    private LZCompress compressLZ;
    private MainForm form;

    Controller(){
        createLZCompress();
    }

    private void encode(String text) {
        try {
            ArrayList<ArrayList<String>> toShow = compressLZ.compress(text);
            form.showResult(toShow.get(0), toShow.get(1));
        } catch (Exception e) {
            form.showString(e.getMessage());
        }
    }

    private void decode(String text) {
        ArrayList<Integer> input = new ArrayList<>();
        String[] splitted = text.split(",");
        try {
            for (String s : splitted)
                input.add(Integer.parseInt(s));
            try {
                ArrayList<ArrayList<String>> result = compressLZ.decompress(input);
                form.showResult(result.get(0), result.get(1));
            } catch (Exception e) {
                form.showString(e.getMessage());
            }
        }
        catch (NumberFormatException e){
            form.showString(NOT_INTEGER_ERROR);
        }
    }

    private void createLZCompress() {
        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");

        compressLZ = new LZCompress(alphabet);
        compressLZ.addProgressListener(this);
    }

    void execute(String text, boolean command) {
        if (command == ENCODE) {
            encode(text);
        } else {
            decode(text);
        }
    }

    @Override
    public void run() {
        form = new MainForm(this);
    }

    @Override
    public void onProgressChanged(int percent) {
        form.setProgress(percent);
    }
}
