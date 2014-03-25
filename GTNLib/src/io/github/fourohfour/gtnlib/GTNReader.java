package io.github.fourohfour.gtnlib;

public interface GTNReader<S, M> {
	//Accumulator methods
	abstract S singleAccumulator(GroupedTagList list, String name);
	abstract M multipleAccumulator(GroupedTagList list, String name);
	
	//Reader
	abstract S reader(GroupedTagItem i) throws InvalidFileException;
}
