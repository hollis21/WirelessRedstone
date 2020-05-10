package feon.wirelessredstone.world;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;

public class RedstoneNetwork extends WorldSavedData {

    public RedstoneNetwork(String name) {
        super(name);
    }

    private static final String REDSTONE_NETWORK_NAME = "RedstoneNetwork";
    private static final String REDSTONE_NETWORK_FREQUENCY_LIST_KEY = "redstoneNetworkFrequencies";
    private static final String REDSTONE_NETWORK_MAX_ID_KEY = "redstoneNetworkMaxId";
    private static final String REDSTONE_NETWORK_FREQUENCY_KEY = "frequency";
    private static final String REDSTONE_NETWORK_VALUE_KEY = "value";

    private final Map<Integer, Integer> frequencies = new HashMap<>();

    private int maxId = 0;

    public int getFrequencyValue(int frequency) {
        Integer value = frequencies.get(frequency);
        return value == null ? 0 : value.intValue();
    }

    public void setFrequencyValue(int frequency, int value) {
        frequencies.put(frequency, value);
    }

    public void deleteFrequency(int frequency) {
        frequencies.remove(frequency);
    }

    public int getNewFrequency() {
        maxId++;
        return maxId;
    }

    public void save() {
        markDirty();
    }

    public static RedstoneNetwork getNetwork(World world) {
        // return getData(world, () -> new RedstoneChannels(REDSTONE_NETWORK_NAME),
        // REDSTONE_NETWORK_NAME);
        if (world.isRemote) {
            throw new RuntimeException("Don't access this client-side!");
        }

        DimensionSavedDataManager storage = DimensionManager
                .getWorld(world.getServer(), DimensionType.OVERWORLD, false, false).getSavedData();
        return storage.getOrCreate(() -> new RedstoneNetwork(REDSTONE_NETWORK_NAME), REDSTONE_NETWORK_NAME);
    }

    @Override
    public void read(CompoundNBT nbt) {
        frequencies.clear();
        ListNBT list = nbt.getList(REDSTONE_NETWORK_FREQUENCY_LIST_KEY, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT item = list.getCompound(i);
            int frequency = item.getInt(REDSTONE_NETWORK_FREQUENCY_KEY);
            int value = item.getInt(REDSTONE_NETWORK_VALUE_KEY);

            frequencies.put(frequency, value);
        }
        this.maxId = nbt.getInt(REDSTONE_NETWORK_MAX_ID_KEY);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT list = new ListNBT();
        for (Map.Entry<Integer, Integer> entry : this.frequencies.entrySet()) {
            CompoundNBT item = new CompoundNBT();
            item.putInt(REDSTONE_NETWORK_FREQUENCY_KEY, entry.getKey());
            item.putInt(REDSTONE_NETWORK_VALUE_KEY, entry.getValue());
            list.add(item);
        }
        compound.put(REDSTONE_NETWORK_FREQUENCY_LIST_KEY, list);
        compound.putInt(REDSTONE_NETWORK_MAX_ID_KEY, this.maxId);
        return compound;
    }

}