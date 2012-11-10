// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;
import java.util.Vector;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;


// Referenced classes of package net.minecraft.src:
//            TileEntity, World, EntityPlayer

public class TileEntityTimeMachine extends TileEntity implements IInventory,
		ISidedInventory {

	final int MEMORY_WIDTH = 100;
	final int MEMORY_HEIGHT = 256;
	public int field_40068_a;
	public float field_40063_b;

	public float field_40061_d;
	public float field_40062_e;
	public float CurrectFacingAngle;
	public float SendingCurrentFacing;
	public float TargetFacingAngle;
	public final float RndRound = (float) Math.PI * 2;
	public boolean PlayerClosing = false;
	private int chargeLevel = 0;
	public final int MAX_CHARGED = 1000;
	private static Random field_40064_r = new Random();
	public ItemStack[] insideStack = new ItemStack[7];
	private int[][][] memoryArray = null;
	private int[][][] memoryMDArray = null;
	public boolean isRestoring = false;
	private int restoringLayer = 0;
	private int restoreTick=0;
	private final int RESTORE_TICK=10;

	public TileEntityTimeMachine() {

	}

	public void updateEntity() {
		super.updateEntity();
		UpdateClock();
		if (!this.isRestoring) {
			charge();
			if (memoryArray == null || memoryMDArray == null)
				this.startMemory();
		} else {
			if (++restoreTick==RESTORE_TICK){
				restoreProgress();
				restoreTick=0;
			}
		}
	}

	private void restoreProgress() {
		Random rand=this.worldObj.rand;
		int blockIDTmp = 0, metaTmp = 0;
		for (int posX = 0; posX < MEMORY_WIDTH; posX++) {
			for (int posZ = 0; posZ < MEMORY_WIDTH; posZ++) {
				int check = this.worldObj.getBlockId(this.xCoord + posX
						- (this.MEMORY_WIDTH / 2), restoringLayer, this.zCoord
						+ posZ - (this.MEMORY_WIDTH / 2));
				if (isNonPerserveBlock(check))
					continue;
				blockIDTmp = this.memoryArray[posX][restoringLayer][posZ];
				metaTmp = this.memoryMDArray[posX][restoringLayer][posZ];
				this.worldObj.setBlockAndMetadata(this.xCoord + posX
						- (this.MEMORY_WIDTH / 2), restoringLayer, this.zCoord
						+ posZ - (this.MEMORY_WIDTH / 2), blockIDTmp, metaTmp);
				if (blockIDTmp != 0)
					this.worldObj.spawnParticle("portal", this.xCoord + posX
							- (this.MEMORY_WIDTH / 2)
							+ (rand.nextDouble() - 0.5D), restoringLayer
							+ rand.nextDouble() , posZ - (this.MEMORY_WIDTH / 2)
							+ (rand.nextDouble() - 0.5D)
							,
							(rand.nextDouble() - 0.5D) * 2.0D,
							-rand.nextDouble(),
							(rand.nextDouble() - 0.5D) * 2.0D);
			}
		}
		this.worldObj.playSoundEffect(this.xCoord, restoringLayer, this.zCoord,
				"mob.endermen.portal", 1.0F, 1.0F);
		this.chargeLevel = this.MAX_CHARGED
				- (int) ((float) this.restoringLayer
						/ (float) this.MEMORY_HEIGHT * this.MAX_CHARGED);
		if ((++restoringLayer) >= this.MEMORY_HEIGHT) {
			this.isRestoring = false;
			this.restoringLayer = 0;
			this.chargeLevel = 0;
		}
	}

	public void UpdateClock() {
		SendingCurrentFacing = CurrectFacingAngle;
		EntityPlayer entityplayer = worldObj.getClosestPlayer(
				(float) xCoord + 0.5F, (float) yCoord + 0.5F,
				(float) zCoord + 0.5F, 3D);
		if (entityplayer != null) {
			PlayerClosing = true;
			double d = entityplayer.posX - (double) ((float) xCoord + 0.5F);
			double d1 = entityplayer.posZ - (double) ((float) zCoord + 0.5F);
			TargetFacingAngle = (float) Math.atan2(d1, d) + RndRound / 4;
		} else {
			PlayerClosing = false;
			TargetFacingAngle += 0.02F;
		}
		for (; CurrectFacingAngle >= RndRound / 2; CurrectFacingAngle -= RndRound) {
		}
		for (; CurrectFacingAngle < -RndRound / 2; CurrectFacingAngle += RndRound) {
		}
		for (; TargetFacingAngle >= RndRound / 2; TargetFacingAngle -= RndRound) {
		}
		for (; TargetFacingAngle < -RndRound / 2; TargetFacingAngle += RndRound) {
		}
		float f;
		for (f = TargetFacingAngle - CurrectFacingAngle; f >= RndRound / 2; f -= RndRound) {
		}
		for (; f < -RndRound / 2; f += RndRound) {
		}
		CurrectFacingAngle += f * 0.4F;

		field_40068_a++;

		float f1 = (field_40061_d - field_40063_b) * 0.4F;
		float f2 = 0.2F;
		if (f1 < -f2) {
			f1 = -f2;
		}
		if (f1 > f2) {
			f1 = f2;
		}
		field_40062_e += (f1 - field_40062_e) * 0.9F;
		field_40063_b = field_40063_b + field_40062_e;

	}

	private boolean NotAllowed(byte BID) {
		if (BID == Block.blockDiamond.blockID
				|| BID == Block.oreDiamond.blockID)
			return true;
		else
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
		if (this.insideStack[var1] != null) {
			ItemStack var2 = this.insideStack[var1];
			this.insideStack[var1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public int getSizeInventory() {
		return this.insideStack.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.insideStack[var1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.insideStack[par1] != null) {
			ItemStack var3;

			if (this.insideStack[par1].stackSize <= par2) {
				var3 = this.insideStack[par1];
				this.insideStack[par1] = null;
				return var3;
			} else {
				var3 = this.insideStack[par1].splitStack(par2);

				if (this.insideStack[par1].stackSize == 0) {
					this.insideStack[par1] = null;
				}

				return var3;
			}
		} else {
			return null;
		}
	}



	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.insideStack[var1] = var2;

		if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
			var2.stackSize = this.getInventoryStackLimit();
		}

	}

	@Override
	public String getInvName() {
		return "Time Machine Stack";
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : var1.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	private void charge() {
		if (!this.isCharged())
			this.chargeLevel++;
	}

	public int getChargeLevel() {
		return this.chargeLevel;
	}

	public boolean isCharged() {
		return this.chargeLevel == this.MAX_CHARGED;
	}

	public void startWork() {
		if (this.memoryArray == null || this.memoryMDArray == null)
			return;
		if (!this.isCharged())
			return;
		this.isRestoring = true;
	}

	public void startMemory() {

		this.memoryArray = new int[MEMORY_WIDTH][MEMORY_HEIGHT][MEMORY_WIDTH];
		this.memoryMDArray = new int[MEMORY_WIDTH][MEMORY_HEIGHT][MEMORY_WIDTH];
		for (int posX = 0; posX < MEMORY_WIDTH; posX++) {
			for (int posY = 0; posY < MEMORY_HEIGHT; posY++) {
				for (int posZ = 0; posZ < MEMORY_WIDTH; posZ++) {
					int idTmp = this.worldObj.getBlockId(this.xCoord + posX
							- MEMORY_WIDTH / 2, posY, this.zCoord + posZ
							- MEMORY_WIDTH / 2);
					if (isNonPerserveBlock(idTmp))
						idTmp = 0;
					if (idTmp != 0) {
						this.memoryMDArray[posX][posY][posZ] = this.worldObj
								.getBlockMetadata(this.xCoord + posX
										- MEMORY_WIDTH / 2, posY, this.zCoord
										+ posZ - MEMORY_WIDTH / 2);
					} else
						this.memoryMDArray[posX][posY][posZ] = 0;
					this.memoryArray[posX][posY][posZ] = idTmp;
				}
			}
		}

	}

	private boolean isNonPerserveBlock(int idTmp) {
		if (idTmp == 0)
			return false;
		Block tmp = Block.blocksList[idTmp];
		if (tmp.hasTileEntity())
			return true;
		if (tmp == Block.blockDiamond || tmp == Block.oreDiamond)
			return true;
		return false;
	}

}
