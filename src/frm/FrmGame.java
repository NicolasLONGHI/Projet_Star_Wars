package frm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.Configuration;
import configuration.Utilitaire;
import main.FrmMain;
import representation.Caracteristique;
import representation.ChanceNode;
import representation.DecisionNode;
import representation.InnerNode;
import representation.ModifyCharacterNode;
import representation.Node;
import representation.SoundNode;
import representation.TerminalNode;
import representation.Event;
import representation.EventDecorator;
import representation.FightNode;
import representation.ImageNode;
import univers.Jedi;
import univers.Maitre;
import univers.Padawan;
import univers.Personnage;
import univers.Sabre;
import univers.Sith;

import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Représente la fenêtre utilisée lorsque nous jouons.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */

public class FrmGame extends JFrame implements MusiqueFond {

	private static final long serialVersionUID = 1L;
    private int compteurClique;
    private long dernierCliqueTemps;
    private boolean easterEggVieTrouve = false;
	private JPanel contentPane;
	private JLabel lblDescription;
	private JLabel lblPseudo;
	private JLabel lblVie;
	private JLabel lblAttaque;
	private JLabel lblForce;
	private JLabel lblSabre;
	private JLabel lblSpecificite;
	private JLabel lblImage;
	private JLabel lblQuitter;
	private JLabel lblSauvegarder;
	private JButton btnChoix1;
	private JButton btnChoix2;
	private JButton btnChoix3;
	private JButton btnChoix4;
	
	private Clip musiqueDeFond;
	private boolean arreterMusiqueFond = false;
	
	private Personnage monPersonnage;
	private Event nodeActuelle;
	private Node nodeActuelleProfonde;
	List<JButton> listeBoutons = new ArrayList<>();
	private JLabel lblSon;

	
	
	/**
	 * Modifie l'interface et réagit en fonction de la node actuelle.
	 *
	 * @param unIDNode Identifiant de la prochaine node où nous sommes redirigés, 0 signifit que le jeu commence.
	 */
	private void jouer(int unIDNode) {
		if (unIDNode != 0) {
			nodeActuelle = ((InnerNode) nodeActuelleProfonde).chooseNext(unIDNode);
			nodeActuelleProfonde = recupNodePlusProfonde(nodeActuelle);
		}
		
		if (nodeActuelleProfonde instanceof TerminalNode) {
			lblDescription.setText(nodeActuelle.display());
			btnChoix1.setVisible(false);
			btnChoix2.setVisible(false);
			btnChoix3.setVisible(false);
			btnChoix4.setVisible(false);
			JOptionPane.showMessageDialog(this, "Fin de la partie !", "Fin", JOptionPane.WARNING_MESSAGE);
			Utilitaire.stopperMusiqueFond(this);
    		FrmMain frmMain = new FrmMain();
    		this.setVisible(false);
    		frmMain.setVisible(true);
		}
		else if (nodeActuelleProfonde instanceof ChanceNode) {
			jouer(((ChanceNode) nodeActuelleProfonde).chooseNext(unIDNode).getId());
		}
		else if (nodeActuelleProfonde instanceof FightNode) {
			Utilitaire.stopperMusiqueFond(this);
			FrmFight frmFight = new FrmFight(monPersonnage, ((FightNode) nodeActuelleProfonde).getEnnemi(), ((FightNode) nodeActuelleProfonde).getProchainesNodes().get(0), ((FightNode) nodeActuelleProfonde).getProchainesNodes().get(1), ((FightNode) nodeActuelleProfonde).getCheminMusique());
			setVisible(false);
			frmFight.setVisible(true);
		}
		else if (nodeActuelleProfonde instanceof InnerNode) {
			
			lblDescription.setText(nodeActuelle.display());

			//Affiche les petites descriptions dans les boutons et modifie la taille de la fenêtre en fonction du nombre de choix
			ArrayList<Event> lesProchainesNodes = ((InnerNode) nodeActuelleProfonde).getProchainesNodes();
			int nbProchaineNode = ((InnerNode) nodeActuelleProfonde).getIDProchaines().size();
			if (nbProchaineNode == 1) {
				btnChoix2.setVisible(false);
				btnChoix3.setVisible(false);
				btnChoix4.setVisible(false);
			}
			else if (nbProchaineNode == 2) {
				btnChoix2.setVisible(true);
				btnChoix3.setVisible(false);
				btnChoix4.setVisible(false);
			}
			else if (nbProchaineNode == 3) {
				btnChoix2.setVisible(true);
				btnChoix3.setVisible(true);
				btnChoix4.setVisible(false);
			}
			else {
				btnChoix2.setVisible(true);
				btnChoix3.setVisible(true);
				btnChoix4.setVisible(true);
			}
			JButton btnActuel;
			listeBoutons.addAll(Arrays.asList(btnChoix1, btnChoix2, btnChoix3, btnChoix4));
			int i = 0;
			for (Event uneNode : lesProchainesNodes) {
				btnActuel = listeBoutons.get(i);
				Node nodePlusProfonde = recupNodePlusProfonde(uneNode);
				btnActuel.setText(((Node) nodePlusProfonde).getPetiteDescription());
				btnActuel.putClientProperty("hiddenValue", uneNode.getId());
				i++;
			}
		}
		
		//Afficher image
		Event uneImageNode = recupImageNode(nodeActuelle);
		if (uneImageNode instanceof ImageNode) {
			Utilitaire.afficherImage(lblImage, ((ImageNode) uneImageNode).getChemin(), "Une erreur est survenue lors du chargement de l'image. La partie continue.");
		}
		else {
			lblImage.setIcon(null);
		}
		
		//Modifier type personnage
		Event unModifyCharacterNode = recupModifyCharacterNode(nodeActuelle);
		if (unModifyCharacterNode instanceof ModifyCharacterNode<?>) {
			if (((ModifyCharacterNode) unModifyCharacterNode).getCaracteristique() == Caracteristique.TYPEPERSONNAGE) {
				Personnage nouveauPersonnage = ((ModifyCharacterNode) unModifyCharacterNode).getPersonnage();
				nouveauPersonnage.setListeObservateurs(monPersonnage.getListeObservateurs());
				monPersonnage.notifierObservateurs(nouveauPersonnage);
				monPersonnage = nouveauPersonnage;
			}
		}

		majInfoPersonnage();
	}
	
