import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Correcteur {

    private static ArrayList<String> ensembleMots = new ArrayList<>();
    private static Map<String, Integer> directionaire = new HashMap<>();


    public static void initMotNonCorriges(String path) {

        Pattern patternMot = Pattern.compile("[a-zA-Z\\u00C0-\\u017F\\-']+");
        Pattern patternSeparateur = Pattern.compile("[^a-zA-Z\\u00C0-\\u017F\\']+");


        try {
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            Scanner s = new Scanner(fileReader);
            s.useDelimiter("\\b");
            String mot = "";
            while (s.hasNext(patternMot)) {
                // Lire un mot
                mot += s.next(patternMot);
                System.out.println("Mot: " + mot);
                // Lire un séparateur
                String separateur = s.next(patternSeparateur);
                System.out.println(separateur);
                if (separateur.equals("-") || separateur.equals("'") ){
                    System.out.println("im in");
                    mot+=separateur;
                    continue;
                }
                ensembleMots.add(mot);
                mot="";
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initDictionnaire(String path) {
        try {
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader br = new BufferedReader(fileReader);

            String ligne;

            while ((ligne = br.readLine()) != null) {
                directionaire.put(ligne, 0);
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

        OutilCorrection o = new OutilCorrection(ensembleMots, directionaire);

        System.out.println(o.corrigerMots());


    }
}
