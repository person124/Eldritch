package com.person124.elrh.block;

import com.person124.elrh.Eldritch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class EldritchBlock extends Block {

	public EldritchBlock(Material mat, String name) {
		super(mat);
		setUnlocalizedName(name);
		setCreativeTab(Eldritch.TAB);
	}

	public String getName() {
		return getUnlocalizedName().replace("tile.", "");
	}

	public EldritchBlock setHardness(float hard) {
		super.setHardness(hard);
		return this;
	}

	public EldritchBlock setLightLevel(float light) {
		super.setLightLevel(light);
		return this;
	}

	public EldritchBlock setBlockUnbreakable() {
		super.setBlockUnbreakable();
		return this;
	}

	public EldritchBlock setStepSound(SoundType sound) {
		super.setStepSound(sound);
		return this;
	}

	public EldritchBlock setHarvestLevelNew(String tool, int level) {
		super.setHarvestLevel(tool, level);
		return this;
	}

}
