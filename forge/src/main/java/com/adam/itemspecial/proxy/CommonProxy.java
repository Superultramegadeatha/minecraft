package com.adam.itemspecial.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.adam.itemspecial.items.ItemSpecialSpear;
import com.adam.itemspecial.items.itemabilities.EnchantmentAbility;
import com.adam.itemspecial.network.client.ClientItemAbilityMessage;
import com.adam.itemspecial.network.server.ServerMessageHandler;



public class CommonProxy{
	public static SimpleNetworkWrapper simpleNetworkWrapper;
    public static ItemSpecialSpear srh;
    public static EnchantmentAbility enchantment;
    
	public void fmlLifeCycleEvent(FMLPreInitializationEvent event){
		registerItems();
		registerNetworkWrapper();
	}
	
	public void fmlLifeCycleEvent(FMLInitializationEvent event){}
	public void fmlLifeCycleEvent(FMLPostInitializationEvent event){}
	public void fmlLifeCycleEvent(FMLServerStartedEvent event){
	}
	
	public void registerItems(){
        srh = (ItemSpecialSpear)(new ItemSpecialSpear().setUnlocalizedName("itemspecialspear"));
        GameRegistry.registerItem(srh, "itemspecialspear");
	}
	
	public void registerNetworkWrapper(){
		// You MUST register the messages in Common, not in ClientOnly.
		final byte ITEM_ABILITY_MESSAGE_ID = 65; // a unique ID for this message type. It helps detect errors if you don't use zero!
		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("ItemAbilityChannel");
		simpleNetworkWrapper.registerMessage(	ServerMessageHandler.class, 
												ClientItemAbilityMessage.class,
												ITEM_ABILITY_MESSAGE_ID, Side.SERVER);
		//if(event.getSide() != Side.SERVER)
		//simpleNetworkWrapper.registerMessage(ClientMessageHandler.class, 
		//									 bilityMessage.class,
		//									 ITEM_ABILITY_MESSAGE_ID, Side.CLIENT);
	}
}