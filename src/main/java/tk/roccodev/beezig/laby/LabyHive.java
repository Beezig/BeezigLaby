package tk.roccodev.beezig.laby;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.ServerQuitEvent;
import net.labymod.api.events.TabListEvent;
import net.labymod.servermanager.ChatDisplayAction;
import net.labymod.servermanager.Server;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.PacketBuffer;
import tk.roccodev.beezig.IHive;

import java.util.List;

/**
 * Created by Rocco on 05/01/2019.
 */
public class LabyHive extends Server {

    LabyHive() {
        super("The Hive", "premium.hivemc.com", "premium.hive.sexy", "hive.sexy", "hivemc.eu", "play.hivemc.com",
                "premium.hivemc.eu, play.hivemc.eu", "eu.hivemc.com");
    }



    @Override
    public void onJoin(ServerData serverData) {
        System.out.println("Joined Hive\n\n");
        The5zigAPI.getAPI().setServerInstance(new IHive(), serverData.serverIP);
    }

    @Override
    public void reset() {
        System.out.println("Left Hive \n\n");
        The5zigAPI.getAPI().setServerInstance(null, null);
        The5zigAPI.getAPI().getPluginManager().fireEvent(new ServerQuitEvent());
    }

    @Override
    public ChatDisplayAction handleChatMessage(String s, String s1) throws Exception {
        return ChatDisplayAction.NORMAL;
    }

    @Override
    public void handlePluginMessage(String s, PacketBuffer packetBuffer) throws Exception {

    }

    @Override
    public void handleTabInfoMessage(TabListEvent.Type type, String s, String s1) throws Exception {

    }

    @Override
    public void fillSubSettings(List<SettingsElement> list) {

    }
}
