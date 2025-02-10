package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;
import org.yunz21.powerofthevoid.network.ModMessages;
import org.yunz21.powerofthevoid.network.packet.BatteryDataSyncPacket;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import org.yunz21.powerofthevoid.registries.VSchoolRegistry;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;
import org.yunz21.powerofthevoid.registries.VSpellRegistry;

import java.util.List;
import java.util.Optional;

import static org.yunz21.powerofthevoid.PowerOfTheVoid.MODID;

@AutoSpellConfig
public class MachRushSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(MODID, "mach_rush");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getSpellPower(spellLevel, caster) * 40, 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(VSchoolRegistry.OROKIN_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(1)
            .build();

    public MachRushSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 3;
        this.castTime = 200;
        this.baseManaCost = 0;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
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
        return Optional.of(VSoundRegistry.MACH_RUSH.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.addEffect(new MobEffectInstance(VMobEffectRegistry.MACH_RUSH.get(), (int) (getSpellPower(spellLevel, entity) * 40), spellLevel - 1));
    }

    public int getSpellLevel () {
        return spellPowerPerLevel;
    }

    private static boolean canHit(Entity owner, Entity target) {
        return target != owner && target.isAlive() && target.isPickable() && !target.isSpectator();
    }

    public static void castShockWave(Level level, Player player){//} Level level, LivingEntity entity, int radius, float damage) {
        //Vec3 start = player.getBoundingBox().getCenter();
        double shockwaveRadius = 5.0;
        List<Entity> nearbyEntities = player.level().getEntities(
                player, player.getBoundingBox().inflate(shockwaveRadius),
                e -> e instanceof LivingEntity && e != player
        );

        for (Entity entity : nearbyEntities) {
            entity.hurt(entity.damageSources().indirectMagic(player, player), 4.0F);
            Vec3 knockback = new Vec3(entity.getX() - player.getX(), 0.5, entity.getZ() - player.getZ()).normalize().scale(2.0);
            entity.setDeltaMovement(knockback); // Apply outward knockback
        }

        //System.out.println("[DEBUG] Shockwave triggered on wall impact!");
    }


    @Mod.EventBusSubscriber(modid = MODID)
    public static class MachRushEvent {
        private static boolean usedSprinting = false;

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.START && VSpellRegistry.MACH_RUSH_SPELL.isPresent()) { //VSpellRegistry.MACH_RUSH.isPresent()

                Player player = event.player;
                //MobEffectInstance effect = player.getEffect(VMobEffectRegistry.MACH_RUSH.get());
                //float defaultStep = player.maxUpStep();
                //BlockPos pos = player.blockPosition();
                //BlockState blockStateBot = player.level().getBlockState(pos.below()); // Check block right under player is fluid or not
                //boolean isOnLiquid = !blockState.getFluidState().isEmpty();

                if (player.hasEffect(VMobEffectRegistry.MACH_RUSH.get()) && player.isSprinting()) {
                    // When player switch from walk to sprint, play sound
                    if (!usedSprinting) {
                        player.level().playSound(null, player.blockPosition(), VSoundRegistry.MACH_RUSH.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        usedSprinting = true;
                    }

                    // Make player able to walk on water when sprinting with effect MACH_RUSH
                    //if (!blockStateBot.getFluidState().isEmpty()) {
                        // Keep the player exactly at the liquid surface
                        //double waterSurfaceY = pos.getY() + 1 - player.getBbHeight() * 0.5;
                        //player.setPos(player.getX(), waterSurfaceY, player.getZ());

                        // Preserve sprinting speed on water
                        // Get player's current movement speed attribute
                        //double baseSpeed = player.getAttributeValue(Attributes.MOVEMENT_SPEED);
                        //double sprintSpeed = baseSpeed * 1.3; // Sprinting speed factor in Minecraft
                        //player.setOnGround(true);
                        // Dynamically adjust speed multiplier
                        //double speedMultiplier = player.getAttributeValue(Attributes.MOVEMENT_SPEED) / 0.23;

                        // Preserve sprinting speed on water
//                        Vec3 motion = player.getDeltaMovement();
//                        Vec3 newMotion = new Vec3(motion.x * speedMultiplier, 0, motion.z * speedMultiplier);
//                        player.setDeltaMovement(newMotion);
                    //}

                    // 增加移动速度（默认速度的 1.5 倍）
                    player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.15 * 1.5); // 默认速度为 0.1，1.5 倍为 0.15

                    // 增加跨越高度（默认 0.6，增加到 1.2）
                    player.setMaxUpStep(1.6f);

                    // Damage & Knock Back Nearby Entities
                    double range = 1.5; // Attack range in front of the player
                    List<Entity> nearbyEntities = player.level().getEntities(
                            player, player.getBoundingBox().inflate(range), e -> e instanceof LivingEntity && e != player
                    );

                    for (Entity entity : nearbyEntities) {
                        entity.hurt(entity.damageSources().indirectMagic(player, player), 1.0F);

                        player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                            battery.addCharge(1.0f);
                            if (player instanceof ServerPlayer) {
                                ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), battery.getMaxCharge(), battery.getPercent()), (ServerPlayer) player);
                                //System.out.println("[DEBUG]hit enemy, gain 1 charge");
                            }
                        });

                        // Calculate outward knockback direction (X, Z only)
                        Vec3 knockbackDir = entity.position().subtract(player.position());
                        Vec3 horizontalKnockbackDir = new Vec3(knockbackDir.x, 0, knockbackDir.z).normalize();

                        // Get player's forward direction (X, Z only)
                        Vec3 forwardDir = player.getLookAngle();
                        Vec3 horizontalForwardDir = new Vec3(forwardDir.x, 0, forwardDir.z).normalize();

                        // Reduce knockback along the player's looking direction
                        double forwardInfluence = 0.5; // Lower value means stronger reduction
                        Vec3 adjustedKnockback = horizontalKnockbackDir.subtract(horizontalForwardDir.scale(forwardInfluence)).normalize().scale(1.5);

                        entity.setDeltaMovement(adjustedKnockback);
                    }

                    // Check if a 2-block-high wall is ahead
                    BlockPos frontBlock = player.blockPosition().relative(player.getDirection());
                    BlockPos aboveFrontBlock = frontBlock.above();

                    boolean isWall = player.level().getBlockState(frontBlock.above()).isSolidRender(player.level(), frontBlock.above()) // Middle block must be solid
                            && player.level().getBlockState(frontBlock.above(2)).isSolidRender(player.level(), frontBlock.above(2)); // Top block must be solid

                    if (isWall) {
                        player.level().playSound(null, player.blockPosition(), VSoundRegistry.HIT_WALL.get(), SoundSource.PLAYERS, 1.0f, 1.0f);

                        // Release shockwave effect
                        castShockWave(player.level(), player);

                        // Stop sprinting after hitting the wall
                        player.setSprinting(false);
                        usedSprinting = false;
                    }

                } else {
                    // 重置移动速度和跨越高度
                    player.getAttribute(Attributes.MOVEMENT_SPEED)
                            .setBaseValue(0.1f); // 恢复默认速度
                    player.setMaxUpStep(0.6f); // 恢复默认跨越高度
                    usedSprinting = false;
                }

                //System.out.println(player.maxUpStep());
            }
        }
    }
}
