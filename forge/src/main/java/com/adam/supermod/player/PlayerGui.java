package com.adam.supermod.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import com.adam.supermod.SuperMod;

public class PlayerGui extends GuiIngame{
	private static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
	private static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");

	
	public PlayerGui(Minecraft mc){
		super(mc);
	}
	
	public static void renderHealthBar(RenderGameOverlayEvent.Pre event){
		
	}

	public void renderCrosshairs(RenderGameOverlayEvent.Pre event) {
	}
}
