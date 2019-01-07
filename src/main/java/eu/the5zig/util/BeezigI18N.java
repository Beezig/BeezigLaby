package eu.the5zig.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class BeezigI18N {


    private static ResourceBundle strings;

    public static void init() {
        Locale currentLocale = Locale.getDefault();
        strings = ResourceBundle.getBundle("language", currentLocale);
        System.out.println("Strings\n\n\n" + strings + "\n\n\n");
    }

    public static String s(String key) {
        return strings.getString(key);
    }

}
