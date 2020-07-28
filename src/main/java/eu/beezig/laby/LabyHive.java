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

package eu.beezig.laby;

import eu.beezig.core.server.ServerHive;
import eu.beezig.forge.gui.briefing.BriefingGui;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.ServerQuitEvent;
import eu.the5zig.mod.server.AbstractGameListener;
import eu.the5zig.mod.server.GameListenerRegistry;
import eu.the5zig.mod.server.GameMode;
import net.labymod.api.events.TabListEvent;
import net.labymod.gui.elements.Tabs;
import net.labymod.servermanager.ChatDisplayAction;
import net.labymod.servermanager.Server;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.PacketBuffer;

import java.util.List;
import java.util.Map;

/**
 * Created by Rocco on 05/01/2019.
 */
public class LabyHive extends Server {

    private static Consumer<Map<String, Class<? extends GuiScreen>[]>> tab;

    LabyHive() {
        super("The Hive", "premium.hivemc.com", "premium.hive.sexy", "hive.sexy", "hivemc.eu", "play.hivemc.com",
                "premium.hivemc.eu, play.hivemc.eu", "eu.hivemc.com", "hivemc.com");
    }


    @Override
    public void onJoin(ServerData serverData) {
        The5zigAPI.getAPI().setServerInstance(new ServerHive(), serverData.serverIP);
        GameMode gm = The5zigAPI.getAPI().getActiveServer().getGameListener().getCurrentGameMode();
        for (AbstractGameListener list : GameListenerRegistry.gameListeners) {
            try {
                list.onServerConnect(gm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tabs.getTabUpdateListener().add(tab = stringMap -> stringMap.put("The Hive", new Class[]{BriefingGui.class}));
    }

    @Override
    public void reset() {
        GameMode gm = The5zigAPI.getAPI().getActiveServer() == null ? null : The5zigAPI.getAPI().getActiveServer().getGameListener().getCurrentGameMode();
        for (AbstractGameListener list : GameListenerRegistry.gameListeners) {
            try {
                list.onServerDisconnect(gm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        The5zigAPI.getAPI().setServerInstance(null, null);
        The5zigAPI.getAPI().getPluginManager().fireEvent(new ServerQuitEvent());
        Tabs.getTabUpdateListener().remove(tab);
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
