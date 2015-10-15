package com.person124.elrh.handler.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class HandlerRitualBase {
	
	public final byte ID;
	
	public HandlerRitualBase(byte id) {
		ID = id;
	}
	
	public boolean execute(BlockPos pos, EntityPlayer player) {
		return false;
	}

}
