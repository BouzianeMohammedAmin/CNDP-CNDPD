import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Graph {
			private ArrayList<Sommet> sommet ;
	private boolean Graph_Type;//1 =>NO_orient   0 => orient  
	public Graph(boolean type) {
	sommet=new ArrayList<Sommet>();
	this.Graph_Type=type;
	}
public Graph(String nameFile)throws IOException {
    sommet=new ArrayList<Sommet>();
	    FileReader fr = new FileReader(nameFile);
    BufferedReader bfr = new BufferedReader(fr);
    String lin ;
    boolean nbSommet=true;
    Sommet [] tabSommet =null;
    while((lin=bfr.readLine())!=null) {

        if (nbSommet) {
            int nbS = Integer.valueOf(lin);
            tabSommet = new Sommet[nbS];
            for (int i = 0; i < nbS; i++) {
                Sommet s=new Sommet("v" + (i + 1)) ;
                tabSommet[i] =s;
                sommet.add(s);
            }

            nbSommet = false;

        } else {
            String arcS[] = lin.split(" ");
            this.addEdge(tabSommet[Integer.valueOf(arcS[0]) - 1], tabSommet[Integer.valueOf(arcS[1]) - 1]);
        }
    }

}
	public Graph(Graph g){
	sommet =new ArrayList<Sommet>();
		for(int i=0;i<g.getsommet().size();i++){
			Sommet s = new Sommet(g.getsommet().get(i).getNom());
			for(int j=0;j<g.getsommet().get(i).get_adjs().size();j++){
				s.add_adjs(new Sommet(g.getsommet().get(i).get_adjs().get(j).getNom()));
			}
			sommet.add(s);
		}

	}
	public void addSommet(Sommet s){
		if(!il_exist(s))sommet.add(s);
	}
	public void addEdge(Sommet sommet1,Sommet sommet2) {
	if(! il_exist(sommet1)) {
		addSommet(sommet1);
	}
	if(!il_exist(sommet2)) {
		addSommet(sommet2);
	}
	
	//if(Graph_Type)
	if(true){//No_orient
	sommet1.add_adjs(sommet2);
	sommet2.add_adjs(sommet1);	

	}else {//Orient
	sommet1.add_adjs(sommet2);	
	}
}
//////////////***********///////////
public boolean isAdjs(String s1,String s2){
	Sommet sommet1 = null,sommet2 = null ;
	for(int i=0;i<sommet.size();i++) {
		if(sommet.get(i).getNom()==s1) {
			sommet1=sommet.get(i);
		}
		if(sommet.get(i).getNom()==s2) {
			sommet2=sommet.get(i);
		}}
	return sommet1.is_adjs(sommet2);}
/////////////*********////////////


//////////////////***************/
public void affiche() {
	for(int i=0;i<this.sommet.size();i++) {
		System.out.print(sommet.get(i).getNom()+" => ");
		sommet.get(i).affiche_adj();
		System.out.println();
	}
}
////////////**/***********///////////
public boolean il_exist(Sommet s) {
	boolean il_exis=false;
	for(int i=0;i<sommet.size();i++) {
		if(sommet.get(i).equals(s))il_exis=true;
	}
	return il_exis;
	}	

public ArrayList<Sommet>getsommet(){return sommet;}
public void setsommet(ArrayList<Sommet>s) {
	this.sommet=s;
}
}
