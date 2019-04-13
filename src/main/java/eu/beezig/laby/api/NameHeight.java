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

import net.labymod.main.LabyMod;
import net.labymod.user.User;

import java.util.UUID;

public class NameHeight {

    public static float get(UUID uuid) {
        User user = LabyMod.getInstance().getUserManager().getUser(uuid);
        if(user == null) return 0f;
        return user.getMaxNameTagHeight();
    }

}
