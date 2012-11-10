// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Iterator;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;


// Referenced classes of package net.minecraft.src:
//            Container, Slot, TileEntityDispenser, IInventory, 
//            EntityPlayer

public class ContainerFeeder extends Container
{
	int lastVegValue=0;
	int lastMeatValue=0;
    public ContainerFeeder(IInventory iinventory, TileEntity tileentityfeeder)
    {
        tileEntityFeeder = (TileEntityFeeder) tileentityfeeder;

        addSlotToContainer(new Slot(tileEntityFeeder, 0, 60, 62));
		addSlotToContainer(new Slot(tileEntityFeeder, 1, 104, 62));
        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlotToContainer(new Slot(iinventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
            addSlotToContainer(new Slot(iinventory, k, 8 + k * 18, 142));
        }

    }
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.updateCraftingInventoryInfo(this, 0, this.tileEntityFeeder.VegCurrent);
        par1ICrafting.updateCraftingInventoryInfo(this, 1, this.tileEntityFeeder.MeatCurrent);
    }
    public void updateCraftingResults()
    {
        super.updateCraftingResults();
        Iterator var1 = this.crafters.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();

            if (this.lastVegValue != this.tileEntityFeeder.VegCurrent)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.tileEntityFeeder.VegCurrent);
            }

            if (this.lastMeatValue != this.tileEntityFeeder.MeatCurrent)
            {
                var2.updateCraftingInventoryInfo(this, 1, this.tileEntityFeeder.MeatCurrent);
            }

        }

        this.lastVegValue = this.tileEntityFeeder.VegCurrent;
        this.lastMeatValue = this.tileEntityFeeder.MeatCurrent;
    }
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tileEntityFeeder.VegCurrent=par2;
        }

        if (par1 == 1)
        {
            this.tileEntityFeeder.MeatCurrent=par2;
        }

    }
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return tileEntityFeeder.isUseableByPlayer(entityplayer);
    }
    public ItemStack func_82846_b(int i)
    {
    	return null;
    }
    private TileEntityFeeder tileEntityFeeder;
}
