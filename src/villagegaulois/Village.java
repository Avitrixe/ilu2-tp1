package villagegaulois;

import exception.VillageSansChefException;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nombreEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nombreEtal);
	}
	
	public static class Marche{
		private Etal[] etals;
		private int nombreEtal;
		
		public Marche(int nombreEtal) {
			this.etals = new Etal[nombreEtal];
			this.nombreEtal = nombreEtal;
			for(int i=0; i<nombreEtal; i++) etals[i] = new Etal();
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if(etals[indiceEtal].isEtalOccupe()) {
				System.out.println("Cette �tal est d�j� occup�.");
			}
			else {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		public int trouverEtalLibre() {
			for(int i=0; i<nombreEtal; i++) {
				if(!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			Etal[] etalProduit = new Etal[nombreEtal];
			int nombreEtalProduit = 0;
			for(int i=0; i<nombreEtal; i++) {
				if(etals[i].isEtalOccupe()) {
					if(etals[i].contientProduit(produit)) {
						etalProduit[nombreEtalProduit] = etals[i];
						nombreEtalProduit ++;
					}
				}
			}
			return etalProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<nombreEtal; i++) {
				if(etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for(int i=0; i<nombreEtal; i++) {
				if(!(etals[i].isEtalOccupe())) {
					nbEtalVide ++;
				}
				else {
					chaine.append(etals[i].afficherEtal());
				}
			}
			chaine.append("Il reste " + nbEtalVide + " étals non utilisées dans le marché.\n");
			return chaine.toString();
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int numerosEtalVide = marche.trouverEtalLibre();
		if(numerosEtalVide == -1) {
			return "Les �tals sont toutes libres.";
		}
		else {
			marche.utiliserEtal(numerosEtalVide, vendeur, produit, nbProduit);
			
			return vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\nLe vendeur " 
			+ vendeur.getNom() + " vend des " + produit + " � l'�tal n�" + numerosEtalVide + "\n";
		}
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalProduit = marche.trouverEtals(produit);
		String chaine = "Les vendeurs qui proposent des fleurs sont :\n";
		for(int i=0; (i<etalProduit.length) && (etalProduit[i] != null); i++) {
			chaine += "- " + etalProduit[i].getVendeur().getNom() + "\n";
		}
		return chaine;
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village " + this.getNom() + " possède plusieurs étals :\n" + marche.afficherMarche());
		return chaine.toString();
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException{
		if(chef == null) throw new VillageSansChefException("Le village n'a pas de chef");
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}