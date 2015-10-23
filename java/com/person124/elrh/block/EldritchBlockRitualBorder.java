package com.person124.elrh.block;

import java.util.Random;

import com.person124.elrh.ElrhItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Person124
 */
public class EldritchBlockRitualBorder extends EldritchBlock {

	public EldritchBlockRitualBorder(String name) {
		super(Material.circuits, name);
		setStepSound(soundTypeSand);
		setBlockBounds(0f, 0f, 0f, 1f, 0.0625F, 1f);
		setTickRandomly(true);
		setCreativeTab(null);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) || worldIn.getBlockState(pos.down()).getBlock() == Blocks.glowstone;
	}

	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) {
			if (!canPlaceBlockAt(worldIn, pos)) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(ElrhItems.SIMBOL_PUTTY);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ElrhItems.SIMBOL_PUTTY;
	}

}
