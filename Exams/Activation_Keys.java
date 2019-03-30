import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activation_Keys {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[] inputKeys = scanner.nextLine().split("&");
        Pattern keyValidation = Pattern.compile("^[a-zA-Z0-9]+$");
        List<String> CDKeys = new ArrayList<>();

        for(String key : inputKeys) {

            Matcher matcher = keyValidation.matcher(key);
            boolean validKey = (key.length() == 16 || key.length() == 25) && matcher.find();

            if (!validKey) {
                continue;
            }
            if (key.length() == 16) {
               CDKeys.add(keyFixer(key));
            }else {
                CDKeys.add(keyFixer(key));
            }
        }
        System.out.println(CDKeys.toString().replaceAll("[\\[\\]]", ""));

    }
    static public String keyFixer (String CDKey) {
        StringBuilder fixed = new StringBuilder();
        int module;

        if (CDKey.length() == 16) {
            module = 4;
        }else {
            module = 5;
        }
        boolean added = false;

        for (int i = 0; i < CDKey.length(); i++) {
            if (i % module == 0 && added) {
                fixed.append("-");
                added = false;
                i -= 1;
            }else {
                if (Character.isDigit(CDKey.charAt(i))) {
                    fixed.append(9 - Character.getNumericValue(CDKey.charAt(i)));
                }else {
                    added = true;
                    fixed.append(CDKey.charAt(i));
                }
            }
        }
        return fixed.toString().toUpperCase();
    }
}
