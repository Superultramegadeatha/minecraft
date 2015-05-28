package com.super_deathagon.itemspecial.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.super_deathagon.itemspecial.items.itemabilities.EnchantmentAbility;
import com.super_deathagon.itemspecial.util.LangString;


public class ItemSpecial extends Item{
	EnchantmentAbility ability;
	int abilityLevel;
	
	
	
    /**
     * allows items to add custom lines of information to the mouseover description
     *  
     * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
     * @param advanced Whether the setting "Advanced tooltips" is enabled
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
    	advanced = true;
        if(ability != null){
            if(advanced){
    	        tooltip.add(LangString.enchantmentUsage);
    	        tooltip.add(ability.getTranslatedDescription(abilityLevel));
            }
        }
    }
    
   @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player){
   		stack.setStackDisplayName("Super's Spear " + Math.random());
   		//EnchantmentAbility ability = EnchantmentAbility.getRandomAbility(stack, world, player);
   		
   		if(!world.isRemote){
   			EnchantmentAbility enchantment = EnchantmentAbility.getEnchantmentById(64);
   			
	    	if(enchantment != null){
	    		int level = 1 + (int)Math.floor(Math.random()*(enchantment.getMaxLevel() - 1));
		    	stack.addEnchantment(enchantment, level);
		    	ability = enchantment;
		    	abilityLevel = level;
	    	}
   		}
    }
    
    public void useItemAbility(ItemStack stack, World world, EntityPlayer player, byte weight){
    	if (stack == null){
            return;
        }else{
            NBTTagList nbttaglist = stack.getEnchantmentTagList();
            if (nbttaglist == null || nbttaglist.hasNoTags()){
                return;
            }
            else{
                for (int j = 0; j < nbttaglist.tagCount(); ++j){
                    short id = nbttaglist.getCompoundTagAt(j).getShort("id");
                    short level = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                    EnchantmentAbility enchantment = EnchantmentAbility.getEnchantmentById(id);
                    if (enchantment != null){
                    	enchantment.useAbility(player, (byte)(1.0*weight/enchantment.getMaxLevel()*level));
                    }
                }
            }
        }
    }
    
    public EnchantmentAbility getAbilityFromNBT(ItemStack stack){
    	if (stack == null){
    		System.out.println("Stack is null.");
            return null;
        }else{
            NBTTagList nbttaglist = stack.getEnchantmentTagList();
            if (nbttaglist == null || nbttaglist.hasNoTags()){
            	System.out.println("Stack tag list is null.");
                return null;
            }
            else{
                for (int j = 0; j < nbttaglist.tagCount(); ++j){
                    short short1 = nbttaglist.getCompoundTagAt(j).getShort("id");
                    short short2 = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                    System.out.println("Getting id " + short1);
                    System.out.println("Getting level" + short2);
                    EnchantmentAbility enchantment = EnchantmentAbility.getEnchantmentById(short1);
                    if (enchantment != null){
                    	abilityLevel = short2;
                        return enchantment;
                    }
                }
                System.out.println("Enchantment was null.");
                return null;
            }
        }
    }
}