	/**
	 * Met à jour les informations du personnage dans l'interface graphique.
	 * Affiche le pseudo, la vie, l'attaque, la force, et le sabre du personnage.
	 * Pour les personnages spécifiques (Padawan, Sith, Jedi), affiche également leurs caractéristiques spéciales.
	 */
	private void majInfoPersonnage() {
		lblPseudo.setText("Pseudo : " + monPersonnage.getPseudo());
		lblVie.setText("Vie : " + String.valueOf(monPersonnage.getVie()));
		lblAttaque.setText("Attaque : " + String.valueOf(monPersonnage.getAttaque()));
		lblForce.setText("Force : " + String.valueOf(monPersonnage.getForce()));
		if (monPersonnage.getSabre() == null) {
			lblSabre.setText("Sabre : Aucun");
		}
		else {
			lblSabre.setText("Sabre : " + String.valueOf(monPersonnage.getSabre()));
		}
		if (monPersonnage instanceof Padawan) {
			String leMaitre = "";
			if (((Padawan) monPersonnage).getMaitre() == null) {
				leMaitre = "Aucun";
			}
			else {
				leMaitre = ((Padawan) monPersonnage).getMaitre().toString();
			}
			lblSpecificite.setText("Maitre : " + leMaitre);
		}
		if (monPersonnage instanceof Sith) {
			lblSpecificite.setText("Puissance éclair : " + ((Sith) monPersonnage).getPuissanceEclair());
		}
		if (monPersonnage instanceof Jedi) {
			lblSpecificite.setText("Lancer sabre : " + ((Jedi) monPersonnage).getPuissanceLanceSabre());
		}
	}
	
	
	/**
	 * Récupère la node le plus profond à partir d'un événement donné, c'est-à-dire la node non décorée.
	 *
	 * @param uneNode L'événement à partir duquel récupérer la node le plus profonde.
	 * @return La node la plus profonde associée à l'événement.
	 */
	private Node recupNodePlusProfonde(Event uneNode) {
		Event retour = uneNode;
		while (!(retour instanceof Node)) {
			retour = ((EventDecorator) retour).getDecoratedNode();
		}
		return (Node) retour;
	}
	
	/**
	 * Récupère une ImageNode associée à un événement donné.
	 *
	 * @param uneNode L'événement à partir duquel récupérer l'ImageNode.
	 * @return L'ImageNode associé à l'événement, ou null s'il n'y en a pas.
	 */
	private Event recupImageNode(Event uneNode) {
		Event retour = uneNode;
		while (!(retour instanceof ImageNode) && !(retour instanceof Node)) {
			if (retour instanceof EventDecorator) {
				retour = ((EventDecorator) retour).getDecoratedNode();
			}
			else {
				return null;
			}
		}
		return (Event) retour;
	}

	/**
	 * Récupère une ModifyCharacterNode associée à un événement donné.
	 *
	 * @param uneNode L'événement à partir duquel récupérer la ModifyCharacterNode.
	 * @return La ModifyCharacterNode associé à l'événement, ou null s'il n'y en a pas.
	 */
	private Event recupModifyCharacterNode(Event uneNode) {
		Event retour = uneNode;
		while (!(retour instanceof ModifyCharacterNode<?>) && !(retour instanceof Node)) {
			if (retour instanceof EventDecorator) {
				retour = ((EventDecorator) retour).getDecoratedNode();
			}
			else {
				return null;
			}
		}
		return (Event) retour;
	}
	
	/**
     * Obtient la musique de fond.
     *
     * @return La musique de fond.
     */
	public Clip getMusiqueDeFond() {
		return this.musiqueDeFond;
	}
	
	/**
     * Définit la musique de fond.
     *
     * @param uneMusiqueDeFond La musique de fond.
     */
	public void setMusiqueDeFond(Clip uneMusiqueDeFond) {
		this.musiqueDeFond = uneMusiqueDeFond;
	}
	
	/**
     * Obtient si la musique de fond est arrêtée ou non.
     *
     * @return La musique de fond arrêtée ou non.
     */
	public boolean getArreterMusiqueFond() {
		return this.arreterMusiqueFond;
	}
	
	/**
     * Définit si la musique de fond est arrêtée ou non.
     *
     * @param uneArreterMusiqueFond La musique de fond arrêtée ou non.
     */
	public void setArreterMusiqueFond(boolean uneArreterMusiqueFond) {
		this.arreterMusiqueFond = uneArreterMusiqueFond;
	}
	
