package com.super_deathagon.itemspecial.items.itemabilities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.super_deathagon.itemspecial.network.EnumItemAbility;
import com.super_deathagon.itemspecial.network.client.ClientItemAbilityMessage;
import com.super_deathagon.itemspecial.proxy.CommonProxy;
import com.super_deathagon.itemspecial.util.BlackMagic;
import com.super_deathagon.itemspecial.util.LangString;
import com.super_deathagon.itemspecial.util.Util;

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
	public void useAbility(EntityPlayer player, byte magnitude){
		//ClientItemAbilityMessage msg = new ClientItemAbilityMessage(EnumItemAbility.FIREBOLT, weight);
		//CommonProxy.network.sendToServer(msg);
		if(!player.worldObj.isRemote){
			BlackMagic.firebolt(player, magnitude);
		}
	}
	
	@Override
	public String getTranslatedDescription(int level){
		return LangString.enchantmentDescriptionFirebolt + level;
	}
}
