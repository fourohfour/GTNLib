package io.github.fourohfour.gtnlib;

import java.util.Map;

public class GroupedTagItem {
	private Map<String, String> map;
	
	public GroupedTagItem(Map<String, String> m){
		this.map = m;
	}
	
	public String getTag(String s){
		return map.get(s);
	}
	
	public boolean hasTag(String s){
		return map.containsKey(s);
	}
}
