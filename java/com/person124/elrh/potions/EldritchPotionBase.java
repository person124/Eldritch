package com.person124.elrh.potions;

import com.person124.elrh.Eldritch;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EldritchPotionBase extends Potion {

	private static byte textureX, textureY;

	public EldritchPotionBase(int id, String name, boolean badEffect, int potionColor) {
		super(EldritchPotions.BASE + id, new ResourceLocation(Eldritch.MODID + ":" + name), badEffect, potionColor);
		setPotionName("potion." + name);
	}

	public static void setTextureIndex(int x, int y) {
		textureX = (byte) (x * 18);
		textureY = (byte) (y * 18);
	}

	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		mc.getTextureManager().bindTexture(EldritchPotions.LOCATION);
		mc.currentScreen.drawTexturedModalRect(x + 6, y + 6, textureX, textureY, 18, 18);
	}

	public void onUpdate(EntityLivingBase e) {

	}

}
