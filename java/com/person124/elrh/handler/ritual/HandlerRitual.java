package com.person124.elrh.handler.ritual;

import com.person124.elrh.enums.EnumRituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

/**
 * @author Person124
 */
public class HandlerRitual {

	public final byte ID;

	/**
	 * @param id The id of the ritual this handler is for.
	 */
	public HandlerRitual(byte id) {
		ID = id;
	}

	/**
	 * Attempts to perform a ritual. <b>This method shouldn't be overridden unless you know what you are doing.</b>
	 * @param pos The BlockPos of the center ritual block.
	 * @param player The player doing the ritual.
	 * @return If the ritual was successful or not.
	 */
	public boolean execute(BlockPos pos, EntityPlayer player) {
		if (!EnumRituals.getById(ID).getKnowRequirement().canPlayerPerform(player)) return false;
		return go(pos, player);
	}

	/**
	 * The Handler specific ritual attempt method. <b>This is the method you override.</b>
	 * @param pos The BlockPos of the center ritual block.
	 * @param player The player doing the ritual.
	 * @return If the ritual was successful or not.
	 */
	protected boolean go(BlockPos pos, EntityPlayer player) {
		return false;
	}

}
