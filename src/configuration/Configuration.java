package configuration;

import java.io.File;

/**
 * La classe Configuration contient des constantes liées à la configuration du jeu, telles que les chemins des fichiers audio et des images.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class Configuration {
	//Noms et chemins des dossiers utilisés
	public static final String nomDossierSauvegarde = "saves";
	public static final String nomSauvegardeNode = "node";
	public static final String nomSauvegardePersonnage = "personnage";
	public static final String cheminDossierSauvegarde = nomDossierSauvegarde + File.separator + "save";
	
	// --------------------------------------------------------------------------
	//Fonds sonore
	public static final String cheminFondSound = "/sounds/fondSound.wav";
	public static final String cheminFight1Sound = "/sounds/fight1Sound.wav";
	public static final String cheminFight2Sound = "/sounds/fight2Sound.wav";
	public static final String cheminFight3Sound = "/sounds/fight3Sound.wav";
	
	
	//Bruitages
	public static final String cheminLevelUpSound = "/sounds/levelUpSound.wav";
	public static final String cheminDeathSound = "/sounds/deathSound.wav";

	
	//Bruitages combat
	public static final String cheminLaserSound = "/sounds/laserSound.wav";
	public static final String cheminAttaqueSound = "/sounds/attaqueSound.wav";
	public static final String cheminForceSound = "/sounds/forceSound.wav";
	public static final String cheminAttaqueLaserSound = "/sounds/attaqueLaserSound.wav";
	public static final String cheminEclairSound = "/sounds/eclairSound.wav";
	public static final String cheminLancerSabreSound = "/sounds/lancerSabreSound.wav";
	public static final String cheminPvBasSound = "/sounds/pvBasSound.wav";
	public static final String cheminYodaSound = "/sounds/yodaSound.wav";
	public static final String cheminObiWanSound = "/sounds/obiwanSound.wav";
	public static final String cheminLukeSound = "/sounds/lukeSound.wav";
	public static final String cheminAnakinSound = "/sounds/anakinSound.wav";
	public static final String cheminVictoireSound = "/sounds/victoireSound.wav";
	public static final String cheminDefaiteSound = "/sounds/defaiteSound.wav";


	// --------------------------------------------------------------------------
	//Images pour les nodes
    public static final String cheminPorteImage = "/images/porteImage.png";
    public static final String cheminTourFImage = "/images/tourfImage.png";
    public static final String cheminTourGImage = "/images/tourgImage.png";
    public static final String cheminVolcanImage = "/images/volcanImage.png";
    public static final String cheminMontagneImage = "/images/montagneImage.png";
    public static final String cheminAubergeImage = "/images/aubergeImage.png";
    public static final String cheminBarImage = "/images/barImage.png";
	
	//Images Personnage
	public static final String cheminPadawan1Image = "/images/padawan1Image.png";
	public static final String cheminPadawan2Image = "/images/padawan2Image.png";
	public static final String cheminSith1Image = "/images/sith1Image.png";
	public static final String cheminSith2Image = "/images/sith2Image.png";
	public static final String cheminPalpatineImage = "/images/palpatineImage.png";
	public static final String cheminDarkMaulImage = "/images/darkMaulImage.png";
	public static final String cheminDarkTyranusImage = "/images/darkTyranusImage.png";
	public static final String cheminMaceWinduImage = "/images/maceWinduImage.png";
	public static final String cheminGrievousImage = "/images/grievousImage.png";
	public static final String cheminDarkVadorImage = "/images/darkVadorImage.png";
	public static final String cheminAhsokaImage = "/images/ahsokaImage.png";

	public static final String cheminYodaMaitreImage = "/images/yodaMaitreImage.png";
	public static final String cheminObiWanMaitreImage = "/images/obiwanMaitreImage.png";
	public static final String cheminLukeMaitreImage = "/images/lukeMaitreImage.png";
	public static final String cheminAnakinMaitreImage = "/images/anakinMaitreImage.png";
	
	public static final String cheminChangerPseudoImage = "/images/changerPseudoImage.png";

	// --------------------------------------------------------------------------
	public static final String cheminSupprimerIcone = "/icons/supprimerIcone.png";
	public static final String cheminQuitterIcone = "/icons/quitterIcone.png";
	public static final String cheminSauvegarderIcone = "/icons/sauvegarderIcone.png";
	public static final String cheminSonIcone = "/icons/sonIcone.png";
	public static final String cheminSonCoupeIcone = "/icons/sonCoupeIcone.png";
	public static final String cheminInfosIcone = "/icons/infosIcone.png";
	public static final String cheminTutorielIcone = "/icons/tutorielIcone.png";
	
}
