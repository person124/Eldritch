package com.person124.elrh.network.sync;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.enums.EnumSimbolGods;
import com.person124.elrh.network.MessageHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EldritchPacketGodSync implements IMessage {
	
	private EnumSimbolGods god;
	
	public EldritchPacketGodSync() {}
	
	public EldritchPacketGodSync(EnumSimbolGods god) {
		this.god = god;
	}

	public void fromBytes(ByteBuf buf) {
		god = EnumSimbolGods.byDamage(buf.readByte());
	}

	public void toBytes(ByteBuf buf) {
		buf.writeByte(god.getDamage());
	}
	
	public static class Handler extends MessageHandler.Client<EldritchPacketGodSync> {

		public IMessage handleClientMessage(final EntityPlayer player, final EldritchPacketGodSync packet, MessageContext cxt) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					EldritchPlayerData.get(player).setBoundGod(packet.god);
				}
			});
			return null;
		}
		
	}

}
