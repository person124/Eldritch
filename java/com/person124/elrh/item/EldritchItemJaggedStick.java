package com.person124.elrh.item;

import com.person124.elrh.potions.EldritchPotions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EldritchItemJaggedStick extends EldritchItem {

	public EldritchItemJaggedStick(String name) {
		super(name);
		setMaxDamage(Item.ToolMaterial.WOOD.getMaxUses());
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		PotionEffect potion = new PotionEffect(EldritchPotions.getPotion(0).id, 100);
		potion.setCurativeItems(null);
		target.addPotionEffect(potion);
		
		stack.damageItem(1, attacker);
		
		return false;
	}

}
