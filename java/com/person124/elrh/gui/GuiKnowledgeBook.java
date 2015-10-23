package com.person124.elrh.gui;

import java.io.IOException;
import java.util.HashMap;

import com.person124.elrh.Eldritch;
import com.person124.elrh.EldritchPlayerData;
import com.person124.elrh.enums.EnumRituals;
import com.person124.elrh.enums.EnumSimbolType;
import com.person124.elrh.enums.KnowledgeBookPages;
import com.person124.elrh.network.packet.EldritchPacketGetRitual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

/**
 * I'll explain the methods later......
 * @author Person124
 */
public class GuiKnowledgeBook extends GuiScreen {

	private static Pages lastPageOnClose = null;
	private static int lastPageNumOnClose = -1;
	private static String lastSelectedRitual = null;
	public static HashMap<Byte, String> cachedRecipes = null;

	private enum Pages {
		INDEX(),
		INTRODUCTION((byte) 1, 2),
		SIMBOLS((byte) 2, 17),
		HOW_TO_RITUAL((byte) 3, 2),
		RITUALS((byte) 4, KnowledgeBookPages.PAGE_COUNT),
		ABOUT_RITUAL(3);

		private final boolean NEED_CHANGE_BTN;
		private final int MAX_PAGES;
		private final byte ID;

		private Pages() {
			ID = -1;
			NEED_CHANGE_BTN = false;
			MAX_PAGES = 1;
		}

		private Pages(byte id) {
			ID = id;
			NEED_CHANGE_BTN = false;
			MAX_PAGES = 1;
		}

		private Pages(int maxPages) {
			ID = -1;
			NEED_CHANGE_BTN = true;
			MAX_PAGES = maxPages;
		}

		private Pages(byte id, int maxPages) {
			ID = id;
			NEED_CHANGE_BTN = true;
			MAX_PAGES = maxPages;
		}

		public static Pages byID(byte id) {
			for (Pages p : values()) {
				if (p.ID == id) return p;
			}
			return null;
		}
	}

	private final ResourceLocation BOOK_TEXTURE = new ResourceLocation(Eldritch.MODID + ":textures/gui/basic_book.png");
	private final ResourceLocation CRAFTING_GRID = new ResourceLocation("textures/gui/container/crafting_table.png");
	private final ResourceLocation RITUAL_GRID = new ResourceLocation(Eldritch.MODID + ":textures/gui/ritual_crafting_grid.png");
	private final int IMG_SIZE = 192, TEXT_IN_SIZE = 35, TEXT_DOWN_BASE = 17, TEXT_DOWN_EXTRA = 12, TEXT_WRAP = 120;
	private final int COLOR = 0x81090a, HOVER_COLOR = 0xff0000;
	private final EldritchPlayerData DATA;

	private Pages page;
	private GuiButton buttonNextPage, buttonLastPage, buttonReturnToIndex;
	private int w, h, currentPage, totalPages;
	private String selectedRitual;

