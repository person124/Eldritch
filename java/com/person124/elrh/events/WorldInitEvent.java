package com.person124.elrh.events;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.person124.elrh.Eldritch;
import com.person124.elrh.EldritchWorldSaveData;

public class WorldInitEvent {

	@SubscribeEvent
	public void onWorldGen(WorldEvent.Load event) {
		if (event.world.provider.getDimensionId() == 0 && event.world.getWorldInfo().isInitialized()) {

			Eldritch.worldData = (EldritchWorldSaveData) event.world.getMapStorage().loadData(EldritchWorldSaveData.class, Eldritch.MODID);
			if (Eldritch.worldData == null) {
				Eldritch.worldData = new EldritchWorldSaveData();
				event.world.getMapStorage().setData(Eldritch.MODID, Eldritch.worldData);
			}

			if (!Eldritch.worldData.isCurrent()) {
				Eldritch.worldData.generate();
			}
		}
	}

}
