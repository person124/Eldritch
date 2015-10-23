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
public class HandlerRitualMoony extends HandlerRitual {

	public HandlerRitualMoony() {
		super(EnumRituals.MOONY.getId());
	}

	public boolean go(BlockPos pos, EntityPlayer player) {
		if (player.inventory.hasItem(ElrhItems.BOTTLE_BLOOD_UNDEAD)) {
			player.inventory.consumeInventoryItem(ElrhItems.BOTTLE_BLOOD_UNDEAD);
			player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
			player.worldObj.setWorldTime(18000);
			player.worldObj.getWorldInfo().setRaining(false);
			player.worldObj.updateWeatherBody();
			return true;
		}
		return false;
	}

}
