package io.github.fourohfour.gtnlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.plugin.java.JavaPlugin;

public class GTNLib extends JavaPlugin{
	public static Map<String, String> getTags(String s) throws InvalidFileException{
		Map<String, String> smap = new HashMap<String, String>();
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(c == '{'){
				String x = iterator(s, i + 1);
				String[] split = x.split(":");
				smap.put(split[0], split[1]);
			}
		}
		return smap;
	}
	
	private static String iterator(String s, int i){
		String acc = "";
		for(int x = i; x < s.length(); x++){
			char c = s.charAt(x);
			if(c == '}'){
				return acc;
			}
			else{
				acc = acc + c;
			}
		}
		return null;
	}
	
	public static GroupedTagList buildGTL(File f) throws InvalidFileException{
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String str = new String();
		while (s.hasNext()){
			str = str + " " + s.next();
		}
		s.close();
		str = str.replace("\n", "").replace("\r", "");
		String[] strl = str.split(";");
		
		GroupedTagList list = new GroupedTagList();
		String n = null;
		for (String i : strl){
			int torem = 0;
			for(int ind = 0; ind < i.length(); ind++){
				char ch = i.charAt(ind);
				if(!(ch == ' ')){
					torem = ind;
					break;
				}
			}
			i = i.substring(torem);
			
			if (!(i.charAt(0) == '/' || i == "")){  
			    if(i.charAt(0) == '@'){
			    	n = i.substring(1);
			    }
			    else{
			    	GroupedTagItem item = new GroupedTagItem(getTags(i));
			    	if(!(n == null)){
					    list.addItem(n, item);
			    	}
			    	else{
			    		list.addItem("base", item);
			    	}
			    }
			}
	    }
		return list;
    }
}