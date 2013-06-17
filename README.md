CS340
=====

The project for CS340 is a HyPeerCube implementation that is built to mirror a networking protocol that pairs peers in a manner that minimizes communication time between nodes on a network.

Javadoc
http://students.cs.byu.edu/~nz3004/doc

Coverage Report
http://students.cs.byu.edu/~nz3004/coverage

Use Cases
http://students.cs.byu.edu/~nz3004/usecases/use_cases.html

Constraints
http://students.cs.byu.edu/~nz3004/usecases/Constraints.html

Below is a UML diagram representing the conceptual model of the HyPeerWeb itself.
![Conceptual Model](/extra_content/IMG_0313.jpg "Conceptual Model")

Fold: A node’s fold is the node that has the Web ID that is the binary compliment of the node’s  Web ID.Surrogate Fold: Only present when fold is missing.  Is the node withthe same Web ID, but with the highest order bit flipped.Neighbor: Any node that is “next to” the node in the hyper cube.  TheirWeb IDs can only differ by 1 bit.Surrogate Neighbor: When the hyper cube is incomplete, surrogate neighborsstand in for missing neighbors.  They are the neighbors that will be theneighbors of a new node if/when added.  Their Web ID’s are obtainedby flipping the bits (one at a time) of the Web ID of the nonexistent node.Parent: Parent’s only have one child.  The child’s height is the same asthe parents + 1.Child: The child can be an edge node.  Only one child per parent.  Thechild’s height is 1 + the parents.Number of Web ID's: Always 2^roundUp(log2(N)); where n is the height of the hyper cube.

Below is a Pertt chart illustrating what's next with the project.
![PERT Chart](/extra_content/PERT-Chart.png "PERT Chart")

Skeleton Project (including GitHub setup) - Joseph - May 1

Basic Class Methods - Joseph, Jason, and Nathan - May 3

Create Schema - Craig and Nathan - May 4

Create Store - Craig and Jason - May 6

Create Retrieve - Craig and Nathan - May 6

Test Database - Craig - May 7

UML Diagram - Everyone - May 8

Create PERT Charts - Joseph

Below is the PERTT chart for project 6.  Colored tasks are related by milestone.  Node sizes are estimates of task difficulty.  No hard due dates have been set.  Assignments will be updated as progress is made.
![Final PERTT Chart](/extra_content/ProjectSixChart.jpg "Final PERTT Chart")

The Following Are The Required Use Cases:
Name: Add Node
Description: The requirements and results of requesting to add a node to the HyPeerWeb.  Regardless of the source of the request, requirements and outcomes are the same.
Triggers: Direct method invocation (probably caused by GUI interaction with the "Add Node" button)
Input: No input required.  Insertion point is an optional input.
Output: No output.
Pre-Conditions: 
	HyPeerWeb must exist.  If HyPeerWeb does not yet exist it must be created.
	HyPeerWeb must be initialized to have a non-empty network of nodes.  If not, it must be initialized and the node being added is the root node.
	The Program must have been allowed to go through it's standard "life cycle" of initialization before adding.  If not, results are undefined.
Post-Conditions: 
	The HyPeerWeb is fully initialized with at least one node in it's Hyper Cube.
	The node that was added must exist within the Hyper Cube.
	If an insertion point was given, the insertion node must be the parent of the new node.
	Unmodified nodes must have the same Web ID as before the operation began.
	All other constraints defined by the above conceptual model must be met.

Name : Remove Node
Description: The requirements and results of requesting to delete a node from the HyPeerWeb.  Outcomes are not dependent on source of the request.
Triggers: Direct method invocation (probably caused by GUI interaction with the "Add Node" button)
Input: A Node to be removed from the HyPeerWeb
Output: No output.
Pre-Conditions:
	HyPeerWeb must exist.
	HyPeerWeb must be initialized to have a non-empty network of nodes.
	The Node to be deleted must be a node that exists in the web. 
Post-Conditions:
	The node that was deleted must not exist within the Hyper Cube.
	All surrogates, neighbors, etc must be updated to reflect the rearranged web.
	Remove can only result in an empty web when the last node remaining is removed.
	All other constraints defined by the above conceptual model must be met.

Name : Send Visitor
Description: The requirements and results of requesting to have a node send a visitor to another node.  Outcomes are dependent on the node called on and the node being targeted.
Triggers: Called directly, frequently through the GUI.
Input: 
	A destination node (or WebID).  This node will be the end destination to which the visitor will be sent.
	A visitor.  This visitor will have the opportunity to perform some undefined operation at each node along the path between the target node and the node performing this action.
Output: No required output.  Note that Visitors may have some behavior that gives an output.
Pre-Conditions:
	The Node that send() is called on is connected to the web.
	The Target Node (or WebID) is a valid Node (or WebID) connected to the web.
	The visitor must implement visitor.
	The visitor must not be null.
Post-Conditions:
	The start Node will have had the visitor visit it.
	The target Node will have had the visitor visit it.
	All nodes in-between will have had the visitor visit it.
	In-between is loosely defined as one of the shortest paths leading from the start node and the target node.  This path cannot be controlled by the caller.

Name : Broadcast Visitor
Description: The requirements and results of requesting to have a node broadcast a visitor to all other nodes.  Outcomes are not dependent on the start node.
Triggers: Called directly, frequently through the GUI.
Input: A visitor.
Output: No required output.  Visitors may have some behavior that gives an output.
Pre-Conditions:
	The Node is a valid node.
	The Node that broadcast is called on is connected to the web.
	The visitor must implement visitor.
	The visitor must not be null.
Post-Conditions:
	The start Node will have had the visitor "visit" it.
	All nodes in the web will have had the visitor "visit" it.

![Contacts](/extra_content/contact_info.png "Contacts")