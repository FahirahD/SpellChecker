import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Correcteur {

    private static ArrayList<String> ensembleMots = new ArrayList<>();
    private static Map<String, Integer> directionaire = new HashMap<>();

    public static Map<String, Integer> createDictionnaire(String path) {

        Map<String, Integer> dictionaire = new HashMap<>();

        try {
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader br = new BufferedReader(fileReader);

            String ligne;

            while ((ligne = br.readLine()) != null) {
                dictionaire.put(ligne, 0);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictionaire;
    }

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Attention: 2 arguments sont attendus");
            System.exit(-1);
        }

        Pattern patternMot = Pattern.compile("[a-zA-Z\\u00C0-\\u017F\\-']+");
        Pattern patternSeparateur = Pattern.compile("[^a-zA-Z\\u00C0-\\u017F\\']+");

        String correction = "";
        OutilCorrection o = new OutilCorrection(createDictionnaire(args[1]));

        try {
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(args[0]), "UTF-8");
            Scanner s = new Scanner(fileReader);
            s.useDelimiter("\\b");
            String mot = "";
            while (s.hasNext(patternMot)) {
                // Lire un mot
                mot += s.next(patternMot);
                // Lire un séparateur
                String separateur = s.next(patternSeparateur);
                if (separateur.equals("-") || separateur.equals("'") || separateur.equals("’") || separateur.equals("ʼ")) {
                    mot += separateur;
                    continue;
                }
                correction += o.corrigerMots(mot) + separateur;
                mot = "";
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(correction);
    }
}
