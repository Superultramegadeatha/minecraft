package com.super_deathagon.itemspecial.proxy.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import com.super_deathagon.itemspecial.SpecialItems;
import com.super_deathagon.itemspecial.proxy.CommonProxy;

public class ClientProxy extends CommonProxy{

	
	@Override
	public void fmlLifeCycleEvent(FMLInitializationEvent event){
		super.fmlLifeCycleEvent(event);
		
	    final int DEFAULT_ITEM_SUBTYPE = 0;
	    ItemModelMesher imm = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
	    
		 // required in order for the renderer to know how to render your item.  Likely to change in the near future.
	    ModelResourceLocation spearResource = new ModelResourceLocation(SpecialItems.MODID + ":itemspecialspear", "inventory");
	    ModelResourceLocation greatSwordResource = new ModelResourceLocation(SpecialItems.MODID + ":itemspecialgreatsword", "inventory");
	    ModelResourceLocation eyeWandResource = new ModelResourceLocation(SpecialItems.MODID + ":itemspecialeyewand", "inventory");
	    
	    imm.register(CommonProxy.spear, DEFAULT_ITEM_SUBTYPE, spearResource);
	    imm.register(CommonProxy.greatSword, DEFAULT_ITEM_SUBTYPE, greatSwordResource);
	    imm.register(CommonProxy.eyeWand, DEFAULT_ITEM_SUBTYPE, eyeWandResource);

	}
}
