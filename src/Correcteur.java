import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Correcteur {

    private static ArrayList<String> ensembleMots = new ArrayList<>();
    private static Map<String ,Integer> directionaire = new HashMap<>();


    public static void initMotNonCorriges(String path){

        Pattern patternMot = Pattern.compile("[a-zA-Z\\u00C0-\\u017F]+");
        Pattern patternSeparateur = Pattern.compile("[^a-zA-Z\\u00C0-\\u017F]+");


        try {
            FileReader fileReader = new FileReader(path);
            Scanner s = new Scanner(fileReader);
            s.useDelimiter("\\b");

            while (s.hasNext(patternMot)) {
                // Lire un mot
                String mot = s.next(patternMot);
                //System.out.println("Mot: " + mot);
                ensembleMots.add(mot);

                // Lire un séparateur
                String separateur = s.next(patternSeparateur);
                //System.out.println("Séparateur: " + separateur);
            }

            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initDictionnaire(String path){
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader);

            String ligne;

            while((ligne = br.readLine()) != null) {
                directionaire.put(ligne,0);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Attention: 2 arguments sont attendus");
            System.exit(-1);
        }

        initMotNonCorriges(args[0]);
        initDictionnaire(args[1]);

        OutilCorrection o = new OutilCorrection(ensembleMots,directionaire);

        System.out.println(o.corrigerMots());





    }
}
