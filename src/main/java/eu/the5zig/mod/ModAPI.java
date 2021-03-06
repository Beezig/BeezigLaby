/*
 *    Copyright 2016 5zig
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package eu.the5zig.mod;

import com.mojang.authlib.GameProfile;
import eu.beezig.core.util.task.WorldTask;
import eu.beezig.laby.LabyMain;
import eu.the5zig.mod.gui.IOverlay;
import eu.the5zig.mod.gui.LabyOverlay;
import eu.the5zig.mod.gui.ingame.ItemStack;
import eu.the5zig.mod.gui.ingame.Scoreboard;
import eu.the5zig.mod.modules.StringItem;
import eu.the5zig.mod.plugin.PluginManager;
import eu.the5zig.mod.render.Formatting;
import eu.the5zig.mod.render.RenderHelper;
import eu.the5zig.mod.server.ServerInstance;
import eu.the5zig.mod.util.NetworkPlayerInfo;
import eu.the5zig.mod.util.PlayerGameMode;
import eu.the5zig.mod.util.component.ChatComponentBuilder;
import eu.the5zig.mod.util.component.MessageComponent;
import eu.the5zig.util.BeezigI18N;
import net.labymod.core.LabyModCore;
import net.labymod.ingamechat.renderer.ChatRenderer;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import java.util.*;

/**
 * Main API class.
 */
public class ModAPI {

	private PluginManager mgr = new PluginManager();
	private ServerInstance serverInstance;
	private boolean initList = false;
	private final List<String> unsupportedModules = Arrays.asList(
			"caicooldowns", "labexperiments", "lablb", "gravstages"
	);
	private RenderHelper renderHelper = new RenderHelper();

	/**
	 * @return the version of the 5zig mod.
	 */
	public String getModVersion() {
		return Source.ABOUT_VERSION;
	}
	/**
	 * @return the version of Minecraft.
	 */
	public String getMinecraftVersion() {
		return "1.8.9";
	}

	/**
	 * Use this method to do important checks when in forge environment. Things like
	 * reflection *may* not work properly then.
	 *
	 * @return true, if we are in a forge environment.
	 */
	public boolean isForgeEnvironment() {
		return LabyMain.IS_FORGE;
	}

	/**
	 * @return the plugin manager.
	 */
	public PluginManager getPluginManager() {
		return mgr;
	}


	/**
	 * Registers a new ModuleItem.
	 *
	 * @param plugin     the plugin instance.
	 * @param key        a unique key of the module item.
	 * @param moduleItem the class of the module item.
	 * @param category   the category of the module item.
	 */

