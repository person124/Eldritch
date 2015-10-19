package com.person124.elrh.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class EldritchPotionBleed extends EldritchPotionBase {
	
	public EldritchPotionBleed() {
		super(EldritchPotions.BASE + 0, "bleed", true, 0xff0000);
		setTextureIndex(0, 0);
	}
	
	public void onUpdate(EntityLivingBase e) {
		e.attackEntityFrom(DamageSource.magic, 1f);
	}

}