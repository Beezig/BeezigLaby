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

package eu.beezig.laby.api;

import eu.beezig.core.util.modules.IModulesProvider;
import net.labymod.settings.LabyModModuleEditorGui;
import net.minecraft.client.Minecraft;

public class LabyModulesProvider implements IModulesProvider {
    @Override
    public void openModulesGui() {
        Minecraft.getMinecraft().displayGuiScreen(new LabyModModuleEditorGui(Minecraft.getMinecraft().currentScreen));
    }
}
