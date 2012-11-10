package net.minecraft.src;

public class ContainerTimeMachine extends Container
{
    private TileEntityTimeMachine timeMachine;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;

    public ContainerTimeMachine(InventoryPlayer par1InventoryPlayer, TileEntityTimeMachine par2TileEntityFurnace)
    {
        this.timeMachine = par2TileEntityFurnace;
        for (int i=0;i<2;i++){
        	for (int j=0;j<3;j++){
        		this.addSlotToContainer(new Slot(par2TileEntityFurnace,j+i*3,7+18*i,17+18*j));
        	}
        }
        this.addSlotToContainer(new Slot(par2TileEntityFurnace,6,79,36));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
   /* public void updateCraftingResults()
    {
        super.updateCraftingResults();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastCookTime != this.timeMachine.furnaceCookTime)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.timeMachine.furnaceCookTime);
            }

            if (this.lastBurnTime != this.timeMachine.furnaceBurnTime)
            {
                var2.updateCraftingInventoryInfo(this, 1, this.timeMachine.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.timeMachine.currentItemBurnTime)
            {
                var2.updateCraftingInventoryInfo(this, 2, this.timeMachine.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.timeMachine.furnaceCookTime;
        this.lastBurnTime = this.timeMachine.furnaceBurnTime;
        this.lastItemBurnTime = this.timeMachine.currentItemBurnTime;
    }*/

    public void updateProgressBar(int par1, int par2)
    {
        
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.timeMachine.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack func_82846_b(int par1)
    {
        
        return null;
    }
}
