// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;



// Referenced classes of package net.minecraft.src:
//            Item, ItemStack, EntityPlayer, FoodStats, 
//            World, PotionEffect, EnumAction

public class ForgeItemFood extends ItemFood
{

    public ForgeItemFood(int i, int j, float f, boolean flag)
    {
        super(i,j,f,flag);

    }
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
    public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	if (itemstack.getItem().shiftedIndex==mod_Fossil.ChickenEss.shiftedIndex){
    		entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
    	}
    	return	super.onFoodEaten(itemstack, world, entityplayer);
    }
}