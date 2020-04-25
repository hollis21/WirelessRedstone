package feon.wirelessredstone;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import feon.wirelessredstone.objects.items.capabilities.targetblock.TargetBlockCapability;

@Mod(Main.MODID)
public class Main {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "wirelessredstone";

    public Main() {
        LOGGER.info(Main.MODID + " Main constructed");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        TargetBlockCapability.register();
    }
}
