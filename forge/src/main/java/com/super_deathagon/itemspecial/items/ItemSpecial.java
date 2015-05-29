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
	
	public void enchant(ItemStack stack){
		String name = "Super's Spear " + Math.random();

		EnchantmentAbility ability = EnchantmentAbility.getEnchantmentById(64);
		int level = 1 + (int)Math.floor(Math.random()*(ability.getMaxLevel() - 1));

   		stack.setStackDisplayName(name);
   		stack.addEnchantment(ability, level);
	}
	
    /**
     * allows items to add custom lines of information to the mouseover description
     *  
     * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
     * @param advanced Whether the setting "Advanced tooltips" is enabled
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
    	tooltip.add(LangString.enchantmentUsage);
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
}
