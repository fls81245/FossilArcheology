package net.minecraft.src;

import java.util.List;
public class ContainerCultivate extends Container
{
	private TileEntityCultivate furnace;
    private int cookTime;
    private int burnTime;
    private int itemBurnTime;
	public ContainerCultivate(InventoryPlayer inventoryplayer, TileEntity tileentityfurnace)
    {
        cookTime = 0;
        burnTime = 0;
        itemBurnTime = 0;
        furnace = (TileEntityCultivate) tileentityfurnace;
        addSlotToContainer(new Slot(furnace, 0, 49, 20));
        addSlotToContainer(new Slot(furnace, 1, 80, 53));
        addSlotToContainer(new SlotFurnace(inventoryplayer.player, furnace, 2, 116, 21));
        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
                addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for(int j = 0; j < 9; j++)
        {
            addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.updateCraftingInventoryInfo(this, 0, this.furnace.furnaceCookTime);
        par1ICrafting.updateCraftingInventoryInfo(this, 1, this.furnace.furnaceBurnTime);
        par1ICrafting.updateCraftingInventoryInfo(this, 2, this.furnace.currentItemBurnTime);
    }
	public void updateCraftingResults()
    {
        super.updateCraftingResults();
        for(int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);
            if(cookTime != furnace.furnaceCookTime)
            {
                icrafting.updateCraftingInventoryInfo(this, 0, furnace.furnaceCookTime);
            }
            if(burnTime != furnace.furnaceBurnTime)
            {
                icrafting.updateCraftingInventoryInfo(this, 1, furnace.furnaceBurnTime);
            }
            if(itemBurnTime != furnace.currentItemBurnTime)
            {
                icrafting.updateCraftingInventoryInfo(this, 2, furnace.currentItemBurnTime);
            }
        }

        cookTime = furnace.furnaceCookTime;
        burnTime = furnace.furnaceBurnTime;
        itemBurnTime = furnace.currentItemBurnTime;
    }
	public void updateProgressBar(int i, int j)
    {
        if(i == 0)
        {
            furnace.furnaceCookTime = j;
        }
        if(i == 1)
        {
            furnace.furnaceBurnTime = j;
        }
        if(i == 2)
        {
            furnace.currentItemBurnTime = j;
        }
    }
	public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return furnace.isUseableByPlayer(entityplayer);
    }
	public ItemStack func_82846_b(int par1)
    {
		 ItemStack itemstack = null;
	        Slot slot = (Slot)inventorySlots.get(par1);
	        if(slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();
	            if(par1 == 2)
	            {
	                if(!mergeItemStack(itemstack1, 3, 39, true))
	                {
	                    return null;
	                }
	            } else
	            if(par1 >= 3 && par1 < 30)
	            {
	                if(!mergeItemStack(itemstack1, 30, 39, false))
	                {
	                    return null;
	                }
	            } else
	            if(par1 >= 30 && par1 < 39)
	            {
	                if(!mergeItemStack(itemstack1, 3, 30, false))
	                {
	                    return null;
	                }
	            } else
	                if(!mergeItemStack(itemstack1, 3, 39, false))
	                {
	                    return null;
	                }
	            if(itemstack1.stackSize == 0)
	            {
	                slot.putStack(null);
	            } else
	            {
	                slot.onSlotChanged();
	            }
	            if (itemstack1.stackSize == itemstack1.stackSize)
	            {
	                return null;
	            }
	        }
	        return itemstack;
    }
}