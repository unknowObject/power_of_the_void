package org.yunz21.powerofthevoid.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import org.yunz21.powerofthevoid.registries.VSpellRegistry;

import static net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE;
import static net.minecraft.world.item.Items.MILK_BUCKET;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderFireEvent {
    @SubscribeEvent
    public static void onRenderFire(RenderBlockScreenEffectEvent event) {
        if(!VSpellRegistry.MACH_RUSH_SPELL.isPresent()) return;
        if(!VSpellRegistry.MESMER_SKIN_SPELL.isPresent()) return;
        if (event.getOverlayType() != RenderBlockScreenEffectEvent.OverlayType.FIRE) return;
        final Player player = event.getPlayer();

        boolean fireImmune = player.hasEffect(VMobEffectRegistry.KINETIC_PLATING.get()) ||
                player.hasEffect(VMobEffectRegistry.INVULNERABLE.get()) ||
                player.hasEffect(VMobEffectRegistry.MESMER_SKIN.get());

        if (Minecraft.getInstance().options.hideGui || player.isCreative() || fireImmune) event.setCanceled(true);
    }
}
