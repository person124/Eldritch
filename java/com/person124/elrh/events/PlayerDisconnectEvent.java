package com.person124.elrh.events;

import com.person124.elrh.gui.GuiKnowledgeBook;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class PlayerDisconnectEvent {

	@SubscribeEvent
	public void onPlayerDisconnect(PlayerLoggedOutEvent event) {
		if (event.player.worldObj.isRemote) {
			GuiKnowledgeBook.onPlayerLeave();
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		if (event.world.isRemote) {
			GuiKnowledgeBook.onPlayerLeave();
		}
	}

}
