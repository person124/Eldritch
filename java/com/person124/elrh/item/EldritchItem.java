package com.person124.elrh.item;

import com.person124.elrh.Eldritch;

import net.minecraft.item.Item;

public class EldritchItem extends Item {

	public EldritchItem(String name) {
		setUnlocalizedName(name);
		setCreativeTab(Eldritch.TAB);
	}

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
