package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class IDException extends IllegalArgumentException {
	private File cfgLocation;
	private HashSet<Integer> ConflictedID=new HashSet<Integer>();
	public IDException setLoc(File cfgLoc){
		this.cfgLocation=cfgLoc;
		return this;
	}
	public boolean IsConflicted(){
		return (!ConflictedID.isEmpty());
	}
	public void add(int ID){
		this.ConflictedID.add(ID);
	}
	public String getMessage(){
		String locPath="/config/mod_Fossil.cfg";
		try {
			locPath=cfgLocation.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder result=new StringBuilder().append("[mod_Fossil Error]ID conflicted at ID:\n");
		Iterator it=ConflictedID.iterator();
		while (it.hasNext()) result.append(it.next()).append(' ');
		result.append('\n').append("Please change ID at ").append(locPath);
		return result.toString();
	}
}
