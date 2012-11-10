package net.minecraft.src;

public class DinoAIControlledByPlayer extends EntityAIBase
{
    private final EntityDinosaurce motionTarget;
    private final float field_82638_b;
    private float field_82639_c = 0.0F;
    private boolean field_82636_d = false;
    private int field_82637_e = 0;
    private int field_82635_f = 0;

    public DinoAIControlledByPlayer(EntityDinosaurce par1EntityLiving, float par2)
    {
        this.motionTarget = par1EntityLiving;
        this.field_82638_b = par2;
        this.setMutexBits(7);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_82639_c = 0.0F;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.field_82636_d = false;
        this.field_82639_c = 0.0F;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.motionTarget.isEntityAlive() && this.motionTarget.riddenByEntity != null && this.motionTarget.riddenByEntity instanceof EntityPlayer && (this.field_82636_d || this.motionTarget.func_82171_bF());
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        EntityPlayer var1 = (EntityPlayer)this.motionTarget.riddenByEntity;
        EntityCreature var2 = (EntityCreature)this.motionTarget;
        float var3 = MathHelper.wrapAngleTo180_float(var1.rotationYaw - this.motionTarget.rotationYaw) * 0.5F;

        if (var3 > 5.0F)
        {
            var3 = 5.0F;
        }

        if (var3 < -5.0F)
        {
            var3 = -5.0F;
        }

        this.motionTarget.rotationYaw = MathHelper.wrapAngleTo180_float(this.motionTarget.rotationYaw + var3);

        if (this.field_82639_c < this.field_82638_b)
        {
            this.field_82639_c += (this.field_82638_b - this.field_82639_c) * 0.01F;
        }

        if (this.field_82639_c > this.field_82638_b)
        {
            this.field_82639_c = this.field_82638_b;
        }

        int var4 = MathHelper.floor_double(this.motionTarget.posX);
        int var5 = MathHelper.floor_double(this.motionTarget.posY);
        int var6 = MathHelper.floor_double(this.motionTarget.posZ);
        float var7 = this.field_82639_c;

        if (this.field_82636_d)
        {
            if (this.field_82637_e++ > this.field_82635_f)
            {
                this.field_82636_d = false;
            }

            var7 += var7 * 1.15F * MathHelper.sin((float)this.field_82637_e / (float)this.field_82635_f * (float)Math.PI);
        }

        float var8 = 0.91F;

        if (this.motionTarget.onGround)
        {
            var8 = 0.54600006F;
            int var9 = this.motionTarget.worldObj.getBlockId(MathHelper.floor_float((float)var4), MathHelper.floor_float((float)var5) - 1, MathHelper.floor_float((float)var6));

            if (var9 > 0)
            {
                var8 = Block.blocksList[var9].slipperiness * 0.91F;
            }
        }

        float var21 = 0.16277136F / (var8 * var8 * var8);
        float var10 = MathHelper.sin(var2.rotationYaw * (float)Math.PI / 180.0F);
        float var11 = MathHelper.cos(var2.rotationYaw * (float)Math.PI / 180.0F);
        float var12 = var2.getAIMoveSpeed() * var21;
        float var13 = Math.max(var7, 1.0F);
        var13 = var12 / var13;
        float var14 = var7 * var13;
        float var15 = -(var14 * var10);
        float var16 = var14 * var11;

        if (MathHelper.abs(var15) > MathHelper.abs(var16))
        {
            if (var15 < 0.0F)
            {
                var15 -= this.motionTarget.width / 2.0F;
            }

            if (var15 > 0.0F)
            {
                var15 += this.motionTarget.width / 2.0F;
            }

            var16 = 0.0F;
        }
        else
        {
            var15 = 0.0F;

            if (var16 < 0.0F)
            {
                var16 -= this.motionTarget.width / 2.0F;
            }

            if (var16 > 0.0F)
            {
                var16 += this.motionTarget.width / 2.0F;
            }
        }

        int var17 = MathHelper.floor_double(this.motionTarget.posX + (double)var15);
        int var18 = MathHelper.floor_double(this.motionTarget.posZ + (double)var16);
        PathPoint var19 = new PathPoint(MathHelper.floor_float(this.motionTarget.width + 1.0F), MathHelper.floor_float(this.motionTarget.height + var1.height + 1.0F), MathHelper.floor_float(this.motionTarget.width + 1.0F));

        if ((var4 != var17 || var6 != var18) && PathFinder.func_82565_a(this.motionTarget, var17, var5, var18, var19, false, false, true) == 0 && PathFinder.func_82565_a(this.motionTarget, var4, var5 + 1, var6, var19, false, false, true) == 1 && PathFinder.func_82565_a(this.motionTarget, var17, var5 + 1, var18, var19, false, false, true) == 1)
        {
            var2.getJumpHelper().setJumping();
        }

        /*if (!var1.capabilities.isCreativeMode && this.field_82639_c >= this.field_82638_b * 0.5F && this.motionTarget.getRNG().nextFloat() < 0.006F && !this.field_82636_d)
        {
            ItemStack var20 = var1.getHeldItem();

            if (var20 != null && var20.itemID == Item.field_82793_bR.shiftedIndex)
            {
                var20.damageItem(1, var1);

                if (var20.stackSize == 0)
                {
                    var1.inventory.mainInventory[var1.inventory.currentItem] = new ItemStack(Item.fishingRod);
                }
            }
        }*/

        this.motionTarget.moveEntityWithHeading(0.0F, var7);
        if (this.field_82636_d) this.motionTarget.BlockInteractive();
    }

    public boolean func_82634_f()
    {
        return this.field_82636_d;
    }

    public void func_82632_g()
    {
        this.field_82636_d = true;
        this.field_82637_e = 0;
        this.field_82635_f = this.motionTarget.getRNG().nextInt(841) + 140;
    }

    public boolean func_82633_h()
    {
        return !this.func_82634_f() && this.field_82639_c > this.field_82638_b * 0.3F;
    }
}
