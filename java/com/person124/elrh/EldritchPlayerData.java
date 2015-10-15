package com.person124.elrh;

import com.person124.elrh.network.sync.EldritchPacketBooleanSync;
import com.person124.elrh.network.sync.EldritchPacketPlayerSync;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EldritchPlayerData implements IExtendedEntityProperties {

	private final EntityPlayer PLAYER;

	private boolean hasBasicKnowledge = false;

	public EldritchPlayerData(EntityPlayer player) {
		PLAYER = player;
	}

	public void setHasBasicKnowledge(boolean b) {
		hasBasicKnowledge = b;
		if (isServerSide()) Eldritch.packetHandler.sendTo(new EldritchPacketBooleanSync(hasBasicKnowledge), (EntityPlayerMP) PLAYER);
	}

	public boolean hasBasicKnowledge() {
		return hasBasicKnowledge;
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
		if (!wasDeath) saveNBTData(nbt);
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
		nbt.setBoolean("basicKnowledge", hasBasicKnowledge);
	}

	public void loadNBTData(NBTTagCompound nbt) {
		hasBasicKnowledge = nbt.getBoolean("basicKnowledge");
	}

	public void init(Entity entity, World world) {

	}

}
