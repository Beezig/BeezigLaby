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
import eu.the5zig.mod.event.ActionBarEvent;
import eu.the5zig.mod.event.ChatEvent;
import eu.the5zig.mod.event.ChatSendEvent;
import eu.the5zig.mod.event.TitleEvent;
import eu.the5zig.mod.server.AbstractGameListener;
import eu.the5zig.mod.server.GameListenerRegistry;
import eu.the5zig.mod.server.GameMode;
import net.labymod.api.EventManager;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.api.events.PluginMessageEvent;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import eu.beezig.laby.LabyMain;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Rocco on 05/01/2019.
 */
public class LabyEventListener {

    public static void init() {
        EventManager mgr = LabyMain.LABY.getEventManager();

        mgr.register((s, s1) -> {
            boolean bool = false;
            boolean apply = true;
            if(The5zigAPI.getAPI().getActiveServer() != null) {
                for (AbstractGameListener list : GameListenerRegistry.gameListeners) {
                    GameMode gm = The5zigAPI.getAPI().getActiveServer().getGameListener().getCurrentGameMode();
                    try {
                        if(!getTypeParam(list).isAssignableFrom(gm.getClass())) continue;
                        boolean result = list.onServerChat(gm, s.replace("§r", ""));
                        if (apply && result) {
                            bool = result;
                            apply = false;
                        }
                    }
                    catch(Exception ignored) {}
                }
                if(The5zigAPI.getAPI().getActiveServer().getGameListener().match(s1)) bool = true;
                return bool || The5zigAPI.getAPI().getPluginManager().fireEvent(new ChatEvent(s.replace("§r", ""), s1)).isCancelled();
            }
            else return false;
        });

        mgr.register((MessageSendEvent) s -> The5zigAPI.getAPI().getPluginManager().fireEvent(new ChatSendEvent(s)).isCancelled());

        mgr.registerOnJoin(serverData -> {

        });

        mgr.registerOnQuit(serverData -> {

        });

        mgr.register((PluginMessageEvent) (s, packetBuffer) -> {
            if(s.equals("MC|Brand")) { // Switched servers
                if (The5zigAPI.getAPI().getActiveServer() != null) {
                    for (AbstractGameListener list : GameListenerRegistry.gameListeners) {
                        GameMode gm = The5zigAPI.getAPI().getActiveServer().getGameListener().getCurrentGameMode();
                        try {
                            list.onServerConnect(gm);
                        } catch (Exception ignored) {}
                    }
                }
            }
        });

        
        mgr.registerOnIncomingPacket(o -> {
            if(The5zigAPI.getAPI().getActiveServer() == null) return;
            if(o instanceof S45PacketTitle) {
                S45PacketTitle pkt = (S45PacketTitle)o;
                String title = "";
                String subtitle = "";
                if(pkt.getType() == S45PacketTitle.Type.TITLE) title = pkt.getMessage().getFormattedText();
                if(pkt.getType() == S45PacketTitle.Type.SUBTITLE) subtitle = pkt.getMessage().getFormattedText();

                The5zigAPI.getAPI().getPluginManager().fireEvent(new TitleEvent(title, subtitle));
                for (AbstractGameListener list : GameListenerRegistry.gameListeners) {
                    GameMode gm = The5zigAPI.getAPI().getActiveServer().getGameListener().getCurrentGameMode();
                    try {
                        list.onTitle(gm, title.isEmpty() ? null : title, subtitle.isEmpty() ? null : subtitle);
                    } catch (Exception ignored) {}
                }
            }
            else if(o instanceof S02PacketChat) {
                S02PacketChat pkt = (S02PacketChat)o;
                if(pkt.getType() == 2) {
                    The5zigAPI.getAPI().getPluginManager().fireEvent(new ActionBarEvent(pkt.getChatComponent().getUnformattedText()));
                    for (AbstractGameListener list : GameListenerRegistry.gameListeners) {
                        GameMode gm = The5zigAPI.getAPI().getActiveServer().getGameListener().getCurrentGameMode();
                        try {
                            list.onActionBar(gm, pkt.getChatComponent().getUnformattedText());
                        } catch (Exception ignored) {}
                    }
                }
            }
        });
    }

     static Class getTypeParam(Object o) {
        return (Class)
                ((ParameterizedType)o.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }
}
