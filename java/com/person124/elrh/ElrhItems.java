package com.person124.elrh;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.person124.elrh.enums.EnumSimbolGods;
import com.person124.elrh.enums.EnumSimbolType;
import com.person124.elrh.item.EldritchItem;
import com.person124.elrh.item.EldritchItemBook;
import com.person124.elrh.item.EldritchItemGodSimbol;
import com.person124.elrh.item.EldritchItemPutty;
import com.person124.elrh.item.EldritchItemSimbol;

public class ElrhItems {

	public static final EldritchItem SIMBOL_PUTTY = new EldritchItemPutty("simbol_putty", ElrhBlocks.RITUAL_BORDER);
	public static final EldritchItem SIMBOL_PUTTY_CENTER = new EldritchItemPutty("simbol_putty_center", ElrhBlocks.RITUAL_CENTER).setMaxStackSize(1);
	public static final EldritchItem SIMBOLS = new EldritchItemSimbol("simbol_rune").setMaxStackSize(16);
	public static final EldritchItem RITUAL_SICK = new EldritchItem("ritual_stick").setMaxStackSize(1);
	public static final EldritchItem SIMBOLS_GODS = new EldritchItemGodSimbol("simbol_god").setMaxStackSize(16);
	public static final EldritchItem BOOK_OF_KNOWLEDGE = new EldritchItemBook("knowledge_book").setMaxStackSize(1);
	public static final EldritchItem BOTTLE_BLOOD = new EldritchItem("bottle_blood").setMaxStackSize(1);

	public static void init() {
		register(SIMBOL_PUTTY);
		for (int i = 0; i < 16; i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(SIMBOL_PUTTY, 2), new ItemStack(Items.clay_ball), new ItemStack(Items.dye, 1, i));
		}

		register(SIMBOL_PUTTY_CENTER);
		GameRegistry.addShapedRecipe(new ItemStack(SIMBOL_PUTTY_CENTER), "**", "**", '*', new ItemStack(SIMBOL_PUTTY));

		register(SIMBOLS, EnumSimbolType.class);
		for (int i = 0; i < 16; i++) {
			GameRegistry.addShapedRecipe(new ItemStack(SIMBOLS, 1, i), EnumSimbolType.byDamage(i).getCraftingRecipe());
		}

		register(RITUAL_SICK);
		GameRegistry.addShapelessRecipe(new ItemStack(RITUAL_SICK), new ItemStack(SIMBOL_PUTTY_CENTER), new ItemStack(Items.stick));

		register(SIMBOLS_GODS, EnumSimbolGods.class);
		for (int i = 0; i < 4; i++) {
			GameRegistry.addShapedRecipe(new ItemStack(SIMBOLS_GODS, 1, i), EnumSimbolGods.byDamage(i).getRecipe());
		}

		register(BOOK_OF_KNOWLEDGE);
		GameRegistry.addShapelessRecipe(new ItemStack(BOOK_OF_KNOWLEDGE), new ItemStack(BOTTLE_BLOOD), new ItemStack(Items.feather), new ItemStack(Items.book));

		register(BOTTLE_BLOOD);
	}

	private static void register(EldritchItem item) {
		GameRegistry.registerItem(item, item.getName());

		if (Eldritch.isClient) {
			Eldritch.renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Eldritch.MODID + ":" + item.getName(), "inventory"));
		}
	}

	private static void register(EldritchItem item, Class<? extends Enum<?>> c) {
		GameRegistry.registerItem(item, item.getName());

		try {
			if (Eldritch.isClient) {
				Enum<?>[] e = c.getEnumConstants();

				String[] temp = new String[e.length];
				for (int i = 0; i < e.length; i++) {
					String s = Eldritch.MODID + ":" + item.getName() + "_" + e[i].toString();
					Eldritch.renderItem.getItemModelMesher().register(item, i, new ModelResourceLocation(s, "inventory"));
					temp[i] = s;
				}

				ModelBakery.addVariantName(item, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
