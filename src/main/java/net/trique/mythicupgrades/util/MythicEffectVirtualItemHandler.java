package net.trique.mythicupgrades.util;

import java.util.HashMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

public class MythicEffectVirtualItemHandler {

    private ItemEffectsList allEffects;

    public MythicEffectVirtualItemHandler(ItemEffectsList allEffects) {
        this.allEffects = allEffects;
    }

    public void handlePostHit(LivingEntity target, LivingEntity attacker) {
        CommonFunctions.addStatusEffects(target, getOnHitEffectsForEnemy(), attacker);
        CommonFunctions.addStatusEffects(attacker, getOnHitEffectsForSelf(), attacker);
    }
    public HashMap<MobEffect, EffectMeta> getMainHandEffects() {
        return allEffects.getForMainHand();
    }
    public HashMap<MobEffect, EffectMeta> getEquipmentEffectsForSelf() {
        return allEffects.getForEquipmentForSelf();
    }
    public HashMap<MobEffect, EffectMeta> getEquipmentEffectsForEnemy() {
        return allEffects.getForEquipmentForEnemy();
    }
    public HashMap<MobEffect, EffectMeta> getOnHitEffectsForEnemy() {
        return allEffects.getOnHitForEnemy();
    }
    public HashMap<MobEffect, EffectMeta> getOnHitEffectsForSelf() {
        return allEffects.getOnHitForSelf();
    }

    public void setNewEffects(ItemEffectsList list) {
        allEffects = list;
    }
}