package com.person124.elrh.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.person124.elrh.ElrhItems;

public class EntityDeathEvent {

	@SubscribeEvent
	public void onEntityDie(LivingDeathEvent event) {
		if (!event.entity.worldObj.isRemote) {
			if (event.source.getSourceOfDamage() instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) event.source.getSourceOfDamage();
				if (p.inventory.getCurrentItem() != null && p.inventory.getCurrentItem().getItem() == Items.glass_bottle) {
					p.inventory.consumeInventoryItem(Items.glass_bottle);
					p.inventory.addItemStackToInventory(new ItemStack(ElrhItems.BOTTLE_BLOOD));
				}
			}
		}
	}

}
