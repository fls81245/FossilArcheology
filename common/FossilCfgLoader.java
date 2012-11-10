package net.minecraft.src;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;

public class FossilCfgLoader{
	private static final File cfgdir;
    public static final File IDcfgfile;
    private static final File OptionConfigFile;
	public static final Properties props = new Properties();
	static 
    {
        cfgdir = new File(".", "/config/");
        IDcfgfile = new File(cfgdir, "ModFossil.cfg");
        OptionConfigFile=new File(cfgdir, "ModFossilOptions.cfg");
    }
	public void CfgLoader(){
	}
	public static Properties loadIDConfig() throws IOException
    {
        cfgdir.mkdir();
        if(!IDcfgfile.exists() && !IDcfgfile.createNewFile())
        {
            return null;
        }
        if(IDcfgfile.canRead())
        {
            FileInputStream fileinputstream = new FileInputStream(IDcfgfile);
            props.load(fileinputstream);
            fileinputstream.close();
			return props;
        }else return null;
    }
	public static Properties loadOptionConfig()
	        throws IOException
	    {
	        cfgdir.mkdir();
	        if(!OptionConfigFile.exists() && !OptionConfigFile.createNewFile())
	        {
	            return null;
	        }
	        if(OptionConfigFile.canRead())
	        {
	            FileInputStream fileinputstream = new FileInputStream(OptionConfigFile);
	            props.load(fileinputstream);
	            fileinputstream.close();
				return props;
	        }else return null;
	    }
	public static void saveIDConfig(Properties props)
        throws IOException
    {
        cfgdir.mkdir();
        if(!IDcfgfile.exists() && !IDcfgfile.createNewFile())
        {
            return;
        }
        if(IDcfgfile.canWrite())
        {
            FileOutputStream fileoutputstream = new FileOutputStream(IDcfgfile);
            props.store(fileoutputstream, "ModFossil Config");
            fileoutputstream.close();
        }
    }
	public static void saveOptionConfig(Properties props)
	        throws IOException
	    {
	        cfgdir.mkdir();
	        if(!OptionConfigFile.exists() && !OptionConfigFile.createNewFile())
	        {
	            return;
	        }
	        if(OptionConfigFile.canWrite())
	        {
	            FileOutputStream fileoutputstream = new FileOutputStream(OptionConfigFile);
	            props.store(fileoutputstream, "ModFossil Option Config");
	            fileoutputstream.close();
	        }
	    }
}