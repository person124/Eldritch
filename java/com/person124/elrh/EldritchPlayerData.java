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

public class EldritchPlayerData implements IExtendedEntityProperties {

	private final EntityPlayer PLAYER;

	private boolean[] knowledge = new boolean[] { false, false, false };
	private EnumSimbolGods boundGod;

	public EldritchPlayerData(EntityPlayer player) {
		PLAYER = player;
	}

	public boolean hasKnowledge(int i) {
		return knowledge[i];
	}

	public void setKnowledge(boolean b1, boolean b2, boolean b3) {
		knowledge = new boolean[] { b1, b2, b3 };
		sendKnowledge();
	}

	public void setKnowledge(int i, boolean b) {
		knowledge[i] = b;
		sendKnowledge();
	}

	public void setKnowledge(boolean[] b) {
		knowledge = b;
		sendKnowledge();
	}

	private void sendKnowledge() {
		if (isServerSide()) Eldritch.packetHandler.sendTo(new EldritchPacketKnowledgeSync(knowledge), (EntityPlayerMP) PLAYER);
	}

	public EnumSimbolGods getBoundGod() {
		return boundGod;
	}

	public void setBoundGod(EnumSimbolGods god) {
		boundGod = god;
		if (isServerSide()) Eldritch.packetHandler.sendTo(new EldritchPacketGodSync(boundGod), (EntityPlayerMP) PLAYER);
	}

	public static EldritchPlayerData get(EntityPlayer player) {
		return (EldritchPlayerData) player.getExtendedProperties(Eldritch.MODID);
	}

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

	public void syncAll() {
		if (isServerSide()) Eldritch.packetHandler.sendTo(new EldritchPacketPlayerSync(this), (EntityPlayerMP) PLAYER);
	}

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
