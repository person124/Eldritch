package com.person124.elrh.handler.ritual;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.ElrhItems;
import com.person124.elrh.enums.EnumRituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

/**
 * @author Person124
 */
public class HandlerRitualKnowledge extends HandlerRitual {

	public HandlerRitualKnowledge() {
		super(EnumRituals.KNOWLEDGE.getId());
	}

	public boolean go(BlockPos pos, EntityPlayer player) {
		if (player.inventory.hasItem(ElrhItems.BOOK_OF_KNOWLEDGE) && !EldritchPlayerData.get(player).hasKnowledge(EnumRituals.KnowRequirement.ONE.getValue())) {
			EldritchPlayerData p = EldritchPlayerData.get(player);
			p.setKnowledge(EnumRituals.KnowRequirement.ONE.getValue(), true);
			p.syncAll();
			return true;
		}
		return false;
	}

}
