/*
 * Copyright (C) 2019 Beezig (RoccoDev, ItsNiklass)
 *
 * This file is part of BeezigLaby.
 *
 * BeezigLaby is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigLaby is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigLaby.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.laby.evt;

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
        for(AbstractGameListener list : GameListenerRegistry.gameListeners) {
            if((list.getGameMode() == null) || (gm != null && LabyEventListener.getTypeParam(list).isAssignableFrom(gm.getClass()))) {
                try {
                    list.onTick(gm);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent evt) {
        The5zigAPI.getAPI().getPluginManager().fireEvent(new KeyPressEvent(0));
    }
}
