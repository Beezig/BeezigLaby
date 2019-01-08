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

package eu.the5zig.mod.gui.ingame;

import java.util.List;

public class ItemStack {

	private net.minecraft.item.ItemStack in;

	public ItemStack(net.minecraft.item.ItemStack in) {
		this.in = in;
	}

	/**
	 * @return the amount of items in this stack
	 */
	public int getAmount() {
		return in.stackSize;
	}

	/**
	 * @return the maximum durability of the ItemStack.
	 */
	public int getMaxDurability() {
		return in.getMaxDamage();
	}

	/**
	 * @return the current durability of the ItemStack.
	 */
	public int getCurrentDurability() {
		return in.getItemDamage();
	}

	/**
	 * @return the resource key of the ItemStack.
	 */
	public String getKey() {
		return in.getUnlocalizedName();
	}

	/**
	 * @return the display name of the ItemStack.
	 */
	public String getDisplayName() {
		return in.getDisplayName();
	}

	/**
	 * @return the lore of the ItemStack.
	 */
	public List<String> getLore() {
		return null;
	}

	/**
	 * @return the food regeneration amount of this item or {@code 0}, if this item is not a food item.
	 */
	public int getHealAmount() {
		return 0;
	}

	/**
	 * @return the food saturation of this item or {@code 0}, if this item is not a food item.
	 */
	float getSaturationModifier() {
		return 0f;
	}


}
