/**
Author: Jimmy Li, #29903320, NetID: jli119
Date: April 29, 2017
Course: CSC172, Professor Ted Pawlicki
Assignment: Project 4 - Street Mapping
Title: Graph.java
*/
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.color.*;

public class Graph extends JPanel{
	private HashMap<String, Vertex> intersections = new HashMap<String, Vertex>();
	private HashMap<String, Edge> roads = new HashMap<String, Edge>();
	private HashMap<String, Edge> roadsWithIDs = new HashMap<String, Edge>();
	private HashMap<HashSet<Vertex>, Edge> roadsWithVerts = new HashMap<HashSet<Vertex>, Edge>();
	private ArrayList<String> roadNames = new ArrayList<String>();
	private ArrayList <Vertex> unvisited = new ArrayList<Vertex>();
	private ArrayList <Vertex> visited = new ArrayList<Vertex>();
	private ArrayList<Edge> mwst = new ArrayList<Edge>();

	private int framewidth;
	private int frameheight;
	
	private double totalDistanceTraveled;
	
	public int getFramewidth() {
		return framewidth;
	}
	public void setFramewidth(int framewidth) {
		this.framewidth = framewidth;
	}
	public int getFrameheight() {
		return frameheight;
	}
	public void setFrameheight(int frameheight) {
		this.frameheight = frameheight;
	}
	public HashMap<String, Vertex> getIntersections() {
		return intersections;
	}
	public HashMap<String, Edge> getRoads() {
		return roads;
	}
	public ArrayList<String> getRoadNames() {
		return roadNames;
	}

	public HashMap<String, Edge> getRoadsWithIDs() {
		return roadsWithIDs;
	}

	public void setRoadsWithIDs(HashMap<String, Edge> roadsWithIDs) {
		this.roadsWithIDs = roadsWithIDs;
	}
	
