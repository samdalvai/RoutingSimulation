# Description
This `Java` application simulates the computation of the routing tables using the `Distance Vector` algorithm.

# Instructions
* Open the command line or `cd` into the folder `src/`.
* From inside the `src/` folder, run `javac *.java` to compile.
* Run `java DistanceVector` to run.


## Input
The `topology.txt` file representing the nodes of the network and their connection with the cost. For example:
```
A B 2
B C 1
A C 5
```
Represents a network with three nodes where `A` is connected to `B` with cost `2`, `B` is connected to `C` with cost 3, and so on.
The `messages.txt` file representing the order in which the nodes exchange messages with each other to compute the distance, for example:
```
A B C A
```
## Output
The `.txt` files named after the nodes, with the specification of the next hop, for example for `A`:
```
A direct
B B
C B
```
