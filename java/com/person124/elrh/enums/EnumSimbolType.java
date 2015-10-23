package com.person124.elrh.enums;

import com.person124.elrh.Eldritch;
import com.person124.elrh.ElrhItems;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

/**
 * @author Person124
 */
public enum EnumSimbolType implements IStringSerializable {

	//God == '!'
	SQUARE(0, "square", 'J', "***", "*#*", "***"),
	ONE(1, "one", 'G', "*#"),
	TWO(2, "two", 'd', "*#*"),
	THREE(3, "three", 'N', "*#*", " * "),
	FOUR(4, "four", 's', " * ", "*#*", " * "),
	HORI(5, "horizontal", 'k', "***", " # "),
	VERT(6, "vertical", 'l', " * ", " *#", " * "),
	BI_HORI(7, "bi_horizontal", 'Y', "***", " # ", "***"),
	BI_VERT(8, "bi_vertical", '6', "* *", "*#*", "* *"),
	RECT(9, "rectangle", 'V', "***", "***", " # "),
	TRI(10, "triangle", 'L', " * ", "*#*"),
	CORNERS(11, "corners", 'i', "* *", " # ", "* *"),
	U(12, "u", 'm', "* *", "*#*", "***"),
	N(13, "n", '7', "***", "*#*", "* *"),
	FOR(14, "forward", 'A', " #*", " * ", "*  "),
	BACK(15, "backward", '4', "*  ", " * ", " #*");

	public static final char GOD_CHAR = '!';

	private static final EnumSimbolType[] DMG_LOOKUP = new EnumSimbolType[values().length];

	private final int DAMAGE;
	private final String UNLOC_NAME;
	private final char CHAR;
	private final String[] CRAFTING;

	private EnumSimbolType(int meta, String unlocName, char c, String... craft) {
		DAMAGE = meta;
		CHAR = c;
		UNLOC_NAME = unlocName;
		CRAFTING = craft;
	}

	public int getDamage() {
		return DAMAGE;
	}

	public String getUnlocalizedName() {
		return UNLOC_NAME;
	}

	public char getChar() {
		return CHAR;
	}

	public String toString() {
		return UNLOC_NAME;
	}

	public String[] getRecipe() {
		return CRAFTING;
	}

	public String getName() {
		return UNLOC_NAME;
	}

	public Object[] getCraftingRecipe() {
		Object[] objs = new Object[4 + CRAFTING.length];
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

		objs[i] = '*';
		i++;
		objs[i] = new ItemStack(ElrhItems.SIMBOL_PUTTY);
		i++;

		objs[i] = '#';
		i++;
		objs[i] = new ItemStack(ElrhItems.SIMBOL_PUTTY_CENTER);
		i++;

		return objs;
	}

	/**
	 * Returns a simbol based on damage values.
	 */
	public static EnumSimbolType byDamage(int damage) {
		if (damage < 0 || damage >= DMG_LOOKUP.length) damage = 0;

		return DMG_LOOKUP[damage];
	}

	/**
	 * Returns a simbol based on the char value.
	 */
	public static EnumSimbolType byChar(char c) {
		for (EnumSimbolType t : values()) {
			if (t.getChar() == c) return t;
		}
		return null;
	}

	public static char getRandomChar() {
		return values()[Eldritch.RAND.nextInt(16)].getChar();
	}

	static {
		EnumSimbolType[] types = values();

		for (int i = 0; i < types.length; i++) {
			EnumSimbolType type = types[i];
			DMG_LOOKUP[type.getDamage()] = type;
		}
	}

}
