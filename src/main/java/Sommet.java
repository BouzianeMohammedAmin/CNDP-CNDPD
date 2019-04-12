import java.util.ArrayList;

public class Sommet {
	private ArrayList<Sommet> adjs;
	private String nom ;
	
	public Sommet(String nom) {
	this.nom=nom;
	adjs=new ArrayList<Sommet>();
	}
	public ArrayList<Sommet> get_adjs(){ return adjs;}
	public void  setAjs(ArrayList<Sommet>a){adjs=a;}
	public void add_adjs(Sommet sommet) {
		this.adjs.add(sommet);

	}
	public boolean is_adjs(Sommet s ) {
		
		boolean b=false;
		if(il_exist(s)) {
			for (int i = 0; i < adjs.size(); i++) if (adjs.get(i).equals(s)) b = true;
			for (int i = 0; i < s.adjs.size(); i++) if (s.adjs.get(i).equals(this)) b = true;
		}
		return b;
	}
	private boolean il_exist(Sommet s) {
		boolean il_exis=false;
		for(int i=0;i<adjs.size();i++) {
			if(adjs.get(i).equals(s))il_exis=true;
		}
		return il_exis;
		}	

	/******************/
	void set_adj_null(){
		adjs=new ArrayList<Sommet>();
	}
	
	////////////////*************////////////
	
	
	///////////********////////////
	public void affiche_adj(){
		System.out.print("[");
	for(int i=0;i<adjs.size();i++) {
		if(i!=adjs.size()-1)System.out.print(adjs.get(i).getNom()+",");
		else System.out.print(adjs.get(i).getNom());
	}
	System.out.print("]");

	}
String getNom(){return this.nom;}	

}
