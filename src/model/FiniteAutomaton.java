package model;

import java.util.HashSet;
import java.util.Set;

/**
 * NFA in form of a graph
 * 
 * @author nielstron
 *
 */
public class FiniteAutomaton<S, T> {

	/**
	 * Set of possible states
	 */
	private Set<S> states;

	/**
	 * Set of possible Inputs
	 */
	private Set<T> inputValues;

	/**
	 * Transition function for the automaton
	 */
	private TransitionFunction<S, T> transitionF;

	/**
	 * Initial States
	 */
	private Set<S> init;

	/**
	 * Set of accepting states
	 */
	private Set<S> accepting;

	public FiniteAutomaton(Set<S> states, Set<T> inputValues, TransitionFunction<S, T> transitionF, Set<S> init,
			Set<S> accepting) {
		super();
		this.states = states;
		this.inputValues = inputValues;
		this.transitionF = transitionF;
		this.init = init;
		this.accepting = accepting;
	}

	public FiniteAutomaton() {
		this(new HashSet<>(), new HashSet<>(), (state, input) -> {
			return new HashSet<S>();
		}, new HashSet<>(), new HashSet<>());
	}

	/**
	 * @return the states
	 */
	public Set<S> getStates() {
		return states;
	}

	/**
	 * @param states
	 *            the states to set
	 */
	public void setStates(Set<S> states) {
		this.states = states;
	}

	/**
	 * @return the inputValues
	 */
	public Set<T> getInputValues() {
		return inputValues;
	}

	/**
	 * @param inputValues
	 *            the inputValues to set
	 */
	public void setInputValues(Set<T> inputValues) {
		this.inputValues = inputValues;
	}

	/**
	 * @return the transitionF
	 */
	public TransitionFunction<S, T> getTransitionF() {
		return transitionF;
	}

	/**
	 * @param transitionF
	 *            the transitionF to set
	 */
	public void setTransitionF(TransitionFunction<S, T> transitionF) {
		this.transitionF = transitionF;
	}

	/**
	 * @return the init
	 */
	public Set<S> getInit() {
		return init;
	}

	/**
	 * @param init
	 *            the init to set
	 */
	public void setInit(Set<S> init) {
		this.init = init;
	}

	/**
	 * @return the accepting
	 */
	public Set<S> getAccepting() {
		return accepting;
	}

	/**
	 * @param accepting
	 *            the accepting to set
	 */
	public void setAccepting(Set<S> accepting) {
		this.accepting = accepting;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NFA [Q=" + states + ", Σ=" + inputValues + ", Δ=" + transitionF
				+ ", q0=" + init + ", F=" + accepting + "]";
	}
	
	

}
