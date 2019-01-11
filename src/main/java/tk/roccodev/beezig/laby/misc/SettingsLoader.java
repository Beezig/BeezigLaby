package tk.roccodev.beezig.laby.misc;

import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import tk.roccodev.beezig.settings.Setting;

import java.util.List;

public class SettingsLoader {

    public static void addSettings(List<SettingsElement> settings) {
        for(Setting setting : Setting.values()) {
            settings.add(new BooleanElement(setting.getBrieferDescription(),
                    new ControlElement.IconData(Material.LEVER),
                    setting::setValue, setting.getValue()));
        }
    }

}
