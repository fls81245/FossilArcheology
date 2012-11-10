package net.minecraft.src;

public class ItemCoins extends ForgeItem {
	public static final int BASEICONLOC=45;
	private EnumCoinType delfWorldType=null;	
	protected ItemCoins(int i,EnumCoinType selfType) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize=64;
		this.delfWorldType=selfType;
	}
	public int getIconFromDamage(int i)
    {
		return BASEICONLOC+i;
    }
	public String getItemNameIS(ItemStack itemstack)
    {
		return "coins";
    }
	public int getTargetDimension(){
		return this.delfWorldType.targetDimension;
	}
}
