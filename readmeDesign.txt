Design Decisions

Hashing:
We have tentatively decided to continue with the double polynomial hashing scheme and simply rely on the modularity of the HashMap to decrease the size of these numbers. If this turns out to give  Integer overflows, we can attempt to use long's and if that also fails, we can just go back to a single polynomial hashing scheme with summation over blocks. 

Move searching:
We will create a two-dimensional boolean array that describes the spaces that are available. This can be done once at the initialization of the first block, and then with each successive move we can update this array with the single block that was moved. This means that we will have to pass along this array with each recursive call. 

Once we have the boolean array we can iterate over each block and determine which (if any) of the four moves (up, down, left, right) that the particular block can do. If a block can achieve 1, 2, 3, or all the moves then we will create a separate arrayList entry for each of these possible outcomes. Once the entry is calculated, we can hash it and test if this move has already been completed. Once all the blocks have been iterated over, and all possible moves have been discovered, we will pass it along to a recursive call.

Discovering the goal configuration:
This part is the meat of the assignment. We will be doing a breadth-first search over all of the possible moves of each node in the tree. After a node has been expanded, its children will also be expanded, and so on until the branch dies out, or the goal configuration has been discovered. So explicitly, the two bases are, the expansion of all possible unique nodes, without the discovery of the goal configuration, or the goal configuration. With each recursive call down the tree there are three major pieces of information that need to be passed down, and one piece of information that needs to be passed back up.
Passing down:
1. The tray that you are working to discover the children of (the current tray). 
2. The current whitespace array.
3. The move that got you two the current tray configuration.
Passing up:
1. A growing collection of the moves that led you to your goal configuration, or null, the response that there exists no route to a goal configuration. 