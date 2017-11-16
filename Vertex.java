import java.util.ArrayList;

public class Vertex {
	private double dist;
	private boolean known;
	private boolean isStart;
	private boolean isEnd;
	private Vertex path;
	private double latitude;
	private double longitude;
	private ArrayList<Vertex> adjList;
	private String id;
	private boolean visited;
	private boolean visitedTwice;

	public boolean isVisitedTwice() {
		return visitedTwice;
	}
	public void setVisitedTwice(boolean visitedTwice) {
		this.visitedTwice = visitedTwice;
	}
	public Vertex(){
		dist = Double.POSITIVE_INFINITY;
		known = false;
		visited = false;
		visitedTwice = false;
		isStart = false;
		isEnd = false;
		adjList = new ArrayList<Vertex>();
	}
	public ArrayList<Vertex> getAdjList() {
		return adjList;
	}
	public boolean isEnd(){
		return isEnd;
	}
	public void setEnd(boolean isEnd){
		this.isEnd = isEnd;
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAdjList(ArrayList<Vertex> adjList) {
		this.adjList = adjList;
	}
	public double getDist() {
		return dist;
	}
	public void setDist(double dist){
		this.dist = dist;
	}
	public Vertex getPath() {
		return path;
	}
	public void setPath(Vertex path) {
		this.path = path;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public boolean getKnown() {
		return known;
	}
	public void setKnown(boolean known){
		this.known = known;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
