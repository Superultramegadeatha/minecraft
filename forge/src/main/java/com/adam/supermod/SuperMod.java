package com.adam.supermod;

import com.adam.supermod.proxy.CommonProxy;
import com.adam.supermod.seras.Seras;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid 	 = 	SuperMod.MODID,
	 name 	 = 	SuperMod.MODNAME,
	 version = 	SuperMod.MODVERSION)
public class SuperMod {
	public static final String MODID = "supermod";
	public static final String MODNAME = "SuperMod";
	public static final String MODVERSION = "0.0.4";
	//public static final String MODDESCRIPTION = "It's totally super!";
	//public static final String MODAUTHOR = "Super_Deathagon";
	//public static final String MODCREDITS = "Dedicated to the police girl.";
    public static String[] ModUserList = new String[]{"Super_Deathagon"};


	@SidedProxy(clientSide="com.adam.supermod.proxy.client.ClientProxy", 
				serverSide="com.adam.supermod.proxy.server.ServerProxy")
	public static CommonProxy proxy;
	@Instance(MODID)
	public static SuperMod instance;

	@EventHandler
	public void fmlLifeCycleEvent(FMLPreInitializationEvent event){
		//event.getModMetadata().credits = MODCREDITS;
		//event.getModMetadata().authorList.add(EnumChatFormatting.RED+MODAUTHOR);
		//event.getModMetadata().description = EnumChatFormatting.YELLOW+MODDESCRIPTION;
		proxy.fmlLifeCycleEvent(event);
	}
	
	@EventHandler
	public void fmlLifeCycleEvent(FMLInitializationEvent event){
		proxy.fmlLifeCycleEvent(event);
	}
	
	@EventHandler
	public void fmlLifeCycleEvent(FMLPostInitializationEvent event){
		proxy.fmlLifeCycleEvent(event);
	}
}