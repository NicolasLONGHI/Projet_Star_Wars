package representation;

/**
 * La classe abstraite Node représente une node générique dans une structure de données.
 * Elle implémente l'interface Event pour les événements associés à cette node.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public abstract class Node implements Event {

    private static final long serialVersionUID = 1L;
    private int id;
    private String description;
    private String petiteDescription;
    
    /**
     * Constructeur par défaut de la classe Node.
     * Initialise la description et la petite description (qui s'affichera dans le bouton pour séléctionner la node) à des chaînes vides.
     */
    public Node() {
        this.description = "";
        this.petiteDescription = "";
    }
    
    /**
     * Constructeur de la classe Node avec des paramètres.
     * La description est transformée en texte html.
     *
     * @param unId              L'identifiant unique de la node.
     * @param uneDescription    La description détaillée de la node.
     * @param unePetiteDescrption La description de la node qui s'affichera dans le bouton pour la séléctionner.
     */
    public Node(int unId, String uneDescription, String unePetiteDescrption) {
		if (unId < 0) {
			throw new IllegalArgumentException("L'ID ne peut pas être négatif !");
		}
        this.id = unId;
        this.description = "<html>" + uneDescription + "</html>";
        this.petiteDescription = unePetiteDescrption;
    }
    
    /**
     * Obtient la description de la node.
     *
     * @return La description détaillée de la node.
     */
    public String display() {
        return this.description;
    }

    /**
     * Obtient l'identifiant de la node.
     *
     * @return L'identifiant de la node.
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la node.
     *
     * @param unId L'identifiant à définir.
     */
    public void setId(int unId) {
        this.id = unId;
    }

    /**
     * Obtient la description détaillée de la node.
     *
     * @return La description détaillée de la node.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description détaillée de la node.
     *
     * @param uneDescription La description à définir.
     */
    public void setDescription(String uneDescription) {
        this.description = uneDescription;
    }

    /**
     * Obtient la petite description de la node.
     *
     * @return La petite description de la node.
     */
    public String getPetiteDescription() {
        return petiteDescription;
    }

    /**
     * Définit la petite description de la node.
     *
     * @param unePetiteDescription La petite description à définir.
     */
    public void setPetiteDescription(String unePetiteDescription) {
        this.petiteDescription = unePetiteDescription;
    }
    
    /**
     * Compare la node actuelle à une autre node pour vérifier l'égalité.
     *
     * @param unNode L'objet node à comparer.
     * @return true si les nodes sont égales, sinon false.
     */
    @Override
    public boolean equals(Object unNode) {
        boolean retour = false;
        if (unNode instanceof Node) {
            Node leNode = (Node) unNode;
            if (this.id == leNode.getId() && this.description.equals(leNode.getDescription()) && this.petiteDescription.equals(leNode.getPetiteDescription())) {
                retour = true;
            }
        }
        return retour;
    }
    
    /**
     * Obtient une représentation textuelle de l'objet Node.
     *
     * @return Une chaîne de caractères représentant l'objet Node.
     */
    @Override
    public String toString() {
        return "Node [id=" + this.id + ", description=" + this.description + ", petiteDescription=" + this.petiteDescription + "]";
    }
}
