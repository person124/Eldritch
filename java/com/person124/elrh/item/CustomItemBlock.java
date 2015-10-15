package com.person124.elrh.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.person124.elrh.inter.IMetaBlock;

public class CustomItemBlock extends ItemBlock {

	public CustomItemBlock(Block block) {
		super(block);

		if (!(block instanceof IMetaBlock)) {
			System.out.println("Block: " + block.getUnlocalizedName() + " isn't a instance of IMetaBlock");
		}

		setHasSubtypes(true);
		setMaxDamage(0);
	}

	public int getMetadata(int damage) {
		return damage;
	}

	public String getUnlocalizedName(ItemStack stack) {
		return ((IMetaBlock) block).getUnlocalizedName(stack);
	}

}
