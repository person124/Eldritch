package com.person124.elrh.block;

import com.person124.elrh.enums.EnumBloodLevel;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class EldritchBlockPermentBlood extends EldritchBlockRitualPerment {

	public static final PropertyEnum TYPE = PropertyEnum.create("blood", EnumBloodLevel.class);

	public EldritchBlockPermentBlood(String name) {
		super(name);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumBloodLevel.FULL));
	}

	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + EnumBloodLevel.byDamage(stack.getMetadata()).getName();
	}

	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumBloodLevel.byDamage(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumBloodLevel) state.getValue(TYPE)).getDamage();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}

}
