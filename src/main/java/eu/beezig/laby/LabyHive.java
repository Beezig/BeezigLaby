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

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.ServerQuitEvent;
import net.labymod.api.events.TabListEvent;
import net.labymod.core.LabyModCore;
import net.labymod.gui.elements.Tabs;
import net.labymod.main.LabyMod;
import net.labymod.servermanager.ChatDisplayAction;
import net.labymod.servermanager.Server;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.PacketBuffer;
import eu.beezig.core.BeezigMain;
import eu.beezig.core.IHive;
import eu.beezig.forge.gui.briefing.BriefingGui;

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
        System.out.println("Joined Hive\n\n");
        The5zigAPI.getAPI().setServerInstance(new IHive(), serverData.serverIP);

        Tabs.getTabUpdateListener().add(tab = stringMap -> stringMap.put("The Hive", new Class[] {BriefingGui.class}));
    }

    @Override
    public void reset() {
        System.out.println("Left Hive \n\n");
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
