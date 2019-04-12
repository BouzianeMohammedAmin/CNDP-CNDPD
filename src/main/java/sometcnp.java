import java.util.ArrayList;

public class sometcnp {
	String s ;
	ArrayList<Integer>INDIC_COMPS;
	int Resaulta_obf;
	public sometcnp() {
		
	INDIC_COMPS=new ArrayList<Integer>();
	}
	
	
	boolean il_exist(int  n) {
		boolean b =false;
		int i=0;
		while(i<INDIC_COMPS.size()&&!b) {
			if(INDIC_COMPS.get(i)==n)b=true;
		i++;}
		return b;
	}
	
	
}
