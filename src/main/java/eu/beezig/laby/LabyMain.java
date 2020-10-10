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

import eu.beezig.core.Beezig;
import eu.beezig.core.api.BeezigForge;
import eu.beezig.core.command.commands.BeezigCommand;
import eu.beezig.core.net.session.NetSessionManager;
import eu.beezig.forge.BeezigForgeMod;
import eu.beezig.forge.gui.settings.GuiBeezigSettings;
import eu.beezig.laby.api.LabyModulesProvider;
import eu.beezig.laby.categories.ModuleCategories;
import eu.beezig.laby.evt.LabyEventListener;
import eu.beezig.laby.evt.LabyForgeListener;
import eu.beezig.laby.misc.PlayerMenuEntries;
import eu.beezig.laby.net.LabySessionProvider;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.modules.StringItem;
import eu.the5zig.mod.server.GameListenerRegistry;
import eu.the5zig.util.BeezigI18N;
import net.labymod.addon.AddonLoader;
import net.labymod.api.LabyModAPI;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.LabyModAddonsGui;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.UUID;

public class LabyMain extends LabyModAddon {

    public static Beezig INSTANCE;
    public static LabyModAPI LABY;
    public static BeezigForgeMod FORGE;
    public static LabyMain SELF;
    public static boolean IS_FORGE = false;

    @Override
    public void onEnable() {
        SELF = this;
        NetSessionManager.provider = new LabySessionProvider();
        BeezigCommand.modulesProvider = new LabyModulesProvider();
        try {
            Class.forName("net.minecraftforge.client.GuiIngameForge");
            IS_FORGE = true;
        }
        catch (ClassNotFoundException ex) {}
        INSTANCE = new Beezig(true, AddonLoader.getConfigDirectory());
        LABY = getApi();
        LABY.registerForgeListener(new LabyForgeListener());
        LabyEventListener.init();
        LABY.registerServerSupport(this, new LabyHive());
        ModuleCategories.init();
        The5zigAPI.getAPI().getPluginManager().registerListener(null, INSTANCE);
        GameListenerRegistry.loadPatterns();
    }


    @Override
    public void init(String addonName, UUID uuid) {
        super.init(addonName, uuid);
        INSTANCE.load(null); // Init is called after onEnable (config is accessible here)
        BeezigI18N.init();
        for(StringItem item : The5zigAPI.getAPI().getModulesToRegister()) item.registerSettings();
        PlayerMenuEntries.init();
        try {
            FORGE = new BeezigForgeMod();
        } catch(Exception ignored) {} // Exception is thrown when the user is on Labymod Vanilla
    }

    @Override
    public void onDisable() {
        System.out.println("Disabling BeezigLaby");
    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        list.add(new BooleanElement("Placeholder", "placeholder", new ControlElement.IconData(Material.STONE)) {
            @Override
            public void draw(int x, int y, int maxX, int maxY, int mouseX, int mouseY) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiBeezigSettings(new LabyModAddonsGui(null), Beezig.cfg().toForge()));
            }
        });
    }

    public boolean isForge() {
        return FORGE != null;
    }
}
