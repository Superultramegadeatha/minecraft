package com.super_deathagon.monsters;

import com.super_deathagon.monsters.entity.ai.EntitySuperSpider;
import com.super_deathagon.monsters.handler.MonstersEventHandler;
import com.super_deathagon.monsters.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid 	 = 	Monsters.MODID,
	name 	 = 	Monsters.MODNAME,
	version = 	Monsters.MODVERSION)
public class Monsters {

	public static final String MODID = "monsters";
	public static final String MODNAME = "Super's Monsters";
	public static final String MODVERSION = "0.0.1";

	//@SidedProxy(clientSide="com.super_deathagon.monsters.proxy.client.ClientProxy", 
	//			serverSide="com.super_deathagon.monsters.proxy.server.ServerProxy")
	//public static CommonProxy proxy;
	
	@Instance(MODID)
	public static Monsters instance;
	
	private double percentHPPB = 0.001;
	private double percentDPPB = 0.001;
	private MonstersEventHandler mHandler;
	
	public Monsters(){
		mHandler = new MonstersEventHandler(percentHPPB,percentDPPB);
	}
	
	@EventHandler
	public void fmlLifeCycleEvent(FMLPreInitializationEvent event){
		//proxy.fmlLifeCycleEvent(event);
		EntityRegistry.registerModEntity(EntitySuperSpider.class, "superspider", 64, Monsters.instance, 100, 3, true);
	}
	
	@EventHandler
	public void fmlLifeCycleEvent(FMLInitializationEvent event){
		//proxy.fmlLifeCycleEvent(event);
		MinecraftForge.EVENT_BUS.register(mHandler);
	}
	
	@EventHandler
	public void fmlLifeCycleEvent(FMLPostInitializationEvent event){
		//proxy.fmlLifeCycleEvent(event);
	}
}
