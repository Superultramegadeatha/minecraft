package com.adam.itemspecial.items.itemabilities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.adam.itemspecial.items.ItemSpecial;

public class EnchantmentAbility extends Enchantment{
	public static final Enchantment firebolt = new EnchantmentFirebolt(64, new ResourceLocation("firebolt"), 10);
	
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
	
	public void useAbility(World world, EntityPlayer player, byte weight){}
	
	public String getTranslatedDescription(int level){
		return null;
	}
}
