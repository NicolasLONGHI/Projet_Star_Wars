package frm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.FrmMain;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;

/**
 * Cette classe représente la fenêtre avec les informations sur le jeu.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class FrmInfos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
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
	public FrmInfos() {
		setResizable(false);
		setTitle("Informations");
		addWindowListener(new WindowAdapter() {
			/**
			 * Appelle la fonction {@link #retour()}.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				retour();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 691, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Retour");
		btnNewButton.addActionListener(new ActionListener() {
			/**
			 * Appelle la fonction {@link #retour()}.
			 */
			public void actionPerformed(ActionEvent e) {
				retour();
			}
		});
		
		JTextArea txtrProgrammeCrPar = new JTextArea();
		txtrProgrammeCrPar.setLineWrap(true);
		txtrProgrammeCrPar.setWrapStyleWord(true);
		txtrProgrammeCrPar.setEditable(false);
		txtrProgrammeCrPar.setText("Jeu créé par DUBOSCQ Maxime et LONGHI Nicolas en 2023.\r\n\r\nLes musiques utilisées sont tirées des films Star Wars.\r\nVoici une liste des musiques utilisées :\r\n   - Across the Stars (Samuel Kim)\r\n   - Duel of the Fates (John Williams)\r\n   - Anakin vs. Obi-Wan (John Williams)\r\n   - Love Pledge and the Arena (John Williams & London Symphony Orchestra)\r\n\r\nLa plupart des images ont été générées par IA.\r\nLes autres images proviennent des long-métrage suivants :\r\n   - Ahsoka (Ahsoka  interprété par Rosario Dawson)\r\n   - Star Wars : L'Empire contre-attaque (Luke interprété par Mark Hamill)\r\n   - Star Wars : La Revanche des Sith (Anakin interprété par Hayden Christensen, Mace Windu interprété par Samuel L. Jackson, Obi-Wan interprété par Ewan McGregor, Palpatine interprété par Ian McDiarmid)\r\n   - Star Wars : L'Attaque des clones (Dark Tyranus interprété par Christopher Lee)\r\n   - Star Wars : La Menace fantôme (Dark Maul interprété par Ray Park)");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrProgrammeCrPar)
						.addComponent(btnNewButton))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(txtrProgrammeCrPar, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
