package com.person124.elrh.handler;

import com.person124.elrh.Eldritch;
import com.person124.elrh.ElrhBlocks;
import com.person124.elrh.enums.EnumRituals;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

/**
 * @author Person124
 */
public class RitualHandler {

	/**
	 * Tries to perform a ritual.
	 * @param id The ritual's id.
	 * @param pos The BlockPos of the center ritual block.
	 * @param player The player doing the ritual.
	 */
	public void execute(byte id, BlockPos pos, EntityPlayer player) {
		try {
			EnumRituals rit = EnumRituals.getById(id);
			if (!rit.getHandler().newInstance().execute(pos, player)) failure(pos, player, rit.getImportance());
			else {
				printSuccess(player, rit);
				success(pos, player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * If the ritual was successful.
	 * @param pos The BlockPos of the center ritual block.
	 * @param player The player doing the ritual.
	 */
	private void success(BlockPos pos, EntityPlayer player) {
		World world = player.worldObj;

		world.playSoundAtEntity(player, Eldritch.MODID + ":ritual.complete", 1.0f, 1.0f);
		world.playAuxSFX(2003, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), 0);

		for (int xp = 1; xp >= -1; xp--) {
			for (int zp = -1; zp <= 1; zp++) {
				if (zp == 0 && xp == 0) ;
				else {
					BlockPos p = new BlockPos(pos.getX() + xp, pos.getY(), pos.getZ() + zp);
					Block b = world.getBlockState(p).getBlock();

					if (b != Blocks.air && (b.getDefaultState().getBlock() == ElrhBlocks.RITUAL_SIMBOL || b == ElrhBlocks.RITUAL_SIMBOL_GOD)) world.setBlockToAir(p);
				}
			}
		}
	}

	/**
	 * If the ritual failed.
	 * @param pos The BlockPos of the center ritual block.
	 * @param player The player doing the ritual.
	 * @param impor The importance level of the ritual.
	 */
	private void failure(BlockPos pos, EntityPlayer player, EnumRituals.Importance impor) {
		World world = player.worldObj;

		world.playSoundAtEntity(player, Eldritch.MODID + ":ritual.failure", 1.0f, 1.0f);

		switch (impor) {
			case SMALL:
				int x = Eldritch.RAND.nextBoolean() ? -2 : 2;
				int z = Eldritch.RAND.nextInt(5) - 2;
				BlockPos p = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z);
				world.playAuxSFX(2000, p, 4);
				world.setBlockToAir(p);
				break;
			default:
				break;
		}
	}

	private void printSuccess(EntityPlayer player, EnumRituals rit) {
		player.addChatMessage(new ChatComponentTranslation("book.rituals." + rit.getReference() + ".success"));
	}

	public static void notARitual(BlockPos pos, EntityPlayer player) {
		player.worldObj.playSoundAtEntity(player, "random.fizz", 1.0f, 1.0f);
		player.worldObj.playAuxSFX(2004, pos, 4);
	}

}
