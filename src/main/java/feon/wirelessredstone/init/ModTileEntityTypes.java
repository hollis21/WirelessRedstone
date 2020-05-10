package feon.wirelessredstone.init;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.tileentity.RedstoneReceiverTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Main.MODID);

    public static final RegistryObject<TileEntityType<RedstoneReceiverTileEntity>> REDSTONE_RECEIVER = TILE_ENTITY_TYPES.register("redstone_receiver", () -> TileEntityType.Builder.create(RedstoneReceiverTileEntity::new, ModBlocks.redstone_receiver).build(null));
}