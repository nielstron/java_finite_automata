package view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import guru.nidi.graphviz.attribute.MutableAttributed;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.MutableNodePoint;
import guru.nidi.graphviz.parse.Parser;
import model.FiniteAutomaton;
import model.Pair;
import model.TransitionMap;

/**
 * Convert a Finite Automaton in GraphViz format to a finite automaton object
 * @author nielstron
 *
 */
public class GraphConverter {

	public String getAttribute(MutableAttributed<?> attr, String key){
		
		for(Entry<String, Object> entry : attr){
			if(entry.getKey().equals(key)){
				return entry.getValue().toString();
			}
		}
		return null;
	}
	
	/**
	 * Parses a string-representation of a finite automaton <br>
	 * Format should be in GraphViz like at
	 * https://martin-thoma.com/how-to-draw-a-finite-state-machine/
	 * <ul>
	 * <li> Normal or doublecircled nodes represent states </li>
	 * <li> Edges with labels represent state transitions, edges without labels represent &epsilon; transitions </li>
	 * <li> Accepting states have shape "doublecircle" </li>
	 * <li> Initial states are marked by a state with shape "point" and an edge towards the initial state </li>
	 * <li> The name "__init" may not be used for any node </li> 
	 * </ul>
	 * @param input
	 * @return
	 */
	public FiniteAutomaton<String, String> stringToFA(InputStream inputStream) {
		
		MutableGraph parsedGraph;
		try {
			parsedGraph = Parser.read(inputStream);
		} catch (IOException e1) {
			return new FiniteAutomaton<>();
		}
		
		Collection<MutableNode> nodes = parsedGraph.nodes();
		
		Set<String> states = new HashSet<>();
		Set<String> inputs = new HashSet<>();
		TransitionMap<String, String> transition = new TransitionMap<>();
		Set<String> initStates = new HashSet<>();
		Set<String> acceptingStates = new HashSet<>();
		
		// Add all nodes to state space
		for(MutableNode node : nodes){
			// if the shape is "point" the node is not a real state
			if(!"point".equals(getAttribute(node.attrs(), "shape"))){
				
				String state = node.label().toString();
				states.add(state);
				
				// If the node is doublecircled it is accepting
				if("doublecircle".equals(getAttribute(node, "shape"))){
					acceptingStates.add(state);
				}
			}
			// Process all of its links
			for(Link l : node.links()){
				MutableNode from = ((MutableNodePoint) l.from()).node();
				MutableNode to = ((MutableNodePoint) l.to()).node();
				if("point".equals(getAttribute(from, "shape"))){
					initStates.add(to.label().toString());
					continue;
				}
				String input = getAttribute(l.attrs(), "label");

				if(input == null || input.equals("&epsilon;") || input.equals("")){
					input = "";
				}
				// add the input to input space
				inputs.add(input);
				// add the transition to the transitionfunction
				Pair<String, String> tuple = new Pair<>(from.label().toString(), input);
				if(transition.get(tuple) == null){
					transition.put(tuple, new HashSet<>());
				}
				transition.get(tuple).add(to.label().toString());
			}
		}
		
		
		return new FiniteAutomaton<>(states, inputs, transition, initStates, acceptingStates);
		
	}
	
	/**
	 * Parses a string-representation of a finite automaton <br>
	 * Format should be in GraphViz like at
	 * https://martin-thoma.com/how-to-draw-a-finite-state-machine/
	 * <ul>
	 * <li> Normal or doublecircled nodes represent states </li>
	 * <li> Edges with labels represent state transitions, edges without labels represent &epsilon; transitions </li>
	 * <li> Accepting states have shape "doublecircle" </li>
	 * <li> Initial states are marked by a state with shape "point" and an edge towards the initial state </li>
	 * <li> The name "__init" may not be used for any node </li> 
	 * </ul>
	 * @param input
	 * @return
	 */
	public FiniteAutomaton<String, String> stringToFA(String graphviz){
		
		return stringToFA(new ByteArrayInputStream(graphviz.getBytes()));
	}
	
	public String NFAtoString(FiniteAutomaton<?, ?> nfa){
		
		String result = "digraph {\n";
		String indent = "    ";
		
		result += "\n";
		// Insert the node with reserved name "__init"
		// as a starting point for the incoming arrow to start nodes
		result += indent + "\"__init\" [shape = point];\n";
		
		// Insert all of the normal states
		for(Object s : nfa.getStates()){
			result += indent + "\"" + s.toString().replace('[', '{').replace(']', '}') + "\" [shape = ";
			if(nfa.getAccepting().contains(s)){
				result += "doublecircle";
			}
			else{
				result += "circle";
			}
			result += "];\n";
		}
		
		result += "\n";
		
		// Insert edges to the initial states
		for(Object s : nfa.getInit()){
			result += indent + "__init -> " + "\"" + s.toString().replace('[', '{').replace(']', '}') + "\"" + ";\n";
		}
		
		result += "\n";
		
		// Insert the remaining transitions
		for(Object s : nfa.getStates()){
			for(Object i : nfa.getInputValues()){
				Set<?> sucs = nfa.getTransitionF().transition(s, i);
				if(sucs != null){
					if("".equals(i)){
						i = "&epsilon;";
					}
					for(Object suc : sucs){
						result += indent + "\"" + s.toString().replace('[', '{').replace(']', '}') + "\"" + " -> " + "\"" + suc.toString().replace('[', '{').replace(']', '}') + "\"" + " [label = \"" + i.toString() + "\"];\n";
					}
				}
			}
		}
		
		result += "}";
		
		return result;
		
	}

}
