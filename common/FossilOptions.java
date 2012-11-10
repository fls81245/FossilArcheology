package net.minecraft.src;

public class FossilOptions {
	public static boolean ShouldAnuSpawn=true;
	public static boolean SpawnShipwrecks=true;
	public static boolean SpawnWeaponShop=true;
	public static boolean SpawnAcademy=true;
	public static boolean DinoGrows=true;
	public static boolean DinoHunger=true;
	public static boolean TRexBreakingBlocks=true;
	public static boolean BraBreakingBlocks=true;
	public static final String[] negWords={"false","no","off","close"};
	public static boolean isNegtiveWord(String tester){
		for (int i=0;i<negWords.length;i++){
			if (tester.equalsIgnoreCase(negWords[i])) return true;
		}
		return false;
	}
}
