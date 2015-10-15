package com.person124.elrh.handler.ritual;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.ElrhItems;
import com.person124.elrh.enums.EnumRituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class HandlerRitualKnowledge extends HandlerRitualBase {

	public HandlerRitualKnowledge() {
		super(EnumRituals.KNOWLEDGE.getId());
	}
	
	public boolean execute(BlockPos pos, EntityPlayer player) {
		if (player.inventory.hasItem(ElrhItems.BOOK_OF_KNOWLEDGE) && !EldritchPlayerData.get(player).hasBasicKnowledge()) {
			EldritchPlayerData p = EldritchPlayerData.get(player);
			p.setHasBasicKnowledge(true);
			p.syncAll();
			return true;
		}
		return false;
	}

}
