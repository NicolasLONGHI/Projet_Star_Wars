package representation;

/**
 * Représente une node terminal dans le jeu. 
 * Un TerminalNode est un type de Node qui ne mène à aucune autre node suivante.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class TerminalNode extends Node {

    private static final long serialVersionUID = 1L;
    
	/**
     * Constructeur par défaut pour TerminalNode. Initialise une TerminalNode avec des valeurs par défaut.
     */
	public TerminalNode() {
		super();
	}
	
	/**
     * Constructeur pour initialiser un TerminalNode avec des paramètres spécifiques.
     *
     * @param unId L'identifiant de la node.
     * @param uneDescription La description de la node.
     * @param unePetiteDescription Une petite description de la node.
     */
	public TerminalNode(int unId, String uneDescription, String unePetiteDescription) {
		super(unId, uneDescription, unePetiteDescription);
	}
	
	/**
     * Méthode pour choisir la prochaine node. Dans le cas d'un TerminalNode, 
     * cette méthode retourne toujours la node lui-même, car il ne mène à aucune autre node.
     *
     * @param unIDNode L'identifiant de la prochaine node (non utilisé ici).
     * @return Le TerminalNode lui-même.
     */
	@Override
	public Node chooseNext(int unIDNode) {
		return this;
	}
	
	/**
     * Compare ce TerminalNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unNode L'objet avec lequel comparer.
     * @return true si les TerminalNode sont égales, false sinon.
     */
	@Override
    public boolean equals(Object unNode) {
        boolean retour = false;
        if (unNode instanceof TerminalNode) {
        	TerminalNode leNode = (TerminalNode) unNode;
            if (super.equals(leNode)) {
                retour = true;
            }
        }
        return retour;
    }
	
	/**
     * Fournit une représentation sous forme de chaîne de caractères du TerminalNode.
     *
     * @return Une chaîne de caractères représentant une TerminalNode.
     */
	@Override
	public String toString() {
		return "TerminalNode [getId()=" + super.getId() + ", getDescription()=" + super.getDescription()
				+ ", getPetiteDescription()=" + super.getPetiteDescription() + "]";
	}
	
	
		
}
