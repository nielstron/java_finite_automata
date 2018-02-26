package model;

import java.util.Set;

public interface TransitionFunction<T> {

	/**
	 * Return the set of possible transitions for input from state
	 * @param s
	 * @param input
	 * @return
	 */
	public Set<State> transition(State s, T input);
	
}
