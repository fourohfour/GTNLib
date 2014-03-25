package io.github.fourohfour.mcreader.location;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import io.github.fourohfour.gtnlib.GTNReader;
import io.github.fourohfour.gtnlib.GroupedTagItem;
import io.github.fourohfour.gtnlib.GroupedTagList;
import io.github.fourohfour.gtnlib.InvalidFileException;

public class LocationReader implements GTNReader<Location, Set<Location>>{

	@Override
	public Location singleAccumulator(GroupedTagList list, String name) {
		Set<GroupedTagItem> lset = list.getItems(name);
		Location loc = null;
		try {
			loc = reader(lset.iterator().next());
		} catch (InvalidFileException e) {
			e.printStackTrace();
		}
		return loc;
	}

	@Override
	public Set<Location> multipleAccumulator(GroupedTagList list, String name) {
		Set<GroupedTagItem> lset = list.getItems(name);
		Set<Location> locset = new HashSet<Location>();
		for(GroupedTagItem i : lset){
			try {
				locset.add(reader(i));
			} catch (InvalidFileException e) {
				e.printStackTrace();
			}
		}
		return locset;
	}

	@Override
	public Location reader(GroupedTagItem i) throws InvalidFileException {
		World w = null;
		Double x = 0.0;
		Double y = 0.0;
		Double z = 0.0;
		
		if(i.hasTag("w")){
			w = Bukkit.getWorld(i.getTag("w"));
			if(w == null){
				throw new InvalidFileException(3);
			}
		}
		else{
			w = Bukkit.getWorld("world");
		}
		
		if(i.hasTag("x")){
			try{
			    x = Double.valueOf(i.getTag("x"));
			}
			catch (NumberFormatException e){
				throw new InvalidFileException(3);
			}
		}
		else{
			throw new InvalidFileException(2);
		}
		
		if(i.hasTag("y")){
			try{
			    y = Double.valueOf(i.getTag("y"));
			}
			catch (NumberFormatException e){
				throw new InvalidFileException(3);
			}
			
		}
		else{
			throw new InvalidFileException(2);
		}
		
		if(i.hasTag("z")){
			try{
			    z = Double.valueOf(i.getTag("z"));
			}
			catch (NumberFormatException e){
				throw new InvalidFileException(3);
			}
		}
		else{
			throw new InvalidFileException(2);
		}
		return new Location(w, x, y, z);
	}
}
