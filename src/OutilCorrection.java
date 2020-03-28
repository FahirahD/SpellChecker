import java.util.*;

public class OutilCorrection {

    private ArrayList<String> motNonCorriges = new ArrayList<>();
    private Map<String ,Integer> directionaire = new HashMap<>();

    public OutilCorrection(ArrayList<String> mots , Map<String,Integer> dictionaire){

        this.motNonCorriges = mots;
        this.directionaire = dictionaire;

    }

    private  Set<String> remplacerChqCaractere(String mots){
        Set<String> correctionCandidat = new HashSet<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyzàèìòùáéíóúýâêîôûãñõäëïöüÿç-'".toCharArray();

        for (int i = 0; i < mots.length()-1; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                String new_mot = mots.substring(0, i) + alphabet[j] + mots.substring(i , mots.length() );
                //System.out.println(new_mot);
                correctionCandidat.add(new_mot);
            }
        }
        return correctionCandidat;
    }

    private Set<String> retirerChqCaractere(String mots){
        Set<String> correctionCandidat = new HashSet<>();

        for(int i = 0 ; i < mots.length()-1 ; i++){
            String new_mot = mots.substring(0,i) + mots.substring(i+1,mots.length());
            //System.out.println(new_mot);
            correctionCandidat.add(new_mot);
        }
        return correctionCandidat;
    }

    private Set<String>ajouterCaractere(String mots) {
        Set<String> correctionCandidat = new HashSet<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyzàèìòùáéíóúýâêîôûãñõäëïöüÿç-'".toCharArray();

        for (int i = 0; i < mots.length(); i++) {
            for (int j = 0; j < alphabet.length; j++) {
                String new_mot = mots.substring(0, i) + alphabet[j] + mots.substring(i , mots.length() );
                //System.out.println(new_mot);
                correctionCandidat.add(new_mot);
            }
        }
        return correctionCandidat;
    }


    public String corrigerMots(){

        String text = "";

        for(int i = 0 ; i < this.motNonCorriges.size(); i++){


            Set<String> correctionCandidat = new HashSet<>();
            ArrayList<String> suggestions = new ArrayList<>();

            if(directionaire.containsKey(motNonCorriges.get(i).toLowerCase())){
                text += motNonCorriges.get(i)+" ";
                continue;
            }

            correctionCandidat.addAll(ajouterCaractere(motNonCorriges.get(i)));
            correctionCandidat.addAll(retirerChqCaractere(motNonCorriges.get(i)));
            correctionCandidat.addAll(remplacerChqCaractere(motNonCorriges.get(i)));

            for ( String candidat : correctionCandidat) {
                if (directionaire.containsKey(candidat.toLowerCase())) {
                    suggestions.add(candidat);
                }
            }

            if (suggestions.isEmpty()){
                text +="["+ motNonCorriges.get(i)+" => (?)]";
            }

            else {
                String suggestiontext = "";
                for(int j = 0 ; j < suggestions.size(); j++) {
                    if (j == 0){
                        suggestiontext+="["+ motNonCorriges.get(i)+ " => ";
                        suggestiontext += suggestions.get(j);
                    }

                    else if(j == suggestions.size()-1) {
                        suggestiontext += suggestions.get(j);

                    }

                    else {
                        suggestiontext += suggestions.get(j)+",";
                    }
                }

                text += suggestiontext + "] ";
            }


        }
        return text;

    }
}
