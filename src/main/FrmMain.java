package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.Configuration;
import configuration.Utilitaire;
import frm.FrmGame;
import frm.FrmInfos;
import frm.FrmSave;
import frm.FrmTutoriel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Cette classe représente la fenêtre de démarrage du programme.
 * Elle contient la méthode main.
 * L'utilisateur peut créer une nouvelle partie ou reprendre une partie
 * à partir d'une sauvegarde en allant dans FrmSave.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */

public class FrmMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNomUtilisateur;
	private JLabel lblDerniereConnexion;
	private JLabel lblInfo;
	private JLabel lblTutoriel;
	private JButton btnReprendre;
	private JButton btnNouvellePartie;

	/**
	 * Lance le programme et affiche FrmMain.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FrmMain frame = new FrmMain();
				frame.setVisible(true);
			}
		});
	}
	
	/**
     * Récupère la date de la dernière connexion en parcourant les fichiers de sauvegarde.
     *
     * @return La date de la dernière connexion sous forme de texte formaté.
     */
	private String recupDateDerniereConnexion() {
		ArrayList<Long> listeTimestamp = new ArrayList<>();
        File dossierSauvegarde = new File(Configuration.nomDossierSauvegarde);
        String retour = null;

		try {
	        if (dossierSauvegarde.exists() && dossierSauvegarde.isDirectory()) {
	            File[] lesFichiers = dossierSauvegarde.listFiles();
	
	            if (lesFichiers != null) {
	                for (File unFichier : lesFichiers) {
	                    if (unFichier.isDirectory()) {
	                    	String nomDossier = unFichier.getName();
	                    	long timestamp = Long.parseLong(nomDossier.replace("save", ""));
	                    	listeTimestamp.add(timestamp);
	                    }
	                }
	            }
	            if (!(listeTimestamp.isEmpty())) {
	            	long max = listeTimestamp.get(0);
	                for (int i = 1 ; i < listeTimestamp.size() ; i++) {
	                    long current = listeTimestamp.get(i);
	                    if (current > max) {
	                    	max = current;
	                    }
	                }
	            	Date date = new Date(max);
	                SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm'm'ss's' 'le' dd/MM/yyyy");
	                retour = sdf.format(date);
	            }
	        }
		} catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement de la date de dernière connexion.", "Erreur", JOptionPane.WARNING_MESSAGE);
		}
		return retour;
	}
	
	/**
	 * Créer la fenêtre.
	 */
	public FrmMain() {
		setTitle("Star Wars");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			/**
			 * Affiche la date de dernière connexion dans lblDerniereConnexion.
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				lblNomUtilisateur.setText("Bonjour " + System.getProperty("user.name"));
				String dernierConnexion = recupDateDerniereConnexion();

				Utilitaire.afficherImage(lblInfo, Configuration.cheminInfosIcone, "Une erreur est survenue lors du chargement de l'image.");
				Utilitaire.afficherImage(lblTutoriel, Configuration.cheminTutorielIcone, "Une erreur est survenue lors du chargement de l'image.");
		        
				if (dernierConnexion != null) {
					btnReprendre.setEnabled(true);		           
					lblDerniereConnexion.setText("Dernière connexion : " + dernierConnexion);
				}
				else {
					btnReprendre.setEnabled(false);
					lblDerniereConnexion.setText("Dernière connexion : Aucune date");
				}
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		lblNomUtilisateur = new JLabel("<nomUtilisateur>");
		lblNomUtilisateur.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
		btnReprendre = new JButton("Reprendre");
		btnReprendre.addActionListener(new ActionListener() {
			/**
			 * Ouvre FrmSave.
			 */
			public void actionPerformed(ActionEvent e) {
				FrmSave frmSave = new FrmSave();
				setVisible(false);
				frmSave.setVisible(true);
			}
		});
		
		lblDerniereConnexion = new JLabel("Dernière connexion :<date>");
		
		btnNouvellePartie = new JButton("Nouvelle partie");
		btnNouvellePartie.addActionListener(new ActionListener() {
			/**
			 * Ouvre FrmGame.
			 */
			public void actionPerformed(ActionEvent e) {
				FrmGame frmGame = new FrmGame(null, null);
				setVisible(false);
				frmGame.setVisible(true);
			}
		});
		
		lblInfo = new JLabel("");
		lblInfo.addMouseListener(new MouseAdapter() {
			/**
			 * Ouvre FrmInfo.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				FrmInfos frmInfo = new FrmInfos();
				setVisible(false);
				frmInfo.setVisible(true);
			}
		});
		lblInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblTutoriel = new JLabel("");
		lblTutoriel.addMouseListener(new MouseAdapter() {
			/**
			 * Ouvre FrmTutoriel.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				FrmTutoriel frmTutoriel = new FrmTutoriel();
				setVisible(false);
				frmTutoriel.setVisible(true);
			}
		});
		lblTutoriel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNomUtilisateur)
							.addPreferredGap(ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
							.addComponent(lblTutoriel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblInfo, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblDerniereConnexion)
							.addContainerGap(310, Short.MAX_VALUE))))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(84)
					.addComponent(btnNouvellePartie, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReprendre, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(102, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNomUtilisateur)
							.addGap(43)
							.addComponent(lblDerniereConnexion)
							.addPreferredGap(ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnReprendre)
								.addComponent(btnNouvellePartie)))
						.addComponent(lblInfo, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTutoriel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
