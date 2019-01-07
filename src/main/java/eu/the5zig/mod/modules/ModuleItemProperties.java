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

import eu.the5zig.mod.config.IConfigItem;

import java.util.HashMap;
import java.util.Map;

public class  ModuleItemProperties {

	private StringItem item;

	public ModuleItemProperties(StringItem in) {
		this.item = in;
	}



	public void addSetting(String key, boolean defaultValue) {
		item.addAttribute(key, item.getAttribute(key, Boolean.toString(defaultValue)));
		System.out.println("Attr size for " + key + ": " + item.getAttributes().size());
	}

	/**
	 * Adds a setting to the module item.
	 *
	 * @param key          the identifier of the setting.
	 * @param defaultValue the default value of the setting.
	 * @param enumClass    the class of the Enum.
	 * @see #getSetting(String)
	 */
	public <E extends Enum> void addSetting(String key, E defaultValue, Class<E> enumClass) {
		item.addAttribute(key,  item.getAttribute(key, defaultValue.toString()));
	}

	public IConfigItem getSetting(String key) {
		return new IConfigItem<>(Boolean.parseBoolean(item.getAttribute(key, null)));
	}

	/**
	 * @return the formatting of the module item or {@code null}, if the default mod formatting
	 * should be used.
	 * @see #setFormatting(ModuleItemFormatting)
	 */
	public ModuleItemFormatting getFormatting() {
		return null;
	}

	/**
	 * Sets the formatting of the module item.
	 *
	 * @param formatting the formatting or {@code null}, if the default mod formatting should
	 *                   be used.
	 * @see #getFormatting()
	 */
	public void setFormatting(ModuleItemFormatting formatting) {}

}
