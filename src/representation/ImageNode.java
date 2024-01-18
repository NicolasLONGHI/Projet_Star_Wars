package representation;

/**
 * Classe ImageNode qui étend EventDecorator pour représenter une node avec une image.
 * Cette node permet d'associer une image à un événement dans le jeu.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class ImageNode extends EventDecorator {

    private static final long serialVersionUID = 1L;
	private String chemin;
	
	/**
     * Constructeur par défaut pour ImageNode. Initialise une ImageNode avec des valeurs par défaut.
     */
	public ImageNode() {
		super();
		this.chemin = "";
	}
	
	/**
     * Constructeur d'ImageNode avec des paramètres spécifiques.
     *
     * @param unId Identifiant unique de l'ImageNode.
     * @param uneNode L'Event à décorer.
     * @param unChemin Chemin d'accès à l'image.
     */
	public ImageNode(int unId, Event uneNode, String unChemin) {
		super(unId, uneNode);
		if (unChemin.equals("")) {
			throw new IllegalArgumentException("Le chemin de l'image ne peut pas être égale à rien !");
		}
		this.chemin = unChemin;
	}
	
	/**
     * Affiche les détails de l'événement décoré. Cette méthode délègue l'appel de la méthode display() à l'évènement décoré.
     *
     * @return Le retour du display() de l'évènement décoré.
     */
	@Override
    public String display() {
        return super.getDecoratedNode().display();
    }
	
	 /**
     * Récupère le chemin de l'image associée à cette node.
     *
     * @return Le chemin de l'image.
     */
	public String getChemin() {
		return chemin;
	}

    /**
     * Définit le chemin de l'image pour cette node.
     *
     * @param unChemin Le nouveau chemin de l'image.
     */
	public void setChemin(String unChemin) {
		this.chemin = unChemin;
	}

	/**
     * Compare cette ImageNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unImageNode L'objet avec lequel comparer.
     * @return true si les ImageNode sont égales, false sinon.
     */
	@Override
    public boolean equals(Object unImageNode) {
        boolean retour = false;
        if (unImageNode instanceof ImageNode) {
        	ImageNode leImageNode = (ImageNode) unImageNode;
            if (super.equals(leImageNode) && this.chemin.equals(leImageNode.getChemin())) {
                retour = true;
            }
        }
        return retour;
    }	
	
	/**
     * Fournit une représentation sous forme de chaîne de caractères de l'ImageNode.
     *
     * @return Une chaîne de caractères représentant de l'ImageNode.
     */
	@Override
	public String toString() {
		return "ImageNode [chemin=" + chemin + ", getDecoratedNode()=" + getDecoratedNode() + "]";
	}
	
	
}
