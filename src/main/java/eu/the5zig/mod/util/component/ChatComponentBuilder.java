/*
 * Copyright (c) 2019-2020 5zig Reborn
 *
 * This file is part of The 5zig Mod
 * The 5zig Mod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The 5zig Mod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with The 5zig Mod.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.the5zig.mod.util.component;

import com.google.common.collect.ImmutableMap;
import eu.the5zig.mod.util.component.style.MessageAction;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.Map;

/**
 * Created by 5zig. All rights reserved © 2015
 * <p>
 * Utility class for ChatComponent serialization.
 */
public class ChatComponentBuilder {

    private static final Map<MessageAction.Action, ClickEvent.Action> clickActions = ImmutableMap.of(
            MessageAction.Action.OPEN_URL, ClickEvent.Action.OPEN_URL,
            MessageAction.Action.OPEN_FILE, ClickEvent.Action.OPEN_FILE,
            MessageAction.Action.RUN_COMMAND, ClickEvent.Action.RUN_COMMAND,
            MessageAction.Action.SUGGEST_COMMAND, ClickEvent.Action.SUGGEST_COMMAND);
    private static final Map<MessageAction.Action, HoverEvent.Action> hoverActions = ImmutableMap.of(MessageAction.Action.SHOW_TEXT, HoverEvent.Action.SHOW_TEXT);

    public static IChatComponent fromInterface(MessageComponent api) {
        ChatComponentText text = new ChatComponentText(api.getText());
        ChatStyle style = new ChatStyle();
        MessageAction click = api.getStyle().getOnClick();
        if(click != null) {
            style.setChatClickEvent(new ClickEvent(clickActions.get(click.getAction()), click.getString()));
        }
        MessageAction hover = api.getStyle().getOnHover();
        if(hover != null) {
            style.setChatHoverEvent(new HoverEvent(hoverActions.get(hover.getAction()), fromInterface(hover.getComponent())));
        }
        text.setChatStyle(style);
        for(MessageComponent sibling : api.getSiblings()) {
            text.appendSibling(fromInterface(sibling));
        }
        return text;
    }
}
