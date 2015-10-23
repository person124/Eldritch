package com.person124.elrh.handler.ritual;

import com.person124.elrh.ElrhItems;
import com.person124.elrh.enums.EnumRituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

/**
 * @author Person124
 */
public class HandlerRitualStormy extends HandlerRitual {

	public HandlerRitualStormy() {
		super(EnumRituals.STORMY.getId());
	}

	public boolean go(BlockPos pos, EntityPlayer player) {
		if (player.inventory.hasItem(ElrhItems.BOTTLE_BLOOD) && player.inventory.hasItem(ElrhItems.BOTTLE_BLOOD_UNDEAD)) {
			player.inventory.consumeInventoryItem(ElrhItems.BOTTLE_BLOOD);
			player.inventory.consumeInventoryItem(ElrhItems.BOTTLE_BLOOD_UNDEAD);
			player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle, 2));
			player.worldObj.getWorldInfo().setRaining(true);
			player.worldObj.updateWeatherBody();
			return true;
		}
		return false;
	}

}
