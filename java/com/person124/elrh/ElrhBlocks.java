package com.person124.elrh;

import com.person124.elrh.block.EldritchBlock;
import com.person124.elrh.block.EldritchBlockRitualBorder;
import com.person124.elrh.block.EldritchBlockRitualCenter;
import com.person124.elrh.block.EldritchBlockRitualGods;
import com.person124.elrh.block.EldritchBlockRitualSimbol;
import com.person124.elrh.enums.EnumSimbolGods;
import com.person124.elrh.enums.EnumSimbolType;
import com.person124.elrh.item.CustomItemBlock;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElrhBlocks {

	public static final EldritchBlock RITUAL_BORDER = new EldritchBlockRitualBorder("ritual_border").setHardness(0.5f);
	public static final EldritchBlock RITUAL_CENTER = new EldritchBlockRitualCenter("ritual_center").setHardness(1.0f);
	public static final EldritchBlock RITUAL_SIMBOL = new EldritchBlockRitualSimbol("ritual_simbol").setHardness(0.5f);
	public static final EldritchBlock RITUAL_SIMBOL_GOD = new EldritchBlockRitualGods("ritual_simbol_god").setHardness(0.5f);

	public static void init() {
		register(RITUAL_BORDER, false);

		register(RITUAL_CENTER, false);

		register(RITUAL_SIMBOL, false, CustomItemBlock.class, EnumSimbolType.class);

		register(RITUAL_SIMBOL_GOD, false, CustomItemBlock.class, EnumSimbolGods.class);
	}

	private static void register(EldritchBlock block, boolean invRender) {
		GameRegistry.registerBlock(block, block.getName());

		if (invRender && Eldritch.isClient) {
			Eldritch.renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Eldritch.MODID + ":" + block.getName(), "inventory"));
		}
	}

	private static void register(EldritchBlock block, boolean invRender, Class<? extends ItemBlock> item, Class<? extends Enum<?>> c) {
		GameRegistry.registerBlock(block, item, block.getName());

		if (invRender && Eldritch.isClient) {
			Enum<?>[] e = c.getEnumConstants();

			String[] temp = new String[e.length];
			for (int i = 0; i < e.length; i++) {
				String s = Eldritch.MODID + ":" + block.getName() + "_" + e[i].toString();
				Eldritch.renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), i, new ModelResourceLocation(s, "inventory"));
				temp[i] = s;
			}

			ModelBakery.addVariantName(Item.getItemFromBlock(block), temp);
		}
	}

}
