package com.adam.myeffects;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//@Mod(modid = SupersEffects.MODID, 
//	 name = SupersEffects.NAME, 
//	 version = SupersEffects.VERSION)
public class SupersEffects {
    public static final String MODID = "superseffects";
    public static final String NAME = "Super's Effects";
    public static final String VERSION = "0.1";
    private SupersItems items;
    
    @EventHandler
    //Initialize all items here
    public void preInit(FMLPreInitializationEvent event){
    	items = new SupersItems();
    }
    
    @EventHandler
    //Recepies go here
    public void init(FMLInitializationEvent event){
    	items.registerRenders();
    	items.registerRecipes();
    }
    
    @EventHandler
    //not sure what goes in here yet
    public void postInit(FMLPostInitializationEvent event){
    }
    
    
}
