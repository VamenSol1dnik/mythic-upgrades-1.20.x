package net.trique.mythicupgrades.item;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import static net.trique.mythicupgrades.item.MUItems.*;

public enum MUToolMaterials implements ToolMaterial {
    JADE(MiningLevels.NETHERITE, 2923, 10.0f, 5.0f, 18, Ingredient.ofItems(JADE_INGOT)),
    TOPAZ(MiningLevels.NETHERITE, 8769, 11.0f, 6.0f, 18, Ingredient.ofItems(TOPAZ_INGOT)),
    AQUAMARINE(MiningLevels.NETHERITE, 2923, 10.0f, 5.0f, 18, Ingredient.ofItems(AQUAMARINE_INGOT)),
    SAPPHIRE(MiningLevels.NETHERITE, 2923, 10.0f, 5.0f, 18, Ingredient.ofItems(SAPPHIRE_INGOT)),
    RUBY(MiningLevels.NETHERITE, 2923, 13.0f, 5.0f, 18, Ingredient.ofItems(RUBY_INGOT)),
    KYANITE(MiningLevels.NETHERITE, 2923, 10.0f, 5.0f, 18, Ingredient.ofItems(KYANITE_INGOT)),
    AMETRINE(MiningLevels.NETHERITE, 2923, 10.0f, 5.0f, 18, Ingredient.ofItems(AMETRINE_INGOT));

    //NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> {
    //        return Ingredient.ofItems(new ItemConvertible[]{Items.NETHERITE_INGOT});

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairIngredient;

    MUToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Ingredient repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }
}