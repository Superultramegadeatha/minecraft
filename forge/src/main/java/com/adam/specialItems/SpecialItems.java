package com.adam.specialItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.adam.specialItems.proxy.CommonProxy;

@Mod(modid = SpecialItems.MODID, 
	 name = SpecialItems.NAME, 
	 version = SpecialItems.VERSION)
public class SpecialItems {
    public static final String MODID = "specialitems";
    public static final String NAME = "Super's Special Items";
    public static final String VERSION = "0.0.3";
    public static String[] ModUserList = new String[]{"Super_Deathagon"};
    
	@SidedProxy(clientSide="com.adam.specialItems.proxy.client.ClientProxy", 
				serverSide="com.adam.specialItems.proxy.server.ServerProxy")
	public static CommonProxy proxy;
	@Instance(MODID)
	public static SpecialItems instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
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
