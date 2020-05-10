package feon.wirelessredstone.tileentity;

import feon.wirelessredstone.init.ModTileEntityTypes;
import feon.wirelessredstone.world.RedstoneNetwork;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RedstoneReceiverTileEntity extends TileEntity implements ITickableTileEntity {

    protected int frequency = -1;
    protected int powerLevel = 0;

    public RedstoneReceiverTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public RedstoneReceiverTileEntity() {
        this(ModTileEntityTypes.REDSTONE_RECEIVER.get());
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }
        int newPower = getPowerFromNetwork();
        if (newPower == powerLevel) {
            return;
        }

        powerLevel = newPower;
        markDirty();
        getWorld().notifyNeighborsOfStateChange(this.pos, getWorld().getBlockState(this.pos).getBlock());
    }

    public int getPowerLevel() {
        return this.powerLevel;
    }

    public int getPowerFromNetwork() {
        if (frequency == -1) {
            return 0;
        }

        return RedstoneNetwork.getNetwork(world).getFrequencyValue(frequency);
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return this.frequency == -1 ? null : this.frequency;
    }

    private static final String POWER_LEVEL_KEY = "powerLevel";
    private static final String FREQUENCY_KEY = "frequency";

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.powerLevel = compound.getInt(POWER_LEVEL_KEY);
        this.frequency = compound.getInt(FREQUENCY_KEY);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt(FREQUENCY_KEY, this.frequency);
        compound.putInt(POWER_LEVEL_KEY, this.powerLevel);
        return super.write(compound);
    }
}