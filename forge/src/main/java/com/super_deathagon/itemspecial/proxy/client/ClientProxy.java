package com.super_deathagon.itemspecial.proxy.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.super_deathagon.itemspecial.SpecialItems;
import com.super_deathagon.itemspecial.proxy.CommonProxy;


public class ClientProxy extends CommonProxy{

	
	@Override
	public void fmlLifeCycleEvent(FMLInitializationEvent event){
		super.fmlLifeCycleEvent(event);
		 // required in order for the renderer to know how to render your item.  Likely to change in the near future.
	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(SpecialItems.MODID + ":itemspecialspear", "inventory");
	    final int DEFAULT_ITEM_SUBTYPE = 0;
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CommonProxy.spear, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}
}