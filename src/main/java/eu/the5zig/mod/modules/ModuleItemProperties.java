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
import eu.beezig.laby.LabyMain;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  ModuleItemProperties {

	private StringItem item;

	private HashMap<String, Class> enums = new HashMap<>();

	public ModuleItemProperties(StringItem in) {
		this.item = in;
	}



	public void addSetting(String key, boolean defaultValue) {
		String pkey = "bzg_mdl_" + item.getName() + "_" + key;
		String apply = LabyMain.SELF.getConfig().has(pkey)
				? Boolean.toString(LabyMain.SELF.getConfig().get(pkey).getAsBoolean())
				: Boolean.toString(defaultValue);
		item.addAttribute(key, apply);
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
		String pkey = "bzg_mdl_" + item.getName() + "_" + key;
		String apply = LabyMain.SELF.getConfig().has(pkey)
				? LabyMain.SELF.getConfig().get(pkey).getAsString()
				: defaultValue.toString();

		List<String> build = new ArrayList<>();
		try {
			Object[] raw = (Object[]) enumClass.getMethod("values").invoke(null);
			for(Object o : raw) {
				build.add(o.toString());
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		item.addAttributeEnum(key, apply, build.toArray(new String[0]));
		enums.put(key, enumClass);
	}

	public IConfigItem getSetting(String key) {
	    if(!enums.containsKey(key))
		    return new IConfigItem<>(Boolean.parseBoolean(item.getAttribute(key, null)));
	    else
            return new IConfigItem<>(Enum.valueOf(enums.get(key), item.getAttribute(key, null)));
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
