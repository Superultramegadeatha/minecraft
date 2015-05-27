package com.super_deathagon.itemspecial.items.itemabilities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.super_deathagon.itemspecial.items.ItemSpecial;

public class EnchantmentAbility extends Enchantment{	
	public static EnchantmentFirebolt firebolt;

	protected EnchantmentAbility(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType) {
		super(enchID, enchName, enchWeight, EnumEnchantmentType.WEAPON);	
	}
	
	public boolean canApply(ItemStack stack){
		return stack.getItem() instanceof ItemSpecial ? this.type == EnumEnchantmentType.WEAPON : false;
	}
	
	
	public static EnchantmentAbility getEnchantmentById(int id){
		if(id < 64)
			return null;
		else
			return (EnchantmentAbility) Enchantment.getEnchantmentById(id);
	}
	
	
	public String getTranslatedDescription(int level){
		return null;
	}

	public static void init() {
		firebolt = new EnchantmentFirebolt(64, new ResourceLocation("firebolt"), 10);
	}
	
	public void useAbility(EntityPlayer player, byte weight) {}
}
