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

import eu.the5zig.mod.render.RenderLocation;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.DropDownElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import eu.beezig.core.Log;
import eu.beezig.laby.LabyMain;
import eu.beezig.laby.categories.ModuleCategories;

import java.util.ArrayList;
import java.util.List;

/**
 * A module item that renders a simple text to the screen. Simply extend this class and
 * override {@link #getValue(boolean)}.
 */
public class StringItem extends SimpleModule {

	public static ModuleCategory HIVE;
	private List<SettingsElement> subs = new ArrayList<>();
	private ModuleItemProperties props;
	private String key;
	private int sorting;

	public StringItem(String key) {
		this.key = key;
	}

	public StringItem() {
		props = new ModuleItemProperties(this);
	}

	@Override
	public boolean isShown() {
		return shouldRender(false) && super.isShown();
	}

	/**
	 * Renders a simple text as item onto the screen. The text needs to be returned from an extending class.
	 *
	 * @param x              the x-position of the item.
	 * @param y              the y-position of the item.
	 * @param renderLocation the {@link RenderLocation} of the item.
	 * @param dummy          true, if the item should render dummy values.
	 */
	public void render(int x, int y, RenderLocation renderLocation, boolean dummy) {}

	/**
	 * Only renders the item when {@link #getValue(boolean)} is not {@code null}.
	 *
	 * @param dummy true, if the item should render dummy values.
	 * @return true, if the item should be rendered.
	 */
	public boolean shouldRender(boolean dummy) {
		return getValue(dummy) != null;
	}
	/**
	 * The height of the text.
	 *
	 * @param dummy true, if the item should render dummy values.
	 * @return the height of the module item.
	 */
	public int getHeight(boolean dummy) {
		return 10;
	}

	@Override
	public String getName() {
		return key;
	}

	public String getPrefix() { return Log.t(getTranslation()); }

	public String getTranslation() {return "beezig.module.timv.karma";}

	/**
	 * Needs to be implemented to return a custom value.
	 *
	 * @param dummy true, if the method should return dummy values.
	 * @return a value that should be rendered or {@code null}  if the item should not be rendered to the screen.
	 */
	protected Object getValue(boolean dummy) {return 0;}

    @Override
	public ModuleCategory getCategory() {
        ModuleCategories cat = ModuleCategories.get(getCategoryKey());
        return cat.getCategory();
	}


	@Override
	public String getDisplayName() {
		return Log.t(getTranslation());
	}

	@Override
	public String getDisplayValue() {
		try {
			Object val = getValue(false);
			if(val == null) return "?";
			return val.toString();
		} catch(Exception e) {
			System.out.println("Exception occurred while rendering " + key);
			e.printStackTrace();
			return "Error?";
		}
	}

	@Override
	public String getDefaultValue() {
		return "?";
	}

	@Override
	public ControlElement.IconData getIconData() {
		return null;
	}

	@Override
	public void loadSettings() {
		LabyMain.SELF.saveConfig();
	}

	protected String getCategoryKey() {
	    return key;
    }

	@Override
	public void fillSubSettings(List<SettingsElement> settingsElements) {
		super.fillSubSettings(settingsElements);
		settingsElements.addAll(subs);
	}

	void addAttribute(String key, String attr) {
		setAttribute(key, attr);
		subs.add(new BooleanElement(this, new ControlElement.IconData(Material.LEVER),
				Log.t("modules.item." + this.key + "." + key), key).addCallback(b -> {
					LabyMain.SELF.getConfig().addProperty("bzg_mdl_" + StringItem.this.key + "_" + key,
							Boolean.toString(b));
					LabyMain.SELF.saveConfig();
				}));

	}

	void addAttributeEnum(String key, String attr, String[] en) {
	    setAttribute(key, attr);
	    String disp = Log.t("modules.item." + this.key + "." + key);
	    DropDownMenu<String> menu = new DropDownMenu<String>(disp, 0, 0, 0, 0).fill(en);
	    menu.setSelected(attr);
	    DropDownElement<String> elem = new DropDownElement<>(disp, menu);
        elem.setChangeListener(s -> {
            setAttribute(key, s);
            LabyMain.SELF.getConfig().addProperty("bzg_mdl_" + StringItem.this.key + "_" + key, s);
            LabyMain.SELF.saveConfig();
        });
	    subs.add(elem);
    }

	public void registerSettings() {}

	public ModuleItemProperties getProperties() {
		return props;
	}

	public ModuleItemFormatting getFormatting() {
		return null;
	}

	@Override
	public String getSettingName() {
		return key;
	}

	@Override
	public String getControlName() {
		return Log.t("modules.item." + key);
	}

	@Override
	public String getDescription() {
		return "";
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSortingId(int sorting) {
		this.sorting = sorting;
	}

	@Override
	public int getSortingId() {
		return sorting;
	}
}
