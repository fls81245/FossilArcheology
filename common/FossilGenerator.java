package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

public class FossilGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (int i=0;i<20;i++){
            int OreSelection=random.nextInt(100);
            if (OreSelection>50) 
            	(new WorldGenMinable(mod_Fossil.blockFossil.blockID, 10)).generate(world, random, chunkX*16+random.nextInt(16), random.nextInt(60), chunkZ*16+random.nextInt(16));
            else (new WorldGenMinable(mod_Fossil.blockPermafrost.blockID, 5)).generate(world, random, chunkX*16+random.nextInt(16), random.nextInt(40), chunkZ*16+random.nextInt(16));
           /* if(!FossilOptions.SpawnShipwrecks || !mod_Fossil.ShipA.generate(world, random, chunkX, 60, chunkZ))
            	if (!FossilOptions.SpawnAcademy || !mod_Fossil.AcademyA.generate(world, random, chunkX, 50+random.nextInt(20), chunkZ))
            		if (FossilOptions.SpawnWeaponShop) mod_Fossil.WeaponShopA.generate(world, random, chunkX, 50, chunkZ);*/
		} 


	}

}
