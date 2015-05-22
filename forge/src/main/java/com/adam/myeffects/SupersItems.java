package com.adam.myeffects;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SupersItems{
	private static SupersRightHand rightHand;
	
	public SupersItems(){
		rightHand = new SupersRightHand();
		rightHand.setUnlocalizedName(rightHand.name);
		GameRegistry.registerItem(rightHand,rightHand.name);
	}
	
	public void registerRender(Item item, String name){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(SupersEffects.MODID + ":" + name, "inventory"));
	}
	
	public void registerRenders(){
		registerRender(rightHand,rightHand.name);
	}
	
	public void registerRecipes(){
		GameRegistry.addRecipe(new ItemStack(rightHand), new Object[]{
		     "   ",
		     " A ",
		     "   ", 
		     'A', Blocks.dirt
		});
	}
}
