package io.github.fourohfour.generalreader.numeric;

import java.util.HashSet;
import java.util.Set;

import io.github.fourohfour.gtnlib.GTNReader;
import io.github.fourohfour.gtnlib.GroupedTagItem;
import io.github.fourohfour.gtnlib.GroupedTagList;
import io.github.fourohfour.gtnlib.InvalidFileException;

public class DoubleReader implements GTNReader<Double, Set<Double>>{

	@Override
	public Double singleAccumulator(GroupedTagList list, String name) {
		Double i = null;
		try {
			i = reader(list.getItems(name).iterator().next());
		} catch (InvalidFileException e) {
			e.printWarning();
		}
		
		return i;
	}

	@Override
	public Set<Double> multipleAccumulator(GroupedTagList list, String name) {
		Set<GroupedTagItem> iset = list.getItems(name);
		Set<Double> dset = new HashSet<>();
		
		for(GroupedTagItem i : iset){
			try {
				dset.add(reader(i));
			} catch (InvalidFileException e) {
				e.printWarning();
			}
		}
		return dset;
	}

	@Override
	public Double reader(GroupedTagItem item) throws InvalidFileException {
		if(item.hasTag("t")){
			NumericType ntype = NumericType.valueOf(item.getTag("t"));
			if(!(ntype == null)){
				if(ntype == NumericType.DOUBLE){
					if(item.hasTag("v")){
						try{
						Double d = Double.valueOf(item.getTag("v"));
						return d;
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
