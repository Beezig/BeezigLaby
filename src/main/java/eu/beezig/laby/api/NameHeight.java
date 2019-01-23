package eu.beezig.laby.api;

import net.labymod.main.LabyMod;
import net.labymod.user.User;

import java.util.UUID;

public class NameHeight {

    public static float get(UUID uuid) {
        User user = LabyMod.getInstance().getUserManager().getUser(uuid);
        if(user == null) return 0f;
        return user.getMaxNameTagHeight();
    }

}
