package feon.wirelessredstone.init;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.objects.blocks.RedstoneReceiver;
import feon.wirelessredstone.objects.blocks.RedstoneTransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
  public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Main.MODID);
  
  public static final RegistryObject<Block> REDSTONE_TRANSMITTER = BLOCKS.register("redstone_transmitter", () -> new RedstoneTransmitter(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).sound(SoundType.WOOD)));
  public static final RegistryObject<Block> REDSTONE_RECEIVER = BLOCKS.register("redstone_receiver", () -> new RedstoneReceiver(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).sound(SoundType.WOOD)));
}