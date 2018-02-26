package model;

import java.util.Set;

/**
 * NFA in form of a graph
 * @author nielstron
 *
 */
public class FiniteAutomaton<T> {

	/**
	 * Set of possible states
	 */
	private Set<State> states;
	
	/**
	 * Set of possible Inputs
	 */
	private Set<T> inputValues;

	/**
	 * Transition function for the automaton
	 */
	private TransitionFunction<T> transitionF;
	
	/**
	 * Initial State
	 */
	private State init;
	
	/**
	 * Set of accepting states
	 */
	private Set<State> accepting;

	public FiniteAutomaton(Set<State> states, Set<T> inputValues, TransitionFunction<T> transitionF, State init,
			Set<State> accepting) {
		super();
		this.states = states;
		this.inputValues = inputValues;
		this.transitionF = transitionF;
		this.init = init;
		this.accepting = accepting;
	}

	/**
	 * @return the states
	 */
	public Set<State> getStates() {
		return states;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(Set<State> states) {
		this.states = states;
	}

	/**
	 * @return the inputValues
	 */
	public Set<T> getInputValues() {
		return inputValues;
	}

	/**
	 * @param inputValues the inputValues to set
	 */
	public void setInputValues(Set<T> inputValues) {
		this.inputValues = inputValues;
	}

	/**
	 * @return the transitionF
	 */
	public TransitionFunction<T> getTransitionF() {
		return transitionF;
	}

	/**
	 * @param transitionF the transitionF to set
	 */
	public void setTransitionF(TransitionFunction<T> transitionF) {
		this.transitionF = transitionF;
	}

	/**
	 * @return the init
	 */
	public State getInit() {
		return init;
	}

	/**
	 * @param init the init to set
	 */
	public void setInit(State init) {
		this.init = init;
	}

	/**
	 * @return the accepting
	 */
	public Set<State> getAccepting() {
		return accepting;
	}

	/**
	 * @param accepting the accepting to set
	 */
	public void setAccepting(Set<State> accepting) {
		this.accepting = accepting;
	}
	
	
	
}
