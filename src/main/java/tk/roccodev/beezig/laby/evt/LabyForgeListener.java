package tk.roccodev.beezig.laby.evt;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.KeyPressEvent;
import eu.the5zig.mod.server.AbstractGameListener;
import eu.the5zig.mod.server.GameListenerRegistry;
import eu.the5zig.mod.server.GameMode;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by Rocco on 05/01/2019.
 */
public class LabyForgeListener {


    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent evt) {
        The5zigAPI.getAPI().getPluginManager().fireEvent(new eu.the5zig.mod.event.TickEvent());
        if(The5zigAPI.getAPI().getActiveServer() == null) return;
        if(The5zigAPI.getAPI().getActiveServer().getGameListener() == null) return;
        GameMode gm = The5zigAPI.getAPI().getActiveServer().getGameListener().getCurrentGameMode();
        if(gm == null) return;
        for(AbstractGameListener list : GameListenerRegistry.gameListeners) {
            if(!LabyEventListener.getTypeParam(list).isAssignableFrom(gm.getClass())) continue;
            try {
                list.onTick(gm);
            } catch (Exception ignored) {}
        }
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent evt) {
        The5zigAPI.getAPI().getPluginManager().fireEvent(new KeyPressEvent(0));
    }

}
