package configuration;

import java.awt.Image;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import frm.MusiqueFond;

/**
 * Classe permettant de stocker les méthodes utiles pour plusieurs classes.
 * 
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class Utilitaire {
	
	/**
	 * Affiche une image passé en paramètre dans le label passé en paramètre.
	 * 
	 * @param unLabel Le label où l'image sera affichée.
	 * @param unCheminImage Le chemin d'accès de l'image.
	 */
	public static void afficherImage(JLabel unLabel, String unCheminImage, String unMessageErreur) {
		try {
			ImageIcon icon = new ImageIcon(Utilitaire.class.getResource(unCheminImage));
	        Image img = icon.getImage().getScaledInstance(unLabel.getWidth(), unLabel.getHeight(), Image.SCALE_SMOOTH);
	        icon = new ImageIcon(img);
	        unLabel.setIcon(icon);
		}
        catch (Exception e) {
        	JOptionPane.showMessageDialog(null, unMessageErreur, "Erreur", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	/**
	 * Lance la musique de fond donné dans un JFrame passé en paramètre et associe la variable musiqueDeFond à la musique.
	 * Une fois la musique finie, la musique se relance automatiquement.
	 * 
	 * @param uneFrame La JFrame où jouer la musique.
	 * @param uneMusique Le chemin de la musique à jouer.
	 * @param unMessageErreur Le message si une exception est levée.
	 */
	public static void jouerMusiqueFond(JFrame uneFrame, String uneMusique, String unMessageErreur) {
		try {
			((MusiqueFond) uneFrame).setMusiqueDeFond(AudioSystem.getClip());
			((MusiqueFond) uneFrame).getMusiqueDeFond().open(AudioSystem.getAudioInputStream(Utilitaire.class.getResource(uneMusique)));
			((MusiqueFond) uneFrame).getMusiqueDeFond().addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP &&  ((MusiqueFond) uneFrame).getArreterMusiqueFond() == false) {
                	((MusiqueFond) uneFrame).getMusiqueDeFond().close();
                	jouerMusiqueFond(uneFrame, uneMusique, unMessageErreur);
                }
            });
			((MusiqueFond) uneFrame).getMusiqueDeFond().start();
        }
        catch (Exception e1) {
        	JOptionPane.showMessageDialog(null, unMessageErreur, "Erreur", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	/**
	 * Arrête la musique de fond d'une JFrame passé en paramétre.
	 * 
	 * @param uneFrame La JFrame où stopper la musique.
	 */
	public static void stopperMusiqueFond(JFrame uneFrame) {
		((MusiqueFond) uneFrame).setArreterMusiqueFond(true);
		((MusiqueFond) uneFrame).getMusiqueDeFond().stop();
	}
}
