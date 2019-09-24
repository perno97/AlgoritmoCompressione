package it.unibs;

import java.util.ArrayList;

public class LZCompress implements ICompress {

    private static final String NOT_IN_ALPHABET = "Simbolo non presente nell'alfabeto";
    private static final String INDEX_NOT_IN_CODEBOOK = "Simbolo non presente nel codebook";

    private final ArrayList<String> alphabet;
    private ArrayList<ProgressListener> progressListeners;

    public LZCompress(ArrayList<String> alphabet){
        this.alphabet = alphabet;
        progressListeners = new ArrayList<>();
    }

    private int findInCodebook(ArrayList<String> codebook, String s) {
        for(int i=0; i<codebook.size(); i++){
            if(codebook.get(i).equals(s)) return i;
        }
        return -1;
    }

    private void fireProgressUpdate(int i) {
        for(ProgressListener p:progressListeners)
            p.onProgressChanged(i);
    }

    public void addProgressListener(ProgressListener listener){
        this.progressListeners.add(listener);
    }

    @Override
    public ArrayList<ArrayList<String>> compress(String input) throws Exception {
        ArrayList<String> codebook = new ArrayList<>(alphabet);
        ArrayList<String> output = new ArrayList<>();
        int progress = 0,i;
        for(i = 0; i<input.length(); i++) {
            progress += ((float) i/ (float) input.length())*100;
            fireProgressUpdate(progress);
            if(!alphabet.contains(String.valueOf(input.charAt(i)))) throw new Exception(NOT_IN_ALPHABET); //caso in cui l'input non sia nell'alfabeto
            StringBuilder buffer = new StringBuilder(input.substring(i, i + 1)); //metto nel buffer il carattere in posizione i perche' so che e' sicuramente nel codebook (e' nell'alfabeto)

            //cerco sequenza piu' lunga che sia nel codebook partendo
            while (i < input.length() - 1 && findInCodebook(codebook, buffer + input.substring(i + 1, i + 2)) != -1) {//mi devo fermare al penultimo carattere senno' usando i+1 vado fuori dall'array
                buffer.append(input.charAt(i + 1)); //sequenza trovata, allora aggiungo il carattere nel buffer per ottenerla in esso
                i++;//provo a concatenare il carattere successivo e a vedere se la nuova sequenza e' nel codebook
            }
            output.add(String.valueOf(findInCodebook(codebook, buffer.toString()))); //ciclo interrotto, metto in uscita l'indice della sequenza piu' lunga trovata nel codebook

            //aggiungo al codebook la sequenza del buffer concatenata con il carattere successivo se presente, cioe' se i e' al massimo penultimo
            if(i < input.length() - 1)
                codebook.add(buffer + input.substring(i+1, i+2));
        }
        progress += ((float) i/ (float) input.length())*100;
        fireProgressUpdate(progress);
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        toReturn.add(output);
        toReturn.add(codebook);
        return toReturn;
    }

    @Override
    public ArrayList<ArrayList<String>> decompress(ArrayList<Integer> input) throws Exception {
        ArrayList<String> codebook = new ArrayList<>(alphabet);
        StringBuilder output = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        int progress = 0,i,index;

        for (i=0;i<input.size();i++) {
            index = input.get(i);
            progress += ((float) i/ (float) input.size())*100;
            fireProgressUpdate(progress);
            //caso in cui l'input non e' presente nel codebook
            if (codebook.size() <= index && buffer.length() != 0)
                codebook.add(buffer.toString() + buffer.charAt(0));//il simbolo corrispondente all'indice mancante e' per forza formato dal buffer attuale concatenato al primo simbolo del buffer attuale

            if(codebook.size() <= index) throw new Exception(INDEX_NOT_IN_CODEBOOK);//se il simbolo non e' presente nel codebook e' stato aggiunto nell'if sopracitato

            output.append(codebook.get(index));//metto in uscita la conversione dell'input attuale

            //se il codebook non contiene il simbolo buffer + inputattuale questo viene aggiunto
            if (!codebook.contains(buffer.toString() + codebook.get(index).charAt(0)))
                codebook.add(buffer.toString() + codebook.get(index).charAt(0));

            //il nuovo buffer diventa la conversione dell'input attuale
            buffer = new StringBuilder(codebook.get(index));
        }
        progress += ((float) i/ (float) input.size())*100;
        fireProgressUpdate(progress);
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        //Scompongo la stringa in output in un arraylist di stringhe per compatibilita' con l'interface
        toReturn.add(convertStringToArrayList(output.toString()));
        toReturn.add(codebook);
        return toReturn;
    }

    private ArrayList<String> convertStringToArrayList(String string){
        ArrayList<String> outputArray = new ArrayList<>();
        char[] charOutput = string.toCharArray();
        for (char c : charOutput) outputArray.add(String.valueOf(c));
        return outputArray;
    }
}
