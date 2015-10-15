package com.person124.elrh.network.sync;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.network.MessageHandler;

public class EldritchPacketBooleanSync implements IMessage {

	private boolean data;

	public EldritchPacketBooleanSync() {}

	public EldritchPacketBooleanSync(boolean data) {
		this.data = data;
	}

	public void fromBytes(ByteBuf buf) {
		data = buf.readBoolean();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(data);
	}

	public static class Handler extends MessageHandler.Client<EldritchPacketBooleanSync> {
		public IMessage handleClientMessage(final EntityPlayer player, final EldritchPacketBooleanSync packet, MessageContext cxt) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					EldritchPlayerData.get(player).setHasBasicKnowledge(packet.data);
				}
			});

			return null;
		}
	}

}
