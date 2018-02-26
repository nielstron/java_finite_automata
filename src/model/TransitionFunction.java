package model;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

public interface TransitionFunction<S, T> {

	/**
	 * Return the set of possible transitions for input from state
	 * @param s
	 * @param input
	 * @return
	 */
	@NonNull
	public Set<S> transition(Object s, Object input);
	
}
