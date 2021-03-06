package com.person124.elrh;

import java.util.HashMap;
import java.util.Map;

import com.person124.elrh.enums.EnumRituals;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

/**
 * Saves additional data per world save.
 * @author Person124
 */
public class EldritchWorldSaveData extends WorldSavedData {

	public static final byte VERSION = 8;

	private byte version;
	private HashMap<Byte, String> recipes;

	public EldritchWorldSaveData() {
		super(Eldritch.MODID);
	}

	public EldritchWorldSaveData(String name) {
		this();
	}

	public void generate() {
		if (recipes == null) recipes = new HashMap<Byte, String>();
		for (EnumRituals r : EnumRituals.values()) {
			if (recipes.get(r.getId()) == null) {
				String recipe;
				while (true) {
					recipe = r.generateRecipe();
					if (!recipes.containsValue(recipe)) break;
				}
				recipes.put(r.getId(), recipe);
			}
		}

		version = VERSION;

		markDirty();
	}

	public void readFromNBT(NBTTagCompound nbt) {
		if (!nbt.hasKey("version")) version = (byte) 0;
		else {
			recipes = new HashMap<Byte, String>();
			for (EnumRituals r : EnumRituals.values()) {
				recipes.put(r.getId(), nbt.hasKey(r.getReference()) ? nbt.getString(r.getReference()) : null);
			}

			version = nbt.getByte("version");
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setByte("version", version);

		for (byte b : recipes.keySet()) {
			if (recipes.get(b) != null) nbt.setString(EnumRituals.getById(b).getReference(), recipes.get(b));
		}
	}

	public byte getVersion() {
		return version;
	}

	public boolean isCurrent() {
		return version == VERSION;
	}

	/**
	 * @return All saved recipes as a HashMap of Bytes and Strings.
	 */
	public HashMap<Byte, String> getRecipes() {
		return recipes;
	}

	/**
	 * Takes a recipe input and outputs the matching EnumRitual.
	 * @param s The recipe as a string.
	 */
	public EnumRituals matchRecipe(String s) {
		for (Map.Entry<Byte, String> i : recipes.entrySet()) {
			if (i.getValue().equals(s)) return EnumRituals.getById(i.getKey());
		}
		return null;
	}

	/**
	 * Takes a byte input and outputs the matching recipe as a string.
	 */
	public String getRecipeFromID(byte id) {
		if (!recipes.containsKey(id)) return null;
		return recipes.get(id);
	}

}
