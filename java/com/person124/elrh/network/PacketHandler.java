package com.person124.elrh.network;

import akka.actor.IllegalActorStateException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	private byte nextPacketID = 0;
	private SimpleNetworkWrapper wrapper;
	private String channelID;
	
	public PacketHandler(String channelID) {
		wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(channelID);
		this.channelID = channelID;
	}
	
	@SuppressWarnings("all")
	public boolean registerPacket(Class<? extends IMessage> clazz, AbstractMessageHandler handler, Side target) {
		if (nextPacketID == -1) throw new IllegalActorStateException("Too many packets registered for channel: " + channelID + "!");
		wrapper.registerMessage(handler, clazz, nextPacketID, target);
		nextPacketID++;
		return true;
	}
	
	@SuppressWarnings("all")
	public boolean registerBothPacket(Class<? extends IMessage> clazz, MessageHandler.BothDirectional handler) {
		if (nextPacketID == -1) throw new IllegalActorStateException("Too many packets registered for channel: " + channelID + "!");
		wrapper.registerMessage(handler, clazz, nextPacketID, Side.CLIENT);
		wrapper.registerMessage(handler, clazz, nextPacketID, Side.SERVER);
		nextPacketID++;
		return true;
	}
	
	public void sendToAll(IMessage message) {
		wrapper.sendToAll(message);
	}
	
	public void sendTo(IMessage message, EntityPlayerMP player) {
		if (player.playerNetServerHandler != null) wrapper.sendTo(message, player);
	}
	
	public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		wrapper.sendToAllAround(message, point);
	}
	
	public void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
		sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}
	
	public void sendToAllAround(IMessage message, Entity entity, double range) {
		sendToAllAround(message, entity.worldObj.provider.getDimensionId(), entity.posX, entity.posY, entity.posZ, range);
	}
	
	public void sendToDimension(IMessage message, int dimensionId) {
		wrapper.sendToDimension(message, dimensionId);
	}
	
	public void sendToServer(IMessage message) {
		wrapper.sendToServer(message);
	}
	
}
