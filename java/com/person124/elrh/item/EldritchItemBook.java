package com.person124.elrh.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.person124.elrh.handler.EldritchGuiHandler;

public class EldritchItemBook extends EldritchItem {

	public EldritchItemBook(String name) {
		super(name);
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote) EldritchGuiHandler.GUIs.KNOWLEDGE_BOOK.openGUI(true, world, player);
		return stack;
	}

}
