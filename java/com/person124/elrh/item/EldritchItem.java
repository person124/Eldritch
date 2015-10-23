package com.person124.elrh.item;

import com.person124.elrh.Eldritch;

import net.minecraft.item.Item;

/**
 * All Eldritch mod items should extend this.
 * @author Person124
 */
public class EldritchItem extends Item {

	public EldritchItem(String name) {
		setUnlocalizedName(name);
		setCreativeTab(Eldritch.TAB);
	}

	/**
	 * @return The unlocalized name of the item in a format that can be used by minecraft.
	 */
	public String getName() {
		return getUnlocalizedName().replace("item.", "");
	}

	public EldritchItem setMaxStackSize(int size) {
		super.setMaxStackSize(size);
		return this;
	}

	public EldritchItem setMaxDamage(int damage) {
		super.setMaxDamage(damage);
		return this;
	}

}
