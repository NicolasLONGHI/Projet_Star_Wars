package representation;

import univers.Jedi;
import univers.Maitre;
import univers.Personnage;
import univers.Padawan;
import univers.Sabre;
import univers.Sith;

/**
 * Représente une node de modification de caractéristique d'un personnage.
 * Cette node permet de modifier différentes caractéristiques d'un personnage selon la valeur fournie.
 *
 * @param <E> Le type de la valeur utilisée pour la modification.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class ModifyCharacterNode<E> extends EventDecorator implements Observateur {

    private static final long serialVersionUID = 1L;
	private Personnage personnage;
	private Caracteristique caracteristique;
	private E valeur;
	
	/**
     * Constructeur par défaut pour ModifyCharacterNode. Initialise une ModifyCharacterNode avec des valeurs par défaut.
     */
	public ModifyCharacterNode() {
		super();
		this.personnage = null;
		this.caracteristique = null;
		this.valeur = null;
	}
	
	/**
     * Constructeur pour ModifyCharacterNode avec des paramètres spécifiques.
     *
     * @param unId Identifiant de la node.
     * @param uneNode Event à décorer.
     * @param unPersonnage Personnage à modifier.
     * @param uneCaracteristique Caractéristique à modifier.
     * @param uneValeur Valeur utilisée pour la modification.
     */
	public ModifyCharacterNode(int unId, Event uneNode, Personnage unPersonnage, Caracteristique uneCaracteristique, E uneValeur) {
		super(unId, uneNode);
		if (unPersonnage == null) {
			throw new IllegalArgumentException("Le personnage ne peut pas être égale à null !");
		}
		this.personnage = unPersonnage;
		this.personnage.ajouterObservateur(this);
		if (uneCaracteristique == null) {
			throw new IllegalArgumentException("La caractèristique à modifier ne peut pas être égale à null !");
		}
		this.caracteristique = uneCaracteristique;
		if (uneValeur == null) {
			throw new IllegalArgumentException("La valeur à mettre ne peut pas être égale à null !");
		}
		this.valeur = uneValeur;
	}
	
	/**
     * Affiche la node et effectue la modification du personnage en fonction de la caractèristique choisie.
     *
     * @return La description du noeud décoré après modification du personnage.
     */
	@Override
    public String display() {
		switch (this.caracteristique) {
		case CHEMINIMAGE:
			if (!(this.valeur instanceof String)) {
				throw new IllegalArgumentException("Le type de la valeur doit être String si vous souhaitez modifier le chemin de l'image !");
			}
			this.personnage.setCheminImage((String) this.valeur);
			break;
			
		case VIE:
			if (!(this.valeur instanceof Integer)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Integer si vous souhaitez modifier la vie !");
			}
			this.personnage.setVie(this.personnage.getVie() + (int) this.valeur);
			break;
			
		case ATTAQUE:
			if (!(this.valeur instanceof Integer)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Integer si vous souhaitez modifier l'attaque !");
			}
			this.personnage.setAttaque(this.personnage.getAttaque() + (int) this.valeur);
			break;
			
		case FORCE:
			if (!(this.valeur instanceof Integer)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Integer si vous souhaitez modifier la force !");
			}
			this.personnage.setForce(this.personnage.getForce() + (int) this.valeur);
			break;

		case SABRE:
			if (!(this.valeur instanceof Sabre)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Sabre si vous souhaitez modifier le sabre !");
			}
			this.personnage.setSabre((Sabre) this.valeur);
			break;

		case MAITRE:
			if (!(this.valeur instanceof Maitre)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Maitre si vous souhaitez modifier le maitre !");
			}
			if (!(this.personnage instanceof Padawan)) {
				throw new IllegalArgumentException("Le type du personnage doit être Padawan si vous souhaitez modifier le maitre !");
			}
            ((Padawan) this.personnage).setMaitre((Maitre) this.valeur);
			break;

		case PUISSANCEECLAIR:
			if (!(this.valeur instanceof Integer)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Integer si vous souhaitez modifier la puissance de l'éclair !");
			}
			if (!(this.personnage instanceof Sith)) {
				throw new IllegalArgumentException("Le type du personnage doit être Sith si vous souhaitez modifier la puissance de l'éclair !");
			}
			((Sith) this.personnage).setPuissanceEclair(((Sith) this.personnage).getPuissanceEclair() + (int) this.valeur);
			break;

		case PUISSANCELANCESABRE:
			if (!(this.valeur instanceof Integer)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Integer si vous souhaitez modifier la puissance du lancé de sabre !");
			}
			if (!(this.personnage instanceof Jedi)) {
				throw new IllegalArgumentException("Le type du personnage doit être Jedi si vous souhaitez modifier la puissance du lancé de sabre !");
			}
			((Jedi) this.personnage).setPuissanceLanceSabre(((Jedi) this.personnage).getPuissanceLanceSabre() + (int) this.valeur);
			break;

		case TYPEPERSONNAGE:
			if (!(this.valeur instanceof Caracteristique) || (this.valeur != Caracteristique.JEDI) && (this.valeur != Caracteristique.SITH)) {
				throw new IllegalArgumentException("Le type de la valeur doit être Caracteristique (soit Jedi, soit Sith) si vous souhaitez modifier le type de personnage !");
			}
			if (this.valeur == Caracteristique.JEDI) {
				this.personnage = new Jedi(this.personnage.getPseudo(), this.personnage.getCheminImage(), this.personnage.getVie(), this.personnage.getAttaque(), this.personnage.getForce(), this.personnage.getSabre(), 20);
			}
			else if (this.valeur == Caracteristique.SITH) {
				this.personnage = new Sith(this.personnage.getPseudo(), this.personnage.getCheminImage(), this.personnage.getVie(), this.personnage.getAttaque(), this.personnage.getForce(), this.personnage.getSabre(), 25);
			}
			break;
			
		default:
			break;
		}
        return super.getDecoratedNode().display();
    }
	
	/**
	 * Change la référence du personnage par la nouvelle référence.
	 * Est appelé lorsque le personnage change de type.
	 * 
	 * @param unPersonnage Le personnage où la référence a changé.
	 */
	@Override
    public void mettreAJour(Personnage unPersonnage) {
        this.personnage = unPersonnage;
    }

	/**
    * Récupère le personnage à modifier.
    *
    * @return Le personnage à modifier.
    */
	public Personnage getPersonnage() {
		return personnage;
	}

	/**
     * Définit le personnage à modifier.
     *
     * @param unPersonnage Le personnage à modifier.
     */
	public void setPersonnage(Personnage unPersonnage) {
		this.personnage = unPersonnage;
	}
	
	/**
	* Récupère la caracteristique à modifier.
	*
	* @return La caracteristique à modifier.
	*/
	public Caracteristique getCaracteristique() {
		return caracteristique;
	}
	
	/**
     * Définit la caracteristique à modifier.
     *
     * @param uneCaracteristique La caracteristique à modifier.
     */
	public void setCaracteristique(Caracteristique uneCaracteristique) {
		this.caracteristique = uneCaracteristique;
	}
	
	/**
	* Récupère la valeur à modifier.
	*
	* @return La valeur à modifier.
	*/
	public E getValeur() {
		return valeur;
	}
	
	/**
     * Définit la valeur à modifier.
     *
     * @param uneValeur La valeur à modifier.
     */
	public void setValeur(E uneValeur) {
		this.valeur = uneValeur;
	}
	
	/**
     * Compare ce ModifyCharacterNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unModifyCharacterNode L'objet avec lequel comparer.
     * @return true si les ModifyCharacterNode sont égaux, false sinon.
     */
	@Override
    public boolean equals(Object unModifyCharacterNode) {
        boolean retour = false;
        if (unModifyCharacterNode instanceof ModifyCharacterNode) {
        	ModifyCharacterNode leModifyCharacterNode = (ModifyCharacterNode) unModifyCharacterNode;
            if (super.equals(leModifyCharacterNode) && this.personnage.equals(leModifyCharacterNode.getPersonnage()) && this.caracteristique.equals(leModifyCharacterNode.getCaracteristique()) && this.valeur.equals(leModifyCharacterNode.getValeur())) {
                retour = true;
            }
        }
        return retour;
    }

	/**
     * Fournit une représentation sous forme de chaîne de caractères du ModifyCharacterNode.
     *
     * @return Une chaîne de caractères représentant un ModifyCharacterNode.
     */
	@Override
	public String toString() {
		return "ModifyCharacterNode [personnage=" + personnage + ", caracteristique=" + caracteristique + ", valeur="
				+ valeur + ", getDecoratedNode()=" + getDecoratedNode() + "]";
	}

	
	
	
}
