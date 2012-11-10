package net.minecraft.src;

public enum EnumCoinType {
	NORMAL(1),HELL(0);
	EnumCoinType(int DimensionPar){
		this.targetDimension=DimensionPar;
	}
	public int targetDimension;
}
