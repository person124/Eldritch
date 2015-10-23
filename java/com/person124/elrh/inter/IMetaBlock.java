package com.person124.elrh.inter;

import net.minecraft.item.ItemStack;

/**
 * @author Person124
 */
public interface IMetaBlock {

	/**
	 * Returns the unlocalized name of a block, based on the state.
	 */
	public String getUnlocalizedName(ItemStack stack);

}
