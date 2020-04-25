package feon.wirelessredstone.objects.items.capabilities.targetblock;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TargetBlockPositionStorage implements IStorage<ITargetBlockPosition> {

  private static final String KEY_PREFIX = "wirelessredstone:blockposition";
  private static final String X_KEY = KEY_PREFIX + ".x";
  private static final String Y_KEY = KEY_PREFIX + ".y";
  private static final String Z_KEY = KEY_PREFIX + ".z";

  @Override
  public INBT writeNBT(Capability<ITargetBlockPosition> capability, ITargetBlockPosition instance, Direction side) {
    CompoundNBT compoundNBT = new CompoundNBT();
    BlockPos pos = instance.getBlockPosition();
    if (pos != null) {
      compoundNBT.putInt(X_KEY, pos.getX());
      compoundNBT.putInt(Y_KEY, pos.getY());
      compoundNBT.putInt(Z_KEY, pos.getZ());
    } else {
      compoundNBT.remove(X_KEY);
      compoundNBT.remove(Y_KEY);
      compoundNBT.remove(Z_KEY);
    }
    return compoundNBT;
  }

  @Override
  public void readNBT(Capability<ITargetBlockPosition> capability, ITargetBlockPosition instance, Direction side,
      INBT nbt) {
    if (nbt instanceof CompoundNBT) {
      CompoundNBT compoundNBT = (CompoundNBT) nbt;
      if (!compoundNBT.contains(X_KEY)) {
        instance.setBlockPosition(null);
      } else {
        instance.setBlockPosition(
            new BlockPos(compoundNBT.getInt(X_KEY), compoundNBT.getInt(Y_KEY), compoundNBT.getInt(Z_KEY)));
      }
    }
  }
}