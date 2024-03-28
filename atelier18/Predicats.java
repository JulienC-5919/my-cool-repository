import java.util.function.Predicate;

public class Predicats {

    private Predicats() {
        throw new UnsupportedOperationException("Cette classe ne peut pas être instantiée.");
    }

    public static Predicate<Integer> estPair = i -> (i & 1) == 0;

    public static Predicate<String> estPalindrome = mot -> {
        boolean lettresPareilles = true;

        int i = 0;
        while(lettresPareilles && i < mot.length() / 2) {
            lettresPareilles = mot.charAt(i) == mot.charAt(mot.length() - 1 - i);
            i++;
        }

        return lettresPareilles;
    };
    
    public static Predicate<String> estPallindrome = mot -> {
        boolean lettresPareilles = true;

        int i = 0;
        while(lettresPareilles && i < mot.length() / 2) {
            lettresPareilles = mot.charAt(i) == mot.charAt(mot.length() - 1 - i);
            i++;
        }

        return lettresPareilles;
    };

    public static Predicate<String[]> contientBonjour = liste -> {
        boolean bonjourTrouve = false;

        int i = 0;
        while (!bonjourTrouve && i < liste.length) {
            bonjourTrouve = liste[i].equals("Bonjour");
            i++;
        }

        return bonjourTrouve;
    };
}