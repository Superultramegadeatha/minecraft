package com.adam.specialItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.adam.specialItems.proxy.CommonProxy;

@Mod(modid = SupersEffects.MODID, 
	 name = SupersEffects.NAME, 
	 version = SupersEffects.VERSION)
public class SupersEffects {
    public static final String MODID = "specialitems";
    public static final String NAME = "Super's Special Items";
    public static final String VERSION = "0.0.2";
    
	@SidedProxy(clientSide="com.adam.specialItems.proxy.client.ClientProxy", 
				serverSide="com.adam.specialItems.proxy.server.ServerProxy")
	public static CommonProxy proxy;
	
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        // each instance of your item should have a name that is unique within your mod.  use lower case.
    	proxy.fmlLifeCycleEvent(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	proxy.fmlLifeCycleEvent(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	proxy.fmlLifeCycleEvent(event);
    }
    
    
}
