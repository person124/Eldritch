package com.person124.elrh.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumBloodLevel implements IStringSerializable {

	NONE(0, "none"),
	LOW(1, "low"),
	MEDIUM(2, "medium"),
	FULL(3, "full");

	private static final EnumBloodLevel[] DMG_LOOKUP = new EnumBloodLevel[values().length];

	private final int DAMAGE;
	private final String NAME;

	private EnumBloodLevel(int meta, String name) {
		DAMAGE = meta;
		NAME = name;
	}

	public int getDamage() {
		return DAMAGE;
	}

	public String getName() {
		return NAME;
	}
	
	public String toString() {
		return NAME;
	}

	public static EnumBloodLevel byDamage(int damage) {
		if (damage < 0 || damage >= DMG_LOOKUP.length) damage = 0;

		return DMG_LOOKUP[damage];
	}

	public static EnumBloodLevel byName(String name) {
		for (EnumBloodLevel b : values()) {
			if (b.NAME.equals(name)) return b;
		}
		return null;
	}

	static {
		EnumBloodLevel[] types = values();

		for (int i = 0; i < types.length; i++) {
			EnumBloodLevel type = types[i];
			DMG_LOOKUP[type.getDamage()] = type;
		}
	}

}
