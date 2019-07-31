package it.unibs;

import java.util.ArrayList;

public class LZCompress implements ICompress {

    private static final String NOT_IN_ALPHABET = "Simbolo non presente nell'alfabeto";
    private final ArrayList<String> alphabet;

    public LZCompress(ArrayList<String> alphabet){
        this.alphabet = alphabet;
    }

    @Override
    public ArrayList<String> compress(String input) throws Exception {
        ArrayList<String> codebook = new ArrayList<>(alphabet);
        ArrayList<String> output = new ArrayList<>();
        for(int i = 0; i<input.length(); i++) {
            if(!alphabet.contains(input.charAt(i))) throw new Exception(NOT_IN_ALPHABET);
            StringBuilder buffer = new StringBuilder(input.substring(i, i + 1)); //metto nel buffer il carattere in posizione i

            //trovo sequenza che sia nel codebook con buffer contenete sequenza dalla posizione a cui si e' interrotto al ciclo precedente
            //fino al carattere in posizione i
            while (i < input.length() - 1 && findInCodebook(codebook, buffer + input.substring(i + 1, i + 2)) != -1) {
                buffer.append(input.charAt(i + 1)); //sequenza trovata, allora aggiungo il carattere nel buffer per ottenerla in esso
                i++;
            }
            output.add(String.valueOf(findInCodebook(codebook, buffer.toString()))); //ciclo interrotto, metto in uscita l'indice della sequenza trovata nel codebook

            //aggiungo al codebook la sequenza del buffer concatenata con il carattere successivo se presente, cioe' se i e' al massimo penultimo
            if(i < input.length() - 1)
                codebook.add(buffer + input.substring(i+1, i+2));
        }
        return output;
    }

    @Override
    public String decompress(ArrayList<Integer> input) {
        ArrayList<String> codebook = new ArrayList<>(alphabet);
        StringBuilder output = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        int index;

        for (Integer integer : input) {
            index = integer;
            //caso in cui l'input non e' presente nel codebook
            if (codebook.size() <= integer && buffer.length() != 0)
                codebook.add(buffer.toString() + buffer.charAt(0));

            output.append(codebook.get(index));

            //se il codebook non contiene il simbolo buffer+attuale questo viene aggiunto
            if (!codebook.contains(buffer.toString() + codebook.get(index).charAt(0)))
                codebook.add(buffer.toString() + codebook.get(index).charAt(0));

            //il nuovo buffer diventa la conversione dell'input attuale
            buffer = new StringBuilder(codebook.get(index));
        }

        return output.toString();
    }

    private int findInCodebook(ArrayList<String> codebook, String s) {
        for(int i=0; i<codebook.size(); i++){
            if(codebook.get(i).equals(s)) return i;
        }
        return -1;
    }
}
