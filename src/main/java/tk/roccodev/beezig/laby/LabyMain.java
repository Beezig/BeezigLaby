package tk.roccodev.beezig.laby;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.modules.StringItem;
import eu.the5zig.mod.server.GameListenerRegistry;
import eu.the5zig.util.BeezigI18N;
import net.labymod.addon.AddonLoader;
import net.labymod.api.LabyModAPI;
import net.labymod.api.LabyModAddon;
import net.labymod.core.LabyModCore;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import tk.roccodev.beezig.BeezigMain;
import tk.roccodev.beezig.laby.evt.LabyEventListener;
import tk.roccodev.beezig.laby.evt.LabyForgeListener;

import java.util.List;
import java.util.UUID;

public class LabyMain extends LabyModAddon {

    public static BeezigMain INSTANCE;
    public static LabyModAPI LABY;
    public static LabyMain SELF;

    @Override
    public void onEnable() {
        SELF = this;
        INSTANCE = new BeezigMain(true, AddonLoader.getConfigDirectory());
        LABY = getApi();
        BeezigI18N.init();
        LABY.registerForgeListener(new LabyForgeListener());
        LabyEventListener.init();
        LABY.registerServerSupport(this, new LabyHive());
        StringItem.HIVE = new ModuleCategory("The Hive", true, new ControlElement.IconData(Material.GLOWSTONE_DUST));
        ModuleCategoryRegistry.loadCategory(StringItem.HIVE);
        The5zigAPI.getAPI().getPluginManager().registerListener(null, INSTANCE);
        GameListenerRegistry.loadPatterns();
    }

    @Override
    public void init(String addonName, UUID uuid) {
        super.init(addonName, uuid);
        INSTANCE.onLoad(null); // Init is called after onEnable (config is accessible here)
    }

    @Override
    public void onDisable() {
        System.out.println("Disabling BeezigLaby");
    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }
}
