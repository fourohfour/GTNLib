package io.github.fourohfour.generalreader.text;

import java.util.HashSet;
import java.util.Set;

import io.github.fourohfour.gtnlib.GTNReader;
import io.github.fourohfour.gtnlib.GroupedTagItem;
import io.github.fourohfour.gtnlib.GroupedTagList;
import io.github.fourohfour.gtnlib.InvalidFileException;

public class StringReader implements GTNReader<String, Set<String>>{

	@Override
	public String singleAccumulator(GroupedTagList list, String name) {
		try {
			return reader(list.getItems(name).iterator().next());
		} catch (InvalidFileException e) {
			e.printWarning();
		}
		return null;
	}

	@Override
	public Set<String> multipleAccumulator(GroupedTagList list, String name) {
		Set<String> strset = new HashSet<>();
		for(GroupedTagItem item : list.getItems(name)){
			try {
				strset.add(reader(item));
			} catch (InvalidFileException e) {
				e.printWarning();
			}
		}
		return null;
	}

	@Override
	public String reader(GroupedTagItem i) throws InvalidFileException {
		if(i.hasTag("t")){
			TextType t = null;
			t = TextType.valueOf(i.getTag("t"));
			if(t == TextType.STRING){
				if(i.hasTag("v")){
					return i.getTag("v");
				}
			}
		}
		return null;
		
	}

}
