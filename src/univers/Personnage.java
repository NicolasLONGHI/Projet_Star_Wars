package univers;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import representation.Observateur;

/**
 * La classe Personnage représente un personnage générique dans le jeu. Cette classe est abstraite et sert de classe de base pour d'autres types de personnages.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public abstract class Personnage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String pseudo;
    private String cheminImage;
    private int vie;
    private int attaque;
    private int force;
    private Sabre sabre;
    private List<Observateur> listeObservateurs = new ArrayList<>();

    /**
     * Constructeur par défaut de la classe Personnage. Initialise un personnage avec des valeurs par défaut.
     */
    public Personnage() {
        this.pseudo = "";
        this.vie = 0;
        this.attaque = 0;
        this.force = 0;
        sabre = null;
    }

    /**
     * Constructeur paramétré de la classe Personnage. Initialise un personnage avec des paramètres spécifiques.
     *
     * @param unPseudo        Le pseudo du personnage.
     * @param unCheminImage   Le chemin de l'image représentant le personnage.
     * @param uneVie          Le nombre de points de vie du personnage.
     * @param uneAttaque      Le niveau d'attaque du personnage.
     * @param uneForce        Le niveau de Force du personnage.
     * @param unSabre         Le sabre laser du personnage.
     */
    public Personnage(String unPseudo, String unCheminImage, int uneVie, int uneAttaque, int uneForce, Sabre unSabre) {
        this.pseudo = unPseudo;
        this.cheminImage = unCheminImage;
        if (uneVie < 0) {
        	throw new IllegalArgumentException("La vie doit être initialisée à 0 ou plus !");
        }
        this.vie = uneVie;
        if (uneAttaque < 0) {
        	throw new IllegalArgumentException("L'attaque doit être initialisée à 0 ou plus !");
        }
        this.attaque = uneAttaque;
        if (uneForce < 0) {
        	throw new IllegalArgumentException("La force doit être initialisée à 0 ou plus !");
        }
        this.force = uneForce;
        this.sabre = unSabre;
    }
    
    /**
     * Ajoute un observateur à listeObservateurs.
     * 
     * @param unObservateur Un observateur où on veut que la référence de l'objet soit mise à jour après modification du type de personnage.
     */
    public void ajouterObservateur(Observateur unObservateur) {
    	listeObservateurs.add(unObservateur);
    }
    
    /**
     * Supprime l'observateur de listeObservateurs.
     * 
     * @param unObservateur Un observateur à supprimer de listeObservateurs.
     */
    public void supprimerObservateur(Observateur unObservateur) {
    	listeObservateurs.remove(unObservateur);
    }
    
    /**
     * Notifie tous les observateurs de modifier la référence de l'objet Personnage.
     * 
     * @param unPersonnage Le nouveau personnage avec la nouvelle référence.
     */
    public void notifierObservateurs(Personnage unPersonnage) {
        for (Observateur unObservateur : listeObservateurs) {
        	unObservateur.mettreAJour(unPersonnage);
        }
    }
    
    /**
     * Obtient le pseudo du personnage.
     *
     * @return Le pseudo du personnage.
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Définit le pseudo du personnage.
     *
     * @param unPseudo Le nouveau pseudo du personnage.
     */
    public void setPseudo(String unPseudo) {
        this.pseudo = unPseudo;
    }

    /**
     * Obtient le chemin de l'image représentant le personnage.
     *
     * @return Le chemin de l'image.
     */
    public String getCheminImage() {
        return cheminImage;
    }

    /**
     * Définit le chemin de l'image représentant le personnage.
     *
     * @param cheminImage Le nouveau chemin de l'image.
     */
    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }

    /**
     * Obtient le nombre de points de vie du personnage.
     *
     * @return Le nombre de points de vie.
     */
    public int getVie() {
        return vie;
    }

    /**
     * Définit le nombre de points de vie du personnage.
     *
     * @param uneVie Le nouveau nombre de points de vie.
     */
    public void setVie(int uneVie) {
        this.vie = uneVie;
    }

    /**
     * Obtient le niveau d'attaque du personnage.
     *
     * @return Le niveau d'attaque.
     */
    public int getAttaque() {
        return attaque;
    }

    /**
     * Définit le niveau d'attaque du personnage.
     *
     * @param uneAttaque Le nouveau niveau d'attaque.
     */
    public void setAttaque(int uneAttaque) {
        this.attaque = uneAttaque;
    }

    /**
     * Obtient le niveau de Force du personnage.
     *
     * @return Le niveau de Force.
     */
    public int getForce() {
        return force;
    }

    /**
     * Définit le niveau de Force du personnage.
     *
     * @param uneForce Le nouveau niveau de Force.
     */
    public void setForce(int uneForce) {
        this.force = uneForce;
    }

    /**
     * Obtient le sabre laser du personnage.
     *
     * @return Le sabre laser du personnage.
     */
    public Sabre getSabre() {
        return sabre;
    }

    /**
     * Définit le sabre laser du personnage.
     *
     * @param unSabre Le nouveau sabre laser du personnage.
     */
    public void setSabre(Sabre unSabre) {
        this.sabre = unSabre;
    }
    
    /**
     * Obtient la liste des observateurs du personnage.
     *
     * @return La liste des observateurs du personnage.
     */
    public List<Observateur> getListeObservateurs() {
		return listeObservateurs;
	}
    
    /**
     * Définit la liste des observateurs du personnage.
     *
     * @param uneListeObservateurs La liste des observateurs du personnage.
     */
	public void setListeObservateurs(List<Observateur> uneListeObservateurs) {
		this.listeObservateurs = uneListeObservateurs;
	}

	/**
     * Remplace la méthode equals pour comparer deux personnages.
     *
     * @param unPersonnage L'objet à comparer avec le personnage actuel.
     * @return true si les personnages sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object unPersonnage) {
        boolean retour = false;
        if (unPersonnage instanceof Personnage) {
            Personnage lePersonnage = (Personnage) unPersonnage;
            if (this.pseudo.equals(lePersonnage.getPseudo()) && (this.cheminImage == lePersonnage.getCheminImage()) && (this.vie == lePersonnage.getVie()) && (this.attaque == lePersonnage.getAttaque()) && (this.force == lePersonnage.getForce()) && (this.sabre == lePersonnage.getSabre())) {
                retour = true;
            }
        }
        return retour;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du personnage.
     *
     * @return Une chaîne de caractères représentant le personnage.
     */
    @Override
    public String toString() {
        return "Personnage [pseudo=" + pseudo + ", cheminImage=" + cheminImage + ", vie=" + vie + ", attaque=" + attaque
                + ", force=" + force + ", sabre=" + sabre + "]";
    }
}
