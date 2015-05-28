package com.super_deathagon.monsters.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.super_deathagon.monsters.handler.MonstersEventHandler;

public class CommonProxy {
	
	
	public void fmlLifeCycleEvent(FMLPreInitializationEvent event) {}
	
	public void fmlLifeCycleEvent(FMLInitializationEvent event){
		//MinecraftForge.EVENT_BUS.register(new MonstersEventHandler());
	}

	public void fmlLifeCycleEvent(FMLPostInitializationEvent event) {}

}
