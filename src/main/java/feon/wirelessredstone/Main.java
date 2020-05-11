package feon.wirelessredstone;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        modEventBus.addListener((FMLClientSetupEvent event) -> Main.OnClientSetupEvent(event));

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void OnClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.REDSTONE_RECEIVER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.REDSTONE_TRANSMITTER.get(), RenderType.getCutout());
    }

}