	private int sortingCount = 0;
	private Queue<StringItem> modulesToRegister = new ArrayDeque<>();
	public void registerModuleItem(Object plugin, String key, Class<? extends StringItem> moduleItem, String category) {
		if(unsupportedModules.contains(key)) return;
		try {
			StringItem item = moduleItem.newInstance();
		    item.setKey(key);
		    item.setSortingId(sortingCount++);
			LabyMain.LABY.registerModule(item);
			modulesToRegister.add(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Queue<StringItem> getModulesToRegister() {
		return modulesToRegister;
	}

	/**
	 * Registers a new server instance listener.
	 *
	 * @param plugin         the plugin instance.
	 * @param serverInstance the class of the own server instance.
	 */
	public void registerServerInstance(Object plugin, Class<? extends ServerInstance> serverInstance) {

	}

	/**
	 * @return the active server instance or null, if no server instance is active.
	 */
	public ServerInstance getActiveServer() {
		return serverInstance;
	}

	public void setServerInstance(ServerInstance set, String host) {
		serverInstance = set;
		if(!initList && set != null) {
			initList = true;
			serverInstance.registerListeners();
		}
		if(serverInstance != null)
		serverInstance.handleServer(host, 25565);
	}

	/**
	 * @return a class that contains some utility methods for rendering strings and rectangles.
	 */
	public RenderHelper getRenderHelper() {
		return renderHelper;
	}

	/**
	 * @return the default formatting of all module items.
	 */
	public Formatting getFormatting() {
		return new Formatting() {
			@Override
			public String getPrefixFormatting() {
				return "§r";
			}

			@Override
			public String getMainFormatting() {
				return "§r";
			}

			@Override
			public String getBracketFormatting() {
				return "§r";
			}
		};
	}

	/**
	 * Creates a new overlay message that will appear in the top right corner, simmilar to achievement messages.
	 *
	 * @return a new overlay
	 */
	public IOverlay createOverlay() {
		return new LabyOverlay();
	}

	/**
	 * Translates a key with current Resource Bundle and formats the String.
	 *
	 * @param key    The key of the Resource.
	 * @param format The objects to format the String
	 * @return The formatted, translated value of the key.
	 */
	public String translate(String key, Object... format) {
		return BeezigI18N.s(key, format);
	}

	/**
	 * @return true, if the player is currently playing in a world.
	 */
	public boolean isInWorld() {
		return Minecraft.getMinecraft().currentScreen == null;
	}

	/**
	 * Sends a message from the player to the server. Use {@code /} for commands.
	 *
	 * @param message the message that should be sent.
	 */
	public void sendPlayerMessage(String message) {
		LabyModCore.getMinecraft().getPlayer().sendChatMessage(message);
	}

	/**
	 * Sends a message directly to the client / chat window.
	 *
	 * @param message the message that should be sent.
	 */
	public void messagePlayer(String message) {
		LabyMain.LABY.displayMessageInChat(message);
	}

	/**
	 * Sends a message directly to the second chat of the client.
	 *
	 * @param message the message that should be sent.
	 */
	public void messagePlayerInSecondChat(String message) {
		ChatRenderer chat = LabyMod.getInstance().getIngameChatManager().getSecond();
		WorldTask.submit(() -> chat.addChatLine(message, true, null, null, Minecraft.getMinecraft().ingameGUI.getUpdateCounter(), chat.getChatLines().size(), null, false));
	}

	/**
	 * @return the width of the window.
	 */
	public int getWindowWidth() {
		return LabyMain.LABY.getDrawUtils().getWidth();
	}

	/**
	 * @return the height of the window.
	 */
	public int getWindowHeight() {
		return LabyMain.LABY.getDrawUtils().getHeight();
	}

	/**
	 * @return the scaled width of the window.
	 */
	public int getScaledWidth() { return LabyMain.LABY.getDrawUtils().getScaledResolution().getScaledWidth(); }

	/**
	 * @return the scaled height of the window.
	 */
	int getScaledHeight() { return LabyMain.LABY.getDrawUtils().getScaledResolution().getScaledHeight(); }

	/**
	 * @return the scale factor of the window.
	 */
	int getScaleFactor() { return LabyMain.LABY.getDrawUtils().getScaledResolution().getScaleFactor(); }

	/**
	 * @return the game profile of the current player.
	 */
	public GameProfile getGameProfile() {
		return new GameProfile(LabyMain.LABY.getPlayerUUID(), LabyMain.LABY.getPlayerUsername());
	}

	/**
	 * @return the ip of the server the player is currently playing on.
	 */
	public String getServer() {
		return LabyMain.LABY.getCurrentServer().getIp();
	}

	/**
	 * @return a list containing all entries of the server player list.
	 * @since 1.0.3
	 */
	public List<NetworkPlayerInfo> getServerPlayers() {
        List<NetworkPlayerInfo> result = new ArrayList<>();
        for(net.minecraft.client.network.NetworkPlayerInfo npi :
                LabyModCore.getMinecraft().getConnection().getPlayerInfoMap()) {
            result.add(new NetworkPlayerInfo(npi));
        }
	    return result;
	}


	public PlayerGameMode getGameMode() {
		return PlayerGameMode.SURVIVAL;
	}

	private String fromItemKey(String key) {
		switch(key) {
			case "minecraft:iron_ingot":
				return "item.ingotIron";
			case "minecraft:gold_ingot":
				return "item.ingotGold";
			case "minecraft:diamond":
				return "item.diamond";
			case "minecraft:emerald":
				return "item.emerald";
		}
		return key;
	}

	public int getItemCount(String key) {
		if(key == null) return 0;
		key = fromItemKey(key);
		int count = 0;
		for(net.minecraft.item.ItemStack stack : LabyModCore.getMinecraft().getPlayer().inventory.mainInventory) {
			if(stack != null) {
				if(key.equals(stack.getUnlocalizedName())) count += stack.stackSize;
			}
		}
		return count;
	}


	public Scoreboard getSideScoreboard() {
        net.minecraft.scoreboard.Scoreboard s = LabyModCore.getMinecraft().getWorld().getScoreboard();
        if(s == null) return null;
        ScoreObjective obj = s.getObjectiveInDisplaySlot(1);
        if(obj == null) return null;
        HashMap<String, Integer> lines = new HashMap<>();
        for(Score score : s.getSortedScores(obj)) {
            lines.put(score.getPlayerName(), score.getScorePoints());
        }

        return new Scoreboard(obj.getDisplayName(), lines);
	}


	public void playSound(String sound, float pitch) {
		LabyModCore.getMinecraft().playSound(new ResourceLocation(sound), pitch);
	}

	public ItemStack getItemInMainHand() {
	    return new ItemStack(LabyModCore.getMinecraft().getMainHandItem());
    }

	public void messagePlayerComponent(MessageComponent component, boolean secondChat) {
		if(secondChat) {
			IChatComponent cmp = ChatComponentBuilder.fromInterface(component);
			ChatRenderer chat = LabyMod.getInstance().getIngameChatManager().getSecond();
			chat.addChatLine(cmp.getFormattedText(), true, null, cmp, Minecraft.getMinecraft().ingameGUI.getUpdateCounter(), chat.getChatLines().size(), null, false);
		}
		else Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(ChatComponentBuilder.fromInterface(component));
	}
}
