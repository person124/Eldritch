package com.person124.elrh.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler {

	public static abstract class Client<T extends IMessage> extends AbstractMessageHandler<T> {
		public final IMessage handleServerMessage(final EntityPlayer player, final T packet, MessageContext cxt) {
			return null;
		}
	}

	public static abstract class Server<T extends IMessage> extends AbstractMessageHandler<T> {
		public final IMessage handleClientMessage(EntityPlayer player, T packet, MessageContext cxt) {
			return null;
		}
	}

	public static abstract class BothDirectional<T extends IMessage> extends AbstractMessageHandler<T> {

	}

}
