package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.ImpulseCastData;
import io.redspace.ironsspellbooks.player.SpinAttackType;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.spells.fire.BurningDashSpell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import org.yunz21.powerofthevoid.registries.VSchoolRegistry;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;

import java.util.Optional;


@AutoSpellConfig
public class MachRushSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(PowerOfTheVoid.MODID, "mach_rush");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(VSchoolRegistry.OROKIN_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(1)
            .build();

    public MachRushSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 3;
        this.castTime = 200;
        this.baseManaCost = 0;
    }

    @Override
    public CastType getCastType() {
        return CastType.CONTINUOUS;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(VSoundRegistry.BLOODLETTING.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Vec3 lookVec = entity.getLookAngle().scale(2.0); // 5.0是速度倍数
        entity.setDeltaMovement(lookVec);
        entity.hurtMarked = true; // 强制更新玩家位置

        entity.addEffect(new MobEffectInstance(VMobEffectRegistry.MACH_RUSH.get(), 15));


//        entity.hasImpulse = true;
//        float multiplier = (15 + getSpellPower(spellLevel, entity)) / 12f;
//
//        //Direction for Mobs to cast in
//        Vec3 forward = entity.getLookAngle();
//        if (playerMagicData.getAdditionalCastData() instanceof MachRushDirectionOverrideCastData) {
//            if (Utils.random.nextBoolean())
//                forward = forward.yRot(90);
//            else
//                forward = forward.yRot(-90);
//        }
//
//        //Create Dashing Movement Impulse
//        var vec = forward.multiply(3, 1, 3).normalize().add(0, .25, 0).scale(multiplier);
//        //Start Spin Attack
//        if (entity.onGround()) {
//            entity.setPos(entity.position().add(0, 1.5, 0));
//            vec.add(0, 0.25, 0);
//        }
//        playerMagicData.setAdditionalCastData(new ImpulseCastData((float) vec.x, (float) vec.y, (float) vec.z, true));
//        //entity.setDeltaMovement(entity.getDeltaMovement().add(vec));
//        entity.setDeltaMovement(new Vec3(
//                Mth.lerp(.75f, entity.getDeltaMovement().x, vec.x),
//                Mth.lerp(.75f, entity.getDeltaMovement().y, vec.y),
//                Mth.lerp(.75f, entity.getDeltaMovement().z, vec.z)
//        ));
//
//
//        //entity.addEffect(new MobEffectInstance(MobEffectRegistry.BURNING_DASH.get(), 15, getDamage(spellLevel, entity), false, false, false));
//        entity.invulnerableTime = 1000;
//        //startSpinAttack(entity, 10);
//        //playerMagicData.getSyncedData().setSpinAttackType(SpinAttackType.FIRE);
//        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }



//    public static class MachRushDirectionOverrideCastData implements ICastData {
//        @Override
//        public void reset() {
//
//        }
//    }
}
