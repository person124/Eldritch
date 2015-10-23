package com.person124.elrh.block;

import java.util.Random;

import com.person124.elrh.ElrhItems;
import com.person124.elrh.enums.EnumSimbolType;
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
public class EldritchBlockRitualSimbol extends EldritchBlockRitualBorder implements IMetaBlock {

	public static final PropertyEnum TYPE = PropertyEnum.create("simbol", EnumSimbolType.class);

	public EldritchBlockRitualSimbol(String name) {
		super(name);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumSimbolType.SQUARE));
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ElrhItems.SIMBOLS;
	}

	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + EnumSimbolType.byDamage(stack.getMetadata()).getUnlocalizedName();
	}

	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumSimbolType.byDamage(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumSimbolType) state.getValue(TYPE)).getDamage();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}

	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(ElrhItems.SIMBOLS, 1, getMetaFromState(world.getBlockState(pos)));
	}

}
