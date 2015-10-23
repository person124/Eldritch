package com.person124.elrh;

import com.person124.elrh.enums.EnumSimbolGods;
import com.person124.elrh.network.sync.EldritchPacketGodSync;
import com.person124.elrh.network.sync.EldritchPacketKnowledgeSync;
import com.person124.elrh.network.sync.EldritchPacketPlayerSync;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Used to store extra nbt data for the player.
 * @author Person124
 */
public class EldritchPlayerData implements IExtendedEntityProperties {

	private final EntityPlayer PLAYER;

	private boolean[] knowledge = new boolean[] { false, false, false };
	private EnumSimbolGods boundGod;

	public EldritchPlayerData(EntityPlayer player) {
		PLAYER = player;
	}

	/**
	 * Gets if the player's knowledge level at a certain point. (Must be called from the server)
	 * @param i The wanted level of knowledge value.
	 * @return true or false if player has, or doesn't have the level of knowledge.
	 */
	public boolean hasKnowledge(int i) {
		return knowledge[i];
	}

	public void setKnowledge(boolean b1, boolean b2, boolean b3) {
		knowledge = new boolean[] { b1, b2, b3 };
		sendKnowledge();
	}

	/**
	 * Set the player's knowledge at a certain point. (Must be called from the server)
	 * @param i The level of knowledge.
	 * @param b If the player should have it or not.
	 */
	public void setKnowledge(int i, boolean b) {
		knowledge[i] = b;
		sendKnowledge();
	}

	public void setKnowledge(boolean[] b) {
		knowledge = b;
		sendKnowledge();
	}

	/**
	 * Sends the new knowledge values to the client.
	 */
	private void sendKnowledge() {
		if (isServerSide()) Eldritch.packetHandler.sendTo(new EldritchPacketKnowledgeSync(knowledge), (EntityPlayerMP) PLAYER);
	}

	/**
	 * @return The bound god value of the player, or null if the player is not bound.
	 */
	public EnumSimbolGods getBoundGod() {
		return boundGod;
	}

	/**
	 * Sets the bound god value of the player. (Must be called from the server)
	 * @param god The EnumSimbolGods god value
	 */
	public void setBoundGod(EnumSimbolGods god) {
		boundGod = god;
		if (isServerSide()) Eldritch.packetHandler.sendTo(new EldritchPacketGodSync(boundGod), (EntityPlayerMP) PLAYER);
	}

	/**
	 * Gets the EldritchPlayerData for a specific player.
	 */
	public static EldritchPlayerData get(EntityPlayer player) {
		return (EldritchPlayerData) player.getExtendedProperties(Eldritch.MODID);
	}

	/**
	 * Enables the ability for new data to be added to a player.
	 * @param player
	 */
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(Eldritch.MODID, new EldritchPlayerData(player));
	}

	public boolean isServerSide() {
		return PLAYER instanceof EntityPlayerMP;
	}

	public void saveReviveRelevantNBTData(NBTTagCompound nbt, boolean wasDeath) {
		//if (!wasDeath) saveNBTData(nbt);
		saveNBTData(nbt);
	}

	/**
	 * Syncs all player data from server to the client. (Must be called from the server)
	 */
	public void syncAll() {
		if (isServerSide()) Eldritch.packetHandler.sendTo(new EldritchPacketPlayerSync(this), (EntityPlayerMP) PLAYER);
	}

	/**
	 * Requests the syncAll function to happen on the server side. (Must be called from the client)
	 */
	public void requestSyncAll() {
		if (!isServerSide()) {
			Eldritch.packetHandler.sendToServer(new EldritchPacketPlayerSync(this));
		}
	}

	public void saveNBTData(NBTTagCompound nbt) {
		NBTTagCompound comp = new NBTTagCompound();

		for (int i = 0; i < 3; i++) {
			comp.setBoolean("knowledge." + i, knowledge[i]);
		}

		comp.setByte("boundGod", boundGod != null ? (byte) boundGod.getDamage() : -1);

		nbt.setTag(Eldritch.MODID, comp);
	}

	public void loadNBTData(NBTTagCompound nbt) {
		NBTTagCompound comp = nbt.getCompoundTag(Eldritch.MODID);

		for (int i = 0; i < 3; i++) {
			knowledge[i] = comp.getBoolean("knowledge." + i);
		}

		if (comp.hasKey("boundGod")) {
			byte b = comp.getByte("boundGod");
			boundGod = b != -1 ? EnumSimbolGods.byDamage(b) : null;
		} else boundGod = null;
	}

	public void init(Entity entity, World world) {

	}

}
