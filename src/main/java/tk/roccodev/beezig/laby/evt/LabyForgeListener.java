package tk.roccodev.beezig.laby.evt;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.KeyPressEvent;
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
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent evt) {
        The5zigAPI.getAPI().getPluginManager().fireEvent(new KeyPressEvent(0));
    }

}
