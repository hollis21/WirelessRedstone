package feon.wirelessredstone.events;

import feon.wirelessredstone.Main;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Main.MODID, bus= Bus.FORGE)
public class TestJumpEvent {

  @SubscribeEvent
  public static void onJumpEvent(LivingJumpEvent event) {
    LivingEntity entity = event.getEntityLiving();
    if (!(entity instanceof PlayerEntity)) {
      return;
    }
    // Vec3d motion = entity.getMotion();
    // entity.setMotion(motion.x, motion.y * 2, motion.z);
    // entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 6000, 255));
  }
}