package model;

import java.util.HashMap;
import java.util.Set;

public class TransitionMap<S, T> extends HashMap<Pair<S, T>, Set<S>> implements TransitionFunction<S, T> {

	private static final long serialVersionUID = -825216116929693899L;

	@Override
	public Set<S> transition(S s, T input) {
		return get(new Pair<S, T>(s, input));
	}

	
}
