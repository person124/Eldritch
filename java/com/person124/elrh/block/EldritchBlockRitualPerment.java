package com.person124.elrh.block;

import net.minecraft.block.material.Material;

public class EldritchBlockRitualPerment extends EldritchBlock {

	public EldritchBlockRitualPerment(String name) {
		super(Material.iron, name);
		setStepSound(soundTypeStone);
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
		setTickRandomly(true);
		setBlockUnbreakable();
	}
	
	public boolean isFullCube() {
		return false;
	}

}
