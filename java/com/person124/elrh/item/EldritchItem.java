package com.person124.elrh.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class EldritchItem extends Item {

	public EldritchItem(String name) {
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.tabTransport);
	}

	public String getName() {
		return getUnlocalizedName().replace("item.", "");
	}

	public EldritchItem setMaxStackSize(int size) {
		super.setMaxStackSize(size);
		return this;
	}

}
