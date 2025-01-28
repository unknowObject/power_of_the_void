package org.yunz21.powerofthevoid.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

public class StunnedEffect extends MobEffect {
    public StunnedEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory.HARMFUL, color);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "76a0a153-2c52-4513-8429-68eb72f2ad69", (double) -1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
//        if (entity.getDeltaMovement().y > 0) {
//            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0.1D, 1));
//        }
//        if (entity instanceof Mob mob) {
//            entity.setXRot(30.0F);
//            entity.xRotO = 30.0F;
//            if (!mob.level().isClientSide) {
//                mob.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
//                mob.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
//                mob.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
//            }
//        }

        // Prevent the entity from moving
        entity.setDeltaMovement(0, entity.getDeltaMovement().y, 0); // Stop horizontal movement

        // Lower the entity's head
        if (entity.isAlive() && !(entity instanceof Mob)) {
            entity.setXRot(45.0F); // Set pitch to 45 degrees (lower head)
            entity.xRotO = 45.0F;
        }

    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        //return duration > 0;
        return true;
    }
}
