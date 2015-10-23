package com.person124.elrh.network.packet;

import com.person124.elrh.gui.GuiKnowledgeBook;
import com.person124.elrh.network.MessageHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EldritchPacketCloseBook implements IMessage {

	public void fromBytes(ByteBuf buf) {}

	public void toBytes(ByteBuf buf) {}
	
	public static class Handler extends MessageHandler.Client<EldritchPacketCloseBook> {

		public IMessage handleClientMessage(final EntityPlayer player, final EldritchPacketCloseBook packet, MessageContext cxt) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					GuiKnowledgeBook.onPlayerLeave();
				}
			});
			return null;
		}
		
	}

}
