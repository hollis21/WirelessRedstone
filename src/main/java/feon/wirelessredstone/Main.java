package feon.wirelessredstone;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import feon.wirelessredstone.init.ModBlocks;
import feon.wirelessredstone.init.ModItems;
import feon.wirelessredstone.init.ModTileEntityTypes;

@Mod(Main.MODID)
public class Main {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "wirelessredstone";

    public Main() {
        LOGGER.info(Main.MODID + " Main constructed");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

}
