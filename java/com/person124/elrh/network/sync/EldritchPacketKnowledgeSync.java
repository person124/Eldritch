package com.person124.elrh.network.sync;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.network.MessageHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author Person124
 */
public class EldritchPacketKnowledgeSync implements IMessage {

	private boolean[] data;

	public EldritchPacketKnowledgeSync() {}

	public EldritchPacketKnowledgeSync(boolean[] data) {
		this.data = data;
	}

	public void fromBytes(ByteBuf buf) {
		data = new boolean[3];
		for (int i = 0; i < 3; i++) {
			data[i] = buf.readBoolean();
		}
	}

	public void toBytes(ByteBuf buf) {
		for (int i = 0; i < 3; i++) {
			buf.writeBoolean(data[i]);
		}
	}

	public static class Handler extends MessageHandler.Client<EldritchPacketKnowledgeSync> {

		public IMessage handleClientMessage(final EntityPlayer player, final EldritchPacketKnowledgeSync packet, MessageContext cxt) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					EldritchPlayerData.get(player).setKnowledge(packet.data);
				}
			});
			return null;
		}

	}

}
