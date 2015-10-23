package com.person124.elrh.events;

import com.person124.elrh.ElrhItems;
import com.person124.elrh.potions.EldritchPotions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Person124
 */
public class PlayerRightClickEntityEvent {

	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		if (!event.entity.worldObj.isRemote) {
			EntityLiving e = (EntityLiving) event.target;
			if (!isValidType(e) || e.getActivePotionEffect(EldritchPotions.getPotion(0)) == null) return;
			EntityPlayer p = (EntityPlayer) event.entityPlayer;

			if (p.inventory.getCurrentItem() != null && p.inventory.getCurrentItem().getItem() == Items.glass_bottle) {
				p.inventory.consumeInventoryItem(Items.glass_bottle);

				Item i = ElrhItems.BOTTLE_BLOOD;
				if (isUndead(event.entity)) i = ElrhItems.BOTTLE_BLOOD_UNDEAD;
				p.inventory.addItemStackToInventory(new ItemStack(i));
			}
		}
	}

	private boolean isValidType(Entity e) {
		return !(e instanceof EntitySkeleton || e instanceof EntitySnowman || e instanceof EntityIronGolem || e instanceof EntityBlaze || e instanceof EntitySlime || e instanceof EntityMagmaCube);
	}

	private boolean isUndead(Entity e) {
		return e instanceof EntityZombie || e instanceof EntityPigZombie;
	}

}
