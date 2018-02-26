package model;

import java.util.Set;

public interface TransitionFunction<S, T> {

	/**
	 * Return the set of possible transitions for input from state
	 * @param s
	 * @param input
	 * @return
	 */
	public Set<S> transition(S s, T input);
	
}
