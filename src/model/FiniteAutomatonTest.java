package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.algorithms.RabinScott;
import view.GraphConverter;

public class FiniteAutomatonTest {
	
	private FiniteAutomaton<String, String> nfa;
	private FiniteAutomaton<Set<String>, String> dfa;
	
	@Before
	public void init(){
		nfa = new GraphConverter().stringToFA("digraph {\r\n    \r\n    qi [shape = point];\r\n    node [shape = circle];\r\n    X; 0; 1; 2;\r\n    3 [shape = doublecircle];\r\n    \r\n    qi -> X;\r\n    X -> X [label = \"1\"];\r\n    X -> X [label = \"0\"];\r\n    X -> 0 [label = \"1\"];\r\n    0 -> 1 [label = \"1\"];\r\n    0 -> 1 [label = \"0\"];\r\n    1 -> X [label = \"&epsilon;\"];\r\n    1 -> 2 [label = \"0\"];\r\n    2 -> 3 [label = \"1\"];\r\n    2 -> 3 [label = \"0\"];\r\n\r\n}");
		dfa = new RabinScott<String, String>().constructDNF(nfa);
	}

	@Test
	public void testAccepts(){
		assertTrue(nfa.accepts(Arrays.asList("1", "1", "0", "0")));
		assertTrue(nfa.accepts(Arrays.asList("1", "1", "0", "1")));
		assertTrue(nfa.accepts(Arrays.asList("1", "1", "1", "0", "0")));
		assertTrue(!nfa.accepts(Arrays.asList("0", "0")));
		assertTrue(dfa.accepts(Arrays.asList("1", "1", "0", "0")));
		assertTrue(dfa.accepts(Arrays.asList("1", "1", "0", "1")));
		assertTrue(dfa.accepts(Arrays.asList("1", "1", "1", "0", "0")));
		assertTrue(!dfa.accepts(Arrays.asList("0", "0")));

	}
	
	@Test
	public void testEpsilonClosure(){
		HashSet<String> state1 = new HashSet<>(Arrays.asList("X"));
		assertTrue(nfa.epsilonClosure(state1).equals(state1));
		HashSet<String> state2 = new HashSet<>(Arrays.asList("1"));
		HashSet<String> state2Closure = new HashSet<>(Arrays.asList("X", "1"));
		assertTrue(nfa.epsilonClosure(state2).equals(state2Closure));
	}
	
}
