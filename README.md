CS340
=====

The project for CS340 is a HyPeerCube implementation that is built to mirror a networking protocol that pairs peers in a manner that minimizes communication time between nodes on a network.

Javadoc
http://students.cs.byu.edu/~nz3004/doc

Coverage Report
http://students.cs.byu.edu/~nz3004/coverage

Below is a UML diagram representing the conceptual model of the HyPeerWeb itself.
![Conceptual Model](/extra_content/IMG_0313.jpg "Conceptual Model")
![Conceptual Model](/extra_content/IMG_0314.jpg "Conceptual Model")

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

The Following Are The Required Use Cases:
Name: Add Node
Description: The requirements and results of requesting to add a node to the HyPeerWeb.  Regardless of the source of the request, requirements and outcomes are the same.
Triggers: Direct method invocation (probably caused by GUI interaction with the "Add Node" button)
Input: No input required.  Insertion point is optional input.
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
	All other constraints defined by the conceptual model must be met.

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
	Unmodified nodes must have the same Web ID as before the operation began.
	All other constraints defined by the conceptual model must be met.

Name : Send Visitor
Description: The requirements and results of requesting to have a node send a visitor to another node.  Outcomes are dependent on the start and end nodes.
Triggers: Called directly, frequently through the GUI.
Input: A destination node (or WebID).  A visitor.
Output: No required output.  Visitors may have some behavior that gives an output.
Pre-Conditions:
	The Node that send is called on is connected to the web.
	The Target Node (or WebID) is a valid Node (or WebID) connected to the web.
	The visitor must implement visitor.
	The visitor must not be null.
Post-Conditions:
	The start Node will have had the visitor visit it.
	The target Node will have had the visitor visit it.
	All nodes in-between will have had the visitor visit it.

Name : Broadcast Visitor
Description: The requirements and results of requesting to have a node broadcast a visitor to all other nodes.  Outcomes are not dependent on the start node.
Triggers: Called directly, frequently through the GUI.
Input: A visitor.
Output: No required output.  Visitors may have some behavior that gives an output.
Pre-Conditions:
	The Node that broadcast is called on is connected to the web.
	The visitor must implement visitor.
	The visitor must not be null.
Post-Conditions:
	The start Node will have had the visitor visit it.
	All nodes in the web will have had the visitor visit it.

![Contacts](/extra_content/contact_info.png "Contacts")