package com.person124.elrh.enums;

import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.enums.EnumRituals.KnowRequirement;

/**
 * @author Person124
 */
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

	/**
	 * @return A ritual from a specified line and page.
	 */
	public static EnumRituals getLine(int page, int line) {
		return getPage(page).getLine(line - 2);
	}

	/**
	 * @return If a ritual is on specified line and page.
	 */
	public static boolean isRitOnLine(int page, int line) {
		return getLine(page, line) != null;
	}

	/**
	 * @return The total number of entries on a specified page.
	 */
	public static int getEntries(int page) {
		return getPage(page).getLength();
	}

	/**
	 * @return The ritual on the specified page and line's knowledge value, or if there is no ritual, it returns no requirement.
	 */
	public static EnumRituals.KnowRequirement needsKnowledge(int page, int line) {
		if (isRitOnLine(page, line)) return getLine(page, line).getKnowRequirement();
		else return EnumRituals.KnowRequirement.NONE;
	}

	/**
	 * @return The ritual on specified page and line's reference string.
	 */
	public static String getRitString(int page, int line) {
		return getLine(page, line).getReference();
	}

	/**
	 * @param data The player's EldritchPlayerData.
	 * @return If a player has enough knowledge to perform ritual on specified line and page.
	 */
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
