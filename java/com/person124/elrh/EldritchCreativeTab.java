package com.person124.elrh;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author Calum
 */
public class EldritchCreativeTab extends CreativeTabs {

	public EldritchCreativeTab(String name) {
		super(getNextID(), name);
	}

	public Item getTabIconItem() {
		return ElrhItems.BOOK_OF_KNOWLEDGE;
	}

}
