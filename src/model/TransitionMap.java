package model;

import java.util.HashMap;
import java.util.Set;

public class TransitionMap<T> extends HashMap<Pair<State, T>, Set<State>> implements TransitionFunction<T> {

	private static final long serialVersionUID = -825216116929693899L;

	@Override
	public Set<State> transition(State s, T input) {
		return get(new Pair<State, T>(s, input));
	}

	
}
