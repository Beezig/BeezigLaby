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

package eu.the5zig.mod.modules;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.render.RenderLocation;
import eu.the5zig.mod.server.GameMode;
import eu.the5zig.mod.server.GameState;

public abstract class LargeTextItem<T extends GameMode> extends GameModeItem<T> {

	public LargeTextItem(Class<? extends T> modeClass, GameState... state) {
		super(modeClass, state);
	}

	@Override
	public void render(int x, int y, RenderLocation renderLocation, boolean dummy) {
		if (dummy) {
			return;
		}
		The5zigAPI.getAPI().getRenderHelper().drawLargeText(The5zigAPI.getAPI().getFormatting().getPrefixFormatting() + getText());
	}

	protected abstract String getText();

	@Override
	protected Object getValue(boolean dummy) {
		return getText();
	}


	@Override
	public int getHeight(boolean dummy) {
		return 0;
	}
}
