package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * (General) NFA in form of a graph
 * 
 * @param <S> State space
 * @param <T> Input space
 * @author nielstron
 *
 */
public class FiniteAutomaton<S, T> {
	
	/**
	 * Set of currently active states
	 */
	private Set<S> currentStates;
	
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
		this.reset();
	}

	public FiniteAutomaton() {
		this(new HashSet<>(), new HashSet<>(), (state, input) -> {
			return new HashSet<S>();
		}, new HashSet<>(), new HashSet<>());
	}
	
	/**
	 * Reset the internal state of the automaton
	 */
	public void reset() {
		this.currentStates = this.init;
	}
	
	/**
	 * Returns the epsilon closure of this node
	 * @param state
	 * @return
	 */
	public Set<S> epsilonClosure(Set<S> state){
		Set<S> closure = new HashSet<>();
		
		Set<S> newNodes = new HashSet<>();
		newNodes.addAll(state);
		while(!newNodes.isEmpty()){
			closure.addAll(newNodes);
			
			Set<S> nextNodes = new HashSet<>();
			// Determine all nodes that can be reached from the newly discovered nodes
			for(S cur : newNodes){
				Set<S> suc = transitionF.transition(cur, "");
				// Exclude nodes we already know
				suc.removeAll(closure);
				nextNodes.addAll(suc);
			}
			newNodes = nextNodes;
		}
		
		return closure;
	}
	
	/**
	 * Returns if a list of inputs is accepted
	 * <br>
	 * <br>
	 * <b> Does not change the internal state of the automaton nor depend on its current state</b>
	 * @param inputs
	 * @return Is the automaton in accepting state after reading this input
	 */
	public boolean accepts(List<T> inputs){
		Set<S> currentStates = init;
		
		for(T i : inputs){
			Set<S> newStates = new HashSet<>();
			for(S state : currentStates){
				newStates.addAll(transitionF.transition(state, i));
			}
			currentStates = newStates;
		}
		
		return isAccepting(currentStates);
	}
	
	/**
	 * Returns if a given input is accepted<br>
	 * <br>
	 * <b> Does change the internal state of the automaton and depend on its previous state
	 * @param next
	 * @return Is the automaton in accepting state
	 */
	public boolean accept(T next){
		Set<S> newStates = new HashSet<>();
		for(S state : currentStates){
			newStates.addAll(transitionF.transition(state, next));
		}
		this.currentStates = newStates;
		return isAccepting();
	}
	
	/**
	 * Returns true if the automaton accepts the given input list<br>
	 * <br>
	 * <b> Does change the internal state of the automaton and depend on its previous state
	 * @param next
	 * @return
	 */
	public boolean accept(List<T> next){
		for(T n : next){
			accept(n);
		}
		return isAccepting();
	}

	/**
	 * Returns whether or not this automaton is currently in accepting state
	 * @return
	 */
	public boolean isAccepting(){
		return isAccepting(this.currentStates);
	}
	
	/**
	 * Returns whether or not the given state is an accepting state
	 * @param state
	 * @return
	 */
	public boolean isAccepting(S state){
		return accepting.contains(state);
	}
	
	/**
	 * Returns whether or not a state in among the given states is an accepting state
	 * @param states
	 * @return
	 */
	public boolean isAccepting(Set<S> states){
		for(S state : states){
			if(isAccepting(state))
				return true;
		}
		return false;
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
