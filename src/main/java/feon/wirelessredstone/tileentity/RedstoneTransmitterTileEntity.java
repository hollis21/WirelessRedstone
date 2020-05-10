package feon.wirelessredstone.tileentity;

import feon.wirelessredstone.init.ModTileEntityTypes;
import feon.wirelessredstone.world.RedstoneNetwork;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class RedstoneTransmitterTileEntity extends TileEntity {

    protected int frequency = -1;
    protected int powerLevel = 0;

    public RedstoneTransmitterTileEntity() {
        super(ModTileEntityTypes.REDSTONE_TRANSMITTER.get());;
    }
    
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return this.frequency == -1 ? null : this.frequency;
    }
    
    public void setPowerLevel(int power) {
        if (frequency == -1 || this.powerLevel == power) {
            return;
        }
        this.powerLevel = power;
        RedstoneNetwork network = RedstoneNetwork.getNetwork(world);
        network.setFrequencyValue(frequency, this.powerLevel);
        network.save();
    }

    public int getPowerFromNetwork() {
        if (frequency == -1) {
            powerLevel = 0;
            return this.powerLevel;
        }

        powerLevel = RedstoneNetwork.getNetwork(world).getFrequencyValue(frequency);
        return this.powerLevel;
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