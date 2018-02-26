package model;

import java.util.HashMap;
import java.util.Set;

public class TransitionMap<S, T> extends HashMap<Pair<S, T>, Set<S>> implements TransitionFunction<S, T> {

	private static final long serialVersionUID = -825216116929693899L;

	public Set<S> transition(Object s, Object input) {
		return get(new Pair<S, T>((S) s, (T) input));
	}

	
}
