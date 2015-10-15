package com.person124.elrh.network.sync;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.network.MessageHandler;

public class EldritchPacketPlayerSync implements IMessage {

	private NBTTagCompound data;

	public EldritchPacketPlayerSync() {}

	public EldritchPacketPlayerSync(EldritchPlayerData playerData) {
		data = new NBTTagCompound();
		playerData.saveNBTData(data);
	}

	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);
	}

	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);
	}

	public static class Handler extends MessageHandler.BothDirectional<EldritchPacketPlayerSync> {

		public IMessage handleClientMessage(final EntityPlayer player, final EldritchPacketPlayerSync packet, MessageContext cxt) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					EldritchPlayerData.get(player).loadNBTData(packet.data);
				}
			});
			return null;
		}

		public IMessage handleServerMessage(final EntityPlayer player, final EldritchPacketPlayerSync packet, MessageContext cxt) {
			MinecraftServer.getServer().addScheduledTask(new Runnable() {
				public void run() {
					EldritchPlayerData.get(player).syncAll();
				}
			});
			return null;
		}

	}

}
