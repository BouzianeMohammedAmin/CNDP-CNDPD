import java.util.ArrayList;
import java.util.Random;
public class CNDP {
	///////////////////*******************************************

	public static Graph MaximalIndepSet(Graph g) {
		Graph mis = new Graph(true);//true ==> graph orienty
		ArrayList<Sommet> sommet_graph = new ArrayList<Sommet>(g.getsommet());
		ArrayList<Sommet> rest = new ArrayList<Sommet>();
		Random random = new Random();

		while (sommet_graph.size() != 0) {
			int i = random.nextInt(sommet_graph.size());
			mis.addSommet(sommet_graph.get(i));
			Sommet sommet_selct = sommet_graph.get(i);
			sommet_graph.remove(i);

			for (int j = 0; j < sommet_graph.size(); j++) {
				if (sommet_graph.get(j).is_adjs(sommet_selct)) {
					sommet_graph.remove(sommet_graph.get(j));
					if(j!=0)j--;
				}
			}
		}
		//**suprimer tous les adjs de sommets ****///
		ArrayList<Sommet> mx = new ArrayList<Sommet>();
		for (int i = 0; i < mis.getsommet().size(); i++) {
			String s = mis.getsommet().get(i).getNom();
			mx.add(new Sommet(s));
		}
		mis.setsommet(mx);
		return mis;
	}


	//////////////****************///////////////////////////////////////

	public static ArrayList<Sommet> CriticalNode(Graph g, int k) {
		Graph mis = MaximalIndepSet(g);
		Random random = new Random();
		int misSize = mis.getsommet().size();
		final int graphSize = g.getsommet().size();

		ArrayList<sometcnp> Rest = new ArrayList<sometcnp>();
		ArrayList<ArrayList<String>> les_comps = new ArrayList<ArrayList<String>>();
		/////////////////////*****remplire Rest*******////////////
		int indic_rest = 0;
		for (int i = 0; i < graphSize; i++) {
			String sommet_g, sommet_m;
			boolean b = true;
			sommet_g = g.getsommet().get(i).getNom();
			for (int j = 0; j < misSize; j++) {
				sommet_m = mis.getsommet().get(j).getNom();
				if (sommet_g.equals(sommet_m)) b = false;
			}
			if (b) {
				Rest.add(new sometcnp());
				Rest.get(indic_rest).s = sommet_g;
				indic_rest++;
			}
		}
//////////////////////////******remplire les compst********//////////////////
		for (int i = 0; i < misSize; i++) {
			ArrayList<String> nv = new ArrayList<String>();
			nv.add(mis.getsommet().get(i).getNom());
			les_comps.add(nv);
		}

///////////////////////////////////////////////////////////

		while (misSize != (graphSize - k)) {
			int min = 0;
			for (int i = 0; i < Rest.size(); i++) {
				remplir_indic_comp(Rest.get(i), les_comps, g);
				//System.out.println("indic : "+Rest.get(i).INDIC_COMPS);
				remplir_Resaulta_FOB(Rest.get(i), les_comps);
				//	System.out.println("FOB : "+Rest.get(i).Resaulta_obf);
                // initialisé le min fobt
				if (i == 0) min = Rest.get(i).Resaulta_obf;
				//tester s'il ya un graph
				if (Rest.get(i).Resaulta_obf < min) {
					min = Rest.get(i).Resaulta_obf;
				}
			}

			ArrayList<sometcnp> min_s = new ArrayList<sometcnp>();

			for (int i = 0; i < Rest.size(); i++) {
				if (Rest.get(i).Resaulta_obf == min) min_s.add(Rest.get(i));
			}

			int indice_min_c = random.nextInt(min_s.size());

			ArrayList<String> nv_comps = new ArrayList<String>();

			for (int i = 0; i < min_s.get(indice_min_c).INDIC_COMPS.size(); i++) {
				nv_comps.addAll(les_comps.get(min_s.get(indice_min_c).INDIC_COMPS.get(i)));
			}

			for (int i = 0; i < min_s.get(indice_min_c).INDIC_COMPS.size(); i++) {
				les_comps.remove(min_s.get(indice_min_c).INDIC_COMPS.get(i));
			}


			nv_comps.add(min_s.get(indice_min_c).s);
			les_comps.add(nv_comps);

			Rest.remove(min_s.get(indice_min_c));

			for (int i = 0; i < Rest.size(); i++) {
				Rest.get(i).INDIC_COMPS = new ArrayList<Integer>();
			}

			misSize++;
		}
		// returner les sommet originale
		ArrayList<Sommet> Resaulta_final = new ArrayList<Sommet>();
		for (int i = 0; i < Rest.size(); i++) {
			int j = 0;
			boolean b = true;
			while (j < g.getsommet().size() && b) {
				if (Rest.get(i).s == g.getsommet().get(j).getNom()) {
					b = false;
					Resaulta_final.add(g.getsommet().get(j));
				}
				j++;
			}
		}
		return Resaulta_final;
	}

	///////////////////*************************/////////////


