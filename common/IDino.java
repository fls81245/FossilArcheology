package net.minecraft.src;

public interface IDino {
	public static final int GROW_TIME_COUNT = 12000;
	
	public abstract String getOwnerName();
	
	public abstract void setOwner(String s);
	
	public abstract boolean isTamed();
	
	public abstract void setTamed(boolean flag);
	
	public abstract void SetOrder(EnumOrderType input);
	
	public abstract boolean HandleEating(int FoodValue);
	
	public abstract boolean HandleEating(int FoodValue,boolean FernFlag);
		
	public abstract boolean CheckEatable(int itemIndex);

}
