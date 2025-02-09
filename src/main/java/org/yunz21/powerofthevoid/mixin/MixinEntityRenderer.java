package org.yunz21.powerofthevoid.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import org.yunz21.powerofthevoid.registries.VSpellRegistry;

import static org.yunz21.powerofthevoid.registries.VMobEffectRegistry.MESMER_SKIN;
import static org.yunz21.powerofthevoid.registries.VMobEffectRegistry.KINETIC_PLATING;
import static org.yunz21.powerofthevoid.registries.VMobEffectRegistry.INVULNERABLE;

@Mixin(Entity.class)
public abstract class MixinEntityRenderer {

    @Shadow
    public abstract AABB getBoundingBox();

    @Shadow
    public Level level;

    @Shadow
    private int remainingFireTicks;

    @Shadow public abstract void setRemainingFireTicks(int p_20269_);

//    @Inject(method = "baseTick", at = @At("TAIL"))
//    private void inject$baseTick(CallbackInfo ci) {
//        extinguishIfResistantToFire();
//    }

    @Shadow public abstract boolean removeTag(String p_20138_);

    private void extinguishIfResistantToFire() {
        if (((Entity) (Object) this) instanceof LivingEntity) {
            //boolean isCreative = false;
            boolean hasFireResistance = false;
            //int remainingDuration = 0;
            if (((LivingEntity) (Object) this).hasEffect(MESMER_SKIN.get()) ||
                    ((LivingEntity) (Object) this).hasEffect(INVULNERABLE.get()) ||
                    ((LivingEntity) (Object) this).hasEffect(KINETIC_PLATING.get())) {
                hasFireResistance = true;
            }
//            if (((Entity) (Object) this) instanceof Player && ((Player) (Object) this).isCreative())
//                isCreative = true;

//            if (((Entity) (Object) this).getRemainingFireTicks() > 0 && hasFireResistance && !isCreative
//                    && !(this.level.getBlockStatesIfLoaded(this.getBoundingBox().expandTowards(-0.001D, -0.001D, -0.001D)).anyMatch((blockStatex)
//                    -> blockStatex.is(BlockTags.FIRE) || blockStatex.is(Blocks.LAVA))
//                    && hasFireResistance)) {
//                this.remainingFireTicks = 0;
//            } else
            if (hasFireResistance){//isCreative) {
                this.remainingFireTicks = 0;
            }
        }
    }

    @Inject(method = "isOnFire", at = @At("HEAD"), cancellable = true)
    private void preventFireRendering(CallbackInfoReturnable<Boolean> cir) {
        boolean hasFireResistance = false;
        if (!VSpellRegistry.MESMER_SKIN_SPELL.isPresent()) return;
        if (((Entity) (Object) this) instanceof LivingEntity) {
            //int remainingDuration = 0;
            if (((LivingEntity) (Object) this).hasEffect(MESMER_SKIN.get()) ||
                    ((LivingEntity) (Object) this).hasEffect(INVULNERABLE.get()) ||
                    ((LivingEntity) (Object) this).hasEffect(KINETIC_PLATING.get())) {
                hasFireResistance = true;
            }
        }
        if (hasFireResistance){
            cir.setReturnValue(false); // This prevents fire rendering
        }
    }
}