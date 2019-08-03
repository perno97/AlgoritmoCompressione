package it.unibs;

import java.util.ArrayList;

public class Controller implements Runnable, ProgressListener{
    public static final boolean ENCODE = true;
    public static final boolean DECODE = false;

    private static final String NOT_INTEGER_ERROR = "Uno o piu' numeri inseriti non sono interi";

    private LZCompress compressLZ;

    private MainForm form;

    public Controller(){
        createLZCompress();
    }

    private void encode(String text) {
        ArrayList<String> toShow = null;
        try {
            toShow = compressLZ.compress(text);
        } catch (Exception e) {
            form.showString(e.getMessage());
        }
        if(toShow != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < toShow.size() - 1; i++)
                builder.append(toShow.get(i)).append(",");
            builder.append(toShow.get(toShow.size() - 1));
            form.showString(builder.toString());
        }
    }

    private void decode(String text) {
        ArrayList<Integer> input = new ArrayList<>();
        String[] splitted = text.split(",");
        boolean failed = false;
        try {
            for (String s : splitted)
                input.add(Integer.parseInt(s));
        }
        catch (NumberFormatException e){
            form.showString(NOT_INTEGER_ERROR);
            failed = true;
        }
        if(!failed) form.showString(compressLZ.decompress(input));
    }

    private void createLZCompress() {
        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");

        compressLZ = new LZCompress(alphabet);
        compressLZ.addProgressListener(this);
    }

    public void execute(String text, boolean command) {
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
