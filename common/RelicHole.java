package net.minecraft.src;

import java.util.Random;

public class RelicHole {

		public int CordX,CordY,CordZ,range,MixedIndex;
		public RelicHole(){
			CordX=CordY=CordZ=0;
		}
		public RelicHole(Random rand,int WidthX,int Layers,int WidthZ,byte[] BlockArray,int HoleSize){
			do{
				CordX=rand.nextInt(WidthX);
				CordY=rand.nextInt(Layers);
				CordZ=rand.nextInt(WidthZ);
				MixedIndex=CordY*WidthX*WidthZ+CordZ*WidthX+CordX;
			}while(BlockArray[MixedIndex]==0);
			range=rand.nextInt(HoleSize)+1;
		}
		public boolean isHole(int x,int y,int z){
			int Distance=(int)Math.sqrt(Math.pow((CordX-x), 2)+Math.pow((CordY-y), 2)+Math.pow((CordZ-z), 2));
			return (Distance<=range);
		}

}
