package net.minecraft.src;
import java.util.Random;
public enum EnumShipTypes {
	Science(0),Battleship(1),Cargo(2),MetalTrader(3);
	private final int MetaData;
	public int getMetaData() {
		return MetaData;
	}

	private EnumShipTypes(int MD){
		this.MetaData=MD;
	}
	
	public static EnumShipTypes GetRandom(Random rand){
		int Tick=rand.nextInt(values().length);
		return values()[Tick];
	}
}
