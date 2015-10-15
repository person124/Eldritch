package com.person124.elrh.enums;

import com.person124.elrh.EldritchPlayerData;

public class KnowledgeBookPages {

	private static Page[] pages;

	static {
		pages = new Page[1];

		pages[0] = new Page(EnumRituals.KNOWLEDGE, null, EnumRituals.SUNNY);
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
		switch (know) {
			case NONE:
				return true;
			case ONE:
				return data.hasBasicKnowledge();
			default:
				return false;
		}
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
