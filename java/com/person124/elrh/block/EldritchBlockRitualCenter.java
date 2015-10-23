package com.person124.elrh.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.person124.elrh.Eldritch;
import com.person124.elrh.ElrhBlocks;
import com.person124.elrh.ElrhItems;
import com.person124.elrh.enums.EnumRituals;
import com.person124.elrh.enums.EnumSimbolType;
import com.person124.elrh.handler.RitualHandler;

public class EldritchBlockRitualCenter extends EldritchBlockRitualBorder {

	public EldritchBlockRitualCenter(String name) {
		super(name);
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float x, float y, float z) {
		if (!world.isRemote && gridComplete(world, pos)) {
			if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ElrhItems.RITUAL_SICK) {
				String s = "";

				for (int xp = 1; xp >= -1; xp--) {
					for (int zp = -1; zp <= 1; zp++) {
						if (zp == 0 && xp == 0) ;
						else {
							BlockPos p = new BlockPos(pos.getX() + xp, pos.getY(), pos.getZ() + zp);
							Block b = world.getBlockState(p).getBlock();

							if (b != Blocks.air && (b.getDefaultState().getBlock() == ElrhBlocks.RITUAL_SIMBOL || b == ElrhBlocks.RITUAL_SIMBOL_GOD)) {
								if (b.getDefaultState().getBlock() == ElrhBlocks.RITUAL_SIMBOL) {
									int damage = b.getDamageValue(world, p);
									s += EnumSimbolType.byDamage(damage).getChar();
								} else s += '!';
							} else s += '-';
						}
					}
				}

				player.inventory.getCurrentItem().damageItem(1, player);
				EnumRituals rit = Eldritch.worldData.matchRecipe(s);
				if (rit != null) rit.execute(pos, player);
				else RitualHandler.notARitual(pos, player);

				return true;
			}
		}

		return false;
	}

	private boolean gridComplete(World world, BlockPos pos) {
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				if ((x != -2 && x != 2) && (z != -2 && z != 2)) z = 2;

				Block b = world.getBlockState(new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() + z)).getBlock();
				if (b != ElrhBlocks.RITUAL_BORDER) return false;
			}
		}
		return true;
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ElrhItems.SIMBOL_PUTTY_CENTER;
	}

	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(ElrhItems.SIMBOL_PUTTY_CENTER);
	}

}
