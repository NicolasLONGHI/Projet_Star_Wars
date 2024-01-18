package representation;

import java.util.ArrayList;

/**
 * La classe InnerNode est abstraite. Elle représente une node faisant passer le joueur d'une node à l'autre.
 * Elle étend la classe Node pour définir des nodes internes.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class InnerNode extends Node {

    private static final long serialVersionUID = 1L;
	private ArrayList<Integer> IDProchaines;
	private ArrayList<Event> prochainesNodes;
	
	/**
     * Constructeur par défaut pour InnerNode. Initialise une InnerNode avec des valeurs par défaut.
     */
	public InnerNode() {
		super();
		this.IDProchaines = null;
		this.prochainesNodes = null;
	}
	
	/**
     * Constructeur de la classe InnerNode avec des paramètres.
     *
     * @param unId               L'identifiant unique de la node interne.
     * @param uneDescription     La description détaillée de la node interne.
     * @param unePetiteDescription La petite description de la node interne.
     * @param lesIDProchaines     Une liste d'identifiants des prochaines nodes.
     */
	public InnerNode(int unId, String uneDescription, String unePetiteDescription, ArrayList<Integer> lesIDProchaines) {
		super(unId, uneDescription, unePetiteDescription);
		if (lesIDProchaines == null) {
			throw new IllegalArgumentException("IDProchaines ne peut pas être égale à null !");
		}
		if (lesIDProchaines.isEmpty()) {
			throw new IllegalArgumentException("IDProchaines doit contenir des ID de prochaines nodes !");
		}
		this.IDProchaines = lesIDProchaines;
		this.prochainesNodes = new ArrayList<Event>();
	}
	
	/**
     * Choix de la prochaine node en fonction de unIDNode passé en paramètre.
     *
     * @param unIDNode L'identifiant de la prochain node à choisir.
     * @return La prochaine node correspondante à l'identifiant, null s'il n'existe pas.
     */
	@Override
	public Event chooseNext(int unIDNode) {
		for (int i = 0 ; i < prochainesNodes.size() ; i++) {
			if (prochainesNodes.get(i).getId() == unIDNode) {
				return prochainesNodes.get(i);
			}
		}
		return null;
	}
	
	
	/**
	 * Ajoute les instances des prochaines nodes dans l'attribut prochainesNodes en fonction des ID
	 * se trouvant dans uneListeID passé en paramètre.
	 * 
	 * @param uneListeID     Une ArrayList d'entiers composés des ID des nodes à récupérer
	 * @param uneListeNodes  Une ArrayList d'Event où l'on cherche les nodes correspondant aux ID
	 * @return Retourne un tableau de nodes en fonction des ID passé en paramètre
	 */
	private ArrayList<Event> getNodeByID(ArrayList<Integer> uneListeID, ArrayList<Event> uneListeNodes) {
		ArrayList<Event> lesNodes = new ArrayList<>();
		for (int i = 0 ; i < uneListeID.size() ; i++) {
			for (int j = 0 ; j < uneListeNodes.size() ; j++){
				if (uneListeNodes.get(j).getId() == uneListeID.get(i)) {
					lesNodes.add(uneListeNodes.get(j));
				}
			}
		}
	    return lesNodes;
	}
	
	/**
     * Obtient la liste des identifiants des prochaines nodes.
     *
     * @return La liste des identifiants des prochaines nodes.
     */
	public ArrayList<Integer> getIDProchaines() {
		return this.IDProchaines;
	}
	
	/**
     * Définit la liste des identifiants des prochaines nodes.
     *
     * @param uneIDProchaines La liste des identifiants des prochaines nodes à définir.
     */
	public void setIDProchaines(ArrayList<Integer> uneIDProchaines) {
		IDProchaines = uneIDProchaines;
	}
	
	/**
     * Définit les prochaines nodes en fonction des identifiants et de la liste de nodes.
     *
     * @param lesID         Une liste d'identifiants des prochaines nodes.
     * @param uneListeNodes Une liste de node où l'on cherche les nodes correspondant aux identifiants.
     */
	public void definirProchainesNodes(ArrayList<Integer> lesID, ArrayList<Event> uneListeNodes) {
		this.prochainesNodes = getNodeByID(lesID, uneListeNodes);
	}
	
	/**
     * Définit les prochaines nodes en utilisant directement une liste de nodes.
     *
     * @param uneProchainesNodes La liste des prochaines nodes à définir.
     */
	public void setProchainesNodes(ArrayList<Event> uneProchainesNodes) {
		this.prochainesNodes = uneProchainesNodes;
	}
	
	/**
     * Obtient la liste des prochaines nodes.
     *
     * @return La liste des prochaines nodes.
     */
	public ArrayList<Event> getProchainesNodes() {
		return this.prochainesNodes;
		
	}
	
	
	/**
     * Compare ce InnerNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unNode L'objet avec lequel comparer.
     * @return true si les InnerNode sont égaux, false sinon.
     */
	@Override
    public boolean equals(Object unNode) {
        boolean retour = false;
        if (unNode instanceof InnerNode) {
        	InnerNode leNode = (InnerNode) unNode;
            if (super.equals(leNode) && this.IDProchaines.equals(leNode.getIDProchaines())) {
                retour = true;
            }
        }
        return retour;
    }
	
	/**
     * Fournit une représentation sous forme de chaîne de caractères de l'InnerNode.
     *
     * @return Une chaîne de caractères représentant un InnerNode.
     */
	@Override
	public String toString() {
		return "InnerNode [getId()=" + super.getId() + ", getDescription()=" + super.getDescription()
				+ ", getPetiteDescription()=" + super.getPetiteDescription() + ", prochainesNodes=" + this.prochainesNodes.toString() + ", IDProchaines="
						+ this.IDProchaines.toString() + "]";
	}


	
	
}
