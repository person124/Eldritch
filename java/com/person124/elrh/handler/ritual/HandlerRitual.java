package com.person124.elrh.handler.ritual;

import com.person124.elrh.enums.EnumRituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class HandlerRitual {
	
	public final byte ID;
	
	public HandlerRitual(byte id) {
		ID = id;
	}
	
	public boolean execute(BlockPos pos, EntityPlayer player) {
		if (!EnumRituals.getById(ID).getKnowRequirement().canPlayerCast(player)) return false;
		return go(pos, player);
	}
	
	protected boolean go(BlockPos pos, EntityPlayer player) {
		return false;
	}

}
