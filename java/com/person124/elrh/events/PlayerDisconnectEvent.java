package com.person124.elrh.events;

import com.person124.elrh.Eldritch;
import com.person124.elrh.network.packet.EldritchPacketCloseBook;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class PlayerDisconnectEvent {

	@EventHandler
	public void onPlayerDisconnect(PlayerLoggedOutEvent event) {
		if (!event.player.worldObj.isRemote) {
			Eldritch.packetHandler.sendTo(new EldritchPacketCloseBook(), (EntityPlayerMP) event.player);
		}
	}

}
