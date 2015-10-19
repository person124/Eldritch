package com.person124.elrh.events;

import com.person124.elrh.potions.EldritchPotions;

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NewPotionsEvent {

	@SubscribeEvent
	public void onEntityTick(LivingUpdateEvent event) {
		if (!event.entityLiving.worldObj.isRemote) EldritchPotions.triggerUpdate(event.entityLiving);
	}

}
