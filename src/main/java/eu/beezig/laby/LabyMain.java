package eu.beezig.laby;

import eu.beezig.laby.categories.ModuleCategories;
import eu.beezig.laby.evt.LabyEventListener;
import eu.beezig.laby.evt.LabyForgeListener;
import eu.beezig.laby.misc.SettingsLoader;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.server.GameListenerRegistry;
import eu.the5zig.util.BeezigI18N;
import net.labymod.addon.AddonLoader;
import net.labymod.api.LabyModAPI;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import eu.beezig.core.BeezigMain;
import eu.beezig.forge.BeezigForgeMod;
import eu.beezig.laby.misc.PlayerMenuEntries;

import java.util.List;
import java.util.UUID;

public class LabyMain extends LabyModAddon {

    public static BeezigMain INSTANCE;
    public static LabyModAPI LABY;
    public static BeezigForgeMod FORGE;
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
        ModuleCategories.init();
        The5zigAPI.getAPI().getPluginManager().registerListener(null, INSTANCE);
        GameListenerRegistry.loadPatterns();
    }


    @Override
    public void init(String addonName, UUID uuid) {
        super.init(addonName, uuid);
        INSTANCE.onLoad(null); // Init is called after onEnable (config is accessible here)

        PlayerMenuEntries.init();

        try {
            FORGE = new BeezigForgeMod();
            FORGE.onPre(null);
            FORGE.onInit(null);
        } catch(Exception ignored) {} // Exception is thrown when the user is on Labymod Vanilla
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
        SettingsLoader.addSettings(list);
    }

    public boolean isForge() {
        return FORGE != null;
    }
}
