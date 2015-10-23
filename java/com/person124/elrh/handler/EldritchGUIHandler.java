package com.person124.elrh.handler;

import java.lang.reflect.InvocationTargetException;

import com.person124.elrh.Eldritch;
import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.gui.GuiKnowledgeBook;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * @author Person124
 */
public class EldritchGuiHandler implements IGuiHandler {

	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return GUIs.byID(id).getInstance(player);
	}

	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return GUIs.byID(id).getInstance(player);
	}

	public enum GUIs {
		KNOWLEDGE_BOOK(0, GuiKnowledgeBook.class);

		private int id;
		private Class<? extends GuiScreen> clazz;

		private GUIs(int id, Class<? extends GuiScreen> c) {
			this.id = id;
			clazz = c;
		}

		public void openGUI(boolean isClient, World world, EntityPlayer player) {
			if (world.isRemote == isClient) {
				player.openGui(Eldritch.instance, id, world, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
		}

		public Object getInstance(EntityPlayer p) {
			try {
				return clazz.getConstructor(EldritchPlayerData.class).newInstance(EldritchPlayerData.get(p));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static GUIs byID(int i) {
			for (GUIs g : values()) {
				if (g.id == i) return g;
			}
			return null;
		}
	}

}
