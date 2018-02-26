# NFA Powerset constructruction

Creates a DFA based on a given NFA. For more information on the procedure have a look at [Powerset construction](https://en.wikipedia.org/wiki/Powerset_construction).

Reads in Graphs formatted in the [DOT language](https://graphviz.gitlab.io/_pages/doc/info/lang.html) format by [GraphViz](https://graphviz.gitlab.io/).

Following format should be considered:
 - Normal or doublecircled nodes represent states
 - Edges with labels represent state transitions, edges without labels represent &epsilon; transitions
 - Accepting states have shape "doublecircle"
 - Initial states are marked by a state with shape "point" and an edge towards the initial state
 - The name "\_\_init" may not be used for any node

Outputs an equivalent DNF in DOT language format

&epsilon; moves aren't yet supported.
