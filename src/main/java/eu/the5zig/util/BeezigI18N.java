package eu.the5zig.util;

import eu.beezig.core.config.Settings;
import eu.beezig.core.config.i18n.LanguageSetting;
import net.minecraft.client.Minecraft;

import java.util.Locale;
import java.util.ResourceBundle;

public class BeezigI18N {

    private static ResourceBundle defaults;
    private static ResourceBundle strings;

    public static void init() {
        Locale currentLocale = ((LanguageSetting) Settings.LANGUAGE.get().getValue()).getLocaleId();
        try {
            defaults = ResourceBundle.getBundle("lang/language", Locale.US);
            strings = ResourceBundle.getBundle("lang/language", currentLocale);
        } catch(Exception e) {
            strings = ResourceBundle.getBundle("lang/language", Locale.US);
        }
    }

    public static String s(String key, Object... format) {
        if(!strings.containsKey(key)) {
            if(defaults.containsKey(key)) {
                if(format.length == 0) return defaults.getString(key);
                else return String.format(defaults.getString(key), format);
            }
            return key;
        }
        if(format.length == 0)
        return strings.getString(key);
        else return String.format(strings.getString(key), format);
    }

}
