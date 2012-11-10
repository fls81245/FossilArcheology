package net.minecraft.src;


public class ItemGen extends Item{
	public ItemGen(int i){
		super(i);
		maxStackSize=64;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
}