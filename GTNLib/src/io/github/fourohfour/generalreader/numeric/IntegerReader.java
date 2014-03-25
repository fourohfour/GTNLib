package io.github.fourohfour.generalreader.numeric;

import java.util.HashSet;
import java.util.Set;

import io.github.fourohfour.gtnlib.GTNReader;
import io.github.fourohfour.gtnlib.GroupedTagItem;
import io.github.fourohfour.gtnlib.GroupedTagList;
import io.github.fourohfour.gtnlib.InvalidFileException;

public class IntegerReader implements GTNReader<Integer, Set<Integer>>{

	@Override
	public Integer singleAccumulator(GroupedTagList list, String name) {
		Integer i = null;
		try {
			i = reader(list.getItems(name).iterator().next());
		} catch (InvalidFileException e) {
			e.printWarning();
		}
		
		return i;
	}

	@Override
	public Set<Integer> multipleAccumulator(GroupedTagList list, String name) {
		Set<GroupedTagItem> iset = list.getItems(name);
		Set<Integer> intset = new HashSet<>();
		
		for(GroupedTagItem i : iset){
			try {
				intset.add(reader(i));
			} catch (InvalidFileException e) {
				e.printWarning();
			}
		}
		return intset;
	}

	@Override
	public Integer reader(GroupedTagItem item) throws InvalidFileException {
		if(item.hasTag("t")){
			NumericType ntype = NumericType.valueOf(item.getTag("t"));
			if(!(ntype == null)){
				if(ntype == NumericType.INT){
					if(item.hasTag("v")){
						try{
						Integer i = Integer.valueOf(item.getTag("v"));
						return i;
						}
						catch(NumberFormatException e){
							throw new InvalidFileException(3);
						}
					}
				}
			}
			else{
				throw new InvalidFileException(3);
			}
		}
		return null;
	}
}
