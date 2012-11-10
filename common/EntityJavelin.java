package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityJavelin extends EntityArrow implements IEntityAdditionalSpawnData
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private int inData;
    protected boolean inGround;
    /*public boolean doesArrowBelongToPlayer;
    public int arrowShake;
    public Entity shootingEntity;*/
    private double damage = 2.0D;
    private int ticksInGround;
    private int ticksInAir;
    private int field_46027_au;
    public boolean arrowCritical;
    public EnumToolMaterial SelfMaterial=EnumToolMaterial.WOOD;
    public EntityJavelin(World world)
    {
        super(world);

    }

    public EntityJavelin(World world, double d, double d1, double d2)
    {
        super(world,d,d1,d2);

    }
    public EntityJavelin(World world, EntityLiving entityliving, float f,EnumToolMaterial material)
    {
         super(world,entityliving,f);   	
    	this.SelfMaterial=material;

    }
    public EntityJavelin(World world, EntityLiving entityliving, float f)
    {
        super(world,entityliving,f);
    }

    protected void entityInit()
    {
    }

    public void setArrowHeading(double d, double d1, double d2, float f,
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        ticksInGround = 0;
    }

    public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch;
            prevRotationYaw = rotationYaw;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            ticksInGround = 0;
        }
    }

    public void onUpdate()
    {
        //super.onUpdate();
    	onEntityUpdate();
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
        }
        int i = worldObj.getBlockId(xTile, yTile, zTile);
        if (i > 0)
        {
        	 Block.blocksList[i].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
             AxisAlignedBB var2 = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

             if (var2 != null && var2.isVecInside(this.worldObj.func_82732_R().getVecFromPool(this.posX, this.posY, this.posZ)))
             {
                 this.inGround = true;
             }
        }
        if (arrowShake > 0)
        {
            arrowShake--;
        }
        if (inGround)
        {
            int j = worldObj.getBlockId(xTile, yTile, zTile);
            int k = worldObj.getBlockMetadata(xTile, yTile, zTile);
            if (j != inTile || k != inData)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
                return;
            }
            ticksInGround++;
            if (ticksInGround == 1200)
            {
                setDead();
            }
            return;
        }
        ticksInAir++;
        Vec3 var17 = this.worldObj.func_82732_R().getVecFromPool(this.posX, this.posY, this.posZ);
        Vec3 var3 = this.worldObj.func_82732_R().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition var4 = this.worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
        var17 = this.worldObj.func_82732_R().getVecFromPool(this.posX, this.posY, this.posZ);
        var3 = this.worldObj.func_82732_R().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (var4 != null)
        {
            var3 = this.worldObj.func_82732_R().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
        }
        Entity entity = null;
        List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double var7 = 0.0D;
        Iterator var9 = var6.iterator();
        float var11;

        while (var9.hasNext())
        {
            Entity var10 = (Entity)var9.next();

            if (var10.canBeCollidedWith() && (var10 != this.shootingEntity || this.ticksInAir >= 5))
            {
                var11 = 0.3F;
                AxisAlignedBB var12 = var10.boundingBox.expand((double)var11, (double)var11, (double)var11);
                MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);

                if (var13 != null)
                {
                    double var14 = var17.distanceTo(var13.hitVec);

                    if (var14 < var7 || var7 == 0.0D)
                    {
                        entity = var10;
                        var7 = var14;
                    }
                }
            }
        }

        if (entity != null)
        {
        	var4 = new MovingObjectPosition(entity);
        }
        if (var4 != null)
        {
            if (var4.entityHit != null)
            {
                float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                int j1 = (int)Math.ceil((double)f1 * this.damage);
                j1+=this.SelfMaterial.getDamageVsEntity();
                if (arrowCritical)
                {
                    j1 += rand.nextInt(j1 / 2 + 2);
                }
                
                DamageSource damagesource = null;
                if (shootingEntity == null)
                {
                    damagesource = DamageSource.causeArrowDamage(this, this);
                }
                else
                {
                    //damagesource = DamageSource.causeArrowDamage(this, shootingEntity);
                	damagesource = DamageSource.causeThrownDamage(this, shootingEntity);
                }
                if (isBurning())
                {
                    var4.entityHit.setFire(5);
                }
                if (var4.entityHit.attackEntityFrom(damagesource, j1))
                {
                    if (var4.entityHit instanceof EntityLiving)
                    {
                        ((EntityLiving)var4.entityHit).arrowHitTempCounter++;
                        if (field_46027_au > 0)
                        {
                            float f7 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                            if (f7 > 0.0F)
                            {
                                var4.entityHit.addVelocity((motionX * (double)field_46027_au * 0.60000002384185791D) / (double)f7, 0.10000000000000001D, (motionZ * (double)field_46027_au * 0.60000002384185791D) / (double)f7);
                            }
                        }
                    }
                    worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                    if (!arrowCritical) setDead();
                    
                }
                /*else
                {
                    motionX *= -0.10000000149011612D;
                    motionY *= -0.10000000149011612D;
                    motionZ *= -0.10000000149011612D;
                    rotationYaw += 180F;
                    prevRotationYaw += 180F;
                    ticksInAir = 0;
                }*/
            }
            else
            {
                xTile = var4.blockX;
                yTile = var4.blockY;
                zTile = var4.blockZ;
                inTile = worldObj.getBlockId(xTile, yTile, zTile);
                inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
                motionX = (float)(var4.hitVec.xCoord - posX);
                motionY = (float)(var4.hitVec.yCoord - posY);
                motionZ = (float)(var4.hitVec.zCoord - posZ);
                float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                posX -= (motionX / (double)f2) * 0.05000000074505806D;
                posY -= (motionY / (double)f2) * 0.05000000074505806D;
                posZ -= (motionZ / (double)f2) * 0.05000000074505806D;
                worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                inGround = true;
                arrowShake = 7;
                arrowCritical = false;
            }
        }
        if (arrowCritical)
        {
            for (int i1 = 0; i1 < 4; i1++)
            {
                worldObj.spawnParticle("crit", posX + (motionX * (double)i1) / 4D, posY + (motionY * (double)i1) / 4D, posZ + (motionZ * (double)i1) / 4D, -motionX, -motionY + 0.20000000000000001D, -motionZ);
            }
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for (rotationPitch = (float)((Math.atan2(motionY, f3) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f4 = 0.99F;
        float f6 = 0.05F;
        if (isInWater())
        {
            for (int k1 = 0; k1 < 4; k1++)
            {
                float f8 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f8, posY - motionY * (double)f8, posZ - motionZ * (double)f8, motionX, motionY, motionZ);
            }

            f4 = 0.8F;
        }
        motionX *= f4;
        motionY *= f4;
        motionZ *= f4;
        motionY -= f6;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("inData", (byte)inData);
        nbttagcompound.setByte("shake", (byte)arrowShake);
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
        nbttagcompound.setByte("pickup", (byte)this.canBePickedUp);
        nbttagcompound.setDouble("damage", this.damage);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        inData = nbttagcompound.getByte("inData") & 0xff;
        arrowShake = nbttagcompound.getByte("shake") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
        if (nbttagcompound.hasKey("pickup"))
        {
            this.canBePickedUp = nbttagcompound.getByte("pickup");
        }
        if (nbttagcompound.hasKey("damage"))
        {
        	this.damage=nbttagcompound.getDouble("damage");
        }
    }

    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
        {
            boolean var2 = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !addJavelinToPlayer(par1EntityPlayer))
            {
                var2 = false;
            }

            if (var2)
            {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    protected boolean addJavelinToPlayer(EntityPlayer player) {
    	ItemStack javelin;
		switch (this.SelfMaterial){
		default:
		case WOOD:
			javelin=new ItemStack(mod_Fossil.Woodjavelin,1);
			break;
		case IRON:
			javelin=new ItemStack(mod_Fossil.Ironjavelin,1);
			break;
		case EMERALD:
			javelin=new ItemStack(mod_Fossil.Diamondjavelin,1);
			break;
		case STONE:	
			javelin=new ItemStack(mod_Fossil.Stonejavelin,1);
			break;
		case GOLD:
			javelin=new ItemStack(mod_Fossil.Goldjavelin,1);
			break;
		}
		return player.inventory.addItemStackToInventory(javelin);
	}

	public float getShadowSize()
    {
        return 0.0F;
    }



    public void setDamage(double par1)
    {
        this.damage = par1;
    }
    private Item GetJavelinByMaterial(){
    	switch (SelfMaterial){
    	case STONE:
    		return mod_Fossil.Stonejavelin;

    	case IRON:
    		return mod_Fossil.Ironjavelin;
    	case GOLD:
    		return mod_Fossil.Goldjavelin;
    	case EMERALD:
    		return mod_Fossil.Diamondjavelin;
    	case WOOD:
    	default:
    		return mod_Fossil.Woodjavelin;
    	}
    }

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(this.SelfMaterial.ordinal());
		
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		this.SelfMaterial=EnumToolMaterial.values()[data.readInt()];
		
	}
}
