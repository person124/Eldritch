package com.person124.elrh.events;

import com.person124.elrh.gui.GuiKnowledgeBook;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/**
 * @author Person124
 */
public class PlayerDisconnectEvent {

	@SubscribeEvent
	public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		GuiKnowledgeBook.onPlayerLeave();
	}

}
