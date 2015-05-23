package com.adam.supermod.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.adam.supermod.network.AbilityMessage;
import com.adam.supermod.seras.BlackMagic;

public class ClientMessageHandler implements IMessageHandler<AbilityMessage, IMessage>{

	@Override
	public IMessage onMessage(final AbilityMessage message, MessageContext ctx) {
		
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			public void run() {
				System.out.println("Client: Message received.");
				processMessage(message);
			}
		});
		return null;
	}

	private void processMessage(AbilityMessage message){
		World world = Minecraft.getMinecraft().theWorld;
		EntityPlayer sendingPlayer = world.getPlayerEntityByUUID(message.getSendingPlayerUUID());
		
		switch (message.getAbility()) {
		case ARROW: 
			//BlackMagic.fireArrow(sendingPlayer);
			break;
		case TELEPORT: 
			BlackMagic.spawnTeleportParticles(sendingPlayer);
			break;
		case FAMILIARS: 
			//BlackMagic.makeufat(sendingPlayer);
			break;
		default: 
			System.err.println("Invalid ability type in ServerMessageHandler:" + String.valueOf(message.getAbility()));
			return;
		}
	}
}
