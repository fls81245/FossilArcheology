package net.minecraft.src;

import java.util.List;

public class EntityMLighting extends EntityLightningBolt {
	private int lightningState;
	public long boltVertex;
	private int boltLivingTime;

	public EntityMLighting(World world, double d, double d1, double d2) {
		super(world,d,d1,d2);
        this.lightningState = 2;
        this.boltLivingTime = this.rand.nextInt(3) + 1;
	}

	public void onUpdate() {
		super.onUpdate();
		if (lightningState == 2) {
			worldObj.playSoundEffect(posX, posY, posZ,
					"ambient.weather.thunder", 10000F,
					0.8F + rand.nextFloat() * 0.2F);
			worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 2.0F,
					0.5F + rand.nextFloat() * 0.2F);
		}
		lightningState--;
		if (lightningState < 0) {
			if (boltLivingTime == 0) {
				setDead();
			} else if (lightningState < -rand.nextInt(10)) {
				boltLivingTime--;
				lightningState = 1;
				boltVertex = rand.nextLong();
				if (worldObj.doChunksNearChunkExist(
						MathHelper.floor_double(posX),
						MathHelper.floor_double(posY),
						MathHelper.floor_double(posZ), 10)) {
					int i = MathHelper.floor_double(posX);
					int j = MathHelper.floor_double(posY);
					int k = MathHelper.floor_double(posZ);
					if (worldObj.getBlockId(i, j, k) == 0
							&& Block.fire.canPlaceBlockAt(worldObj, i, j, k)) {
						worldObj.setBlockWithNotify(i, j, k, Block.fire.blockID);
					}
				}
			}
		}
		if (lightningState >= 0) {
			double var6 = 3D;
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					 AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(this.posX - var6, this.posY - var6, this.posZ - var6, this.posX + var6, this.posY + 6.0D + var6, this.posZ + var6));
			for (int l = 0; l < list.size(); l++) {
				Entity entity = (Entity) list.get(l);
				if (!(entity instanceof EntityPlayer)
						&& !(entity instanceof EntityFriendlyPigZombie)
						&& !(entity instanceof EntityPig))
					entity.onStruckByLightning(new EntityLightningBolt(
							worldObj, posX, posY, posZ));
			}

			worldObj.lightningFlash = 2;
		}
	}
}
