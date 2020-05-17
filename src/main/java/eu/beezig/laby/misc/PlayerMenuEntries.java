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

package eu.beezig.laby.misc;

import eu.beezig.core.server.ServerHive;
import eu.beezig.core.server.modes.TIMV;
import eu.beezig.forge.ActiveGame;
import eu.beezig.laby.gui.ReportReasonScreen;
import eu.the5zig.mod.The5zigAPI;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.gui.UserActionGui;
import net.labymod.user.util.UserActionEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerMenuEntries {

    public static UserActionEntry STATS, TIMV_TEST, REPORT;

    public static void init() {

        try {
            Field f = UserActionGui.class.getDeclaredField("defaultEntries");
            f.setAccessible(true);
            List<UserActionEntry> entries = (List<UserActionEntry>) f.get(LabyMod.getInstance().getUserManager().getUserActionGui());


        STATS = new UserActionEntry("[Beezig] Show stats", UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {
            @Override
            public void execute(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                The5zigAPI.getAPI().sendPlayerMessage("/stats " + networkPlayerInfo.getGameProfile().getName());
            }

            @Override
            public boolean canAppear(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                return ActiveGame.current() != null && !ActiveGame.current().isEmpty();
            }
        });

        TIMV_TEST = new UserActionEntry("[Beezig] Ask to test", UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {
            @Override
            public void execute(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                /*int random = ThreadLocalRandom.current().ints(0, TIMV.testRequests.size()).distinct()
                        .filter(i -> i != TIMV.lastTestMsg).findFirst().getAsInt();
                TIMV.lastTestMsg = random;
                The5zigAPI.getAPI().sendPlayerMessage(TIMV.testRequests.get(random).replaceAll("\\{p\\}",
                        networkPlayerInfo.getGameProfile().getName())); */ // TODO Add back when TIMV test messages are supported
            }

            @Override
            public boolean canAppear(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                return eu.beezig.core.util.ActiveGame.get() instanceof TIMV;
            }
        });

        REPORT = new UserActionEntry("[Beezig] Report", UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {
            @Override
            public void execute(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                Minecraft.getMinecraft().displayGuiScreen(new ReportReasonScreen(null, networkPlayerInfo.getGameProfile().getName()));
            }

            @Override
            public boolean canAppear(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                return The5zigAPI.getAPI().getActiveServer() instanceof ServerHive;
            }
        });

        entries.add(STATS);
        entries.add(TIMV_TEST);
        entries.add(REPORT);

        } catch(Exception ignored) {}
    }


}
