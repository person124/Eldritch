package com.person124.elrh.handler.ritual;

import com.person124.elrh.Eldritch;
import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.ElrhBlocks;
import com.person124.elrh.ElrhItems;
import com.person124.elrh.enums.EnumRituals;
import com.person124.elrh.enums.EnumSimbolGods;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

/**
 * @author Person124
 */
public class HandlerRitualBind extends HandlerRitual {

	public HandlerRitualBind() {
		super(EnumRituals.BIND.getId());
	}

	public boolean go(BlockPos pos, EntityPlayer player) {
		if (!EldritchPlayerData.get(player).hasKnowledge(1) && player.inventory.hasItem(ElrhItems.BOTTLE_BLOOD_UNDEAD) && player.worldObj.getWorldTime() >= 14000) {
			player.inventory.consumeInventoryItem(ElrhItems.BOTTLE_BLOOD_UNDEAD);
			player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));

			int i = Eldritch.worldData.getRecipeFromID(ID).indexOf('!');
			if (i > 3) i++;
			int xp = 1 - i / 3;
			int zp = i % 3 - 1;

			BlockPos p = new BlockPos(pos.getX() + xp, pos.getY(), pos.getZ() + zp);
			EnumSimbolGods god = EnumSimbolGods.byDamage(ElrhBlocks.RITUAL_SIMBOL_GOD.getMetaFromState(player.worldObj.getBlockState(p)));

			EldritchPlayerData data = EldritchPlayerData.get(player);
			data.setBoundGod(god);
			data.setKnowledge(1, true);

			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1200));

			EntityLightningBolt bolt = new EntityLightningBolt(player.worldObj, pos.getX(), pos.getY(), pos.getZ());
			player.worldObj.spawnEntityInWorld(bolt);
			return true;
		}
		return false;
	}

}
