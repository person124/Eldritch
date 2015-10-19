package com.person124.elrh.enums;

import com.person124.elrh.Eldritch;
import com.person124.elrh.handler.RitualHandler;
import com.person124.elrh.handler.ritual.HandlerRitualBase;
import com.person124.elrh.handler.ritual.HandlerRitualKnowledge;
import com.person124.elrh.handler.ritual.HandlerRitualMoony;
import com.person124.elrh.handler.ritual.HandlerRitualSunny;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public enum EnumRituals {

	KNOWLEDGE(0, 2, "knowledge", HandlerRitualKnowledge.class, Importance.SMALL, KnowRequirement.NONE),
	SUNNY(1, 3, "sunny", HandlerRitualSunny.class, Importance.SMALL, KnowRequirement.ONE),
	MOONY(2, 3, "moony", HandlerRitualMoony.class, Importance.SMALL, KnowRequirement.ONE);

	public static final RitualHandler HANDLER = new RitualHandler();

	private byte id, complexity;
	private String reference;
	private Importance impor;
	private KnowRequirement know;
	private boolean god;
	private Class<? extends HandlerRitualBase> handler;

	private EnumRituals(int id, int complex, String ref, Class<? extends HandlerRitualBase> clazz, Importance impor, KnowRequirement know) {
		this.id = (byte) id;
		complexity = (byte) (complex <= 8 ? complex : 8);
		reference = ref;
		handler = clazz;
		this.impor = impor;
		this.know = know;
		god = false;
	}

	private EnumRituals(int id, int complex, boolean god, String ref, Class<? extends HandlerRitualBase> clazz, Importance impor, KnowRequirement know) {
		this.id = (byte) id;
		complexity = (byte) (complex <= 8 ? complex : 8);
		this.god = god;
		reference = ref;
		handler = clazz;
		this.impor = impor;
		this.know = know;
	}

	public String generateRecipe() {
		char[] temp = "--------".toCharArray();

		byte i = 0;
		while (i < complexity) {
			int place = Eldritch.RAND.nextInt(8);
			if (temp[place] == '-') {
				i++;
				temp[place] = EnumSimbolType.getRandomChar();
			}
		}

		if (god) {
			while (true) {
				if (complexity == 8) {
					temp[Eldritch.RAND.nextInt(8)] = EnumSimbolType.GOD_CHAR;
					break;
				} else {
					int place = Eldritch.RAND.nextInt(8);
					if (temp[place] != '-') {
						temp[place] = EnumSimbolType.GOD_CHAR;
						break;
					}
				}
			}
		}

		return new String(temp);
	}

	public void execute(BlockPos pos, EntityPlayer player) {
		HANDLER.execute(id, pos, player);
	}
	
	public Class<? extends HandlerRitualBase> getHandler() {
		return handler;
	}

	public static EnumRituals getById(byte b) {
		for (EnumRituals r : values()) {
			if (r.id == b) return r;
		}
		return null;
	}

	public static EnumRituals getByReference(String ref) {
		for (EnumRituals r : values()) {
			if (r.reference.equals(ref)) return r;
		}
		return null;
	}

	public byte getId() {
		return id;
	}

	public byte getComplexity() {
		return complexity;
	}

	public boolean hasGod() {
		return god;
	}

	public String getReference() {
		return reference;
	}

	public Importance getImportance() {
		return impor;
	}

	public KnowRequirement getKnowRequirement() {
		return know;
	}

	public enum Importance {
		SMALL,
		MEDIUM,
		LARGE,
		HS;
	}

	public enum KnowRequirement {
		NONE,
		ONE,
		TWO,
		THREE;
	}

}
