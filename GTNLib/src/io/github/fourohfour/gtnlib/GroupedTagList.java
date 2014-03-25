package io.github.fourohfour.gtnlib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupedTagList {
	private Map<String, Set<GroupedTagItem>> cont;
	public GroupedTagList(){
		this.cont = new HashMap<String, Set<GroupedTagItem>>();
	}
	
	public GroupedTagList addItem(String n, GroupedTagItem i){
		if (!(cont.get(n) == null)){
	        Set<GroupedTagItem> s = cont.get(n);
	        s.add(i);
		    cont.put(n, s);
		}
		else{
			Set<GroupedTagItem> set = new HashSet<GroupedTagItem>();
			set.add(i);
			cont.put(n, set);
		}
		return this;
	}
	
	public Set<GroupedTagItem> getItems(String n){
		return cont.get(n);
	}
	
	public boolean hasItems(String n){
		if (cont.get(n) == null){
			return false;
		}
		return true;
	}
}
