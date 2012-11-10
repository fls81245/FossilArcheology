package net.minecraft.src;

import java.util.List;
public class ContainerAnalyzer extends Container
{
	private TileEntityAnalyzer analyzer;
    private int cookTime;
    private int burnTime;
    private int itemBurnTime;
	public ContainerAnalyzer(InventoryPlayer inventoryplayer, TileEntity tileentityanalyzer)
    {
        cookTime = 0;
        burnTime = 0;
        itemBurnTime = 0;
        analyzer = (TileEntityAnalyzer) tileentityanalyzer;
		for (int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				addSlotToContainer(new Slot(analyzer,j+i*3,20+j*18,17+i*18));
			}
		}
		addSlotToContainer(new SlotFurnace(inventoryplayer.player,analyzer,9,116,21));
        //addSlot(new Slot(tileentityfurnace, 0, 56, 17));
        //addSlot(new Slot(tileentityfurnace, 1, 56, 53));
        //addSlot(new SlotFurnace(inventoryplayer.player, tileentityfurnace, 2, 116, 35));
		for (int i=0;i<3;i++){
			addSlotToContainer(new SlotFurnace(inventoryplayer.player,analyzer,10+i,111+18*i,53));			
		}
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
        par1ICrafting.updateCraftingInventoryInfo(this, 0, this.analyzer.analyzerCookTime);
        par1ICrafting.updateCraftingInventoryInfo(this, 1, this.analyzer.analyzerBurnTime);
        par1ICrafting.updateCraftingInventoryInfo(this, 2, this.analyzer.currentItemBurnTime);
    }
	public void updateCraftingResults()
    {
        super.updateCraftingResults();
        for(int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);
            if(cookTime != analyzer.analyzerCookTime)
            {
                icrafting.updateCraftingInventoryInfo(this, 0, analyzer.analyzerCookTime);
            }
            if(burnTime != analyzer.analyzerBurnTime)
            {
                icrafting.updateCraftingInventoryInfo(this, 1, analyzer.analyzerBurnTime);
            }
            if(itemBurnTime != analyzer.currentItemBurnTime)
            {
                icrafting.updateCraftingInventoryInfo(this, 2, analyzer.currentItemBurnTime);
            }
        }

        cookTime = analyzer.analyzerCookTime;
        burnTime = analyzer.analyzerBurnTime;
        itemBurnTime = analyzer.currentItemBurnTime;
    }
	 public void updateProgressBar(int i, int j)
    {
        if(i == 0)
        {
            analyzer.analyzerCookTime = j;
        }
        if(i == 1)
        {
            analyzer.analyzerBurnTime = j;
        }
        if(i == 2)
        {
            analyzer.currentItemBurnTime = j;
        }
    }
	public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return analyzer.isUseableByPlayer(entityplayer);
    }
	public ItemStack func_82846_b(int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(i);
        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(i == 2)
            {
                if(!mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }
            } else
            if(i >= 3 && i < 30)
            {
                if(!mergeItemStack(itemstack1, 30, 39, false))
                {
                    return null;
                }
            } else
            if(i >= 30 && i < 39)
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