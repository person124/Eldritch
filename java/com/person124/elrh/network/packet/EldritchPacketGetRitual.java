package com.person124.elrh.network.packet;

import com.person124.elrh.Eldritch;
import com.person124.elrh.gui.GuiKnowledgeBook;
import com.person124.elrh.network.MessageHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EldritchPacketGetRitual implements IMessage {

	private byte id;
	private String recipe;

	public EldritchPacketGetRitual() {}

	public EldritchPacketGetRitual(byte id) {
		this.id = id;
		recipe = "--------";
	}

	public EldritchPacketGetRitual(byte id, String s) {
		this.id = id;
		recipe = s;
	}

	public void fromBytes(ByteBuf buf) {
		id = (byte) buf.readChar();
		
		String temp = "";
		for (int i = 0; i < 8; i++) {
			temp += buf.readChar();
		}
		recipe = temp;
	}

	public void toBytes(ByteBuf buf) {
		buf.writeChar(id);
		for (int i = 0; i < 8; i++) {
			buf.writeChar(recipe.charAt(i));
		}
	}

	public static class Handler extends MessageHandler.BothDirectional<EldritchPacketGetRitual> {

		public IMessage handleClientMessage(final EntityPlayer player, final EldritchPacketGetRitual packet, MessageContext cxt) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					if (!GuiKnowledgeBook.cachedRecipes.containsKey(packet.id)) GuiKnowledgeBook.cachedRecipes.put(packet.id, packet.recipe);
				}
			});
			return null;
		}

		public IMessage handleServerMessage(final EntityPlayer player, final EldritchPacketGetRitual packet, MessageContext cxt) {
			final String s = Eldritch.worldData.getRecipeFromID(packet.id);
			MinecraftServer.getServer().addScheduledTask(new Runnable() {
				public void run() {
					Eldritch.packetHandler.sendTo(new EldritchPacketGetRitual(packet.id, s), (EntityPlayerMP) player);
				}
			});
			return null;
		}
	}

}
