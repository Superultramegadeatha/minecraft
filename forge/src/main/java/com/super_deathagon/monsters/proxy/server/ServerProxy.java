package com.super_deathagon.monsters.proxy.server;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.super_deathagon.monsters.handler.MonstersEventHandler;
import com.super_deathagon.monsters.proxy.CommonProxy;

public class ServerProxy extends CommonProxy{

	@EventHandler
	public void fmlLifeCycleEvent(FMLPreInitializationEvent event){
		super.fmlLifeCycleEvent(event);
	}
	
	@EventHandler
	public void fmlLifeCycleEvent(FMLInitializationEvent event){
		super.fmlLifeCycleEvent(event);
	}
	
	@EventHandler
	public void fmlLifeCycleEvent(FMLPostInitializationEvent event){
		super.fmlLifeCycleEvent(event);
	}
}
