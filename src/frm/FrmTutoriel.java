package frm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.Configuration;
import configuration.Utilitaire;
import main.FrmMain;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Cette classe représente la fenêtre de tutoriel sur le fonctionnement du jeu.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class FrmTutoriel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitreChangerPseudo;
	private JLabel lblImageChangerPseudo;
	private JLabel lblTitreSystemeCombat;
	private JTextArea txtSystemeCombat;
	private JButton btnRetour;
	
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
	public FrmTutoriel() {
		setTitle("Tutoriel");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			
			
			@Override
			public void windowOpened(WindowEvent e) {
				Utilitaire.afficherImage(lblImageChangerPseudo, Configuration.cheminChangerPseudoImage, "Une erreur est survenue lors du chargement de l'image.");
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
		setBounds(100, 100, 1020, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		lblTitreChangerPseudo = new JLabel("Changer de pseudo");
		lblTitreChangerPseudo.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		lblImageChangerPseudo = new JLabel("");
		
		JTextArea txtChangerPseudo = new JTextArea();
		txtChangerPseudo.setWrapStyleWord(true);
		txtChangerPseudo.setLineWrap(true);
		txtChangerPseudo.setEditable(false);
		txtChangerPseudo.setText("Pour changer de pseudo, il suffit de cliquer sur \"Pseudo\" durant une partie.\r\nUne fenêtre nous demande notre nouveau pseudo.\r\nCliquez sur Ok pour appliquer les changements.");
		
		lblTitreSystemeCombat = new JLabel("Système de combat");
		lblTitreSystemeCombat.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txtSystemeCombat = new JTextArea();
		txtSystemeCombat.setWrapStyleWord(true);
		txtSystemeCombat.setText("Durant les combats, nous avons le choix entre 4 attaques qui ont chacun un taux de réussite spécifique :\r\n   - Attaque simple (100 %)\r\n   - Force (90 %)\r\n   - Sabre (80 %)\r\n   - Attaque spéciale (65 %)\r\n\r\nL'attaque spéciale dépend du type de personnage.\r\nSi c'est un Padawan, il peut recevoir une aide de son maître. L'attaque du maître dépend du maitre (Yoda : 15, Obi-Wan : 10, Luke : 12 et Anakin : 17).\r\nSi c'est un Jedi, il peut lancer son sabre.\r\nSi c'est un Sith, il peut lancer des éclairs.\r\n\r\nLes sabres ont également leur propre puissance :\r\n   - Bleu : 9\t\t\t- Vert : 10\r\n   - Rouge : 15 \t\t\t- Violet : 15\r\n   - Entrainement : 7  \t\t- Blanc : 17\r\nIl faut réaliser une action spéciale pour avoir le sabre blanc d'Ahsoka ...\r\n\r\nToutes les statistiques de chaque combattant sont visibles dans l'interface de combat.\r\nAprès chaque combat, notre vie revient à 100.");
		txtSystemeCombat.setLineWrap(true);
		txtSystemeCombat.setEditable(false);
		
		btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			
			/**
			 * Appelle la fonction {@link #retour()}.
			 */
			public void actionPerformed(ActionEvent e) {
				retour();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(136)
							.addComponent(lblTitreChangerPseudo)
							.addGap(324)
							.addComponent(lblTitreSystemeCombat))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(22)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtChangerPseudo, 0, 0, Short.MAX_VALUE)
								.addComponent(lblImageChangerPseudo, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
							.addGap(66)
							.addComponent(txtSystemeCombat, GroupLayout.PREFERRED_SIZE, 505, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnRetour)))
					.addContainerGap(362, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitreChangerPseudo)
						.addComponent(lblTitreSystemeCombat))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblImageChangerPseudo, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtChangerPseudo, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtSystemeCombat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addComponent(btnRetour)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

}
