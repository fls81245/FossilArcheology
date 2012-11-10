package net.minecraft.src;



public class ItemBrokenSword extends Item{
	public ItemBrokenSword(int i){
		super(i);
		maxStackSize=1;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
}