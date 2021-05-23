# Boolean Network Support Tool
This tool was developed as part of my dissertation for MComp Computer Science at Newcastle University.  

## Usage

To use the tool you must first put the network you wish to analyse into the required format, for this tool the format used is a set of truth tables for each entity in the network. Some example networks can be found in *test_files/*. As well as the truth tables there is the option of adding a title and description for the network, this is demonstrated in *test_files/test_case_1.txt*. 

The tool has functionality for performing a trace of a network, identifying attractors , and applying modifiers (over-expression and knock-out). All of these behaviours can be seen in the visualisations created by the tool which show a wiring and state transition diagram.

### Updating a network

While analysing a network, it is possible to change the state of that network in order to perform a trace starting from that state, this can be done by selecting an entity on the wiring diagram, which will cause the state of the entity to flip. 

To update the entire network at once you can select a global state from the state transition diagram.

As mentioned previously it is also possible to apply modifiers to a network, which will also cause it to update. The changes in the network caused by these modifiers can be seen in the attractors identified in the network, and the state transition diagram which will reflect the changes in the next state rules for the network.

**Note**: To update a network using diagrams, you must be in picking mode. To enter picking mode click on the diagram and press the *p* key. To exit picking mode so that you can move pan/zoom the diagram again, click on the diagram and press the *t* key to enter transform mode.

### Moving through a network

When looking at a network, you can use the *Update* button underneath the network visualisations to update the network a single step to its next state. This update can be seen in both the wiring and state transition diagrams.

### Viewing an attractor

When analysing a network, the tool finds all of the attractors in the network and lists them, this list updates inline with any changes to the network which have the potential to alter its attractors. To visualise an attractor simply select it in the list and it will be shown in place of the state transition diagram, allowing you to focus solely on the attractor. To bring back the state transition diagram simply click on the attractor currently being displayed in the list to dismiss it.

### Exporting diagrams

In the File menu of the tool there is the option to export the diagrams shown in the tool. Both the wiring diagram and state transition diagram are exported, the latter of which reflects any modifiers applied to the network at the time of exporting.

The diagrams are exported to a *.dot* file which can be used with the tool [Graphviz](graphviz.org).







