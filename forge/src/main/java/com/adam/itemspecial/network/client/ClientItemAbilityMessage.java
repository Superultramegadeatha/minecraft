package com.adam.itemspecial.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ClientItemAbilityMessage implements IMessage {
	public enum EnumItemAbility{
		TELEPORT(0,"TELEPORT"),
		FLY(1,"FLY"),
		FIREBALL(2,"FIREBALL"),
		LIGHTNING(3,"LIGHTNING"),
		FLOOD(4,"FLOOD"),
		FREEZE(5,"FREEZE"),
		EXPLOSION(6,"EXPLOSION"),
		FAMILIARS(7,"FAMILIARS");
		private final byte ID;
		private final String NAME;
		
		private EnumItemAbility(int id, String name){
			ID = (byte)id;
			NAME = name;
		}
		
		@Override
		public String toString(){
			return "ItemAbility Name:  " + NAME + " ID: " + ID;
		}
		
		public String getName(){
			return NAME;
		}
		
		public static EnumItemAbility fromBytes(ByteBuf buf) {		 
			byte id = buf.readByte();
			for (EnumItemAbility ability : EnumItemAbility.values()) {
				if (id == ability.ID) return ability;
			}
			return null;	
		}

		public void toBytes(ByteBuf buf) {	
			buf.writeByte(ID);
		}
		
		public byte getID(){
			return ID;
		}		
	} 
	private EnumItemAbility ability;
	private byte magnitude;
	private double[] coordinates;
	private boolean valid;
	
	/**
	 * Constructor used by the message handler only.
	 */
	public ClientItemAbilityMessage(){
		valid = false;
	}
	
	/**
	 * ClientItemAbilityMessage is used by the client to send an ItemAbilityMessage
	 * to the server requesting to use that ability.
	 * @param ia The ability being requested.
	 * @param mag The magnitude the ability should be performed at.
	 * @param x The x coordinate for which the ability should be used.
	 * @param y The y coordinate for which the ability should be used.
	 * @param z The z coordinate for which the ability should be used.
	 */
	public ClientItemAbilityMessage(EnumItemAbility ia, byte mag, double x, double y, double z){
		ability = ia;
		magnitude = mag;
		coordinates = new double[]{x,y,z};
		valid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		ability = EnumItemAbility.fromBytes(buf);
		magnitude = buf.readByte();
		coordinates[0] = buf.readDouble();
		coordinates[1] = buf.readDouble();
		coordinates[2] = buf.readDouble();
		valid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if(valid){
			ability.toBytes(buf);
			buf.writeByte(magnitude);
			buf.writeDouble(coordinates[0]);
			buf.writeDouble(coordinates[1]);
			buf.writeDouble(coordinates[2]);
		}
	}

	public boolean isMessageValid(){
		return valid;
	}
	
	public EnumItemAbility getAbility(){
		return ability;
	}
	
	public double[] getCoordinates(){
		return coordinates;
	}

	public byte getMagnitude() {
		return magnitude;
	}
}