	public void setIntersections(HashMap<String, Vertex> intersections) {
		this.intersections = intersections;
	}
	public void setRoads(HashMap<String, Edge> roads) {
		this.roads = roads;
	}
	public void setRoadNames(ArrayList<String> roadNames) {
		this.roadNames = roadNames;
	}
public HashMap<HashSet<Vertex>, Edge> getRoadsWithVerts() {
		return roadsWithVerts;
	}
	public void setRoadsWithVerts(HashMap<HashSet<Vertex>, Edge> roadsWithVerts) {
		this.roadsWithVerts = roadsWithVerts;
	}
	public ArrayList<Vertex> getUnvisited() {
		return unvisited;
	}
	public void setUnvisited(ArrayList<Vertex> unvisited) {
		this.unvisited = unvisited;
	}
	/**
	 * Reads in the name of a text file and fills a 
	 * hashmap with the information from the text file.
	 *
	 * @param  filename  The name of the text file being read
	 */
	public static Graph createFromFile(String filename){
		HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
		HashMap<String, Edge> edges = new HashMap<String, Edge>();
		HashMap<String, Edge> edgesWithIDs = new HashMap<String, Edge>();
		HashMap<HashSet<Vertex>, Edge> edgesWithVerts = new HashMap<HashSet<Vertex>, Edge>();
		ArrayList<String> roadIDs = new ArrayList<String>();
		ArrayList<Vertex> vs = new ArrayList<Vertex>();
        String line = "";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int index = 0;
            while((line = bufferedReader.readLine()) != null) {
            	Scanner scan = new Scanner(line);
            	String label = scan.next(); // 'i' or 'r'
            	String id;
            	
            	if (label.equals("i")){
            		id = scan.next();
            		double latitude = Double.parseDouble(scan.next());
            		double longitude = Double.parseDouble(scan.next());
            		Vertex v = new Vertex();
            		v.setLatitude(latitude);
            		v.setLongitude(longitude);
            		v.setId(id);
            		vs.add(v);
            		vertices.put(id, v);
            	}
            	else if (label.equals("r")){
            		id = scan.next();
            		roadIDs.add(id);
            		String intersection1 = scan.next();
            		String intersection2 = scan.next();
            		String name_combo1 = intersection1 + intersection2;
            		String name_combo2 = intersection2 + intersection1;
            		double first_latitude = vertices.get(intersection1).getLatitude();
            		double first_longitude = vertices.get(intersection1).getLongitude();
            		double second_latitude = vertices.get(intersection2).getLatitude();
            		double second_longitude = vertices.get(intersection2).getLongitude();
            		double lat_difference = first_latitude - second_latitude;
            		double long_difference = first_longitude - second_longitude;
            		double lat_squared = Math.pow(lat_difference, 2.0);
            		double long_squared = Math.pow(long_difference, 2.0);
            		double dist = Math.sqrt(lat_squared + long_squared);
            		Vertex v1 = vertices.get(intersection1);
            		Vertex v2 = vertices.get(intersection2);
            		Edge e = new Edge(v1, v2, dist);
            		HashSet<Vertex> s = new HashSet<Vertex>();
            		s.add(v1);
            		s.add(v2);
            		edgesWithVerts.put(s, e);
            		e.setId(id);
            		if (v1.getAdjList().contains(v2) != true){
            			v1.getAdjList().add(v2);
            		}
            		if (v2.getAdjList().contains(v1) != true){
            			v2.getAdjList().add(v1);
            		}
            		edges.put(name_combo1, e);
            		edges.put(name_combo2, e);
            		edgesWithIDs.put(id, e);
            	}
            	index++;
            	scan.close();
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
        }
        Graph new_graph = new Graph();
        new_graph.setIntersections(vertices);
        new_graph.setRoads(edges);
        new_graph.setRoadNames(roadIDs);
        new_graph.setRoadsWithIDs(edgesWithIDs);
        new_graph.setRoadsWithVerts(edgesWithVerts);
        new_graph.setUnvisited(vs);
        return new_graph;
	}
	public double[] scaleMap(){
		Set set = intersections.entrySet();
		Iterator i = set.iterator();
		int index = 0;
		double min_lat = 100.0;
		double max_lat = -100.0;
		double min_long = 100.0;
		double max_long = -100.0;
		//find min and max values for latitude and longitude
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			//System.out.print(me.getKey() + ": ");
			Vertex v = (Vertex)me.getValue();
			
			//initialize min & max values
			if (index == 0){
				min_lat = max_lat = v.getLatitude();
				min_long = max_long = v.getLongitude();
			}
			//check for min & max values
			else {
				if (v.getLatitude() < min_lat){
					min_lat = v.getLatitude();
				}
				if (v.getLatitude() > max_lat){
					max_lat = v.getLatitude();
				}
				if (v.getLongitude() < min_long){
					min_long = v.getLongitude();
				}
				if (v.getLongitude() > max_long){
					max_long = v.getLongitude();
				}
			}
			index++;
		}
		//max minus min values equals the range of values
		double lat_range = max_lat - min_lat;
		double long_range = max_long - min_long;
		
		//divide the ranges by the width and height of the frame to find the scaling factors
		int min_frame_dim = Math.min(framewidth, frameheight);
		double lat_scaling_factor = lat_range/min_frame_dim;
		double long_scaling_factor = long_range/min_frame_dim;
		
