import java.util.*;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

public class TestGraph extends JFrame{
	public void setUpGUI(Graph graph) {
		add(graph);
		setTitle("Map");
		setSize(1000, 750);
		Dimension d = getSize();
		graph.setFramewidth(d.width);
		graph.setFrameheight(d.height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		String text = args[0];
		String show = args[1];
		String startIntersection = args[2];
		String endIntersection = args[3];
		//String meridianMap = args[4];
		Graph g = Graph.createFromFile(text);
		if (startIntersection.equals("null") == false && endIntersection.equals("null") == false){
			g.dijkstrasAlgorithm(g.getIntersections().get(startIntersection), g.getIntersections().get(endIntersection));
		}
//		else if (meridianMap.equals("true")){
//			g.mwst();
//		}
//		else if (meridianMap.equals("false")){
//		}
		if (show.equals("true")){
			TestGraph tg = new TestGraph();
			tg.setVisible(true);
	        tg.setUpGUI(g);
		}
		else if (show.equals("false")){
			
		}
        
	}
}
