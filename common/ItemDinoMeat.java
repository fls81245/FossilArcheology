package net.minecraft.src;

import java.util.List;



public class ItemDinoMeat extends ForgeItemFood {
	public static final int TypeCount=EnumDinoType.values().length;
	public ItemDinoMeat(int i, int j, float f, boolean flag) {
		super(i, j, f, flag);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize=64;
	}
	public int getIconFromDamage(int i)
    {
		return 54;
    }
	public String getItemNameIS(ItemStack itemstack)
    {
			switch(GetTypeFromInt(itemstack.getItemDamage())){
			case Triceratops:
				return "Meattriceratops";
			case Raptor:
				return "MeatRaptor";
			case TRex:
				return "MeatTRex";
			case Pterosaur:
				return "MeatPterosaur";
			case Nautilus:
				return "MeatNautilus";
			case Plesiosaur:
				return "MeatPlesiosaur";
			case Mosasaurus:
				return "MeatMosasaurus";
			case Stegosaurus:
				return "MeatStegosaurus";
			case dilphosaur:
				return "MeatUtahraptor";
			case Brachiosaurus:
				return "MeatBrachiosaurus";
			default:
				return "DinoMeat";
		}
    }
	private EnumDinoType GetTypeFromInt(int data){
		EnumDinoType[] resultArray=EnumDinoType.values();
		return resultArray[data];
	}
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < EnumDinoType.values().length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