		//calculate the min of the two scaling factors
		double min_scaling_factor = Math.max(lat_scaling_factor, long_scaling_factor);
		double[] min_and_sf = new double[5];
		min_and_sf[0] = min_lat;
		min_and_sf[1] = min_long;
		min_and_sf[2] = min_scaling_factor;
		min_and_sf[3] = max_lat;
		min_and_sf[4] = max_long;
		return min_and_sf;
	}
	private void drawMap(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		double[] min_sf = scaleMap();
		Set set2 = roads.entrySet();
		Iterator i2 = set2.iterator();
		int beg_rounded_long = 0;
		int beg_rounded_lat = 0;
		int dest_rounded_long = 0;
		int dest_rounded_lat = 0;
		int rounded_lat_tres = 0;
		while (i2.hasNext()) {
			Map.Entry me2 = (Map.Entry) i2.next();
			Edge e = (Edge)me2.getValue();
			double lat1 = e.getV().getLatitude();
			double long1 = e.getV().getLongitude();
			double lat2 = e.getW().getLatitude();
			double long2 = e.getW().getLongitude();
			
			//subtract lat & long by minimum values
			double lat_difference1 = lat1 - min_sf[0];
			double long_difference1 = long1 - min_sf[1];
			
			//divide differences by scaling factor
			double lat_quotient1 = lat_difference1/min_sf[2];
			double long_quotient1 = long_difference1/min_sf[2];
			
			//round quotients to the nearest ints
			int rounded_lat1 = (int)Math.round(lat_quotient1);
			int rounded_long1 = (int)Math.round(long_quotient1);
			
			//subtract lat & long by minimum values
			double lat_difference2 = lat2 - min_sf[0];
			double long_difference2 = long2 - min_sf[1];
			
			//divide differences by scaling factor
			double lat_quotient2 = lat_difference2/min_sf[2];
			double long_quotient2 = long_difference2/min_sf[2];
			
			//round quotients to the nearest ints
			int rounded_lat2 = (int)Math.round(lat_quotient2);
			int rounded_long2 = (int)Math.round(long_quotient2);
			
			double lat_difference3 = min_sf[3] - min_sf[0];
			double lat_quotient3 = lat_difference3/min_sf[2];
			int rounded_lat3 = (int)Math.round(lat_quotient3);
			rounded_lat_tres = rounded_lat3;
		
			//set stroke
			if (e.isPartOfPath()){
				g2d.setStroke(new BasicStroke(3));
				Color google_lightBlue = new Color(0,160,245);
				g2d.setColor(google_lightBlue);
			}
			
			else{
				g2d.setStroke(new BasicStroke(1));
				g2d.setColor(Color.BLACK);
			}
	        
			//set background
			Color google_lightGray = new Color(244,244,244);
			setBackground(google_lightGray);
			
			//draw lines
			g2d.drawLine(rounded_long1, rounded_lat3-rounded_lat1, rounded_long2, rounded_lat3-rounded_lat2);
			//draw start and end points
			if (e.getV().isStart()){
				beg_rounded_long = rounded_long1;
				beg_rounded_lat = rounded_lat1;
			}
			if (e.getV().isEnd()){
				dest_rounded_long = rounded_long1;
				dest_rounded_lat = rounded_lat1;
			}
			if (e.getW().isEnd()){
				dest_rounded_long = rounded_long2;
				dest_rounded_lat = rounded_lat2;
			}
			if (e.getW().isStart()){
				beg_rounded_long = rounded_long2;
				beg_rounded_lat = rounded_lat2;
			}
		}
		
		for (int i = 0; i < mwst.size(); i++){
			Edge e = mwst.get(i);
			double lat1 = e.getV().getLatitude();
			double long1 = e.getV().getLongitude();
			double lat2 = e.getW().getLatitude();
			double long2 = e.getW().getLongitude();
			
			//subtract lat & long by minimum values
			double lat_difference1 = lat1 - min_sf[0];
			double long_difference1 = long1 - min_sf[1];
			
			//divide differences by scaling factor
			double lat_quotient1 = lat_difference1/min_sf[2];
			double long_quotient1 = long_difference1/min_sf[2];
			
			//round quotients to the nearest ints
			int rounded_lat1 = (int)Math.round(lat_quotient1);
			int rounded_long1 = (int)Math.round(long_quotient1);
			
			//subtract lat & long by minimum values
			double lat_difference2 = lat2 - min_sf[0];
			double long_difference2 = long2 - min_sf[1];
			
			//divide differences by scaling factor
			double lat_quotient2 = lat_difference2/min_sf[2];
			double long_quotient2 = long_difference2/min_sf[2];
			
			//round quotients to the nearest ints
			int rounded_lat2 = (int)Math.round(lat_quotient2);
			int rounded_long2 = (int)Math.round(long_quotient2);
			
			double lat_difference3 = min_sf[3] - min_sf[0];
			double lat_quotient3 = lat_difference3/min_sf[2];
			int rounded_lat3 = (int)Math.round(lat_quotient3);
			rounded_lat_tres = rounded_lat3;
		
			//set stroke
			g2d.setStroke(new BasicStroke(4));
			g2d.setColor(Color.RED);
			
			
			//draw lines
			g2d.drawLine(rounded_long1, rounded_lat3-rounded_lat1, rounded_long2, rounded_lat3-rounded_lat2);
			//draw start and end points
			if (e.getV().isStart()){
				beg_rounded_long = rounded_long1;
				beg_rounded_lat = rounded_lat1;
			}
			if (e.getV().isEnd()){
				dest_rounded_long = rounded_long1;
				dest_rounded_lat = rounded_lat1;
			}
			if (e.getW().isEnd()){
				dest_rounded_long = rounded_long2;
				dest_rounded_lat = rounded_lat2;
			}
			if (e.getW().isStart()){
				beg_rounded_long = rounded_long2;
				beg_rounded_lat = rounded_lat2;
			}
			
		}
		g2d.setColor(Color.WHITE);
		Color google_darkBlue = new Color(0,118,255);
		g2d.fillOval(beg_rounded_long-6, rounded_lat_tres-beg_rounded_lat-6, 12, 12);
		g2d.setColor(google_darkBlue);
		g2d.fillOval(beg_rounded_long-4, rounded_lat_tres-beg_rounded_lat-4, 8, 8);
		g2d.setColor(Color.BLACK);
		
		//import image
		BufferedImage image = null;
		String path = "map-pin-red-hi.png";
        File file = new File(path);
		try {
			image = ImageIO.read(file);
		} catch (IOException exception) {

		}
		g2d.drawImage(image, dest_rounded_long-15, rounded_lat_tres-dest_rounded_lat-35, null); 
		g2d.setColor(Color.BLACK);
		g2d.fillOval(dest_rounded_long-6, rounded_lat_tres-dest_rounded_lat-6, 12, 12);
		g2d.setColor(Color.WHITE);
		g2d.fillOval(dest_rounded_long-4, rounded_lat_tres-dest_rounded_lat-4, 8, 8);
		g2d.setColor(Color.BLACK);
		g2d.fillOval(dest_rounded_long-2, rounded_lat_tres-dest_rounded_lat-2, 4, 4);
	}
	
	//Make hashtable for weights of paths
	//Priority queue: arraylist, iterate through to find smallest weight, remove it from the queue
	//Dijkstra's Algorithm
	//Iteratively:
	//Add starting vertex to priority queue
	//In a while loop:
	//While the priority queue is not empty:
	//Take current vertex and set it to known and take off priority queue, and find its adj verticies 
	//And add to priority queue if not known
	//Compare current vertex dist + dist_to_adj_vertex with the adj_vertex's current distance
	//If smaller, set adj vertices's path to current vertex & set adj vertices's weight as current vertex dist + dist_to_adj_vertex
	//Else, do nothing.
	//When the destination is set to known, then stop while loop
	
	public void dijkstrasAlgorithm(Vertex start, Vertex destination){
		start.setStart(true);
		destination.setEnd(true);
		ArrayList<Vertex> priority_queue = new ArrayList<Vertex>();
		start.setDist(0.0);
		priority_queue.add(start);
		Vertex current_vertex = start;
		current_vertex.setKnown(true);
		Boolean isDone = false;
		while (priority_queue.isEmpty() != true && isDone == false){
			
			
			ArrayList<Vertex> adjVerts = current_vertex.getAdjList();
			for (Vertex v: adjVerts){
				if (isDone == false){
					if (v.equals(destination) && v.getKnown() == true){
						isDone = true;
					}
					else if (v.getKnown() == false){
						if (priority_queue.contains(v) != true){
							priority_queue.add(v);
						}
						String currentID = current_vertex.getId();
						String adjID = v.getId();
						String IDcombo = currentID + adjID;
						if (current_vertex.getDist() + roads.get(IDcombo).getWeight() < v.getDist()){
							v.setPath(current_vertex);
							v.setDist(current_vertex.getDist() + roads.get(IDcombo).getWeight());
						}
					}
				}
			}
			priority_queue.remove(current_vertex);
			//go through priority queue to find smallest dist
			double min = Double.POSITIVE_INFINITY;
			int counter = -1;
			for (int i = 0; i < priority_queue.size(); i++){
				if (priority_queue.get(i).getDist() < min){
					min = priority_queue.get(i).getDist();
					counter = i;
				}
			}
			if (counter != -1){
				current_vertex = priority_queue.get(counter);
				current_vertex.setKnown(true);
			}
			else {
				isDone = true;
			}
			
		}
		System.out.println("Shortest Path: ");
		backTrack(start, destination, true);
		System.out.println("\nTotal Distance Traveled: " + totalDistanceTraveled*69 + " miles");
	}
	
	public void backTrack(Vertex start, Vertex destination, boolean finish){
		if (!destination.equals(start)){
			backTrack(start,destination.getPath(), false);
			roads.get(destination.getPath().getId()+destination.getId()).setPartOfPath(true);
			totalDistanceTraveled += roads.get(destination.getPath().getId()+destination.getId()).getWeight();
			if (finish){
				System.out.print(destination.getId() + " > Finish");
			}
			else {
				System.out.print(destination.getId() + " > ");
			}
			
		}
		else{
			System.out.print("Start > " + destination.getId() + " > ");
		}
		
	}
	
	//Minimum Weight Spanning Tree
	//Use Prim's Algorithm
	
	public void prims(){
		ArrayList<Edge> edges = new ArrayList<Edge>();
		Vertex v = unvisited.get(0);
		visited.add(v);
		unvisited.remove(v);
		while (unvisited.isEmpty() != true && v != null) {
			ArrayList<Vertex> adjVerts = v.getAdjList();
			double min = Double.POSITIVE_INFINITY;
			Vertex minV = null;
			for (Vertex adjacent: adjVerts){
				if (visited.contains(adjacent) != true){
					double first_latitude = v.getLatitude();
		    		double first_longitude = v.getLongitude();
		    		double second_latitude = adjacent.getLatitude();
		    		double second_longitude = adjacent.getLongitude();
		    		double lat_difference = first_latitude - second_latitude;
		    		double long_difference = first_longitude - second_longitude;
		    		double lat_squared = Math.pow(lat_difference, 2.0);
		    		double long_squared = Math.pow(long_difference, 2.0);
		    		double dist = Math.sqrt(lat_squared + long_squared);
		    		if (dist < min){
		    			min = dist;
		    			minV = adjacent;
		    		}
				}	
			}
			if (minV != null){
				edges.add(new Edge(v, minV));
				v = minV;
				visited.add(v);
				unvisited.remove(v);
			}
			else {
				v = null;
			}
		}
		mwst = edges;
	}

	public void mwst(){
		//goes through all vertices
		Set set = intersections.entrySet();
		Iterator i = set.iterator();
		ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			Vertex v = (Vertex)me.getValue();
			ArrayList<Vertex> adj = v.getAdjList();
			double minDist = Double.POSITIVE_INFINITY;
			Edge edgeToBeAdded = null;
			Vertex minVert = new Vertex();
			for (Vertex w: adj){
				double first_latitude = v.getLatitude();
	    		double first_longitude = v.getLongitude();
	    		double second_latitude = w.getLatitude();
	    		double second_longitude = w.getLongitude();
	    		double lat_difference = first_latitude - second_latitude;
	    		double long_difference = first_longitude - second_longitude;
	    		double lat_squared = Math.pow(lat_difference, 2.0);
	    		double long_squared = Math.pow(long_difference, 2.0);
	    		double dist = Math.sqrt(lat_squared + long_squared);
	    		if (dist < minDist){
	    			minDist = dist;
	    			minVert = w;
	    			edgeToBeAdded = new Edge(v, w, minDist);	
	    		}
			}
			String xID = v.getId() + minVert.getId();
			if (roads.get(xID).isPartOfMWST() != true){
				roads.get(xID).setPartOfMWST(true);
				listOfEdges.add(edgeToBeAdded);
			}
		}
		System.out.println("List of All Roads Traveled in Meridian Map:");
		Set set2 = roadsWithIDs.entrySet();
		Iterator i2 = set2.iterator();
		while (i2.hasNext()) {
			Map.Entry me2 = (Map.Entry) i2.next();
			Edge e = (Edge)me2.getValue();
			if (e.isPartOfMWST()){
				
				if (i2.hasNext()){
					System.out.print(new String((String)me2.getKey()) + ", ");
				}
				else {
					System.out.print(new String((String)me2.getKey()));
				}
			}
		}
		mwst = listOfEdges;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMap(g);
	}
}