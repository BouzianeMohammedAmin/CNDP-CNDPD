import java.io.*;
import java.util.ArrayList;

public class main {
	public static void main(String[] args) throws IOException {
		Graph g =new Graph("graph.txt");
		g.affiche();
			ArrayList<Sommet> cndp = CNDP.CriticalNode(g, 3);
			System.out.println("-----------CN-------------");
			//for (int i = 0; i < cndp.size(); i++) System.out.println(cndp.get(i).getNom());
		System.out.println("-----------CNDP----------*");
		 cndp =CNDP.CriticalNodeLS(g,3);
		System.out.println("---------------");

	}}