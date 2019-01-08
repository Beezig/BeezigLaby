package eu.the5zig.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class BeezigI18N {


    private static ResourceBundle strings;

    public static void init() {
        Locale currentLocale = Locale.getDefault();
        try {
            strings = ResourceBundle.getBundle("language", currentLocale);
        } catch(Exception e) {
            strings = ResourceBundle.getBundle("language", Locale.US);
        }
        System.out.println("Strings\n\n\n" + strings + "\n\n\n");
    }

    public static String s(String key, Object... format) {
        if(format.length == 0)
        return strings.getString(key);
        else return String.format(strings.getString(key), format);
    }

}
