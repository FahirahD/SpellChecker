import java.util.*;

public class OutilCorrection {

    private Map<String, Integer> directionaire ;
    private char[] alphabet;
    private ArrayList<String> suggestions;

    public OutilCorrection( Map<String, Integer> dictionaire) {
        this.alphabet = "abcdefghijklmnopqrstuvwxyzàèìòùáéíóúýâêîôûãñõäëïöüÿç-'’ʼ".toCharArray();
        this.directionaire = dictionaire;
        this.suggestions = new ArrayList<>();

    }

    private Set<String> remplacerChqCaractere(String mots) {
        Set<String> correctionCandidat = new HashSet<>();

        for (int i = 0; i < mots.length() - 1; i++) {
            for (int j = 0; j < this.alphabet.length; j++) {
                String new_mot = mots.substring(0, i) + this.alphabet[j] + mots.substring(i + 1, mots.length());
                correctionCandidat.add(new_mot);
            }
        }
        return correctionCandidat;
    }

    private Set<String> retirerChqCaractere(String mots) {
        Set<String> correctionCandidat = new HashSet<>();

        for (int i = 0; i < mots.length() - 1; i++) {
            String new_mot = mots.substring(0, i) + mots.substring(i + 1, mots.length());
            correctionCandidat.add(new_mot);
        }
        return correctionCandidat;
    }

    private Set<String> ajouterCaractere(String mots) {
        Set<String> correctionCandidat = new HashSet<>();
        String new_mot = "";
        for (int i = 0; i < mots.length(); i++) {
            for (int j = 0; j < this.alphabet.length; j++) {
                if ( i == mots.length()-1){
                    new_mot = mots.substring(0, i) + mots.substring(i, mots.length())+this.alphabet[j];
                    correctionCandidat.add(new_mot);
                }
                new_mot = mots.substring(0, i) + this.alphabet[j] + mots.substring(i, mots.length());
                correctionCandidat.add(new_mot);
            }
        }
        return correctionCandidat;
    }

    public String corrigerMots(String mots) {

            this.suggestions.clear();
            Set<String> correctionCandidat = new HashSet<>();

            if (!this.directionaire.containsKey(mots.toLowerCase())) {
                correctionCandidat.addAll(ajouterCaractere(mots));
                correctionCandidat.addAll(retirerChqCaractere(mots));
                correctionCandidat.addAll(remplacerChqCaractere(mots));
            }
            else {
                return mots;
            }

            for (String candidat : correctionCandidat) {
                if (this.directionaire.containsKey(candidat.toLowerCase())) {
                    suggestions.add(candidat);
                }
            }
        return formSuggestionString(mots);
    }

    private String formSuggestionString( String mot){
        String text = "";
        if (this.suggestions.isEmpty()) {
            text += "[" + mot + " => (?)]";
        } else {
            String suggestiontext = "";
            for (int j = 0; j < this.suggestions.size(); j++) {
                if (j == 0) {
                    suggestiontext += "[" + mot + " => ";
                    suggestiontext += this.suggestions.get(j);
                } else {
                    suggestiontext += "," + this.suggestions.get(j);
                }
            }
            text += suggestiontext + "]";
        }
        return text;
    }
}
