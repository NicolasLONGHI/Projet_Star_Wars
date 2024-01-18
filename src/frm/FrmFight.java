package frm;

import java.awt.Color;

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

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

/**
 * Cette classe représente la fenêtre de combat.
 * Gère les attaques, bruitages et images du joueur et de l'ennemi.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class FrmFight extends JFrame implements MusiqueFond {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCommentaire;
	private JLabel lblViePerso;
	private JLabel lblPerso;
	private JLabel lblAttaquePerso;
	private JLabel lblForcePerso;
	private JLabel lblSabrePerso;
	private JLabel lblSpecificitePerso;
	private JLabel lblImagePerso;
	private JLabel lblImageEnnemi;
	private JLabel lblEnnemi;
	private JLabel lblVieEnnemi;
	private JLabel lblAttaqueEnnemi;
	private JLabel lblForceEnnemi;
	private JLabel lblSabreEnnemi;
	private JLabel lblSpecificiteEnnemi;
	private JButton btnAttaquer;
	private JButton btnSabre;
	private JButton btnForce;
	private JButton btnSpecificite;
	
	private Random nbAleatoire = new Random();
	private Clip musiqueDeFond;
	private boolean arreterMusiqueFond = false;
	private boolean musiqueOn = true;

	private boolean possibiliteAttaque = false;
	private Personnage personnageVictime;
	private Event nodeSuivante;

	private Personnage monPersonnage;
	private Personnage ennemi;
	private Event nodeGagne;
	private Event nodePerdu;
	private String musique;
	private JLabel lblSon;

	/**
	 * Affiche le commentaire dans la zone des commentaires (txtCommentaire).
	 * Le message s'affiche au fur et à mesure avec un délai fourni.
	 * 
	 * @param unCommentaire Le commentaire à afficher.
	 * @param unDelai Le temps entre chaque caractère de s'afficher.
	 */
	private void afficherCommentaire(String unCommentaire, int unDelai) {
		txtCommentaire.setText("");
        Timer timer = new Timer(unDelai, null);
        int[] index = {0};

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index[0] < unCommentaire.length()) {
                	txtCommentaire.setText(txtCommentaire.getText() + unCommentaire.charAt(index[0]));
                    index[0]++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }
	
	/**
	 * Effectue une attaque en fonction de l'attaque spécifiée et de l'attaquant donné.
	 * 
	 * @param uneAttaque Le type d'attaque effectué.
	 * @param unAttaquant Le personnage qui effectue l'attaque.
	 */
	private void attaquer(int uneAttaque, Personnage unAttaquant) {
		possibiliteAttaque = false;
		personnageVictime = monPersonnage;
		if (unAttaquant.equals(monPersonnage)) {
			personnageVictime = ennemi;
		}
		else {
			uneAttaque = nbAleatoire.nextInt(4);
			if (unAttaquant instanceof Padawan) {
				if (((Padawan) unAttaquant).getMaitre() == null) {
					uneAttaque = nbAleatoire.nextInt(3);
				}
			}
		}
		
		
		if (uneAttaque == 0) { //Attaque simple
			playSound(Configuration.cheminAttaqueSound);
			personnageVictime.setVie(personnageVictime.getVie() - unAttaquant.getAttaque());
			afficherCommentaire(unAttaquant.getPseudo() + " fait " + unAttaquant.getAttaque() + " de dommage à " + personnageVictime.getPseudo() + ".", 15);
		}
		else if (uneAttaque == 1) { //Force : 9/10 de chance
			int chance = nbAleatoire.nextInt(11);
			if (chance < 10) {
				playSound(Configuration.cheminForceSound);
				personnageVictime.setVie(personnageVictime.getVie() - unAttaquant.getForce());
				afficherCommentaire(unAttaquant.getPseudo() + " utilise la force et enlève " + unAttaquant.getForce() + " de points de vie à " + personnageVictime.getPseudo() + ".", 15);
			}
			else {
				afficherCommentaire(unAttaquant.getPseudo() + " a echoué son attaque.", 15);
			}
		}
		else if (uneAttaque == 2) { //Sabre : 16/20 de chance
			int chance = nbAleatoire.nextInt(20);
				if (chance < 17) {
				playSound(Configuration.cheminAttaqueLaserSound);
				personnageVictime.setVie(personnageVictime.getVie() - unAttaquant.getSabre().getPuissance());
				afficherCommentaire(unAttaquant.getPseudo() + " donne des coups de sabre à " + personnageVictime.getPseudo() + " et inflige " + unAttaquant.getSabre().getPuissance() + " de dégats.", 15);
			}
			else {
				afficherCommentaire(unAttaquant.getPseudo() + " a echoué son attaque.", 15);
			}
		}
		else if (uneAttaque == 3) { //Specificite : 2/3 de chance
			int chance = nbAleatoire.nextInt(3);
			if (chance < 2) {
				if (unAttaquant instanceof Padawan) { //Aide Maitre
					String nomMaitre = ((Padawan) unAttaquant).getMaitre().getNomAffiche();

					playSound(((Padawan) unAttaquant).getMaitre().getCheminSon());
					
					if (unAttaquant.equals(monPersonnage)) {
						Utilitaire.afficherImage(lblImagePerso, ((Padawan) unAttaquant).getMaitre().getCheminImage(), "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
					}
					else {
						Utilitaire.afficherImage(lblImageEnnemi, ((Padawan) unAttaquant).getMaitre().getCheminImage(), "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
					}
			        
					personnageVictime.setVie(personnageVictime.getVie() - ((Padawan) unAttaquant).getMaitre().getPuissance());
					afficherCommentaire(nomMaitre + " aide son Padawan et inflige " + ((Padawan) unAttaquant).getMaitre().getPuissance() + " à " + personnageVictime.getPseudo() + ".", 15);
				}
				else if (unAttaquant instanceof Sith) { //Eclair
					playSound(Configuration.cheminEclairSound);
					personnageVictime.setVie(personnageVictime.getVie() - ((Sith) unAttaquant).getPuissanceEclair());
					afficherCommentaire(unAttaquant.getPseudo() + " utilise la puissance obscure et invoque des éclairs qui infligent " + ((Sith) unAttaquant).getPuissanceEclair() + " à " + personnageVictime.getPseudo() + ".", 15);
				}
				else if (unAttaquant instanceof Jedi) { //Lancer de sabre
					playSound(Configuration.cheminLancerSabreSound);
					personnageVictime.setVie(personnageVictime.getVie() - ((Jedi) unAttaquant).getPuissanceLanceSabre());
					afficherCommentaire(unAttaquant.getPseudo() + " lance son sabre et fait " + ((Jedi) unAttaquant).getPuissanceLanceSabre() + " de dégats à " + personnageVictime.getPseudo() + ".", 15);
				}
			}
			else {
				afficherCommentaire(unAttaquant.getPseudo() + " a echoué son attaque.", 15);
			}
		}
		if (monPersonnage.getVie() > 0 && monPersonnage.getVie() <= 20 && unAttaquant.equals(ennemi)) {
			playSound(Configuration.cheminPvBasSound);
		}
		majVie();
		
		pause(4000, () -> {
			if (monPersonnage.getVie() <= 0) { //Perdu
				finCombat(false);
			}
			else if (ennemi.getVie() <= 0) { //Gagne
				finCombat(true);
			}
			else { //Tour suivant
				Utilitaire.afficherImage(lblImagePerso, monPersonnage.getCheminImage(), "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
		        
				if (unAttaquant.equals(monPersonnage)) {
					attaquer(0, personnageVictime);
				}
				else {
					afficherCommentaire("C'est à vous d'attaquer.", 15);
					pause(600, () -> {
						possibiliteAttaque = true;
					});
				}
			}
		});
	}
	
	/**
	 * Met fin au combat et ouvre FrmGame sur la node selon si le joueur a gagné ou perdu.
	 * 
	 * @param unGagne true si le joueur a gagné, false sinon.
	 */
	private void finCombat(boolean unGagne) {
		Utilitaire.stopperMusiqueFond(this);
		if (unGagne) {
			playSound(Configuration.cheminVictoireSound);
			txtCommentaire.setForeground(new Color(13, 111, 16));
			afficherCommentaire(ennemi.getPseudo() + " a perdu le combat. Vous avez gagné !", 25);
			nodeSuivante = nodeGagne;
		}
		else {
			playSound(Configuration.cheminDefaiteSound);
			txtCommentaire.setForeground(Color.RED);
			afficherCommentaire("Vous avez été vaincu !", 25);
			nodeSuivante = nodePerdu;
		}
		monPersonnage.setVie(100);
		pause(6000, () -> {
			FrmGame frmGame = new FrmGame(nodeSuivante, monPersonnage);
			setVisible(false);
			frmGame.setVisible(true);
		});
		
	}
	
	/**
	 * Met à jour la vie des combattants.
	 */
	private void majVie() {
		if (monPersonnage.getVie() <= 0) {
			monPersonnage.setVie(0);
		}
		if (monPersonnage.getVie() > 0 && monPersonnage.getVie() <= 20) {
			lblViePerso.setForeground(Color.RED);
		}
		lblViePerso.setText("Vie : " + monPersonnage.getVie());
		
		
		if (ennemi.getVie() <= 0) {
			ennemi.setVie(0);
		}
		if (ennemi.getVie() > 0 && ennemi.getVie() <= 20) {
			lblVieEnnemi.setForeground(Color.RED);
		}
		lblVieEnnemi.setText("Vie : " + ennemi.getVie());
	}
	
	/**
	 * Met à jour toutes les informations des combattants.
	 * Appelle la fonction {@link #majVie()}.
	 */
	private void majInfos() {
		lblAttaquePerso.setText("Attaque : " + monPersonnage.getAttaque());
		lblForcePerso.setText("Force : " + monPersonnage.getForce());
		lblSabrePerso.setText("Sabre : " + monPersonnage.getSabre() + " (" + monPersonnage.getSabre().getPuissance() + ")");
		if (monPersonnage instanceof Padawan) {
			if (((Padawan) monPersonnage).getMaitre() == null) {
				lblSpecificitePerso.setText("Maitre : Aucun");
				btnSpecificite.setVisible(false);
			}
			else {
				lblSpecificitePerso.setText("Maitre : " + ((Padawan) monPersonnage).getMaitre() + " (" + ((Padawan) monPersonnage).getMaitre().getPuissance() + ")");
				btnSpecificite.setText("Aide du maitre");
			}
		}
		else if (monPersonnage instanceof Sith) {
			lblSpecificitePerso.setText("Eclair : " + ((Sith) monPersonnage).getPuissanceEclair());
			btnSpecificite.setText("Eclair");
		}
		else if (monPersonnage instanceof Jedi) {
			lblSpecificitePerso.setText("Lancer sabre : " + ((Jedi) monPersonnage).getPuissanceLanceSabre());
			btnSpecificite.setText("Lancer sabre");
		}

		lblEnnemi.setText("Ennemi : " + ennemi.getPseudo());
		lblAttaqueEnnemi.setText("Attaque : " + ennemi.getAttaque());
		lblForceEnnemi.setText("Force : " + ennemi.getForce());
		lblSabreEnnemi.setText("Sabre : " + ennemi.getSabre() + " (" + ennemi.getSabre().getPuissance() + ")");
		if (ennemi instanceof Padawan) {
			if (((Padawan) ennemi).getMaitre() == null) {
				lblSpecificiteEnnemi.setText("Maitre : Aucun");
			}
			else {
				lblSpecificiteEnnemi.setText("Maitre : " + ((Padawan) ennemi).getMaitre() + " (" + ((Padawan) ennemi).getMaitre().getPuissance() + ")");
			}
		}
		else if (ennemi instanceof Sith) {
			lblSpecificiteEnnemi.setText("Eclair : " + ((Sith) ennemi).getPuissanceEclair());
		}
		else if (ennemi instanceof Jedi) {
			lblSpecificiteEnnemi.setText("Lancer sabre : " + ((Jedi) ennemi).getPuissanceLanceSabre());
		}
		majVie();
	}
	
	/**
	 * Fait une pause avant d'éxecuter le code passé en paramètre.
	 * 
	 * @param unDelai Délai en millisecondes du temps d'attente.
	 * @param unCodeExecute Le code à éxecuter après le délai.
	 */
	private void pause(int unDelai, Runnable unCodeExecute) {
	    Timer timer = new Timer(unDelai, e -> {
	    	unCodeExecute.run();
	    });
	    timer.setRepeats(false);
	    timer.start();
	}
	
	/**
	 * Joue un bruitage pour animer le combat.
	 * 
	 * @param unChemin Le chemin du bruitage à jouer.
	 */
	private void playSound(String unChemin) {
		if (musiqueOn) {
	    	try {
	            Clip clip = AudioSystem.getClip();
	            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(unChemin)));
	            clip.start();
	        }
	        catch (Exception e) {
	        	JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement du bruitage. Le combat continue.", "Erreur", JOptionPane.WARNING_MESSAGE);
	        }
		}
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
	public FrmFight(Personnage unPersonnage, Personnage unEnnemi, Event uneNodeGagne, Event uneNodePerdu, String uneMusique) {
		setTitle("Combat");
		this.monPersonnage = unPersonnage;
		this.ennemi = unEnnemi;
		this.nodeGagne = uneNodeGagne;
		this.nodePerdu = uneNodePerdu;
		this.musique = uneMusique;
		addWindowListener(new WindowAdapter() {
			
			/**
			 * Démarrer la musique de fond, affiche les images des combattants, mets à jour leurs informations
			 * et défini aléatoirement qui commence le combat.
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				Utilitaire.jouerMusiqueFond(FrmFight.this, uneMusique, "Une erreur est survenue lors du chargement de la musique de fond. Le combat continue.");

				Utilitaire.afficherImage(lblImagePerso, unPersonnage.getCheminImage(), "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
				Utilitaire.afficherImage(lblImageEnnemi, unEnnemi.getCheminImage(), "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
				Utilitaire.afficherImage(lblSon, Configuration.cheminSonIcone, "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
		        
				majInfos();
				afficherCommentaire("Le combat commence contre " + ennemi.getPseudo() + ".", 15);
				
				pause(5000, () -> {
					int personneQuiCommence = nbAleatoire.nextInt(2);
					if (personneQuiCommence == 0) {
						afficherCommentaire("C'est à vous d'attaquer.", 15);
						possibiliteAttaque = true;
					}
					else {
						afficherCommentaire(ennemi.getPseudo() + " attaque !", 15);
						playSound(Configuration.cheminLaserSound);
						pause(3000, () -> {
							attaquer(0, unEnnemi);
						});
					}
				});
			}
			
			/**
			 * Demande si le joueur veut revenir à l'écran principal et ne pas sauvegarder sa partie.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				int choix = JOptionPane.showConfirmDialog(null, "Si vous quittez le combat, la partie ne sera pas sauvegardée.\n Voulez-vous vraiment quitter le combat ?", "Confirmation", JOptionPane.YES_NO_OPTION);

		        if (choix == JOptionPane.YES_OPTION) {
		        	Utilitaire.stopperMusiqueFond(FrmFight.this);
		        	retour();
		        }
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		txtCommentaire = new JTextField();
		txtCommentaire.setEditable(false);
		txtCommentaire.setColumns(10);
		
		lblPerso = new JLabel("Vous");
		lblPerso.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblViePerso = new JLabel("Vie : <vie>");
		lblAttaquePerso = new JLabel("Attaque : <attaque>");
		lblForcePerso = new JLabel("Force : <force>");
		lblSabrePerso = new JLabel("Sabre : <sabre>");
		lblSpecificitePerso = new JLabel("Specificite : <specificite>");
		lblImagePerso = new JLabel("");
		lblEnnemi = new JLabel("Ennemi");
		lblEnnemi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblImageEnnemi = new JLabel("");
		lblVieEnnemi = new JLabel("Vie : <vie>");
		lblAttaqueEnnemi = new JLabel("Attaque : <attaque>");
		lblForceEnnemi = new JLabel("Force : <force>");
		lblSabreEnnemi = new JLabel("Sabre : <sabre>");
		lblSpecificiteEnnemi = new JLabel("Specificite : <specificite>");
		btnAttaquer = new JButton("Attaque simple");
		btnAttaquer.addActionListener(new ActionListener() {

			/**
			 * Appelle la fonction {@link #attaquer()} avec en paramètre le choix de l'attaque et le personnage qui attaque.
			 */
			public void actionPerformed(ActionEvent e) {
				if (possibiliteAttaque) {
					attaquer(0, monPersonnage);
				}
			}
		});
		btnForce = new JButton("Force");
		btnForce.addActionListener(new ActionListener() {
			
			/**
			 * Appelle la fonction {@link #attaquer()} avec en paramètre le choix de l'attaque et le personnage qui attaque.
			 */
			public void actionPerformed(ActionEvent e) {
				if (possibiliteAttaque) {
					attaquer(1, monPersonnage);
				}
			}
		});
		btnSabre = new JButton("Sabre");
		btnSabre.addActionListener(new ActionListener() {

			/**
			 * Appelle la fonction {@link #attaquer()} avec en paramètre le choix de l'attaque et le personnage qui attaque.
			 */
			public void actionPerformed(ActionEvent e) {
				if (possibiliteAttaque) {
					attaquer(2, monPersonnage);
				}
			}
		});
		btnSpecificite = new JButton("<specificite>");
		btnSpecificite.addActionListener(new ActionListener() {
			
			/**
			 * Appelle la fonction {@link #attaquer()} avec en paramètre le choix de l'attaque et le personnage qui attaque.
			 */
			public void actionPerformed(ActionEvent e) {
				if (possibiliteAttaque) {
					attaquer(3, monPersonnage);
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
				if (musiqueOn) {
					musiqueOn = false;
					Utilitaire.stopperMusiqueFond(FrmFight.this);
					Utilitaire.afficherImage(lblSon, Configuration.cheminSonCoupeIcone, "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
				}
				else {
					musiqueOn = true;
					Utilitaire.jouerMusiqueFond(FrmFight.this, uneMusique, "Une erreur est survenue lors du chargement de la musique de fond. Le combat continue.");
					Utilitaire.afficherImage(lblSon, Configuration.cheminSonIcone, "Une erreur est survenue lors du chargement de l'image. Le combat continue.");
				}
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblViePerso)
								.addComponent(lblAttaquePerso)
								.addComponent(lblForcePerso)
								.addComponent(lblSpecificitePerso, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
								.addComponent(lblSabrePerso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(45)
							.addComponent(lblImagePerso, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addGap(68)
							.addComponent(lblImageEnnemi, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblSpecificiteEnnemi, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
								.addComponent(lblSabreEnnemi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblForceEnnemi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblAttaqueEnnemi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblVieEnnemi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSon, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(109)
							.addComponent(lblPerso)
							.addPreferredGap(ComponentPlacement.RELATED, 343, Short.MAX_VALUE)
							.addComponent(lblEnnemi, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
							.addGap(51))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtCommentaire, GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnAttaquer, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
									.addGap(94)
									.addComponent(btnForce, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
									.addGap(71)
									.addComponent(btnSabre, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
									.addComponent(btnSpecificite, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPerso)
						.addComponent(lblEnnemi, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSon, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblVieEnnemi)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblAttaqueEnnemi)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblForceEnnemi)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSabreEnnemi)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSpecificiteEnnemi))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblViePerso)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblAttaquePerso)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblForcePerso)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSabrePerso)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSpecificitePerso))
						.addComponent(lblImagePerso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblImageEnnemi, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGap(11)
					.addComponent(txtCommentaire, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnAttaquer, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSabre, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnForce, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSpecificite, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(106, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