	public GuiKnowledgeBook(EldritchPlayerData data) {
		DATA = data;

		if (cachedRecipes == null) cachedRecipes = new HashMap<Byte, String>();
		if (lastPageOnClose == null) updatePage(Pages.INDEX);
		else {
			updatePage(lastPageOnClose);
			currentPage = lastPageNumOnClose;
			selectedRitual = lastSelectedRitual;
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(BOOK_TEXTURE);
		//Maybe change h?
		updateWAndH();
		drawTexturedModalRect(w, h, 0, 0, IMG_SIZE, IMG_SIZE);

		if (page == Pages.INDEX) {
			int hover = -1;
			if (mouseX >= w + TEXT_IN_SIZE && mouseX <= w + TEXT_IN_SIZE + TEXT_WRAP) hover = (mouseY - h - TEXT_DOWN_BASE) / 12;

			drawTitle("book.index.name", w, h, COLOR);
			drawText("book.intro.name", 1, hover == 1 ? HOVER_COLOR : COLOR);
			drawText("book.simbols.name", 2, hover == 2 ? HOVER_COLOR : COLOR);
			drawText("book.howto.name", 3, hover == 3 ? HOVER_COLOR : COLOR);
			drawText("book.rituals.name", 4, hover == 4 ? HOVER_COLOR : COLOR);
		} else if (page == Pages.INTRODUCTION) {
			drawTitleWithPage("book.intro.name", w, h, COLOR);
			drawText("book.intro.text." + currentPage, 1, COLOR);
		} else if (page == Pages.SIMBOLS) {
			drawTitleWithPage("book.simbols.name", w, h, COLOR);
			if (currentPage != 1) {
				drawTitle("item.simbol_rune." + EnumSimbolType.byDamage(currentPage - 2).getUnlocalizedName() + ".name", w, h + TEXT_DOWN_EXTRA, COLOR);
				drawSimbolRecipe(w + 65, h + 50, EnumSimbolType.byDamage(currentPage - 2));
			} else drawText("book.simbols.text", 1, COLOR);
		} else if (page == Pages.HOW_TO_RITUAL) {
			drawTitleWithPage("book.howto.name", w, h, COLOR);

			drawText("book.howto.text." + currentPage, 1, COLOR);
		} else if (page == Pages.RITUALS) {
			drawTitle("book.rituals.name", w, h, COLOR);
			drawLocalizedText("--------------------", 1, COLOR);

			int hover = -1;
			if (mouseX >= w + TEXT_IN_SIZE && mouseX <= w + TEXT_IN_SIZE + TEXT_WRAP) hover = (mouseY - h - TEXT_DOWN_BASE) / 12;

			for (int i = 2; i < KnowledgeBookPages.getEntries(currentPage) + 2; i++) {
				if (!KnowledgeBookPages.isRitOnLine(currentPage, i)) {
					drawLocalizedText("--------------------", i, COLOR);
				} else if (KnowledgeBookPages.hasEnoughKnowledge(currentPage, i, DATA)) {
					drawText("book.rituals." + KnowledgeBookPages.getRitString(currentPage, i), i, hover == i ? HOVER_COLOR : COLOR);
				} else drawGalaticText(" youdontknowthetruth", i, COLOR);
			}
		} else if (page == Pages.ABOUT_RITUAL) {
			drawTitle("book.rituals." + selectedRitual, w, h, COLOR);

			if (currentPage != 3) drawText("book.rituals." + selectedRitual + "." + currentPage, 1, COLOR);
			else {
				drawTitle("book.rituals.recipe", w, h + TEXT_DOWN_EXTRA, COLOR);
				drawRitualRecipe(w + 54, h + 50, EnumRituals.getByReference(selectedRitual).getId());
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (page == Pages.INDEX) {
			if (mouseX >= w + TEXT_IN_SIZE && mouseX <= w + TEXT_IN_SIZE + TEXT_WRAP) {
				byte hover = (byte) ((mouseY - h - TEXT_DOWN_BASE) / 12);
				if (hover != 0) buttonNextPage.playPressSound(mc.getSoundHandler());
				updatePage(Pages.byID(hover));
			}
		} else if (page == Pages.RITUALS) {
			byte hover = (byte) ((mouseY - h - TEXT_DOWN_BASE) / 12);

			if (KnowledgeBookPages.isRitOnLine(currentPage, hover)) {
				if (KnowledgeBookPages.hasEnoughKnowledge(currentPage, hover, DATA)) {
					buttonNextPage.playPressSound(mc.getSoundHandler());
					selectedRitual = KnowledgeBookPages.getRitString(currentPage, hover);
					updatePage(Pages.ABOUT_RITUAL);
				}
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == buttonReturnToIndex.id) {
			if (page != Pages.ABOUT_RITUAL) updatePage(Pages.INDEX);
			else updatePage(Pages.RITUALS);
		} else if (button.id == buttonNextPage.id) {
			if (currentPage + 1 <= totalPages) currentPage++;
		} else if (button.id == buttonLastPage.id) {
			if (currentPage - 1 >= 1) currentPage--;
		}

		updateButtons();
	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		updateWAndH();
		buttonList.add(buttonNextPage = new NextPageButton(0, w + 120, h + 154, true));
		buttonList.add(buttonLastPage = new NextPageButton(1, w + 38, h + 154, false));
		buttonList.add(buttonReturnToIndex = new GuiButton(2, w + 70, h + 147, 40, 20, "Back"));

		updateButtons();
	}

	private void updateButtons() {
		if (buttonNextPage != null) {
			buttonNextPage.visible = page.NEED_CHANGE_BTN && currentPage != totalPages;
			buttonLastPage.visible = page.NEED_CHANGE_BTN && currentPage != 1;

			buttonReturnToIndex.visible = page != Pages.INDEX;
		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	private void updateWAndH() {
		w = (width - IMG_SIZE) / 2;
		h = (height - IMG_SIZE) / 2;
	}

	private void updatePage(Pages p) {
		if (p == null) return;
		page = p;
		totalPages = page.MAX_PAGES;
		currentPage = 1;
		if (p != Pages.ABOUT_RITUAL) selectedRitual = null;
		updateButtons();
	}

	public void onGuiClosed() {
		lastPageOnClose = page;
		lastPageNumOnClose = currentPage;
		lastSelectedRitual = selectedRitual;
	}

	private void drawTitleWithPage(String key, int baseX, int baseY, int color) {
		drawLocalizedTitleWithPage(I18n.format(key), baseX, baseY, color);
	}

	private void drawLocalizedTitleWithPage(String s, int baseX, int baseY, int color) {
		drawLocalizedTitle(s + " " + currentPage + "/" + totalPages, baseX, baseY, color);
	}

	private void drawTitle(String key, int baseX, int baseY, int color) {
		drawLocalizedTitle(I18n.format(key), baseX, baseY, color);
	}

	private void drawLocalizedTitle(String s, int baseX, int baseY, int color) {
		fontRendererObj.drawString(s, baseX + 93 - fontRendererObj.getStringWidth(s) / 2, baseY + 17, color);
	}

	private void drawTextAtCoord(String key, int x, int y, int color) {
		drawLocalizedTextAtCoord(I18n.format(key), x, y, color);
	}

	private void drawLocalizedTextAtCoord(String s, int x, int y, int color) {
		fontRendererObj.drawString(s, x, y, color);
	}

	private void drawText(String key, int rowNum, int color) {
		drawLocalizedText(I18n.format(key), rowNum, color);
	}

	private void drawLocalizedText(String s, int rowNum, int color) {
		fontRendererObj.drawSplitString(s, w + TEXT_IN_SIZE, h + TEXT_DOWN_BASE + TEXT_DOWN_EXTRA * rowNum, TEXT_WRAP, color);
	}

	private void drawGalaticText(String s, int rowNum, int color) {
		mc.standardGalacticFontRenderer.drawString(s, w + TEXT_IN_SIZE, h + TEXT_DOWN_BASE + TEXT_DOWN_EXTRA * rowNum, color);
	}

	private void drawSimbolRecipe(int wid, int hei, EnumSimbolType type) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(CRAFTING_GRID);
		drawTexturedModalRect(wid, hei, 28, 15, 56, 56);

		ResourceLocation putty = new ResourceLocation(Eldritch.MODID + ":textures/items/simbol_putty.png");
		ResourceLocation center = new ResourceLocation(Eldritch.MODID + ":textures/items/simbol_putty_center.png");

		int i = 0;
		for (String s : type.getRecipe()) {
			for (char c : s.toCharArray()) {
				if (c != ' ') {
					mc.getTextureManager().bindTexture(c == '*' ? putty : center);
					drawModalRectWithCustomSizedTexture(wid + 2 + (18 * (i % 3)), hei + 2 + (18 * (i / 3)), 0, 0, 16, 16, 16, 16);
				}

				i++;
			}
		}

		mc.getTextureManager().bindTexture(CRAFTING_GRID);
		drawTexturedModalRect(wid + 14, hei + 60, 118, 29, 28, 28);
		mc.getTextureManager().bindTexture(new ResourceLocation(Eldritch.MODID + ":textures/items/simbol_rune_" + type.getUnlocalizedName() + ".png"));
		drawModalRectWithCustomSizedTexture(wid + 20, hei + 66, 0, 0, 16, 16, 16, 16);
	}

	private void drawRitualRecipe(int wid, int hei, byte id) {
		if (!cachedRecipes.containsKey(id) || cachedRecipes.get(id) == null) {
			Eldritch.packetHandler.sendToServer(new EldritchPacketGetRitual(id));
			return;
		}

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(RITUAL_GRID);
		drawTexturedModalRect(wid, hei, 0, 0, 80, 80);

		int x = 0, y = 0;
		for (char c : cachedRecipes.get(id).toCharArray()) {
			if (c != '-') {
				int xp = wid + 16 + (16 * x);
				int yp = hei + 16 + (16 * y);

				if (c == '!') {
					mc.getTextureManager().bindTexture(BOOK_TEXTURE);
					drawTexturedModalRect(xp, yp, 49, 194, 16, 16);
				} else {
					mc.getTextureManager().bindTexture(new ResourceLocation(Eldritch.MODID + ":textures/items/simbol_rune_" + EnumSimbolType.byChar(c).getUnlocalizedName() + ".png"));
					drawModalRectWithCustomSizedTexture(xp, yp, 0, 0, 16, 16, 16, 16);
				}
			}

			x++;
			if (y == 1 && x == 1) x++;
			if (x == 3) {
				x = 0;
				y++;
			}
		}
	}

	public static void onPlayerLeave() {
		lastPageOnClose = null;
		lastPageNumOnClose = -1;
		lastSelectedRitual = null;
		cachedRecipes = null;
	}

	private class NextPageButton extends GuiButton {

		private boolean isNext;

		public NextPageButton(int id, int x, int y, boolean next) {
			super(id, x, y, 23, 13, "");
			isNext = next;
		}

		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			if (visible) {
				boolean hover = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(BOOK_TEXTURE);

				int textureX = hover ? 23 : 0, textureY = 192 + (!isNext ? 13 : 0);
				drawTexturedModalRect(xPosition, yPosition, textureX, textureY, this.width, this.height);
			}
		}

	}

}
