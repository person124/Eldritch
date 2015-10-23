package com.person124.elrh.potions;

import com.person124.elrh.Eldritch;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * @author Person124
 */
public class EldritchPotions {

	public static final byte BASE = 124;
	public static final ResourceLocation LOCATION = new ResourceLocation(Eldritch.MODID + ":textures/gui/potions.png");

	private static final EldritchPotionBase[] P;

	static {
		P = new EldritchPotionBase[1];
		P[0] = new EldritchPotionBleed(0);
	}

	public static void triggerUpdate(EntityLivingBase e) {
		for (EldritchPotionBase p : P) {
			if (e.isPotionActive(p.id)) p.onUpdate(e);
		}
	}

	/**
	 * Get a potion based on its local id (i.e. it starts at 0, and not the actuall in game id)
	 */
	public static EldritchPotionBase getPotion(int id) {
		if (id >= P.length) return null;
		return P[id];
	}

}
