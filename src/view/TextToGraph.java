package view;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;

import model.FiniteAutomaton;
import model.Pair;
import model.TransitionMap;

/**
 * Convert a Finite Automaton in GraphViz format to a finite automaton object
 * 
 * @author nielstron
 *
 */
public class TextToGraph {

	/**
	 * Parses a string-representation of a finite automaton <br>
	 * Format should be in GraphViz like at
	 * https://martin-thoma.com/how-to-draw-a-finite-state-machine/
	 * <ul>
	 * <li>Normal or doublecircled nodes represent states</li>
	 * <li>Edges with labels represent state transitions, edges without labels
	 * represent &epsilon; transitions</li>
	 * <li>Accepting states have shape "doublecircle"</li>
	 * <li>Initial states are marked by a state with shape "point" and an edge
	 * towards the initial state</li>
	 * </ul>
	 * 
	 * @param input
	 * @return
	 */
	public FiniteAutomaton<String, String> parseFA(String graphviz) {

		GraphParser parser = new GraphParser(new ByteArrayInputStream(graphviz.getBytes()));

		Map<String, GraphNode> nodes = parser.getNodes();
		Map<String, GraphEdge> edges = parser.getEdges();

		Set<String> states = new HashSet<>();
		Set<String> inputs = new HashSet<>();
		TransitionMap<String, String> transition = new TransitionMap<>();
		Set<String> initStates = new HashSet<>();
		Set<String> acceptingStates = new HashSet<>();

		// Add all nodes to state space
		for (GraphNode node : nodes.values()) {
			// if the shape is "point" the node is not a real state
			if (node.getAttribute("shape").equals("point")) {
				continue;
			}

			String state = node.getId();
			states.add(state);

			// If the node is doublecircled it is accepting
			if (node.getAttribute("shape").equals("doublecircle")) {
				acceptingStates.add(state);
			}
		}

		// Add all edges to the transitionfunction and all inputs to input space
		for (GraphEdge e : edges.values()) {
			// If the starting nodes shape is "point" this marks an initial
			// state
			if ("point".equals(e.getNode1().getAttribute("shape"))) {
				initStates.add(e.getNode1().getId());
				continue;
			}
			String input = (String) e.getAttribute("label");
			String from = e.getNode1().getId();
			String to = e.getNode2().getId();
			if (input == null)
				input = "";
			// add the input to input space
			inputs.add(input);
			// add the transition to the transitionfunction
			Pair<String, String> tuple = new Pair<>(from, input);
			if (transition.get(tuple) == null) {
				transition.put(tuple, new HashSet<>());
			}
			transition.get(tuple).add(to);
		}

		return new FiniteAutomaton<>(states, inputs, transition, initStates, acceptingStates);

	}
}
