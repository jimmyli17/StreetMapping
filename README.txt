README

Student Name: Jimmy Li
Student ID: #29903320
Email: jli119@u.rochester.edu
Lab Section: CRN#49047 in 244 Gavett Hall on Monday & Wednesday from 6:15-7:30PM.
Lab TA: Lauren Oey

Explanation of Project:
This is Project4 of CSC 172, which covers Street Mapping.
The project instructs us to implement a "Graph" class that maps the shortest distance between two intersections, 
given a text file of intersections and roads of UofR's campus, Monroe County, and New York State,
by constructing Graph, Vertex, and Edge objects and by implementing Dijkstra's Algorithm.
The program prints out the path, the total distance of the path in miles to the console, and the path is highlighted in blue on the map..
It also asks us to implement Prim's Algorithm to find the minimum weight spanning tree of the intersections of the UofR campus.
The roads taken in the tree are printed and the path is highlighted in red on the map.

How My Code Works:
createFromFile(String filename) creates a Graph object from a text file.
scaleMap() scales the coordinates to fit inside a Java graphics window.
drawMap() draws the map using Java graphics.
dijkstrasAlgorithm() implements Dijkstra's Algorithm and finds the shortest path from one point to another.
backTrack() is a helper method for dijkstrasAlgorithm() to backtrack through the path from one point to another. 
mwst() implements Prim's Algorithm and finds the minimum weight spanning tree of the map. 
(NOTE: Although I implemented Prim's Algorithm, I didn't figure out how to accurately print it on the map.)
paintComponent() paints the map to the screen.

Expected Runtime for Drawing the Map: O(n) because each of the draw methods have multiple loops but none are nested.
Expected Runtime for Finding the Shortest Path: O(n^2) because it iterates through all the vertices twice.
Expected Runtime for Finding the Minimum Weight Spanning Tree: O(n^2) because it also iterates through all the vertices twice.

Explanation of Files in Zip Folder:
'Graph' includes methods to print the map, to implement Dijkstra's algorithm, and to implement Prim's algorithm, and
‘Edge.java’ contains the class that represents an Edge in the graph, and
‘TestGraph.java’ contains the main method for testing 'Graph', and
‘Vertex.java’ contains the class that represents a point in the graph, and
‘AdjList.java’ contains the interface that represents an adjacency list of a vertex in the graph, and
‘OUTPUT.txt’ contains the compile and run steps of my code.