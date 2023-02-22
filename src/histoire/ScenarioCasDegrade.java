package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois asterix = new Gaulois("Astérix", 8);
		try {
			//etal.libererEtal();
			//etal.acheterProduit(5, null);
			//etal.acheterProduit(0, asterix);
			etal.acheterProduit(5, asterix);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch(IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
	}

}
