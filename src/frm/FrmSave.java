package frm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.Configuration;
import configuration.Utilitaire;
import main.FrmMain;
import representation.Event;
import univers.Jedi;
import univers.Padawan;
import univers.Personnage;
import univers.Sith;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Cette classe représente la fenêtre de séléction des sauvegardes.
 * L'utilisateur peut consulter le détail d'une sauvegarde, en charger une et la supprimer.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class FrmSave extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPseudo;
	private JLabel lblForce;
	private JLabel lblVie;
	private JLabel lblSabre;
	private JLabel lblSpecificite;
	private JLabel lblSupprimer;
	private JButton btnJouer;
	private JList lstSauvegarde;
	private HashMap<String, String> listeSauvegarde;
	private HashMap<String, Personnage> listePersonnage;
	private DefaultListModel<String> listModel;
	private JLabel lblAttaque;
	
	/**
	 * Liste tous les dossiers de sauvegarde se trouvant dans le dossier de sauvegarde.
	 * 
     * @return Une HashMap ayant pour clé le nom de la sauvegarde à afficher dans l'interface et en valeur le nom du dossier de sauvegarde.
	 */
	private HashMap<String, String> listerDossier() {
		HashMap<String, String> retour = new HashMap<>();
        File dossierSauvegarde = new File(Configuration.nomDossierSauvegarde);
        try {
	        if (dossierSauvegarde.exists() && dossierSauvegarde.isDirectory()) {
	            File[] lesFichiers = dossierSauvegarde.listFiles();
	
	            if (lesFichiers != null) {
	                for (File unFichier : lesFichiers) {
	                    if (unFichier.isDirectory()) {
	                    	String nomDossier = unFichier.getName();
	                    	long timestamp = Long.parseLong(nomDossier.replace("save", ""));
	                    	String nomAfficher = "";
	                    	
	                    	Date date = new Date(timestamp);
	                        SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm'm'ss's' 'le' dd/MM/yyyy");
	                        String dateLisible = sdf.format(date);
	                        
	                        nomAfficher = "Sauvegarde - " + dateLisible;
	                    	retour.put(nomAfficher, nomDossier);
	                    }
	                }
	            }
	        }
		} catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement de la liste des sauvegardes. "
        			+ "Vous allez être redirigé vers le menu principal.", "Erreur", JOptionPane.WARNING_MESSAGE);
        	retour();
		}
        return retour;
    }
	
	/**
	 * Désérialise le personnage se trouvant dans le dossier passé en paramètre.
	 * 
	 * @param unChemin Le chemin du dossier où se trouve l'objet Personnage sérialisé sous forme de chaine de caractère.
     * @return Le personnage désérialisé ou null si le personnage n'est pas trouvé.
	 */
	private Personnage recupPersonnage(String unChemin) {
		Personnage retour = null;
		String timestamp = unChemin.replace("save", "");
		String nomFichier = Configuration.nomDossierSauvegarde + File.separator + unChemin + File.separator + Configuration.nomSauvegardePersonnage + timestamp;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(nomFichier)));
			retour = (Personnage) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
        	JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement des détails des sauvegardes. "
        			+ "Vous allez être redirigé vers le menu principal.", "Erreur", JOptionPane.WARNING_MESSAGE);
        	retour();
		}
		return retour;
	}
	
	
	/**
	 * Supprime le dossier passé en paramètre et tout son contenu.
	 * 
	 * @param unDossier L'instance File du dossier à supprimer.
	 */
	private void supprimerDossier(File unDossier) {
        if (unDossier.exists()) {
            File[] lesFichiers = unDossier.listFiles();
            
            if (lesFichiers != null) {
                for (File unFichier : lesFichiers) {
                    if (unFichier.isDirectory()) {
                    	supprimerDossier(unFichier);
                    } else {
                    	unFichier.delete();
                    }
                }
            }
            unDossier.delete();
        }
    }
	
	/**
	 * Permet de charger une sauvegarde sélectionnée, de récupérer la node de l'événement et le personnage associé,
	 * puis d'ouvrir FrmGame avec ces données.
	 */
	private void chargerSauvegarde() {
		if (lstSauvegarde.getSelectedValue() != null) {
			try {
				String nomDossierSelectionne = lstSauvegarde.getSelectedValue().toString();
				String timestamp = listeSauvegarde.get(nomDossierSelectionne).replace("save", "");
				File nodeSelect = new File(Configuration.nomDossierSauvegarde + File.separator + listeSauvegarde.get(nomDossierSelectionne) + File.separator + Configuration.nomSauvegardeNode + timestamp);
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nodeSelect));
				Event node = (Event) ois.readObject();
				ois.close();
				Personnage personnage = listePersonnage.get(listeSauvegarde.get(nomDossierSelectionne));
				
				FrmGame frmGame = new FrmGame(node, personnage);
				setVisible(false);
				frmGame.setVisible(true);
			} catch (IOException | ClassNotFoundException e1) {
	        	JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement de la sauvegarde. "
	        			+ "Vous allez être redirigé vers le menu principal.", "Erreur", JOptionPane.WARNING_MESSAGE);
	        	retour();
			}
		}
	}
	
	
	/**
	 * Ouvre FrmMain et ferme la fenêtre actuelle.
	 */
	private void retour() {
		FrmMain frmMain = new FrmMain();
		setVisible(false);
		frmMain.setVisible(true);
	}

	/**
	 * Créer la fenêtre. 
	 */
	public FrmSave() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			
			/**
			 * Mets l'icône de la poubelle dans lblSupprimer, affiche dans l'ordre décroissant les sauvegardes,
			 * puis sélectionne le premier élément de lstSauvegarde.
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				
				Utilitaire.afficherImage(lblSupprimer, Configuration.cheminSupprimerIcone, "Une erreur est survenue lors du chargement de l'image.");
				
				listModel = new DefaultListModel<>();
				listeSauvegarde = listerDossier();
				listePersonnage = new HashMap<>();
				
				List<String> listeNomAfficher = new ArrayList<>(listeSauvegarde.keySet());
				Collections.sort(listeNomAfficher);
				Collections.reverse(listeNomAfficher);
				for (String unElement : listeNomAfficher) {
					listModel.addElement(unElement);
				}
				lstSauvegarde.setModel(listModel);
				
							
				for (Map.Entry<String, String> paire : listeSauvegarde.entrySet()) {
					String cle = paire.getKey();
					String valeur = paire.getValue();
					Personnage p1 = recupPersonnage(valeur);
					listePersonnage.put(valeur, p1);
			    }
				if (!listeSauvegarde.isEmpty()) {
					 lstSauvegarde.setSelectedIndex(0);
				}
			}
			
			/**
			 * Appelle la fonction {@link #retour()}.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				retour();
			}
			
		});

		lstSauvegarde = new JList();
		lstSauvegarde.addKeyListener(new KeyAdapter() {
			
			/**
			 * Appelle la fonction {@link #chargerSauvegarde()}.
			 *
			 * @param e L'événement déclenché par le relachement d'une touche.
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            chargerSauvegarde();
		        }
			}
		});
		lstSauvegarde.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstSauvegarde.addListSelectionListener(new ListSelectionListener() {
			
			/**
			 * Affiche les détails du personnage associé à la sauvegarde sélectionnée dans les labels correspondants.
			 *
			 * @param e L'événement de changement de sélection.
			 */
			public void valueChanged(ListSelectionEvent e) {
				if (lstSauvegarde.getSelectedValue() != null) {
					String nomDossierSelectionne = lstSauvegarde.getSelectedValue().toString();
					Personnage persoSauvSelect = listePersonnage.get(listeSauvegarde.get(nomDossierSelectionne));
					lblPseudo.setText("Pseudo : " + persoSauvSelect.getPseudo());
					lblVie.setText("Vie : " + String.valueOf(persoSauvSelect.getVie()));
					lblAttaque.setText("Attaque : " + String.valueOf(persoSauvSelect.getAttaque()));
					lblForce.setText("Force : " + String.valueOf(persoSauvSelect.getForce()));
					if (persoSauvSelect.getSabre() == null) {
						lblSabre.setText("Sabre : Aucun");
					}
					else {
						lblSabre.setText("Sabre : " + String.valueOf(persoSauvSelect.getSabre()));
					}
					if (persoSauvSelect instanceof Padawan) {
						if (((Padawan) persoSauvSelect).getMaitre() == null) {
							lblSpecificite.setText("Maitre : Aucun");
						}
						else {
							lblSpecificite.setText("Maitre : " + ((Padawan) persoSauvSelect).getMaitre());
						}
					}
					if (persoSauvSelect instanceof Sith) {
						lblSpecificite.setText("Puissance éclair : " + ((Sith) persoSauvSelect).getPuissanceEclair());
					}
					if (persoSauvSelect instanceof Jedi) {
						lblSpecificite.setText("Maitrise sabre : " + ((Jedi) persoSauvSelect).getPuissanceLanceSabre());
					}
				}
			}
			
		});


		setTitle("Sauvegardes");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 317, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		lblPseudo = new JLabel("Pseudo : <pseudo>");
		lblVie = new JLabel("Vie : <vie>");
		lblForce = new JLabel("Force : <force>");
		lblAttaque = new JLabel("Attaque : <attaque>");
		lblSabre = new JLabel("Sabre : <sabre>");
		lblSpecificite = new JLabel("Specificite : <specificite>");
		btnJouer = new JButton("Jouer");
		btnJouer.addActionListener(new ActionListener() {
			
			/**
			 * Appelle la fonction {@link #chargerSauvegarde()}.
			 *
			 * @param e L'événement de souris déclenché par le clic.
			 */
			public void actionPerformed(ActionEvent e) {
				chargerSauvegarde();
			}
		});
		
		lblSupprimer = new JLabel("");
		lblSupprimer.addMouseListener(new MouseAdapter() {
			
			/**
			 * Lorsqu'un élément est sélectionné et que l'utilisateur clique, une boîte de dialogue
			 * de confirmation s'affiche. Si l'utilisateur confirme, la sauvegarde sélectionnée est supprimée.
			 *
			 * @param e L'événement de souris déclenché par le clic.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lstSauvegarde.getSelectedValue() != null) {
					int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer la sauvegarde ?", "Confirmation", JOptionPane.YES_NO_OPTION);
	
			        if (choix == JOptionPane.YES_OPTION) {
						int indexSelection = lstSauvegarde.getSelectedIndex();
				        String nomDossierSelectionne = lstSauvegarde.getSelectedValue().toString();
				        File SauvSelect = new File(Configuration.nomDossierSauvegarde + File.separator + listeSauvegarde.get(nomDossierSelectionne));
				        supprimerDossier(SauvSelect);
				        listeSauvegarde.remove(nomDossierSelectionne);
				        listePersonnage.remove(nomDossierSelectionne);
				        listModel.remove(indexSelection);
				        lstSauvegarde.setModel(listModel);
				        
				        if (!listModel.isEmpty()) {
				            lstSauvegarde.setSelectedIndex(0);
				        } else {
				        	retour();
				        }
					}
				}
			}
		});
		lblSupprimer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPseudo)
								.addComponent(lblSabre)
								.addComponent(lblAttaque))
							.addGap(43)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblVie)
								.addComponent(lblForce)
								.addComponent(lblSpecificite)))
						.addComponent(lstSauvegarde, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(103)
							.addComponent(btnJouer)
							.addGap(67)
							.addComponent(lblSupprimer, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(148, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lstSauvegarde, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPseudo)
						.addComponent(lblVie))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblForce)
						.addComponent(lblAttaque))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSpecificite)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSupprimer, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnJouer)))
						.addComponent(lblSabre))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
