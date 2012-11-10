package net.minecraft.src;

import java.util.Random;

class RelicHoleList{
	private RelicHole[] Holes;
	private int DEFAULT_HOLE_COUNT=9;
	private int DEFAULT_HOLE_SIZE=7;

	public RelicHoleList(Random rand,int WidthX,int Layers,int WidthZ,byte[] BlockArray,int HoleCount,int HoleSize){
		if (HoleCount<0) HoleCount=DEFAULT_HOLE_COUNT;
		if (HoleSize<0) HoleSize=DEFAULT_HOLE_SIZE;
		Holes=new RelicHole[HoleCount+1];
		for (int i=0;i<Holes.length;i++){
			Holes[i]=new RelicHole(rand,WidthX,Layers,WidthZ,BlockArray,HoleSize);
		}
	}
	public boolean isHole(int x,int y,int z){
		for (int i=0;i<Holes.length;i++){
			if (Holes[i].isHole(x, y, z)) return true;
		}
		return false;
	}
}
