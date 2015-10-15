package com.person124.elrh.enums;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import com.person124.elrh.ElrhItems;

public enum EnumSimbolGods implements IStringSerializable {

	DAMIEN(0, "damien", EnumSimbolType.CORNERS.getChar() + "" + EnumSimbolType.HORI.getChar() + " ", " " + EnumSimbolType.VERT.getChar() + " ", " " + EnumSimbolType.HORI.getChar() + " "),
	THORN(1, "thorn", EnumSimbolType.BI_VERT.getChar() + "" + EnumSimbolType.BI_HORI.getChar(), EnumSimbolType.BI_HORI.getChar() + "" + EnumSimbolType.BI_VERT.getChar()),
	RISK(2, "risk", " " + EnumSimbolType.TRI.getChar() + " ", EnumSimbolType.VERT.getChar() + "" + EnumSimbolType.TWO.getChar() + "" + EnumSimbolType.VERT.getChar(), " " + EnumSimbolType.THREE.getChar() + " "),
	ENOON(3, "enoon", " " + EnumSimbolType.U.getChar() + " ", EnumSimbolType.BI_HORI.getChar() + "" + EnumSimbolType.BI_VERT.getChar() + "" + EnumSimbolType.BI_HORI.getChar(), " " + EnumSimbolType.N.getChar() + " ");

	private static final EnumSimbolGods[] DMG_LOOKUP = new EnumSimbolGods[values().length];

	private final int DAMAGE;
	private final String UNLOC_NAME;
	private final String[] CRAFTING;

	private EnumSimbolGods(int damage, String unloc, String... craft) {
		DAMAGE = damage;
		UNLOC_NAME = unloc;
		CRAFTING = craft;
	}

	public Object[] getRecipe() {
		HashMap<Character, Integer> chars = new HashMap<Character, Integer>();

		for (String s : CRAFTING) {
			for (char c : s.toCharArray()) {
				if (c != ' ' && !chars.containsKey(c)) chars.put(c, EnumSimbolType.byChar(c).getDamage());
			}
		}

		Object[] objs = new Object[CRAFTING.length + chars.size() * 2];
		int i = 0;

		objs[i] = CRAFTING[0];
		i++;
		if (CRAFTING.length > 1) {
			objs[i] = CRAFTING[1];
			i++;
		}
		if (CRAFTING.length > 2) {
			objs[i] = CRAFTING[2];
			i++;
		}

		for (char c : chars.keySet()) {
			objs[i] = c;
			i++;
			objs[i] = new ItemStack(ElrhItems.SIMBOLS, 1, chars.get(c));
			i++;
		}

		return objs;
	}

	public int getDamage() {
		return DAMAGE;
	}

	public String getUnlocalizedName() {
		return UNLOC_NAME;
	}

	public String toString() {
		return UNLOC_NAME;
	}

	public String getName() {
		return UNLOC_NAME;
	}

	public static EnumSimbolGods byDamage(int damage) {
		for (EnumSimbolGods g : values()) {
			if (g.getDamage() == damage) return g;
		}
		return null;
	}

	static {
		EnumSimbolGods[] types = values();

		for (int i = 0; i < types.length; i++) {
			EnumSimbolGods type = types[i];
			DMG_LOOKUP[type.getDamage()] = type;
		}
	}

}