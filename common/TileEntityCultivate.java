package net.minecraft.src;
import java.util.Random;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;


public class TileEntityCultivate extends TileEntity
    implements IInventory, ISidedInventory
	{
		private ItemStack cultivateItemStacks[];
		public int furnaceBurnTime;
		public int currentItemBurnTime;
		public int furnaceCookTime;
		
		public TileEntityCultivate()
		{
			cultivateItemStacks = new ItemStack[3];
			furnaceBurnTime = 0;
			currentItemBurnTime = 0;
			furnaceCookTime = 0;
		}
		public int getSizeInventory()
		{
			return cultivateItemStacks.length;
		}
		public ItemStack getStackInSlot(int i)
		{
			return cultivateItemStacks[i];
		}
		public ItemStack decrStackSize(int i, int j)
		{
			if(cultivateItemStacks[i] != null)
			{
				if(cultivateItemStacks[i].stackSize <= j)
				{
					ItemStack itemstack = cultivateItemStacks[i];
					cultivateItemStacks[i] = null;
					return itemstack;
				}
				ItemStack itemstack1 = cultivateItemStacks[i].splitStack(j);
				if(cultivateItemStacks[i].stackSize == 0)
				{
					cultivateItemStacks[i] = null;
				}
				return itemstack1;
			} else
			{
				return null;
			}
		}
		public void setInventorySlotContents(int i, ItemStack itemstack)
		{
			cultivateItemStacks[i] = itemstack;
			if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			{
				itemstack.stackSize = getInventoryStackLimit();
			}
		}
		public String getInvName()
		{
			return "Cultivate";
		}
		public void readFromNBT(NBTTagCompound nbttagcompound)
		{
			super.readFromNBT(nbttagcompound);
			NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
			cultivateItemStacks = new ItemStack[getSizeInventory()];
			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < cultivateItemStacks.length)
				{
					cultivateItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}

			furnaceBurnTime = nbttagcompound.getShort("BurnTime");
			furnaceCookTime = nbttagcompound.getShort("CookTime");
			currentItemBurnTime = getItemBurnTime(cultivateItemStacks[1]);
		}
		public void writeToNBT(NBTTagCompound nbttagcompound)
		{
			super.writeToNBT(nbttagcompound);
			nbttagcompound.setShort("BurnTime", (short)furnaceBurnTime);
			nbttagcompound.setShort("CookTime", (short)furnaceCookTime);
			NBTTagList nbttaglist = new NBTTagList();
			for(int i = 0; i < cultivateItemStacks.length; i++)
			{
				if(cultivateItemStacks[i] != null)
				{
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte)i);
					cultivateItemStacks[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}

			nbttagcompound.setTag("Items", nbttaglist);
		}
		
		public int getInventoryStackLimit()
		{
			return 64;
		}
		public int getCookProgressScaled(int i)
		{
			return (furnaceCookTime * i) / 6000;
		}
	    public int getBurnTimeRemainingScaled(int i)
		{
			if(currentItemBurnTime == 0)
			{
				currentItemBurnTime = 6000;
			}
			return (furnaceBurnTime * i) / currentItemBurnTime;
		}
		public boolean isBurning()
		{
			return furnaceBurnTime > 0;
		}
		public void updateEntity()
		{
			boolean flag = furnaceCookTime > 0;
			boolean flag1 = false;
			if(furnaceBurnTime > 0)
			{
				furnaceBurnTime--;
			}
			if(!worldObj.isRemote)
			{
				if(furnaceBurnTime == 0 && canSmelt())
				{
					currentItemBurnTime = furnaceBurnTime = getItemBurnTime(cultivateItemStacks[1]);
					if(furnaceBurnTime > 0)
					{
						flag1 = true;
						if(cultivateItemStacks[1] != null)
						{
							if(cultivateItemStacks[1].getItem().hasContainerItem())
							{
								cultivateItemStacks[1] = new ItemStack(cultivateItemStacks[1].getItem().getContainerItem());
							} else
							{
								cultivateItemStacks[1].stackSize--;
							}
							if(cultivateItemStacks[1].stackSize == 0)
							{
								cultivateItemStacks[1] = null;
							}
						}
					}
				}
				if(isBurning() && canSmelt())
				{
					furnaceCookTime++;
					if(furnaceCookTime == 6000)
					{
						furnaceCookTime = 0;
						smeltItem();
						flag1 = true;
					}
				} else
				{
					if (furnaceCookTime != 0){
						if (!canSmelt()) furnaceCookTime = 0;
					}
				}
				if(flag != (furnaceCookTime > 0))
				{
					flag1 = true;
					BlockCultivate.updateFurnaceBlockState(furnaceCookTime > 0, worldObj, xCoord, yCoord, zCoord);
				}
			}
			if(flag1)
			{
				onInventoryChanged();
			}
			if (furnaceCookTime==3001 && new Random().nextInt(100)<=30) ((BlockCultivate)mod_Fossil.blockcultivateIdle).onBlockRemovalLost(worldObj, xCoord, yCoord, zCoord,true);

		}
		private boolean canSmelt()
		{
			if(cultivateItemStacks[0] == null)
			{
				return false;
			}
			ItemStack itemstack = CheckSmelt(cultivateItemStacks[0]);
			if(itemstack == null)
			{
				return false;
			}
			if(cultivateItemStacks[2] == null)
			{
				return true;
			}
			if(!cultivateItemStacks[2].isItemEqual(itemstack))
			{
				return false;
			}
			if(cultivateItemStacks[2].stackSize < getInventoryStackLimit() && cultivateItemStacks[2].stackSize < cultivateItemStacks[2].getMaxStackSize())
			{
				return true;
			}
			return cultivateItemStacks[2].stackSize < itemstack.getMaxStackSize();
		}
		public void smeltItem()
		{
			if(!canSmelt())
			{
				return;
			}
			ItemStack itemstack = CheckSmelt(cultivateItemStacks[0]);
			//need change
			if(cultivateItemStacks[2] == null)
			{
				cultivateItemStacks[2] = itemstack.copy();
			} else
			if(cultivateItemStacks[2].itemID == itemstack.itemID)
			{
				cultivateItemStacks[2].stackSize += itemstack.stackSize;
			}
			if(cultivateItemStacks[0].getItem().hasContainerItem())
			{
				cultivateItemStacks[0] = new ItemStack(cultivateItemStacks[0].getItem().getContainerItem());
			} else
			{
				cultivateItemStacks[0].stackSize--;
			}
			if(cultivateItemStacks[0].stackSize <= 0)
			{
				cultivateItemStacks[0] = null;
			}
		}
		private int getItemBurnTime(ItemStack itemstack)
		{
			if(itemstack == null)
			{
				return 0;
			}
			int i = itemstack.getItem().shiftedIndex;
			if (i==mod_Fossil.biofossil.shiftedIndex) return 300;
			if (i==mod_Fossil.ChickenSoup.shiftedIndex) return 1000;
			if (i==mod_Fossil.Ancientegg.shiftedIndex) return 12000;
			if (i==Item.porkRaw.shiftedIndex) return 3000;
			if (i==Item.fishRaw.shiftedIndex) return 3000;
			if (i==Item.beefRaw.shiftedIndex) return 4000;
			if (i==mod_Fossil.RawDinoMeat.shiftedIndex) return 4000;
			if (i==Item.chickenRaw.shiftedIndex) return 1500;
			if (i==Item.egg.shiftedIndex) return 1000;
			if (i==Item.slimeBall.shiftedIndex) return 800;
			if (i==Item.bucketMilk.shiftedIndex) return 6000;				
			else return 0;
		}
		public boolean isUseableByPlayer(EntityPlayer entityplayer)
		{
			if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
			{
				return false;
			}
			return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
		}
		private ItemStack CheckSmelt(int ItemIndex){
				return null;
		}
		private ItemStack CheckSmelt(ItemStack Itemstack){
				if (Itemstack.itemID==mod_Fossil.DNA.shiftedIndex) return new ItemStack(mod_Fossil.Ancientegg,1,Itemstack.getItemDamage());
				if (Itemstack.itemID==mod_Fossil.AnimalDNA.shiftedIndex) {
					int Tmp=Itemstack.getItemDamage();
					if (Tmp!=3)return new ItemStack(mod_Fossil.EmbyoSyringe,1,(Tmp>3)?Tmp-1:Tmp);
					else return new ItemStack(Item.egg,1); 
				}
				else return null;
		}
		 public void openChest()
		    {
		    }

		    public void closeChest()
		    {
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
	}