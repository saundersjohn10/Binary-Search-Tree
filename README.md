
This project is a gui that allows a user to interact with a Binary Search Tree through the creation of it and the traversing the created tree.

Project utilizes:
- Recursion
- HashTable and LinkedHashMap
- Threads 
- GUI
- Creation of Nodes and Binary Search Tree
- HTML

The point of the project is to help a user learn how a Binary Search Tree is created.  A Binary Search Tree meaning that a node contains data (in this case numbers) which is branched off into up to two other nodes with the nodes to the left being less in value and to the right higher in value.  The user watches as the data they add is brought to the correct location of the tree and a new node is added.  They also watch as the node they are attempting to remove is found by utilizing the order feature of a tree.  

Video showing the GUI in action:

https://drive.google.com/file/d/1JxVUuxra6XWXH0Z2xdzcJPS7wILMeRSV/view?usp=sharing

Example tree that can be created:

<img width="525" alt="screen shot 2018-06-27 at 11 15 03 pm" src="https://user-images.githubusercontent.com/36249204/42011140-202c1a02-7a60-11e8-9f14-f78260a0accf.png">

Adding a node (Worst Case: O(n), Average Case: O(log(n)))-
The tree is parsed through going left when the added value is smaller and right when the added value is greater until the location of the new node is found.  Along the way of this parsing, the nodes it cycles through are added to a LinkedHashMap in order keep track of the nodes that had to be cycled through in order to find the right location.  A thread is then started that prints the tree with the added node at a location it was cycled through and sleeps for .5 seconds.  In the end the final tree is printed and the thread ends.

Removing a node (Worst Case: O(n), Average Case: O(log(n)))-
Removing and added are very similar processes in that the tree is parsed through to find the node and a thread is created shows the process it went through in order to find the right thread.  If the user inputs a number that is not in the tree to remove, nothing is deleted and but a thread is still created that shows a pointer looking for it.

Traversing the tree (O(n))-
Available options to traverse the tree include Pre Order (root, left, right), In Order (left, root, right), and Post Order (left, right, root).  The tree is parsed through in the selected order, adding the nodes into a LinkedHashMap.  A thread is then created that linearly checks the nodes in the map and prints the tree with a marker at the given node then sleeps.  As each marker is placed on a node, a textfield is updated to show the nodes data it just visited, and in the end, shows the list of nodes in the order chosen.

After an In-Order traversal:

<img width="519" alt="screen shot 2018-06-29 at 10 44 14 pm" src="https://user-images.githubusercontent.com/36249204/42120727-2bb469bc-7bee-11e8-89f2-26f8c6cd794b.png">

On start of GUI:

<img width="524" alt="screen shot 2018-06-27 at 10 54 53 pm" src="https://user-images.githubusercontent.com/36249204/42010724-0c09f370-7a5e-11e8-8ca2-6720822deb60.png">

