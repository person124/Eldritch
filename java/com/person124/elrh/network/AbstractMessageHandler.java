package com.person124.elrh.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Person124
 */
abstract class AbstractMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage> {

	/**
	 * Called when specified packet is received on the client.
	 * @param player The player the packet is sent to.
	 * @param msg The packet class itself.
	 * @return Should return null.
	 */
	@SideOnly(Side.CLIENT)
	public abstract IMessage handleClientMessage(final EntityPlayer player, final T msg, final MessageContext cxt);

	/**
	 * Called when a specified packet is received on the server.
	 * @param player The player that sent the packet.
	 * @param msg The packet class itself.
	 * @return Should return null.
	 */
	public abstract IMessage handleServerMessage(final EntityPlayer player, final T msg, final MessageContext cxt);

	@SideOnly(Side.CLIENT)
	private IMessage runHandleClient(T message, MessageContext cxt) {
		return handleClientMessage(cxt.side.isClient() ? Minecraft.getMinecraft().thePlayer : cxt.getServerHandler().playerEntity, message, cxt);
	}

	public final IMessage onMessage(T message, MessageContext cxt) {
		if (cxt.side.isClient()) return runHandleClient(message, cxt);
		else return handleServerMessage(cxt.getServerHandler().playerEntity, message, cxt);
	}
}
