package com.person124.elrh.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.person124.elrh.block.EldritchBlock;

public class EldritchItemPutty extends EldritchItem {

	private EldritchBlock block;

	public EldritchItemPutty(String name, EldritchBlock b) {
		super(name);
		block = b;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float x, float y, float z) {
		pos = pos.add(0, 1f, 0);

		if (block.canPlaceBlockAt(world, pos)) {
			world.setBlockState(pos, block.getDefaultState());
			stack.stackSize--;
			return true;
		}

		return false;
	}

}
