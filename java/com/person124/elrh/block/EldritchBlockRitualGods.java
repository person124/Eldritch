package com.person124.elrh.block;

import java.util.Random;

import com.person124.elrh.ElrhItems;
import com.person124.elrh.enums.EnumSimbolGods;
import com.person124.elrh.inter.IMetaBlock;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author Person124
 */
public class EldritchBlockRitualGods extends EldritchBlockRitualBorder implements IMetaBlock {

	public static final PropertyEnum TYPE = PropertyEnum.create("god", EnumSimbolGods.class);

	public EldritchBlockRitualGods(String name) {
		super(name);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumSimbolGods.DAMIEN));
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ElrhItems.SIMBOLS_GODS;
	}

	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + EnumSimbolGods.byDamage(stack.getMetadata()).getUnlocalizedName();
	}

	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumSimbolGods.byDamage(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumSimbolGods) state.getValue(TYPE)).getDamage();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}

	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(ElrhItems.SIMBOLS_GODS, 1, getMetaFromState(world.getBlockState(pos)));
	}

}
