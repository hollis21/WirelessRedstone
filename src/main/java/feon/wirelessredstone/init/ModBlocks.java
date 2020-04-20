package feon.wirelessredstone.init;

import feon.wirelessredstone.Main;
import feon.wirelessredstone.objects.blocks.RedstoneReceiver;
import feon.wirelessredstone.objects.blocks.RedstoneTransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Bus.MOD)
@ObjectHolder(Main.MODID)
public class ModBlocks extends EventBusSubscriberBase {

  public static final RedstoneReceiver redstone_receiver = null;
  public static final RedstoneTransmitter redstone_transmitter = null;

  @SubscribeEvent()
  public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
    event.getRegistry().registerAll(
      setup(new RedstoneTransmitter(
        Block.Properties
          .create(Material.IRON, MaterialColor.TNT)
          .hardnessAndResistance(5.0F, 6.0F)
          .sound(SoundType.METAL)
      ), "redstone_transmitter"),
      setup(new RedstoneReceiver(
        Block.Properties
          .create(Material.IRON, MaterialColor.TNT)
          .hardnessAndResistance(5.0F, 6.0F)
          .sound(SoundType.METAL)
      ), "redstone_receiver")
    );
  }

  @SubscribeEvent()
  public static void onRegisterBlockItem(final RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(
      setup(new BlockItem(redstone_transmitter, 
        new Item.Properties()
          .group(ModItemGroups.MOD_ITEM_GROUP)
      ), "redstone_transmitter"),
      setup(new BlockItem(redstone_receiver, 
        new Item.Properties()
          .group(ModItemGroups.MOD_ITEM_GROUP)
      ), "redstone_receiver")
    );
  }
}