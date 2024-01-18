package representation;

import java.util.ArrayList;

import java.util.Random;

/**
 * Représente une node de chance dans le jeu.
 * Ce type de node permet de choisir aléatoirement la prochaine node parmi une liste de node possibles.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class ChanceNode extends InnerNode {

    private static final long serialVersionUID = 1L;
	private Random nbAleatoire = new Random();
	
	/**
     * Constructeur par défaut pour ChanceNode. Initialise une ChanceNode avec des valeurs par défaut.
     */
	public ChanceNode() {
		super();
	}
	
	/**
     * Constructeur pour ChanceNode avec des paramètres spécifiques.
     *
     * @param unId Identifiant unique de la node.
     * @param uneDescription Description détaillée de la node.
     * @param unePetiteDescription Petite description de la node.
     * @param lesIDProchaines Liste des identifiants des prochaines nodes possibles.
     */
	public ChanceNode(int unId, String uneDescription, String unePetiteDescription, ArrayList<Integer> lesIDProchaines) {
        super(unId, uneDescription, unePetiteDescription, lesIDProchaines);
    }
	
	/**
     * Choisis aléatoirement la prochaine node parmi une liste de nodes possibles.
     *
     * @param unIDNode L'identifiant de la node (non utilisé ici, c'est simplement pour avoir la même signature).
     * @return La node suivante choisi aléatoirement.
     */
	@Override
	public Event chooseNext(int unIDNode) {
		ArrayList<Event> lesNodes = super.getProchainesNodes();
		return lesNodes.get(nbAleatoire.nextInt(lesNodes.size()));
	}

	/**
     * Compare cette ChanceNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unNode L'objet avec lequel comparer.
     * @return true si les ChanceNode sont égales, false sinon.
     */
	@Override
    public boolean equals(Object unNode) {
        boolean retour = false;
        if (unNode instanceof ChanceNode) {
        	ChanceNode leNode = (ChanceNode) unNode;
            if (super.equals(leNode)) {
                retour = true;
            }
        }
        return retour;
    }

	/**
     * Fournit une représentation sous forme de chaîne de caractères de la ChanceNode.
     *
     * @return Une chaîne de caractères représentant une ChanceNode.
     */
	@Override
	public String toString() {
		return "ChanceNode [getId()=" + super.getId() + ", getDescription()="
				+ super.getDescription() + ", getPetiteDescription()=" + super.getPetiteDescription() + ", getIDProchaines()="
				+ this.getIDProchaines().toString() + ", getProchainesNodes()=" + this.getProchainesNodes().toString() + "]";
	}
    
    
    
}
