package model.algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import model.FiniteAutomaton;
import model.Pair;
import model.TransitionFunction;
import model.TransitionMap;

/**
 * Algorithm for converting a NFA to a DFA 
 * @author nielstron
 *
 */
public class RabinScott<S, T> {

	public Map<Set<S>, Set<S>> epsClosureCache = new HashMap<>();
	
	public Set<S> epsilonClosure(Set<S> nodes, TransitionFunction<S, T> trans){
		if(epsClosureCache.containsKey(nodes)){
			return epsClosureCache.get(nodes);
		}
		
		Set<S> closure = new HashSet<>();
		
		Set<S> newNodes = new HashSet<>();
		newNodes.addAll(nodes);
		while(!newNodes.isEmpty()){
			closure.addAll(newNodes);
			
			Set<S> nextNodes = new HashSet<>();
			// Determine all nodes that can be reached from the newly discovered nodes
			for(S cur : newNodes){
				Set<S> suc = trans.transition(cur, "");
				if(suc != null){
					// Exclude nodes we already know
					suc.removeAll(closure);
					nextNodes.addAll(suc);
				}
			}
			newNodes = nextNodes;
		}
		
		epsClosureCache.put(nodes, closure);
		
		return closure;
	}
	
	/**
	 * Algorithm for converting a NFA to a DFA 
	 */
	public FiniteAutomaton<Set<S>, T> constructDNF(FiniteAutomaton<S, T> fa){
		Set<S> initState = new HashSet<>();
		initState.addAll(epsilonClosure(fa.getInit(), fa.getTransitionF()));
		
		TransitionMap<Set<S>, T> transitionFunc = new TransitionMap<>();
		Set<T> inputs = fa.getInputValues();
		Set<Set<S>> accepting = new HashSet<>();
		
		TransitionFunction<S, T> trans = fa.getTransitionF();

		Queue<Set<S>> work = new LinkedList<>();
		work.offer(new HashSet<>(initState));
		Set<Set<S>> discovered = new HashSet<>();
		while(!work.isEmpty()){
			Set<S> currentStates = work.poll();
			
			// add to accepting states if at least one state is accepting
			for(S state : currentStates){
				if(fa.getAccepting().contains(state)){
					accepting.add(currentStates);
					break;
				}
			}
			
			// For every possible input, test which states can be reached
			for(T input : fa.getInputValues()){				
				Set<S> nextStates = new HashSet<>();
				for(S state : currentStates){
					Set<S> next = trans.transition(state, input);
					if(next!= null){
						nextStates.addAll(epsilonClosure(next, trans));
					}
				}
				if(!nextStates.isEmpty()){
					// Insert that into the new Transitionfunction => set of size one as this new automaton is deterministic
					transitionFunc.put(new Pair<>(currentStates, input), new HashSet<>(Arrays.asList(nextStates)));
					// remember this new state of the resulting automaton for later
					if(discovered.add(nextStates)){
						work.offer(nextStates);
					}
				}
			}
		}
		
		return new FiniteAutomaton<Set<S>, T>(discovered, inputs, transitionFunc, new HashSet<Set<S>>(Arrays.asList(initState)), accepting);
	}
	
}
