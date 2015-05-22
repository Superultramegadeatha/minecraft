package com.adam.supermod.proxy.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import com.adam.supermod.SuperMod;
import com.adam.supermod.player.PlayerGui;
import com.adam.supermod.proxy.CommonProxy;
import com.adam.supermod.seras.Seras;

public class ClientProxy extends CommonProxy{

	
	@Override
	public void fmlLifeCycleEvent(FMLInitializationEvent event){
		super.fmlLifeCycleEvent(event);
		Seras.initClient();
	}

	@Override
	public void keyInputEvent(KeyInputEvent event){	
		if(Seras.isMaster(Minecraft.getMinecraft().thePlayer))
			Seras.keyInputEvent();
		else
			return;
	}
	
	@Override
	public void renderGameOverlayEventPre(RenderGameOverlayEvent.Pre event){
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(Seras.isMaster(player))
			Seras.renderGameOverlayEventPre(event);
		else
			PlayerGui.renderHealthBar(event);
	}

}