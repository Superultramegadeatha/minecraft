package com.adam.itemspecial.items.itemabilities;

import com.adam.itemspecial.items.ItemSpecial;
import com.adam.itemspecial.util.BlackMagic;
import com.adam.itemspecial.util.LangString;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EnchantmentFirebolt extends EnchantmentAbility{
	private int maxLevel = 5;
	
	public EnchantmentFirebolt(int enchID, ResourceLocation enchName, int enchWeight) {
		super(enchID, enchName, enchWeight, EnumEnchantmentType.WEAPON);
		this.name = "firebolt";
	}
	
	public int getMaxLevel(){
		return maxLevel;
	}
	
	public boolean canApplyTogether(Enchantment ench){
		return !(ench instanceof EnchantmentFirebolt);
	}
	
	@Override
	public void useAbility(World world ,EntityPlayer player, byte weight){
		BlackMagic.firebolt(world , player, weight);
	}
	
	@Override
	public String getTranslatedDescription(int level){
		return LangString.enchantmentDescriptionFirebolt + level;
	}
}
