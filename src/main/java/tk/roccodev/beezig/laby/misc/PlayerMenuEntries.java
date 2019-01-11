package tk.roccodev.beezig.laby.misc;

import net.labymod.ingamechat.tools.playermenu.PlayerMenu;
import net.labymod.main.LabyMod;

public class PlayerMenuEntries {

    public static PlayerMenu.PlayerMenuEntry STATS, TIMV_TEST;

    public static void init() {
        STATS = new PlayerMenu.PlayerMenuEntry("[Beezig] Show stats", "/stats {name}", true);

        TIMV_TEST = new PlayerMenu.PlayerMenuEntry("[Beezig] Ask to test", "{name} test", true);
    }

    public static void add(PlayerMenu.PlayerMenuEntry entry) {
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(entry);
    }

    public static void remove(PlayerMenu.PlayerMenuEntry entry) {
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().remove(entry);
    }


}
