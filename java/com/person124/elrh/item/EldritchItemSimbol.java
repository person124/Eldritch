package com.person124.elrh.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.person124.elrh.ElrhBlocks;
import com.person124.elrh.enums.EnumSimbolType;

public class EldritchItemSimbol extends EldritchItem {

	public EldritchItemSimbol(String name) {
		super(name);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float x, float y, float z) {
		pos = pos.add(0, 1f, 0);

		if (ElrhBlocks.RITUAL_SIMBOL.canPlaceBlockAt(world, pos)) {
			world.setBlockState(pos, ElrhBlocks.RITUAL_SIMBOL.getStateFromMeta(getDamage(stack)));
			stack.stackSize--;
			return true;
		}

		return false;
	}

	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + EnumSimbolType.byDamage(stack.getMetadata()).getUnlocalizedName();
	}

	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (int i = 0; i < 16; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}

}