	/*************************************************************///////////////////////////////*********//-//
	private static void remplir_Resaulta_FOB(sometcnp sometcnp, ArrayList<ArrayList<String>> les_comps) {
		int s = 0;
		for (int i = 0; i < sometcnp.INDIC_COMPS.size(); i++) {
			s += les_comps.get(sometcnp.INDIC_COMPS.get(i)).size();
		}
		s = ((s + 1) * s) / 2;
		/* n*(n-1) de autre comps*/
		for (int i = 0; i < les_comps.size(); i++) {
			boolean b = false;
			for (int j = 0; j < sometcnp.INDIC_COMPS.size(); j++) if (i == sometcnp.INDIC_COMPS.get(j)) b = true;
			if (!b) {
				s += (les_comps.get(i).size() - 1) * (les_comps.get(i).size()) / 2;
			}
		}
		sometcnp.Resaulta_obf = s;
	}

	private static void remplir_indic_comp(sometcnp sometcnp, ArrayList<ArrayList<String>> les_comps, Graph g) {
		for (int i = 0; i < les_comps.size(); i++) {
			for (int j = 0; j < les_comps.get(i).size(); j++) {
				if (g.isAdjs(sometcnp.s, les_comps.get(i).get(j))) {
					if (!sometcnp.il_exist(i)) {
						sometcnp.INDIC_COMPS.add(i);
					}
				}
			}
		}
	}

	//////////////**************////////////////
	private static boolean exist(String s, ArrayList<sometcnp> sommet) {
		boolean b = false;
		int i = 0;
		while (i < sommet.size() && !b) {
			if (s.equals(sommet.get(i).s)) b = true;
			i++;
		}
		return b;
	}

	////////////////////////////
	static ArrayList<Sommet> CriticalNodeLS(Graph G, int k) {

	    ArrayList<Sommet> rest_final = null, cndp_element=null;
		int min_fo = 0,fo;

		for (int i = 0; i <G.getsommet().size()*G.getsommet().size(); i++) {
			cndp_element = CriticalNode(G, k);
			fo=find_fo_graph(G, cndp_element);
            if (i == 0) {
				min_fo = fo;
				rest_final = cndp_element;
			}else if (fo<min_fo){
				min_fo = fo;
				rest_final = cndp_element;
			}
		}

        for(int ii=0;ii<rest_final.size();ii++) System.out.print(rest_final.get(ii).getNom()+" ");

        System.out.println("de fob "+ min_fo);
        return rest_final; }

	public static int find_fo_graph(Graph g, ArrayList<Sommet> cndp_alrtoire) {
	    Graph gc = new Graph(g);

//suprimier les sommets cndp dans g
        int indic_sommet_g=0;


        while( indic_sommet_g < gc.getsommet().size()) {
		boolean is_critcal_node=false;
            for (int j = 0; j < cndp_alrtoire.size(); j++) {
				if (gc.getsommet().get(indic_sommet_g).getNom() == cndp_alrtoire.get(j).getNom()) {
					gc.getsommet().remove(indic_sommet_g);
					is_critcal_node=true;
				} }
            if(!is_critcal_node)indic_sommet_g++; }
        // suprm cn dans la lise de adjs de sommets
		for(Sommet s : gc.getsommet()){
       int indic_sommet_adj=0;
    while (indic_sommet_adj< s.get_adjs().size()){
        boolean is_cn=false;
		for (int j = 0; j < cndp_alrtoire.size() && !is_cn; j++) {
					if (s.get_adjs().get(indic_sommet_adj).getNom() == cndp_alrtoire.get(j).getNom()) {
						s.get_adjs().remove(indic_sommet_adj);
						is_cn=true;
					}}
	if(!is_cn)indic_sommet_adj++;
    }}
      /////////////////////////////
		int fob_f =0,f=0 ;
		ArrayList<Sommet> com =new ArrayList<Sommet>();
		while (gc.getsommet().size()!=0){
		    f=0;
		    com.add(gc.getsommet().get(0));
		    for(int i=0;i<com.size();i++){

                for (int j=0;j<com.get(i).get_adjs().size();j++){
                    boolean exist=false ;
		            for(int ii=0;ii<com.size();ii++){
		                    if(com.get(ii).getNom()==com.get(i).get_adjs().get(j).getNom())exist=true;
                    }
          if(!exist){
          Sommet sommet_ajoute =com.get(i).get_adjs().get(j);
          boolean bb=true ;
          for(int aj = 0 ; aj < gc.getsommet().size()&& bb;aj++){
              if(gc.getsommet().get(aj).getNom()==sommet_ajoute.getNom()){
                  sommet_ajoute.setAjs(gc.getsommet().get(aj).get_adjs()); bb=false;}
          }
              com.add(sommet_ajoute);
          } }
		    }
                   int n=com.size();
                   f=(n*(n-1))/2;
                   fob_f+=f;

            int in=0;
            while (in< gc.getsommet().size()){
                boolean b=false ;
            for(int i=0;i<com.size() && !b ;i++){
                if(gc.getsommet().get(in).getNom()==com.get(i).getNom()){
                    b=true;
                    gc.getsommet().remove(in);
                }
            }
                if(!b)in++;

            }
		  com=new ArrayList<Sommet>();
		}
			return fob_f; }
}