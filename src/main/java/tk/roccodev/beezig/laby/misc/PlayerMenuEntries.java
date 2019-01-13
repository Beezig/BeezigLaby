package tk.roccodev.beezig.laby.misc;

import eu.the5zig.mod.The5zigAPI;
import io.netty.util.internal.ThreadLocalRandom;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.gui.UserActionGui;
import net.labymod.user.util.UserActionEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import tk.roccodev.beezig.ActiveGame;
import tk.roccodev.beezig.IHive;
import tk.roccodev.beezig.advancedrecords.AdvancedRecords;
import tk.roccodev.beezig.games.TIMV;
import tk.roccodev.beezig.laby.gui.ReportReasonScreen;

import java.lang.reflect.Field;
import java.util.List;

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
                AdvancedRecords.player = networkPlayerInfo.getGameProfile().getName();
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
                int random = ThreadLocalRandom.current().ints(0, TIMV.testRequests.size()).distinct()
                        .filter(i -> i != TIMV.lastTestMsg).findFirst().getAsInt();
                TIMV.lastTestMsg = random;
                The5zigAPI.getAPI().sendPlayerMessage(TIMV.testRequests.get(random).replaceAll("\\{p\\}",
                        networkPlayerInfo.getGameProfile().getName()));
            }

            @Override
            public boolean canAppear(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                return ActiveGame.is("timv");
            }
        });

        REPORT = new UserActionEntry("[Beezig] Report", UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {
            @Override
            public void execute(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                Minecraft.getMinecraft().displayGuiScreen(new ReportReasonScreen(null, networkPlayerInfo.getGameProfile().getName()));
            }

            @Override
            public boolean canAppear(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                return The5zigAPI.getAPI().getActiveServer() instanceof IHive;
            }
        });

        entries.add(STATS);
        entries.add(TIMV_TEST);
        entries.add(REPORT);

        } catch(Exception ignored) {}
    }


}
