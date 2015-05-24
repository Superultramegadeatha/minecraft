package com.adam.supermod.seras;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import com.adam.supermod.SuperMod;
import com.adam.supermod.network.AbilityMessage;
import com.adam.supermod.proxy.CommonProxy;


//Shift+tele to go through walls, floors, ceilings
public class Seras {
	private static AttributeModifier sprintingSpeedBoostModifier = new AttributeModifier("generic.movementSpeed", 3.0, 2);
	private static KeyBinding[] abilityKeys;
	private static boolean healing = false;
	public static double teleportDistance = 500;
	private static SerasGui gui;
	
	public static boolean isMaster(EntityPlayer player){
		for(String name: SuperMod.ModUserList){
			if(player != null && player.getName().equals(name))
				return true;
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public static void initClient(){
		gui = new SerasGui(Minecraft.getMinecraft());
		initKeyBindings();
	}
	
	@SideOnly(Side.CLIENT)
	private static void initKeyBindings(){
		abilityKeys = new KeyBinding[3];
		abilityKeys[0] = new KeyBinding("key.abilityR.desc", Keyboard.KEY_R, "key.ability.category");
		abilityKeys[1] = new KeyBinding("key.abilityF.desc", Keyboard.KEY_F, "key.ability.category");
		abilityKeys[2] = new KeyBinding("key.abilityC.desc", Keyboard.KEY_C, "key.ability.category");
		for(KeyBinding kb : abilityKeys){
			ClientRegistry.registerKeyBinding(kb);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void renderGameOverlayEventPre(RenderGameOverlayEvent.Pre event){
		//We don't need to see the vanilla health bar 
		//we will render our own health bar
        if (event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH)){
        	event.setCanceled(true);
        	gui.renderHealthBar(event);
        }
        //We don't need to see the food bar either
        if(event.type.equals(RenderGameOverlayEvent.ElementType.FOOD))
        	event.setCanceled(true);
        
        if(event.type.equals(RenderGameOverlayEvent.ElementType.CROSSHAIRS)){
        	gui.renderCrosshairs(event);
        }
	}
	
	@SideOnly(Side.CLIENT)
	public static void keyInputEvent(){
		if(abilityKeys[0].isKeyDown()){
			(new Thread(){
				@Override
				public void run(){
					while(abilityKeys[0].isKeyDown()){
						AbilityMessage amts = new AbilityMessage(Minecraft.getMinecraft().thePlayer.getUniqueID(), 
																	AbilityMessage.Ability.ARROW);
						CommonProxy.simpleNetworkWrapper.sendToServer(amts);
						try{
							Thread.sleep(1);
						}catch(InterruptedException e){
							
						}
					}
				}
			}).start();
		}
		
		if(abilityKeys[1].isKeyDown()){
			System.out.println("Client: sending teleport message to server.");
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
				BlackMagic.teleportThroughBlock(Minecraft.getMinecraft().thePlayer, teleportDistance);	
			}else{	
				BlackMagic.teleportToLook(Minecraft.getMinecraft().thePlayer, teleportDistance);
			}
		}
				
		if(abilityKeys[2].isKeyDown()){
			AbilityMessage amts = new AbilityMessage(Minecraft.getMinecraft().thePlayer.getUniqueID(), 
														AbilityMessage.Ability.FAMILIARS);
			CommonProxy.simpleNetworkWrapper.sendToServer(amts);
		}
	}
	
	public static void playerTickEvent(EntityPlayer player){
		if(player.getFoodStats().getFoodLevel() != 19)
			player.getFoodStats().setFoodLevel(19);
		if(player.isSprinting())
			BlackMagic.moveFast(player, sprintingSpeedBoostModifier);
	}
	
	public static void livingHealEvent(LivingHealEvent event) {
		if(healing){
			healing = false;
			System.out.println("Healed " + event.amount + " hp.");
		}
		else{
			event.setCanceled(true);
		}		
	}

	public static void attackEntityEvent(AttackEntityEvent event) {
		if(event.entityPlayer.getHeldItem() == null && event.target instanceof EntityLiving){
			healing = true;
			float healAmount = ((EntityLiving)event.target).getHealth();
			DamageSource dSource = DamageSource.causePlayerDamage(event.entityPlayer);
			((EntityLiving)event.target).attackEntityFrom(dSource, healAmount);
			
			if(event.entityPlayer.getHealth() == event.entityPlayer.getMaxHealth()){
				AttributeModifier addMaxHealth = new AttributeModifier("generic.maxHealth", healAmount, 0);
				event.entityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(addMaxHealth);
				System.out.println("Max health is now "+event.entityPlayer.getMaxHealth() + ".");
			}
			event.entityPlayer.heal(healAmount);
		}		
	}
}
