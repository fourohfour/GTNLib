package io.github.fourohfour.generalreader.text;

import java.util.HashSet;
import java.util.Set;

import io.github.fourohfour.gtnlib.GTNReader;
import io.github.fourohfour.gtnlib.GroupedTagItem;
import io.github.fourohfour.gtnlib.GroupedTagList;
import io.github.fourohfour.gtnlib.InvalidFileException;

public class CharReader implements GTNReader<Character, Set<Character>>{

	@Override
	public Character singleAccumulator(GroupedTagList list, String name) {
		try {
			return reader(list.getItems(name).iterator().next());
		} catch (InvalidFileException e) {
			e.printWarning();
		}
		return null;
	}

	@Override
	public Set<Character> multipleAccumulator(GroupedTagList list, String name) {
		Set<Character> cset = new HashSet<>();
		for(GroupedTagItem item : list.getItems(name)){
			try {
				cset.add(reader(item));
			} catch (InvalidFileException e) {
				e.printWarning();
			}
		}
		return null;
	}

	@Override
	public Character reader(GroupedTagItem i) throws InvalidFileException {
		if(i.hasTag("t")){
			TextType t = null;
			t = TextType.valueOf(i.getTag("t"));
			if(t == TextType.CHAR){
				if(i.hasTag("v")){
					return i.getTag("v").charAt(0);
				}
			}
		}
		return null;
	}

}
