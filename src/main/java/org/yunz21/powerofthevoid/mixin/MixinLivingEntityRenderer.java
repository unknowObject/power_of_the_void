//package org.yunz21.powerofthevoid.mixin;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.entity.LivingEntityRenderer;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
//
//@Mixin(LivingEntityRenderer.class)
//public abstract class MixinLivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> {

//    @Inject(method = "render", at = @At("HEAD"))
//    private void cancelFireRender(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
//        boolean fireImmune = entity.hasEffect(VMobEffectRegistry.KINETIC_PLATING.get()) ||
//                entity.hasEffect(VMobEffectRegistry.INVULNERABLE.get()) ||
//                entity.hasEffect(VMobEffectRegistry.MESMER_SKIN.get());
//        if (entity instanceof Player && fireImmune) {
//            //entity.clearFire();
//            entity.setRemainingFireTicks(0); // Temporarily removes fire effect on render
//        }
//    }
//}