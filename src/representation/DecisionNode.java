package representation;

import java.util.ArrayList;

/**
 * Représente une node de décision dans le jeu.
 * Un DecisionNode est un type de Node qui permet au joueur de choisir parmi plusieurs nodes suivants.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class DecisionNode extends InnerNode {

    private static final long serialVersionUID = 1L;
    
	/**
     * Constructeur par défaut pour DecisionNode. Initialise une DecisionNode avec des valeurs par défaut.
     */
	public DecisionNode() {
		super();
	}
	
	/**
     * Constructeur pour DecisionNode avec des paramètres spécifiques.
     *
     * @param unId Identifiant unique de la node.
     * @param uneDescription Description détaillée de la node.
     * @param unePetiteDescription Petite description de la node.
     * @param lesIDProchaines Liste des identifiants des prochaines nodes possibles.
     */
	public DecisionNode(int unId, String uneDescription, String unePetiteDescription, ArrayList<Integer> lesIDProchaines) {
        super(unId, uneDescription, unePetiteDescription, lesIDProchaines);
    }

	/**
     * Compare cette DecisionNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unNode L'objet avec lequel comparer.
     * @return true si les DecisionNode sont égales, false sinon.
     */
	
	@Override
    public boolean equals(Object unNode) {
        boolean retour = false;
        if (unNode instanceof DecisionNode) {
        	DecisionNode leNode = (DecisionNode) unNode;
            if (super.equals(leNode)) {
                retour = true;
            }
        }
        return retour;
    }

	/**
     * Fournit une représentation sous forme de chaîne de caractères de la DecisionNode.
     *
     * @return Une chaîne de caractères représentant une DecisionNode.
     */

	@Override
	public String toString() {
		return "DecisionNode [getId()=" + super.getId() + ", getDescription()="
				+ super.getDescription() + ", getPetiteDescription()=" + super.getPetiteDescription() + ", getIDProchaines()=" + 
				super.getIDProchaines().toString() + ", getProchainesNodes()=" + super.getProchainesNodes().toString() + "]";
	}

	
	
}
