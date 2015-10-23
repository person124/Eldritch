package com.person124.elrh.enums;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.enums.EnumRituals.KnowRequirement;

public class KnowledgeBookPages {

	public static final byte PAGE_COUNT = 1;
	private static Page[] pages;

	static {
		pages = new Page[PAGE_COUNT];

		pages[0] = new Page(EnumRituals.KNOWLEDGE, null, EnumRituals.SUNNY, EnumRituals.MOONY, EnumRituals.BIND, null, EnumRituals.STORMY);
	}

	public static Page getPage(int i) {
		return pages[i - 1];
	}

	public static EnumRituals getLine(int page, int line) {
		return getPage(page).getLine(line - 2);
	}

	public static boolean isRitOnLine(int page, int line) {
		return getLine(page, line) != null;
	}

	public static int getEntires(int page) {
		return getPage(page).getLength();
	}

	public static EnumRituals.KnowRequirement needsKnowledge(int page, int line) {
		if (isRitOnLine(page, line)) return getLine(page, line).getKnowRequirement();
		else return EnumRituals.KnowRequirement.NONE;
	}

	public static String getRitString(int page, int line) {
		return getLine(page, line).getReference();
	}

	public static boolean hasEnoughKnowledge(int page, int line, EldritchPlayerData data) {
		EnumRituals.KnowRequirement know = needsKnowledge(page, line);
		if (know == KnowRequirement.NONE) return true;
		return data.hasKnowledge(know.getValue());
	}

	private static class Page {

		private EnumRituals[] rits;

		public Page(EnumRituals... rits) {
			this.rits = rits;
		}

		public EnumRituals getLine(int i) {
			if (i >= rits.length || i < 0) return null;
			return rits[i];
		}

		public int getLength() {
			return rits.length;
		}
	}

}
