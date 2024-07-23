package net.trique.mythicupgrades.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.trique.mythicupgrades.MythicUpgradesDamageTypes;
import net.trique.mythicupgrades.item.*;
import net.trique.mythicupgrades.util.EffectMeta;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.trique.mythicupgrades.util.CommonFunctions.*;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Shadow
    public abstract @NotNull ItemStack getItemBySlot(EquipmentSlot slot);


    @Inject(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void applyEffectsOnSweeping(Entity target, CallbackInfo ci, @Local LivingEntity livingEntity) {
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof MythicEffectsSwordItem sword) {
            for (Holder<MobEffect> effect : sword.getOnHitEffectsForEnemy().keySet()) {
                EffectMeta meta = sword.getOnHitEffectsForEnemy().get(effect);
                int duration = meta.getDuration();
                int amplifier = meta.getAmplifier();
                boolean ambient = meta.isAmbient();
                boolean showIcon = meta.shouldShowIcon();
                boolean showParticles = meta.shouldShowParticles();
                double sweeping_amplifier = this.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO);
                livingEntity.addEffect(new MobEffectInstance(effect, duration, Math.max(0, (int) (amplifier - 0.75 + sweeping_amplifier)), ambient, showParticles, showIcon));
            }
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void applySapphirePercentageDamage(Entity entity, CallbackInfo ci, @Local(ordinal = 2) float h) {
        Item weapon = getItemBySlot(EquipmentSlot.MAINHAND).getItem();
        boolean sapphire_weapon = weapon instanceof VirtualSapphireTool;
        if (sapphire_weapon) {
            int percent = ((VirtualSapphireTool) weapon).getPercent();
            DamageSource source = MythicUpgradesDamageTypes.percentage_damage(this);
            float dmg = (percent / 100f) * h * h;
            if (entity.invulnerableTime <= 10) {
                if (entity instanceof EnderDragonPart part) {
                    EnderDragon dragon = part.parentMob;
                    dmg *= dragon.getMaxHealth();
                    dragon.hurt(part, source, dmg);
                } else if (entity instanceof LivingEntity target) {
                    dmg *= target.getMaxHealth();
                    target.hurt(source, dmg);
                }
                entity.invulnerableTime = 0;
            }
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void applySapphirePercentageDamageOnSweeping(Entity entity, CallbackInfo ci,
                                                         @Local(ordinal = 0) LivingEntity livingEntity) {
        Item weapon = getItemBySlot(EquipmentSlot.MAINHAND).getItem();
        boolean sapphire_weapon = weapon instanceof VirtualSapphireTool;
        if (sapphire_weapon) {
            int percent = ((VirtualSapphireTool) weapon).getPercent();
            DamageSource source = MythicUpgradesDamageTypes.percentage_damage(this);
            float dmg = (percent / 200f) * (0.7f + 0.1f * getEnchantmentLevel(Enchantments.SWEEPING_EDGE, this.level(), getItemBySlot(EquipmentSlot.MAINHAND)));
            if (livingEntity.invulnerableTime <= 10) {
                dmg *= livingEntity.getMaxHealth();
                livingEntity.hurt(source, dmg);
                livingEntity.invulnerableTime = 0;
            }
        }
    }
}