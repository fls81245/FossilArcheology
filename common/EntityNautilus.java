// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            EntityWaterMob, ItemStack, Item, AxisAlignedBB, 
//            Material, World, MathHelper, NBTTagCompound, 
//            EntityPlayer

public class EntityNautilus extends EntityWaterMob
{
    public float field_70861_d = 0.0F;
    public float field_70862_e = 0.0F;
    public float field_70859_f = 0.0F;
    public float field_70860_g = 0.0F;
    public float field_70867_h = 0.0F;
    public float field_70868_i = 0.0F;

    /** angle of the tentacles in radians */
    public float tentacleAngle = 0.0F;

    /** the last calculated angle of the tentacles in radians */
    public float lastTentacleAngle = 0.0F;
    private float randomMotionSpeed = 0.0F;
    private float field_70864_bA = 0.0F;
    private float field_70871_bB = 0.0F;
    private float randomMotionVecX = 0.0F;
    private float randomMotionVecY = 0.0F;
    private float randomMotionVecZ = 0.0F;
    public EntityNautilus(World world)
    {
        super(world);
        this.setSize(0.95F, 0.95F);
        this.field_70864_bA = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
        texture = "/skull/Nautilus.png";

    }

    protected String getLivingSound()
    {
        return null;
    }

    protected String getHurtSound()
    {
        return null;
    }

    protected String getDeathSound()
    {
        return null;
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected int getDropItemId()
    {
        return mod_Fossil.EmptyShell.shiftedIndex;
    }

    protected void dropFewItems(boolean flag,int unusedi)
    {
        int i = rand.nextInt(5);
        if (i<=3) dropItem(mod_Fossil.EmptyShell.shiftedIndex, 1);
        else dropItem(mod_Fossil.MagicConch.shiftedIndex, 1);

    }
    public boolean interact(EntityPlayer entityplayer)
    {
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	if (itemstack==null){
    		ItemStack shell=new ItemStack(mod_Fossil.Ancientegg,1,4);
	        if(entityplayer.inventory.addItemStackToInventory(shell))
	        {
	        	if (!worldObj.isRemote){
		            worldObj.playSoundAtEntity(entityplayer, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
		            this.setDead();
		           
	        	}
	        	return true;
	        }else return false;
	        
    	}
        return false;
    }

    public boolean isInWater()
    {
        return worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.60000002384185791D, 0.0D), Material.water, this);
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.field_70862_e = this.field_70861_d;
        this.field_70860_g = this.field_70859_f;
        this.field_70868_i = this.field_70867_h;
        this.lastTentacleAngle = this.tentacleAngle;
        this.field_70867_h += this.field_70864_bA;

        if (this.field_70867_h > ((float)Math.PI * 2F))
        {
            this.field_70867_h -= ((float)Math.PI * 2F);

            if (this.rand.nextInt(10) == 0)
            {
                this.field_70864_bA = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
            }
        }

        if (this.isInWater())
        {
            float var1;

            if (this.field_70867_h < (float)Math.PI)
            {
                var1 = this.field_70867_h / (float)Math.PI;
                this.tentacleAngle = MathHelper.sin(var1 * var1 * (float)Math.PI) * (float)Math.PI * 0.25F;

                if ((double)var1 > 0.75D)
                {
                    this.randomMotionSpeed = 1.0F;
                    this.field_70871_bB = 1.0F;
                }
                else
                {
                    this.field_70871_bB *= 0.8F;
                }
            }
            else
            {
                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.field_70871_bB *= 0.99F;
            }

            if (!this.worldObj.isRemote)
            {
                this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
                this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
                this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
            }

            var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            this.field_70859_f += (float)Math.PI * this.field_70871_bB * 1.5F;
            this.field_70861_d += (-((float)Math.atan2((double)var1, this.motionY)) * 180.0F / (float)Math.PI - this.field_70861_d) * 0.1F;
        }
        else
        {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.field_70867_h)) * (float)Math.PI * 0.25F;

            if (!this.worldObj.isRemote)
            {
                this.motionX = 0.0D;
                this.motionY -= 0.08D;
                this.motionY *= 0.9800000190734863D;
                this.motionZ = 0.0D;
            }

            this.field_70861_d = (float)((double)this.field_70861_d + (double)(-90.0F - this.field_70861_d) * 0.02D);
        }
    }

    public void moveEntityWithHeading(float f, float f1)
    {
        moveEntity(motionX, motionY, motionZ);
    }

    protected void updateEntityActionState()
    {
        if(rand.nextInt(50) == 0 || !inWater || randomMotionVecX == 0.0F && randomMotionVecY == 0.0F && randomMotionVecZ == 0.0F)
        {
            float f = rand.nextFloat() * 3.141593F * 2.0F;
            randomMotionVecX = MathHelper.cos(f) * 0.2F;
            randomMotionVecY = -0.1F + rand.nextFloat() * 0.2F;
            randomMotionVecZ = MathHelper.sin(f) * 0.2F;
        }
        despawnEntity();
    }
	public void HandleBreed(){
		boolean Extsit=false;
			this.BreedTick--;
			if (this.BreedTick==0){
				int GroupAmount=0;
				List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32D, 32D, 32D));
				for (int i=0;i<list.size();i++){					
					if (list.get(i) instanceof EntityNautilus){ 
						if (!Extsit){
							GroupAmount++;
							if (GroupAmount>50) {
								Extsit=true;
								i=0;
							}
						}else{
							EntityNautilus temp=(EntityNautilus)(list.get(i));
							temp.attackEntityFrom(DamageSource.starve, 100);
						}
					}
				}
				if (!Extsit){
					if (GroupAmount>10) GroupAmount=10;
					EntityLiving entityliving=null;
						if (new Random().nextInt(100)<GroupAmount){
							entityliving = (EntityLiving)new EntityNautilus(worldObj);
							//if (new Random().nextInt(2)==0)	((EntityRaptor)entityliving).ChangeSubType(2);
							//if (worldObj.getBlockId(i, j, k)==Block.snow.blockID) ((EntityRaptor)entityliving).ChangeSubType(1);
							entityliving.setLocationAndAngles(this.posX+(new Random().nextInt(3)-1), this.posY, this.posZ+(new Random().nextInt(3)-1), worldObj.rand.nextFloat() * 360F, 0.0F);
							if(	worldObj.checkIfAABBIsClear(entityliving.boundingBox) && worldObj.getCollidingBoundingBoxes(entityliving, entityliving.boundingBox).size() == 0 && worldObj.isAnyLiquid(entityliving.boundingBox)) worldObj.spawnEntityInWorld(entityliving);
						}
				}
				this.BreedTick=3000;
			}
	}
    
	public int BreedTick=3000;
	@Override
	public int getMaxHealth() {
		return 20;
	}
}
