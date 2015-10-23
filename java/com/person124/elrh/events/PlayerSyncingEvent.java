package com.person124.elrh.events;

import com.person124.elrh.EldritchPlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Person124
 */
public class PlayerSyncingEvent {

	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) EldritchPlayerData.register((EntityPlayer) event.entity);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer) EldritchPlayerData.get((EntityPlayer) event.entity).requestSyncAll();
	}

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event) {
		NBTTagCompound nbt = new NBTTagCompound();
		EldritchPlayerData.get(event.original).saveReviveRelevantNBTData(nbt, event.wasDeath);
		EldritchPlayerData.get(event.entityPlayer).loadNBTData(nbt);
	}

}
