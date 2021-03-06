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

package eu.beezig.laby.net;

import eu.beezig.core.net.session.ISessionProvider;
import net.minecraft.client.Minecraft;

import java.net.Proxy;

public class LabySessionProvider implements ISessionProvider {
    @Override
    public Proxy getProxy() {
        return Minecraft.getMinecraft().getProxy();
    }

    @Override
    public String getSessionString() {
        return Minecraft.getMinecraft().getSession().getToken();
    }
}
