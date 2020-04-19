package feon.wirelessredstone.objects.items.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TargetBlockCapability implements ICapabilitySerializable<INBT> {

  @CapabilityInject(ITargetBlockPosition.class)
  public static final Capability<ITargetBlockPosition> TARGET_BLOCK_CAPABILITY = null;
  private LazyOptional<ITargetBlockPosition> instance = LazyOptional.of(TARGET_BLOCK_CAPABILITY::getDefaultInstance);
  
  public static void register()
  {
      CapabilityManager.INSTANCE.register(ITargetBlockPosition.class, new TargetBlockPositionStorage(), TargetBlockPosition::new);
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    return TARGET_BLOCK_CAPABILITY.orEmpty(cap, instance);  
  }

  @Override
  public INBT serializeNBT() {
    return TARGET_BLOCK_CAPABILITY.getStorage().writeNBT(TARGET_BLOCK_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
  }

  @Override
  public void deserializeNBT(INBT nbt) {
    TARGET_BLOCK_CAPABILITY.getStorage().readNBT(TARGET_BLOCK_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
  }

}