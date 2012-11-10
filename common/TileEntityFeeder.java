// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;




// Referenced classes of package net.minecraft.src:
//            TileEntity, IInventory, ItemStack, NBTTagCompound, 
//            NBTTagList, World, Item, BlockFurnace, 
//            FurnaceRecipes, Block, Material, ModLoader, 
//            EntityPlayer

public class TileEntityFeeder extends TileEntity
    implements IInventory, ISidedInventory
{
	private ItemStack feederItemStacks[];
   // public int furnaceBurnTime;
    //public int currentItemBurnTime;
    //public int furnaceCookTime;
	public int MeatCurrent=0;
	public int MeatMax=10000;
	public int VegCurrent=0;
	public int VegMax=10000;
	public boolean[] ContainType=new boolean[EnumDinoType.values().length];
    public TileEntityFeeder()
    {
         feederItemStacks = new ItemStack[2];
         ClearTypeRecord();
        //furnaceBurnTime = 0;
        //currentItemBurnTime = 0;
        //furnaceCookTime = 0;
    }
    public void ClearTypeRecord(){
    	for (int i=0;i<ContainType.length;i++){
    		ContainType[i]=false;
    	}
    }
    public int getSizeInventory()
    {
        return  feederItemStacks.length;
    }

   public ItemStack getStackInSlot(int i)
    {
        return  feederItemStacks[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if( feederItemStacks[i] != null)
        {
            if( feederItemStacks[i].stackSize <= j)
            {
                ItemStack itemstack =  feederItemStacks[i];
                 feederItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 =  feederItemStacks[i].splitStack(j);
            if( feederItemStacks[i].stackSize == 0)
            {
                 feederItemStacks[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
         feederItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public String getInvName()
    {
        return "Feeder";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
         feederItemStacks = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 <  feederItemStacks.length)
            {
                 feederItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        /*furnaceBurnTime = nbttagcompound.getShort("BurnTime");
        furnaceCookTime = nbttagcompound.getShort("CookTime");
        currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);*/
		MeatCurrent=nbttagcompound.getInteger("MeatCurrent");
		VegCurrent=nbttagcompound.getInteger("VegCurrent");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        /*nbttagcompound.setShort("BurnTime", (short)furnaceBurnTime);
        nbttagcompound.setShort("CookTime", (short)furnaceCookTime);*/
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i <  feederItemStacks.length; i++)
        {
            if( feederItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                 feederItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
		nbttagcompound.setInteger("MeatCurrent",MeatCurrent);
		nbttagcompound.setInteger("VegCurrent",VegCurrent);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public int getMeatBarScaled(int i)
    {
        return (MeatCurrent * i) / MeatMax;
    }
	public int getVegBarScaled(int i)
    {
        return (VegCurrent * i) / VegMax;
    }

    /*public int getBurnTimeRemainingScaled(int i)
    {
        if(currentItemBurnTime == 0)
        {
            currentItemBurnTime = 200;
        }
        return (furnaceBurnTime * i) / currentItemBurnTime;
    }*/

    public boolean isFilled()
    {
        return  (MeatCurrent> 0)||(VegCurrent> 0);
    }

    public void updateEntity()
    {
    	boolean actioned=false;
		boolean flag_before=(MeatCurrent>0||VegCurrent>0);
		if (this.worldObj.isRemote) return;
		if (feederItemStacks[0]!=null){
			int target=feederItemStacks[0].itemID;
			if (MeatValue(target)>0){

				if (feederItemStacks[0].getItem().shiftedIndex==mod_Fossil.RawDinoMeat.shiftedIndex){
					this.ContainType[feederItemStacks[0].getItemDamage()]=true;
				}
				if ((MeatValue(target)*feederItemStacks[0].stackSize)+MeatCurrent<MeatMax) {
					MeatCurrent+=(MeatValue(target)*feederItemStacks[0].stackSize);
					actioned=true;
					feederItemStacks[0]=null;
				}
				else{
					while((MeatValue(target)+MeatCurrent<MeatMax) && feederItemStacks[0]!=null){
						MeatCurrent+=MeatValue(target);
						actioned=true;
						feederItemStacks[0].stackSize--;
						if (feederItemStacks[0].stackSize==0) feederItemStacks[0]=null;
					}
				}
			}
		}
		if (feederItemStacks[1]!=null){
			int target=feederItemStacks[1].itemID;
			if (VegValue(target)>0){
				if ((VegValue(target)*feederItemStacks[1].stackSize)+VegCurrent<VegMax) {
					VegCurrent+=(VegValue(target)*feederItemStacks[1].stackSize);
					actioned=true;
					feederItemStacks[1]=null;
				}
				else{
					while((VegValue(target)+VegCurrent<VegMax) && feederItemStacks[1]!=null){
						VegCurrent+=VegValue(target);
						actioned=true;
						feederItemStacks[1].stackSize--;
						if (feederItemStacks[1].stackSize==0) feederItemStacks[1]=null;
					}
				}
			}
		}
		//if (MeatCurrent>0) MeatCurrent--;
		//if (VegCurrent>0) VegCurrent--;
		boolean flag_After=(MeatCurrent>0||VegCurrent>0);
		if (flag_before!=flag_After) BlockFeeder.updateFurnaceBlockState(flag_After, worldObj, xCoord, yCoord, zCoord);
		if (actioned) 
			this.onInventoryChanged();
    }
	public int MeatValue(int target){
		if (target==Item.egg.shiftedIndex) return 100;
		if (target==Item.porkRaw.shiftedIndex) return 20;
		if (target==Item.porkCooked.shiftedIndex) return 30;
		if (target==Item.chickenCooked.shiftedIndex) return 10;
		if (target==Item.chickenRaw.shiftedIndex) return 30;
		if (target==Item.beefCooked.shiftedIndex) return 20;
		if (target==Item.beefRaw.shiftedIndex) return 40;
		if (target==Item.fishRaw.shiftedIndex) return 40;
		if (target==Item.fishCooked.shiftedIndex) return 60;
		if (target==Item.slimeBall.shiftedIndex) return 10;
		if (target==mod_Fossil.RawDinoMeat.shiftedIndex) return 100;
		if (target==mod_Fossil.CookedDinoMeat.shiftedIndex) return 100;
		return -1;
	}
	public int VegValue(int target){
		if (target==mod_Fossil.FernSeed.shiftedIndex) return 50;
		if (target==Item.appleRed.shiftedIndex) return 100;
		if (target==Item.wheat.shiftedIndex) return 40;
		if (target==Item.reed.shiftedIndex) return 20;
		if (target==Item.paper.shiftedIndex) return 60;
		if (target==Item.book.shiftedIndex) return 180;
		if (target==Item.bread.shiftedIndex) return 120;
		if (target==Item.melon.shiftedIndex) return 25;
		if (target==Block.plantRed.blockID || target==Block.plantYellow.blockID) return 20;
		if (target==Block.mushroomBrown.blockID || target==Block.mushroomBrown.blockID) return 15;
		if (target==Block.leaves.blockID || target==Block.vine.blockID)return 10;
		if (target==Block.sapling.blockID) return 10;
		if (target==Block.melon.blockID)return 200;
		return -1;
	}
    /*private boolean canSmelt()
    {
        if(furnaceItemStacks[0] == null)
        {
            return false;
        }
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);
        if(itemstack == null)
        {
            return false;
        }
        if(furnaceItemStacks[2] == null)
        {
            return true;
        }
        if(!furnaceItemStacks[2].isItemEqual(itemstack))
        {
            return false;
        }
        if(furnaceItemStacks[2].stackSize < getInventoryStackLimit() && furnaceItemStacks[2].stackSize < furnaceItemStacks[2].getMaxStackSize())
        {
            return true;
        }
        return furnaceItemStacks[2].stackSize < itemstack.getMaxStackSize();
    }*/

    /*public void smeltItem()
    {
        if(!canSmelt())
        {
            return;
        }
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);
        if(furnaceItemStacks[2] == null)
        {
            furnaceItemStacks[2] = itemstack.copy();
        } else
        if(furnaceItemStacks[2].itemID == itemstack.itemID)
        {
            furnaceItemStacks[2].stackSize += itemstack.stackSize;
        }
        if(furnaceItemStacks[0].getItem().hasContainerItem())
        {
            furnaceItemStacks[0] = new ItemStack(furnaceItemStacks[0].getItem().getContainerItem());
        } else
        {
            furnaceItemStacks[0].stackSize--;
        }
        if(furnaceItemStacks[0].stackSize <= 0)
        {
            furnaceItemStacks[0] = null;
        }
    }

    private int getItemBurnTime(ItemStack itemstack)
    {
        if(itemstack == null)
        {
            return 0;
        }
        int i = itemstack.getItem().shiftedIndex;
        if(i < 256 && Block.blocksList[i].blockMaterial == Material.wood)
        {
            return 300;
        }
        if(i == Item.stick.shiftedIndex)
        {
            return 100;
        }
        if(i == Item.coal.shiftedIndex)
        {
            return 1600;
        }
        if(i == Item.bucketLava.shiftedIndex)
        {
            return 20000;
        }
        if(i == Block.sapling.blockID)
        {
            return 100;
        } else
        {
            return ModLoader.AddAllFuel(i);
        }
    }*/

    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }
    public void openChest()
    {
    }

    public void closeChest()
    {
    }

    public boolean CheckIsEmpty(EnumDinoEating eating){
    	if (eating==EnumDinoEating.Herbivorous)return (this.VegCurrent==0);
    	else {
    		if (this.MeatCurrent==0){
        		this.ClearTypeRecord();
        		return true;
        	}else return false;
    	}
    }

    public void Feed(EntityDinosaurce target,EnumDinoEating eating){
    	while(target.HandleEating(1) && !CheckIsEmpty(eating)){
    		if(eating==EnumDinoEating.Herbivorous)this.VegCurrent--;
    		else this.MeatCurrent--;
    		//mod_Fossil.ShowMessage("Feeding..");
    	};
    }
    public boolean GetIfEatingSameBreed(EnumDinoType asker){
    	EnumDinoType[] chart=EnumDinoType.values();
    	for (int i=0;i<chart.length;i++){
    		if (asker.equals(chart[i])) return true;
    	}
    	return false;
    }
    public int getSizeInventorySide(ForgeDirection side) {
	    return 1;
    }
    public int getStartInventorySide(ForgeDirection side) {
        if (side == ForgeDirection.DOWN) return 1;
        if (side == ForgeDirection.UP) return 0; 
        return 2;
    }
	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}
	@SideOnly(Side.CLIENT)
	public int getCurrentMeat() {
		// TODO Auto-generated method stub
		return this.MeatCurrent;
	}
	@SideOnly(Side.CLIENT)
	public int getCurreentVeg() {
		// TODO Auto-generated method stub
		return this.VegCurrent;
	}
}
