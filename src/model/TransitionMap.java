package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class TransitionMap<S, T> extends HashMap<Pair<S, T>, Set<S>> implements TransitionFunction<S, T> {

	private static final long serialVersionUID = -825216116929693899L;

	@Override
	public @NonNull Set<S> transition(Object s, Object input) {
		return this.getOrDefault(new Pair<S, T>((S) s, (T) input), new HashSet<>());
	}
	
	@Override
	public Set<S> put(Pair<S, T> key, Set<S> value) {
		if(value == null){
			throw new IllegalArgumentException("Associated value must not be null");
		}
		return super.put(key, value);
	}

	
}