	/**
	 * Sauvegarde la nodeActuelle et monPersonnage dans un dossier ayant comme nom Configuration.cheminDossierSauvegarde + <strong>timestamp</strong> où <strong>timestamp</strong> est le timestamp au moment de la sauvegarde.
	 * Dans le dossier, un fichier spécifique par élément à sauvegarder est créé. La sérialisation est utilisée.
	 */
	private void sauvegarder() {
		long timestamp = System.currentTimeMillis();
		File dossierSauvegarde = new File(Configuration.nomDossierSauvegarde);
		File dossierDeLaSauvegarde = new File(Configuration.cheminDossierSauvegarde + timestamp);
		File cheminNodeSauvegarde = new File(Configuration.cheminDossierSauvegarde + timestamp + File.separator + Configuration.nomSauvegardeNode + timestamp);
		File cheminPersonnageSauvegarde = new File(Configuration.cheminDossierSauvegarde + timestamp + File.separator + Configuration.nomSauvegardePersonnage + timestamp);
		try {
	        if (!dossierSauvegarde.exists()) {
	        	dossierSauvegarde.mkdirs();
	        }
	        if (!dossierDeLaSauvegarde.exists()) {
	        	dossierDeLaSauvegarde.mkdirs();
	        }
			ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(cheminNodeSauvegarde));
			oss.writeObject(nodeActuelle);
			oss.close();
			oss = new ObjectOutputStream(new FileOutputStream(cheminPersonnageSauvegarde));
			oss.writeObject(monPersonnage);
			oss.close();
		} catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la sauvegarde de la partie. Vous allez être redirigé vers le menu principal.", "Erreur", JOptionPane.WARNING_MESSAGE);
    		FrmMain frmMain = new FrmMain();
    		setVisible(false);
    		frmMain.setVisible(true);
		}
	}
	
	/**
	 * Cette méthode est appelée lorsque l'utilisateur souhaite retourner à l'écran principal.
	 * La méthode {@link #demanderSauvearde()} est appelée et la méthode {@link #stopperMusiqueFond()}
	 * est appelée et l'utilisateur est redirigé vers FrmMain.
	 */
	private void retour() {
		demanderSauvegarde("Avant de quitter, voulez-vous sauvegarder la partie ?");
		Utilitaire.stopperMusiqueFond(this);
		FrmMain frmMain = new FrmMain();
		setVisible(false);
		frmMain.setVisible(true);
	}
	
	/**
	 * Affiche une boîte de dialogue de confirmation pour demander si l'utilisateur veut sauvegarder la partie en cours.
	 * Si la réponse est oui, la méthode {@link #sauvegarder()} est appelée.
	 */
	private void demanderSauvegarde(String unMessage) {
		int choix = JOptionPane.showConfirmDialog(this, unMessage, "Confirmation", JOptionPane.YES_NO_OPTION);

        if (choix == JOptionPane.YES_OPTION) {
        	sauvegarder();
        }
	}
	
	/**
	 * Créer la fenêtre de jeu.
	 */
	public FrmGame(Event uneNodeCharge, Personnage unPersonnageCharge) {
		setResizable(false);
		setTitle("Star Wars");
		addWindowListener(new WindowAdapter() {
			
			/**
			 * Initialise les nodes si c'est une nouvelle partie ou charge la node et le personnage si c'est une sauvegarde est chargé.
			 * A la fin, appelle la fonction {@link #jeu()} pour commencer ou reprendre la partie.
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				Utilitaire.jouerMusiqueFond(FrmGame.this, Configuration.cheminFondSound, "Une erreur est survenue lors du chargement de la musique de fond. \nLa partie continue.");
				
				Utilitaire.afficherImage(lblQuitter, Configuration.cheminQuitterIcone, "Une erreur est survenue lors du chargement de l'image. La partie continue.");
				Utilitaire.afficherImage(lblSauvegarder, Configuration.cheminSauvegarderIcone, "Une erreur est survenue lors du chargement de l'image. La partie continue.");
				Utilitaire.afficherImage(lblSon, Configuration.cheminSonIcone, "Une erreur est survenue lors du chargement de l'image. La partie continue.");
				
				if (uneNodeCharge == null || unPersonnageCharge == null) {
					//Déclaration des nodes
					try {
						monPersonnage = new Padawan(System.getProperty("user.name"), Configuration.cheminPadawan1Image, 100, 10, 11, null, null);
					
						DecisionNode node1 = new DecisionNode(1, "Le personnage se réveilla.", "", new ArrayList<>(Arrays.asList(200, 3, 209)));
						TerminalNode node104 = new TerminalNode(104, "Le personnage se rendormit mais ne se réveilla plus.", "Se rendormir");
						DecisionNode node2 = new DecisionNode(2, "Il sortit et regarda le soleil briller.", "Sortir" , new ArrayList<>(Arrays.asList(201, 212, 213, 210)));
						TerminalNode node105 = new TerminalNode(105, "Le personnage resta au soleil mais celui-ci mourut de déshydratation.", "Rester au soleil");
						TerminalNode node106 = new TerminalNode(106, "Le personnage s'allongea sur le sable. <br><i>**2 heures après**</i><br>Il entendit un speeder arriver à toute vitesse et celui-ci percuta le personnage qui mourut instantanément.", "S'allonger sur le sable");
						DecisionNode node3 = new DecisionNode(3, "Il s'agenouilla et commença à mediter mais il fut interrompu par quelqu'un qui toqua à la porte.", "Méditer", new ArrayList<>(Arrays.asList(12, 13)));
						DecisionNode node4 = new DecisionNode(4, "Il prit son speeder et alla à l'auberge.<br>A son arrivée, l'aubergiste le prevint qu'il était recherché par quelqu'un.", "Aller à l'auberge", new ArrayList<>(Arrays.asList(6, 7)));
						DecisionNode node5 = new DecisionNode(5, "Il prit son speeder et alla au bar.<br>A son arrivée, il aperçut de nombreux chasseurs de prime.", "Aller au bar", new ArrayList<>(Arrays.asList(10, 11)));
						DecisionNode node6 = new DecisionNode(6, "Le personnage demanda plus d'information sur la personne qui le recherchait.<br>L'aubergiste lui indiqua que celle-ci portait une cape avec une capuche et qu'il avait l'air menacant.", "Demander plus d'information", new ArrayList<>(Arrays.asList(7, 8)));
						DecisionNode node7 = new DecisionNode(7, "Cependant, il ignora les mises en garde et commanda un repas et alla s'asseoir. Peu après s'être assis, une personne rentra dans l'auberge et s'approcha du personnage. Elle portait une capuche et semblait peu commode. Elle s'arrêta devant lui et soudain, le personnage réalisa que cette personne portait un sabre laser à sa ceinture. C'était un Jedi ...", "Ignorer la mise en garde et commander à manger", new ArrayList<>(Arrays.asList(15, 16)));
						TerminalNode node8 = new TerminalNode(8, "Il décida de sortir et inspecta les alentours afin de trouver la personne qui le cherchait. Cependant, il décida de passer par une allée sombre et étroite et se fit enlever par des brigands", "Sortir et inspecter les alentours");
						DecisionNode node9 = new DecisionNode(9, "Il decida de se cacher derrière le comptoir afin de ne pas être retrouvé mais peu de temps après, des bruits de pas se rapprochèrent de lui et un homme capuché se tint devant lui. Il le regarda de plus près et aperçut un sabre laser attaché à sa ceinture. C'était un Jedi ... ", "Se cacher derrière le comptoir", new ArrayList<>(Arrays.asList(15, 16)));
						TerminalNode node10 = new TerminalNode(10, "Il décida d'aller s'asseoir à une table. Cependant, des hommes peu fréquentables vinrent le voir et le roua de coups.", "Aller s'asseoir à une table");
						DecisionNode node11 = new DecisionNode(11, "Le personnage décida d'aller voir le barman et commanda un verre. Une silouhette derriere lui se rapprocha. C'était un homme capuché avec un air menacant. Le personnage le regarda de plus près et aperçut un sabre à sa ceinture. C'était un Jedi ... ", "Aller voir le barman", new ArrayList<>(Arrays.asList(15, 16)));
						DecisionNode node12 = new DecisionNode(12, "Il décida d'aller ouvrir la porte. Il tomba devant un homme capuché. Il regarda de plus près et aperçut un sabre à sa ceinture. C'était un Jedi ... ", "Ouvrir la porte", new ArrayList<>(Arrays.asList(15, 16)));
						DecisionNode node13 = new DecisionNode(13, "Il regarda par la fenêtre pour savoir qui cela pouvait bien être.<br>Il aperçut un homme cagoulé avec un air menaçant.", "Regarder par la fenêtre", new ArrayList<>(Arrays.asList(12, 14)));
						DecisionNode node14 = new DecisionNode(14, "Il décida de sortir de chez lui par crainte.", "Sortir par la porte de derrière", new ArrayList<>(Arrays.asList(201, 210)));
						DecisionNode node15 = new DecisionNode(15, "Le personnage demanda des informations au Jedi. Celui-ci lui avoua qu'il ressentait une force extraordinaire en lui et souhaitait lui initier la voix du Jedi.", "Poser des questions au Jedi", new ArrayList<>(Arrays.asList(211)));
						DecisionNode node16 = new DecisionNode(16, "Le personnage essaya d'infliger un coup au Jedi mais fut immédiatement bloqué par une force étrange. Le Jedi se rapprocha et dit au personnage qu'il était inconscient mais courageux et qu'il allait lui apprendre à devenir un Jedi. ", "Combattre le Jedi", new ArrayList<>(Arrays.asList(211)));
						DecisionNode node17 = new DecisionNode(17, "Le Jedi l'emmena donc dans son vaisseau et partit pour Coruscant, la ville où tous les Padawans étaient formés. Arrivé là-bas, le personnage se fit attribuer un sabre d'entraînement et on lui inculqua les bases du combat au sabre. Quand enfin fut venu le jour où il se fit choisir comme apprenti par un Jedi.", "Suivant", new ArrayList<>(Arrays.asList(18, 20)));
						DecisionNode node19 = new DecisionNode(19, "Après son entraînement, le personnage alla dans la salle de réunion et se mit au centre. Autour de lui de nombreux Jedi étaient réunis et 4 se tenaient devant lui : Yoda, Obiwan, Luke et Anakin. Il attendit le verdict.", "Fin combat", new ArrayList<>(Arrays.asList(21)));
						DecisionNode node20 = new DecisionNode(20, "Le personnage trop pressé de savoir quel maître le choisirait décida de louper son entraînement. Il alla dans la salle de réunion et se mit au centre. Autour de lui de nombreux Jedi étaient réunis et 4 se tenaient devant lui : Yoda, Obiwan, Luke et Anakin. Le personnage attendit le verdict.", "Louper l'entraînement", new ArrayList<>(Arrays.asList(21)));
						ChanceNode node21 = new ChanceNode(21, "", "Attendre la décision", new ArrayList<>(Arrays.asList(58, 59, 60, 61)));
						DecisionNode node22 = new DecisionNode(22, "Il decida de choisir le sabre vert et sortit de la salle avec son désormais maître...", "Choisir le sabre vert", new ArrayList<>(Arrays.asList(100)));
						DecisionNode node23 = new DecisionNode(23, "Il decida de choisir le sabre bleu et sortit de la salle avec son désormais maître... ", "Choisir le sabre bleu", new ArrayList<>(Arrays.asList(100)));
						DecisionNode node100 = new DecisionNode(100, "La premiere mission lui fut confiée. Le but était de déjouer les tours de Grievious. Ainsi, il alla sur Utapau accompagné de son maître. Une fois devant Grievious, une solution s'offrait à lui : le combattre", "Continuer", new ArrayList<>(Arrays.asList(101)));
						TerminalNode node102 = new TerminalNode(102, "Le personnage ne put parvenir à gagner contre Grievous et mourut dans les bras de son maître", "");
						DecisionNode node103 = new DecisionNode(103, "Le personnage réussit à vaincre Grievous avec l'aide de son maître et ainsi déjoua les plans de celui-ci qui était surement mené par quelqu'un d'autre...", "", new ArrayList<>(Arrays.asList(57)));
						DecisionNode node24 = new DecisionNode(24, "13 ans plus tard ... <br> Dorénavant, le personnage est un Jedi accompli, fort et robuste. Cependant, le coté obscur avait lui aussi grandit et nous savons désormais qui se cache derrière cette force. Il vous a été demandé de retrouver les auteurs de ce mal et de les éliminer.<br>Ainsi 2 pistes peuvent etre exploitées : Hoth, la planète de glace et Mustafar, une planète de feu.", "Continuer", new ArrayList<>(Arrays.asList(25, 26)));
						DecisionNode node25 = new DecisionNode(25, "Il décida de se diriger vers Hoth et pris son X-Wing. Une fois arrivé sur ces terres, 2 chemins lui faisaient face : un vers une tour de glace et un autre vers un pic.", "Explorer Hoth", new ArrayList<>(Arrays.asList(203, 204)));
						DecisionNode node26 = new DecisionNode(26, "Il décida donc de se diriger vers Mustafar et prit son X-Wing. Une fois arrivé sur ces terres, 2 chemins lui faisaient face : un vers une tour de feu et un autre vers un volcan.", "Explorer Mustafar", new ArrayList<>(Arrays.asList(202, 205)));
						DecisionNode node27 = new DecisionNode(27, "Il prit la décision d'aller vers la tour de glace. Une fois là-bas, les lieux étaient vides et calmes. Mais il y regnait une force obscure. Ainsi, il dégaina son sabre et sentit un ennemi derrière lui.", "Aller vers la tour de glace ", new ArrayList<>(Arrays.asList(29)));
						DecisionNode node28 = new DecisionNode(28, "Il prit la décision d'aller vers le pic. Il s'y rendit, le chemin fut long et difficile mais il parvint à arriver en haut. Il y vit 2 sources : une de vie et une de force.", "Aller vers le pic", new ArrayList<>(Arrays.asList(54, 55)));
						ChanceNode node29 = new ChanceNode(29, "", "Attaquer", new ArrayList<>(Arrays.asList(71, 72, 73)));
						DecisionNode node30 = new DecisionNode(30, "Le personnage réussit à vaincre Dark Maul. Son sabre gisait par terre. La tour était purifiée.", "Combat gagné", new ArrayList<>(Arrays.asList(94, 204)));
						DecisionNode node31 = new DecisionNode(31, "Il fut attiré par le sabre et décida de le prendre, il se sentit plus puissant.", "Prendre le sabre", new ArrayList<>(Arrays.asList(204)));
						DecisionNode node32 = new DecisionNode(32, "Il se baigna dans la source de vie et sentit son corps changé, sa vitalité grandir.", "Se baigner dans la source de vie", new ArrayList<>(Arrays.asList(34)));
						DecisionNode node33 = new DecisionNode(33, "Il se baigna dans la source de force et sentit son corps changé, sa force grandir.", "Se baigner dans la source de force", new ArrayList<>(Arrays.asList(34)));
						DecisionNode node34 = new DecisionNode(34, "Il décida donc de rentrer, persuadé que les auteurs à l'origine de la force du mal ne se trouvaient pas là.", "Rentrer", new ArrayList<>(Arrays.asList(26)));
						DecisionNode node35 = new DecisionNode(35, "Il décida donc de se diriger vers la tour de feu. L'atmosphère y était sombre et angoissante mais il monta les étages. Une fois au dernier étage, il y vit un homme avec une force obscure démesurée. C'était Palpatine.", "Aller vers la tour de feu", new ArrayList<>(Arrays.asList(37, 46)));
						DecisionNode node36 = new DecisionNode(36, "Il décida d'aller vers le volcan. L'air était presque irrespirable et la chaleur étouffante mais il sentait qu'il se rapprochait d'une puissante force obscure. Arrivé au cratère, il vit un homme, sombre et puissant. Il portait un masque. Soudain l'homme se retourna vers lui. C'était Dark Vador...", "Aller vers le volcan", new ArrayList<>(Arrays.asList(38)));
						DecisionNode node39 = new DecisionNode(39, "Le personnage réussi à vaincre le seigneur Sith. Il sentit se rétablir l'équilibre dans la force et s'en alla. Sa quête est désormais terminée.", "Gagner", new ArrayList<>(Arrays.asList(41)));
						DecisionNode node40 = new DecisionNode(40, "Le personnage n'avait plus aucune force pour lutter contre le seigneur. Mais celui-ci se rapprocha et lui dit qu'il pouvait lui offrir une force incommensurable. Mais pour cela, sa force devait devenir obscure ...", "Perdu", new ArrayList<>(Arrays.asList(84, 43)));
						TerminalNode node41 = new TerminalNode(41, "La quête du personnage étant terminée et l'équilibre de la force étant restaurée, il devint un héros de guerre et l'un des Jedi les plus respectés.", "Suivant");
						DecisionNode node42 = new DecisionNode(42, "Il accepta l'offre du seigneur et il sentit sa force changée, elle devint bien plus puissante et cela lui plut. Ainsi, le seigneur Sith lui donna une nouvelle quête : Tuer Mace Windu, haut membre du conseil des Jedi.", "Accepter", new ArrayList<>(Arrays.asList(47)));
						TerminalNode node43 = new TerminalNode(43, "Il refusa et le seigneur Sith se rapprocha de lui. Il lui dit à quel point sa décision était stupide et il déférla tous ses éclairs pour le tuer.", "Refuser");
						DecisionNode node44 = new DecisionNode(44, "Le personnage réussit à vaincre son ennemi, un ennemi dont la puissance était inégalable.<br>Il sentit ainsi que l'expérience tiré de ce combat faisait de lui un Jedi extrêmement puissant. Mais il savait que la force à l'origine du mal n'était pas lui.", "Gagner", new ArrayList<>(Arrays.asList(202)));
						TerminalNode node45 = new TerminalNode(45, "Le personnage perdit son combat. Son ennemi était le plus puissant qu'il n'ait jamais vu. Il resta un moment à contempler cet homme et perdit la vie.", "Perdre");
						DecisionNode node46 = new DecisionNode(46, "Le personnage décida d'en savoir plus sur le seigneur Sith et des raisons qui l'ont poussé à rejoindre les forces du mal. Celui-ci lui explique la puissance que ça apporterait à l'homme de passer du côté obscur. Et lui proposa de le rejoindre.", "Discuter", new ArrayList<>(Arrays.asList(37, 84)));
						DecisionNode node47 = new DecisionNode(47, "Ainsi, le personnage décida de partir pour Coruscant afin d'accomplir sa nouvelle quête.<br>Arrivé là-bas, il convoqua Mace Windu avec pour prétexte de lui annoncer de nouvelles informations sur les forces obscures récoltées durant son périple.<br>Une fois Mace Windu devant lui, le personnage lui dit qu'un nouvel individu était de la partie. Et cet individu n'était autre que ... lui-même. ", "Partir pour Coruscant", new ArrayList<>(Arrays.asList(48)));
						DecisionNode node49 = new DecisionNode(49, "Le personnage parvint à tuer Mace Windu avec son sabre au sol et il sentit un plaisir de tuer grandir en lui. Dorénavant, il se sentait tout puissant et voulait toujours plus de pouvoir.", "Gagner", new ArrayList<>(Arrays.asList(92)));
						TerminalNode node50 = new TerminalNode(50, "Le personnage ne parvint pas à vaincre le Jedi et celui-ci lui avoua à quel point il était déçu de lui avant de le tuer.", "Perdre");
						DecisionNode node51 = new DecisionNode(51, "Il prit le sabre du Jedi, ce sabre qui avait une couleur si particulière et qui prouverait à tous le fait que c'était lui qui avait tué Mace Windu.", "Prendre le sabre", new ArrayList<>(Arrays.asList(52)));
						TerminalNode node52 = new TerminalNode(52, "Sa quête étant terminée, le personnage partit de Coruscant afin de perfectionner sa maitrise du côté obscur et devenir le plus grand redouté Sith de l'histoire de la galaxie ... ", "Partir");
						DecisionNode node80 = new DecisionNode(80, "Il attendit la décision quand Obiwan sortit du groupe pour marcher vers lui. Une fois devant, il dit au personnage que dorénavant ce serait son maître.<br><br>Il le fit choisir entre 2 sabres : un sabre vert, symbole de la diplomatie, la négociation et une approche équilibrée de la Force et un sabre bleu, symbole de la défense, la justice et la protection. ", "Attendre la décision", new ArrayList<>(Arrays.asList(90, 91)));
						DecisionNode node81 = new DecisionNode(81, "Il attendit la décision quand Yoda sortit du groupe pour marcher vers lui. Une fois devant, il dit au personnage que dorénavant ce serait son maître.<br><br>Il le fit choisir entre 2 sabres : un sabre vert, symbole de la diplomatie, la négociation et une approche équilibrée de la Force et un sabre bleu, symbole de la défense, la justice et la protection. ", "Attendre la décision", new ArrayList<>(Arrays.asList(90, 91)));
						DecisionNode node82 = new DecisionNode(82, "Il attendit la décision quand Anakin sortit du groupe pour marcher vers lui. Une fois devant, il dit au personnage que dorénavant ce serait son maître.<br><br>Il le fit choisir entre 2 sabres : un sabre vert, symbole de la diplomatie, la négociation et une approche équilibrée de la Force et un sabre bleu, symbole de la défense, la justice et la protection. ", "Attendre la décision", new ArrayList<>(Arrays.asList(90, 91)));
						DecisionNode node83 = new DecisionNode(83, "Il attendit la décision quand Luke sortit du groupe pour marcher vers lui. Une fois devant, il dit au personnage que dorénavant ce serait son maître.<br><br>Il le fit choisir entre 2 sabres : un sabre vert, symbole de la diplomatie, la négociation et une approche équilibrée de la Force et un sabre bleu, symbole de la défense, la justice et la protection. ", "Attendre la décision", new ArrayList<>(Arrays.asList(90, 91)));
						
						ImageNode node200 = new ImageNode(200, node2, Configuration.cheminPorteImage);
						ImageNode node201 = new ImageNode(201, node4, Configuration.cheminAubergeImage);
						ImageNode node202 = new ImageNode(202, node35, Configuration.cheminTourFImage);
						ImageNode node203 = new ImageNode(203, node27, Configuration.cheminTourGImage);
						ImageNode node204 = new ImageNode(204, node28, Configuration.cheminMontagneImage);
						ImageNode node205 = new ImageNode(205, node36, Configuration.cheminVolcanImage);
						ImageNode node210 = new ImageNode(210, node5, Configuration.cheminBarImage);
										
						Personnage ennemi1 = new Sith("Palpatine", Configuration.cheminPalpatineImage, 150, 10, 15, Sabre.ROUGE, 25);
						Personnage ennemi2 = new Sith("Dark Maul", Configuration.cheminDarkMaulImage, 100, 10, 15, Sabre.ROUGE, 10);
						Personnage ennemi3 = new Jedi("Dark Vador", Configuration.cheminDarkVadorImage, 200, 15, 20, Sabre.ROUGE, 20);
						Personnage ennemi4 = new Sith("Inquisiteur", Configuration.cheminSith2Image, 100, 10, 10, Sabre.ROUGE, 10);
						Personnage ennemi5 = new Sith("Dark Tyranus", Configuration.cheminDarkTyranusImage, 100, 15, 10, Sabre.ROUGE, 10);
						Personnage ennemi6 = new Padawan("Padawan", Configuration.cheminPadawan2Image, 50, 5, 5, Sabre.ENTRAINEMENT, null);
						Personnage ennemi7 = new Jedi("Mace Windu", Configuration.cheminMaceWinduImage, 150, 20, 15, Sabre.VIOLET, 15);
						Personnage ennemi8 = new Jedi("Grievous", Configuration.cheminGrievousImage, 100, 10, 10, Sabre.BLEU, 10);

						ModifyCharacterNode<Caracteristique> node57 = new ModifyCharacterNode<>(57, node24, monPersonnage, Caracteristique.TYPEPERSONNAGE, Caracteristique.JEDI);
						ModifyCharacterNode<Caracteristique> node84 = new ModifyCharacterNode<>(84, node42, monPersonnage, Caracteristique.TYPEPERSONNAGE, Caracteristique.SITH);
						ModifyCharacterNode<Maitre> node58 = new ModifyCharacterNode<>(58, node82, monPersonnage, Caracteristique.MAITRE, Maitre.ANAKIN);
						ModifyCharacterNode<Maitre> node59 = new ModifyCharacterNode<>(59, node83, monPersonnage, Caracteristique.MAITRE, Maitre.LUKE);
						ModifyCharacterNode<Maitre> node60 = new ModifyCharacterNode<>(60, node80, monPersonnage, Caracteristique.MAITRE, Maitre.OBIWAN);
						ModifyCharacterNode<Maitre> node61 = new ModifyCharacterNode<>(61, node81, monPersonnage, Caracteristique.MAITRE, Maitre.YODA);
						ModifyCharacterNode<Sabre> node90 = new ModifyCharacterNode<>(90, node23, monPersonnage, Caracteristique.SABRE, Sabre.BLEU);
						ModifyCharacterNode<Sabre> node91 = new ModifyCharacterNode<>(91, node22, monPersonnage, Caracteristique.SABRE, Sabre.VERT);
						ModifyCharacterNode<Sabre> node92 = new ModifyCharacterNode<>(92, node51, monPersonnage, Caracteristique.SABRE, Sabre.VIOLET);
						ModifyCharacterNode<Sabre> node94 = new ModifyCharacterNode<>(94, node31, monPersonnage, Caracteristique.SABRE, Sabre.ROUGE);
						ModifyCharacterNode<Sabre> node211 = new ModifyCharacterNode<>(211, node17, monPersonnage, Caracteristique.SABRE, Sabre.ENTRAINEMENT);			

						SoundNode node206 = new SoundNode(206, node33, Configuration.cheminLevelUpSound);
						SoundNode node207 = new SoundNode(207, node32, Configuration.cheminLevelUpSound);
						SoundNode node208 = new SoundNode(208, node44, Configuration.cheminLevelUpSound);
						SoundNode node209 = new SoundNode(209, node104, Configuration.cheminDeathSound);
						SoundNode node212 = new SoundNode(212, node105, Configuration.cheminDeathSound);
						SoundNode node213 = new SoundNode(213, node106, Configuration.cheminDeathSound);
						
						ModifyCharacterNode<Integer> node54 = new ModifyCharacterNode<>(54,node206, monPersonnage, Caracteristique.FORCE, 10);
						ModifyCharacterNode<Integer> node55 = new ModifyCharacterNode<>(55,node207, monPersonnage, Caracteristique.VIE, 25);
						ModifyCharacterNode<Integer> node56 = new ModifyCharacterNode<>(56, node208, monPersonnage, Caracteristique.FORCE, 25);

						FightNode node18 = new FightNode(18, "", "Aller a l'entraînement", new ArrayList<>(Arrays.asList(19, 19)), Configuration.cheminFight3Sound, ennemi6);
						FightNode node71 = new FightNode(71, "", "Attaquer " + ennemi2.getPseudo(), new ArrayList<>(Arrays.asList(30, 56)), Configuration.cheminFight1Sound, ennemi2);
						FightNode node72 = new FightNode(72, "", "Attaquer " + ennemi4.getPseudo(), new ArrayList<>(Arrays.asList(30, 56)), Configuration.cheminFight3Sound, ennemi4);
						FightNode node73 = new FightNode(73, "", "Attaquer " + ennemi5.getPseudo(), new ArrayList<>(Arrays.asList(30, 56)), Configuration.cheminFight3Sound, ennemi5);
						FightNode node38 = new FightNode(38, "", "Attaquer " + ennemi3.getPseudo(), new ArrayList<>(Arrays.asList(56, 45)), Configuration.cheminFight2Sound, ennemi3);
						FightNode node37 = new FightNode(37, "", "Attaquer " + ennemi1.getPseudo(), new ArrayList<>(Arrays.asList(39, 40)), Configuration.cheminFight1Sound, ennemi1);
						FightNode node48 = new FightNode(48, "", "Attaquer " + ennemi7.getPseudo(), new ArrayList<>(Arrays.asList(49, 50)), Configuration.cheminFight3Sound, ennemi7);
						FightNode node101 = new FightNode(101, "", "Attaquer " + ennemi8.getPseudo(), new ArrayList<>(Arrays.asList(103, 102)), Configuration.cheminFight3Sound, ennemi8);
					
						
						
						//Configure les listes de prochaines nodes dans les nodes
						ArrayList<Event> listeNodes = new ArrayList<>();	
						listeNodes.addAll(Arrays.asList(node1, node2, node3, node4, node5, node6, node7, node8, node9, node10, node11, node12, node13, node14, node15, node16, 
                                node17, node18, node19, node20, node21, node22, node23, node24, node25, node26, node27, node28, node29, node30, node31, node32, node33, node34, 
                                node35, node36, node37, node38, node39, node40, node41, node42, node43, node44, node45, node46, node47, node48, node49, node50, node51, node52, 
                                node54, node55, node56, node57, node58, node59, node60, node61, node71, node72, node73, node80, node81, node82, node83, node84, node90, node91,
                                node92, node94, node100, node101, node102, node103, node200, node201, node202, node203, node204, node205, node206, node207, node208, node210, node211,
                                node104, node209, node212, node213));
						for (int i = 0 ; i < listeNodes.size() ; i++) {
							if (listeNodes.get(i) instanceof InnerNode && ((InnerNode) listeNodes.get(i)).getIDProchaines() != null) {
								((InnerNode) listeNodes.get(i)).definirProchainesNodes(((InnerNode) listeNodes.get(i)).getIDProchaines(), listeNodes);
							}
						}
						
						
						
						//Lancer le jeu à partir de la première node
						nodeActuelle = (DecisionNode) node1;
						nodeActuelleProfonde = (DecisionNode) node1;
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'initialisation de la partie. Vous allez être redirigé vers le menu principal.", "Erreur", JOptionPane.WARNING_MESSAGE);
						Utilitaire.stopperMusiqueFond(FrmGame.this);
						FrmMain frmMain = new FrmMain();
			    		setVisible(false);
			    		frmMain.setVisible(true);
					}
				}
				else {
					nodeActuelle = uneNodeCharge;
					nodeActuelleProfonde = recupNodePlusProfonde(nodeActuelle);
					monPersonnage = unPersonnageCharge;
				}
				jouer(0);
			}
			
			/**
			 * Appelle la fonction {@link #retour()}.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				retour();
			}
			
			
			
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{27, 27, 27, 112, 54, 82, 11, 78, 104, 148, 0};
		gbl_contentPane.rowHeights = new int[]{27, 246, 77, 77, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblQuitter = new JLabel("");
		lblQuitter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblQuitter.addMouseListener(new MouseAdapter() {
			
			/**
			 * Appelle la fonction {@link #retour()()}.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				retour();
			}
		});
		GridBagConstraints gbc_lblQuitter = new GridBagConstraints();
		gbc_lblQuitter.fill = GridBagConstraints.BOTH;
		gbc_lblQuitter.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuitter.gridx = 0;
		gbc_lblQuitter.gridy = 0;
		contentPane.add(lblQuitter, gbc_lblQuitter);
		
		
		btnChoix2 = new JButton("<choix2>");
		btnChoix2.addActionListener(new ActionListener() {
			/**
			 * Appelle la fonction {@link #jouer()} en fonction de la valeur caché dans le bouton.
			 */
			public void actionPerformed(ActionEvent e) {
				jouer((int) btnChoix2.getClientProperty("hiddenValue"));
			}
		});
		lblVie = new JLabel("Vie : <vie>");
		lblVie.addMouseListener(new MouseAdapter() {
			/**
			 * Ajoute +10 de points de vie au personnage si le joueur parvient à cliquer 5 fois sur lblVie en moins de 250 millisecondes.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!easterEggVieTrouve) {
					long tempsActuel = System.currentTimeMillis();
	                if (tempsActuel - dernierCliqueTemps < 250) {
	                    compteurClique++;
	                } else {
	                    compteurClique = 1;
	                }
	                dernierCliqueTemps = tempsActuel;
	
	                if (compteurClique >= 5) {
	        			JOptionPane.showMessageDialog(null, "Vous gagner +10 points de vie !", "Bravo", JOptionPane.INFORMATION_MESSAGE);
	        			monPersonnage.setVie(monPersonnage.getVie() + 10);
	        			majInfoPersonnage();
	                    easterEggVieTrouve = true;
	                }
				}
			}
		});
		
		lblSon = new JLabel("");
		lblSon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSon.addMouseListener(new MouseAdapter() {
			
			/**
			 * Appelle la fonction {@link #jouerMusiqueFond()} ou la fonction {@link #stopperMusiqueFond()}.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (arreterMusiqueFond) {
					arreterMusiqueFond = false;
					Utilitaire.jouerMusiqueFond(FrmGame.this, Configuration.cheminFondSound, "Une erreur est survenue lors du chargement de la musique de fond. \nLa partie continue.");
					Utilitaire.afficherImage(lblSon, Configuration.cheminSonIcone, "Une erreur est survenue lors du chargement de l'image. La partie continue.");
				}
				else {
					Utilitaire.stopperMusiqueFond(FrmGame.this);
					Utilitaire.afficherImage(lblSon, Configuration.cheminSonCoupeIcone, "Une erreur est survenue lors du chargement de l'image. La partie continue.");
				}
			}
		});
		
		lblSauvegarder = new JLabel("");
		lblSauvegarder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSauvegarder.addMouseListener(new MouseAdapter() {
			
			/**
			 * Appelle la fonction {@link #demanderSauvegarde()}.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				demanderSauvegarde("Voulez-vous sauvegarder la partie ?");
			}
		});
		GridBagConstraints gbc_lblSauvegarder = new GridBagConstraints();
		gbc_lblSauvegarder.fill = GridBagConstraints.BOTH;
		gbc_lblSauvegarder.insets = new Insets(0, 0, 5, 5);
		gbc_lblSauvegarder.gridx = 1;
		gbc_lblSauvegarder.gridy = 0;
		contentPane.add(lblSauvegarder, gbc_lblSauvegarder);
		GridBagConstraints gbc_lblSon = new GridBagConstraints();
		gbc_lblSon.fill = GridBagConstraints.BOTH;
		gbc_lblSon.insets = new Insets(0, 0, 5, 5);
		gbc_lblSon.gridx = 2;
		gbc_lblSon.gridy = 0;
		contentPane.add(lblSon, gbc_lblSon);
		lblPseudo = new JLabel("Pseudo : <pseudo>");
		lblPseudo.addMouseListener(new MouseAdapter() {
			/**
			 * Affiche une boite de dialogue demandant au joueur d'insérer son nouveau pseudo.
			 * Ensuite, appelle la fonction {@link #majInfoPersonnage()}.
			 * Si le pseudo rentré est <strong>Ahsoka</strong> alors le joueur reçoit le sabre blanc.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
		        String nouveauPseudo = JOptionPane.showInputDialog(null, "Changer de pseudo :");
		        if (nouveauPseudo != null && !nouveauPseudo.equals("")) {
		            monPersonnage.setPseudo(nouveauPseudo);
		            if (monPersonnage.getPseudo().equals("Ahsoka") && monPersonnage instanceof Jedi) {
		            	monPersonnage.setSabre(Sabre.BLANC);
		            	monPersonnage.setCheminImage(Configuration.cheminAhsokaImage);
		            	majInfoPersonnage();
		            	JOptionPane.showMessageDialog(null, "Vous avez reçu le sabre blanc d'Ahsoka !", "Bravo", JOptionPane.INFORMATION_MESSAGE);
		            }
		            else {
			            majInfoPersonnage();
		            }
		        }
			}
		});
		lblPseudo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblPseudo = new GridBagConstraints();
		gbc_lblPseudo.anchor = GridBagConstraints.NORTH;
		gbc_lblPseudo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPseudo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPseudo.gridx = 3;
		gbc_lblPseudo.gridy = 0;
		contentPane.add(lblPseudo, gbc_lblPseudo);
		GridBagConstraints gbc_lblVie = new GridBagConstraints();
		gbc_lblVie.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblVie.insets = new Insets(0, 0, 5, 5);
		gbc_lblVie.gridx = 4;
		gbc_lblVie.gridy = 0;
		contentPane.add(lblVie, gbc_lblVie);
		lblAttaque = new JLabel("Attaque : <attaque>");
		GridBagConstraints gbc_lblAttaque = new GridBagConstraints();
		gbc_lblAttaque.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAttaque.insets = new Insets(0, 0, 5, 5);
		gbc_lblAttaque.gridwidth = 2;
		gbc_lblAttaque.gridx = 5;
		gbc_lblAttaque.gridy = 0;
		contentPane.add(lblAttaque, gbc_lblAttaque);
		lblForce = new JLabel("Force : <force>");
		GridBagConstraints gbc_lblForce = new GridBagConstraints();
		gbc_lblForce.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblForce.insets = new Insets(0, 0, 5, 5);
		gbc_lblForce.gridx = 7;
		gbc_lblForce.gridy = 0;
		contentPane.add(lblForce, gbc_lblForce);
		btnChoix1 = new JButton("<choix1>");
		btnChoix1.addActionListener(new ActionListener() {
			/**
			 * Appelle la fonction {@link #jouer()} en fonction de la valeur caché dans le bouton.
			 */
			public void actionPerformed(ActionEvent e) {
				jouer((int) btnChoix1.getClientProperty("hiddenValue"));
			}
		});
		lblSabre = new JLabel("Sabre : <sabre>");
		GridBagConstraints gbc_lblSabre = new GridBagConstraints();
		gbc_lblSabre.anchor = GridBagConstraints.NORTH;
		gbc_lblSabre.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSabre.insets = new Insets(0, 0, 5, 5);
		gbc_lblSabre.gridx = 8;
		gbc_lblSabre.gridy = 0;
		contentPane.add(lblSabre, gbc_lblSabre);
		lblSpecificite = new JLabel("<specificite> : <specificite>");
		GridBagConstraints gbc_lblSpecificite = new GridBagConstraints();
		gbc_lblSpecificite.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSpecificite.insets = new Insets(0, 0, 5, 0);
		gbc_lblSpecificite.gridx = 9;
		gbc_lblSpecificite.gridy = 0;
		contentPane.add(lblSpecificite, gbc_lblSpecificite);
		
		lblDescription = new JLabel("<descriptionNodeActuelle>");
		lblDescription.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.fill = GridBagConstraints.BOTH;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridwidth = 6;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 1;
		contentPane.add(lblDescription, gbc_lblDescription);
		lblImage = new JLabel("");
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.fill = GridBagConstraints.BOTH;
		gbc_lblImage.insets = new Insets(0, 0, 5, 0);
		gbc_lblImage.gridwidth = 4;
		gbc_lblImage.gridx = 6;
		gbc_lblImage.gridy = 1;
		contentPane.add(lblImage, gbc_lblImage);
		GridBagConstraints gbc_btnChoix1 = new GridBagConstraints();
		gbc_btnChoix1.fill = GridBagConstraints.BOTH;
		gbc_btnChoix1.insets = new Insets(0, 0, 5, 5);
		gbc_btnChoix1.gridwidth = 6;
		gbc_btnChoix1.gridx = 0;
		gbc_btnChoix1.gridy = 2;
		contentPane.add(btnChoix1, gbc_btnChoix1);
		GridBagConstraints gbc_btnChoix2 = new GridBagConstraints();
		gbc_btnChoix2.fill = GridBagConstraints.BOTH;
		gbc_btnChoix2.insets = new Insets(0, 0, 5, 0);
		gbc_btnChoix2.gridwidth = 4;
		gbc_btnChoix2.gridx = 6;
		gbc_btnChoix2.gridy = 2;
		contentPane.add(btnChoix2, gbc_btnChoix2);
		
		
		btnChoix4 = new JButton("<choix4>");
		btnChoix4.addActionListener(new ActionListener() {
			/**
			 * Appelle la fonction {@link #jouer()} en fonction de la valeur caché dans le bouton.
			 */
			public void actionPerformed(ActionEvent e) {
				jouer((int) btnChoix4.getClientProperty("hiddenValue"));
			}
		});
		
		
		btnChoix3 = new JButton("<choix3>");
		btnChoix3.addActionListener(new ActionListener() {
			/**
			 * Appelle la fonction {@link #jouer()} en fonction de la valeur caché dans le bouton.
			 */
			public void actionPerformed(ActionEvent e) {
				jouer((int) btnChoix3.getClientProperty("hiddenValue"));
			}
		});
		GridBagConstraints gbc_btnChoix3 = new GridBagConstraints();
		gbc_btnChoix3.fill = GridBagConstraints.BOTH;
		gbc_btnChoix3.insets = new Insets(0, 0, 0, 5);
		gbc_btnChoix3.gridwidth = 6;
		gbc_btnChoix3.gridx = 0;
		gbc_btnChoix3.gridy = 3;
		contentPane.add(btnChoix3, gbc_btnChoix3);
		GridBagConstraints gbc_btnChoix4 = new GridBagConstraints();
		gbc_btnChoix4.fill = GridBagConstraints.BOTH;
		gbc_btnChoix4.gridwidth = 4;
		gbc_btnChoix4.gridx = 6;
		gbc_btnChoix4.gridy = 3;
		contentPane.add(btnChoix4, gbc_btnChoix4);
	}
}
