package com.person124.elrh.block;

import com.person124.elrh.Eldritch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * All eldritch mod blocks should extend this.
 * @author Person124
 */
public class EldritchBlock extends Block {

	public EldritchBlock(Material mat, String name) {
		super(mat);
		setUnlocalizedName(name);
		setCreativeTab(Eldritch.TAB);
	}

	/**
	 * @return The unlocalized name of the block in a format that can be used by minecraft.
	 */
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

	/**
	 * @param tool Tool options are: "pickaxe" "axe" "shovel"
	 * @param level 0=Wood, 1=Stone, 2=Iron, 3=Diamond, 0=Gold
	 * @return
	 */
	public EldritchBlock setHarvestLevelNew(String tool, int level) {
		super.setHarvestLevel(tool, level);
		return this;
	}

}
