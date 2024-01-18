package representation;

import java.util.ArrayList;


import univers.Personnage;
/**
 * Représente une node de combat dans le jeu.
 * Ce type de node implique un combat entre le personnage du joueur et un ennemi.
 * Grâce à cette dernière le combat peut être lancé avec tous les paramètres nécessaires.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class FightNode extends InnerNode {

    private static final long serialVersionUID = 1L;
	private String cheminMusique;
	private Personnage ennemi;

	/**
     * Constructeur par défaut pour FightNode. Initialise une FightNode avec des valeurs par défaut.
     */
	public FightNode() {
		super();
		this.ennemi = null;
	}

	/**
     * Constructeur pour FightNode avec des paramètres spécifiques.
     *
     * @param unId Identifiant unique de la node.
     * @param uneDescription Description détaillée de la node.
     * @param unePetiteDescription Brève description de la node.
     * @param lesIDProchaines Liste des identifiants des prochaines nodes possibles.
     * @param unCheminMusique Chemin d'accès à la musique de fond.
     * @param unEnnemi Le personnage ennemi dans le combat.
     */
	public FightNode(int unId, String uneDescription, String unePetiteDescription, ArrayList<Integer> lesIDProchaines, String unCheminMusique, Personnage unEnnemi) {
        super(unId, uneDescription, unePetiteDescription, lesIDProchaines);
		if (unCheminMusique.equals("")) {
			throw new IllegalArgumentException("Le chemin de la musique de fond ne peut pas être égale à rien !");
		}
        this.cheminMusique = unCheminMusique;
		if (unEnnemi == null) {
			throw new IllegalArgumentException("L'ennemi ne peut pas être égale à null !");
		}
        this.ennemi = unEnnemi;
    }

    /**
     * Obtient le chemin de la musique de fond.
     *
     * @return L'identifiant de la node.
     */
	public String getCheminMusique() {
		return this.cheminMusique;
	}

    /**
     * Définit le chemin de la musique de fond.
     *
     * @param unCheminMusique Le chemin de la musique de fond.
     */
	public void setCheminMusique(String unCheminMusique) {
		this.cheminMusique = unCheminMusique;
	}

    /**
     * Obtient l'ennemi du combat.
     *
     * @return L'ennemi du combat.
     */
	public Personnage getEnnemi() {
		return this.ennemi;
	}

    /**
     * Définit l'ennemi durant le combat.
     *
     * @param unEnnemi L'ennemi à combattre.
     */
	public void setEnnemi(Personnage unEnnemi) {
		this.ennemi = unEnnemi;
	}
	
	/**
     * Compare cette FightNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unNode L'objet avec lequel comparer.
     * @return true si les FightNode sont égales, false sinon.
     */
	@Override
    public boolean equals(Object unNode) {
        boolean retour = false;
        if (unNode instanceof FightNode) {
        	FightNode leNode = (FightNode) unNode;
            if (super.equals(leNode) && this.cheminMusique.equals(leNode.getCheminMusique()) && this.ennemi.equals(leNode.getEnnemi())) {
                retour = true;
            }
        }
        return retour;
    }
	
	/**
     * Fournit une représentation sous forme de chaîne de caractères de la FightNode.
     *
     * @return Une chaîne de caractères représentant une FightNode.
     */
	@Override
	public String toString() {
		return "FightNode [getId()=" + super.getId() + ", getDescription()="
				+ super.getDescription() + ", getPetiteDescription()=" + super.getPetiteDescription() + ", getIDProchaines()="
				+ this.getIDProchaines().toString() + ", getProchainesNodes()=" + this.getProchainesNodes().toString() +
				", cheminMusique=" + this.cheminMusique + ", ennemi=" + this.ennemi.toString() + "]";
	}
       
}
