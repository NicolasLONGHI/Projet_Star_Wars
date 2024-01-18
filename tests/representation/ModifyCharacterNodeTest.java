package representation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import configuration.Configuration;
import univers.Jedi;
import univers.Maitre;
import univers.Padawan;
import univers.Personnage;
import univers.Sabre;
import univers.Sith;

class ModifyCharacterNodeTest {

	private static ModifyCharacterNode<Integer> mcn1;
	private static Personnage padawan1;
	private static Personnage jedi1;
	private static Personnage sith1;
	private static TerminalNode node1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		jedi1 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, 4);
		padawan1 = new Padawan("PadawanTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, Maitre.ANAKIN);
		sith1 = new Sith("SithTest", Configuration.cheminSith1Image, 1, 2, 3, Sabre.ROUGE, 10);
		node1 = new TerminalNode(1, "Description", "Petite description");
		mcn1 = new ModifyCharacterNode<>(1, node1, jedi1, Caracteristique.PUISSANCELANCESABRE, 10);
	}
	
	@BeforeEach
	void setUp() throws Exception {
		jedi1 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, 4);
	}
	
	@Test
	public void testConstructeurValeurInvalide() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(-1, node1, jedi1, Caracteristique.VIE, 10);
	    });
	    
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(1, null, jedi1, Caracteristique.VIE, 10);
	    });
	    
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(1, node1, null, Caracteristique.VIE, 10);
	    });
	    
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(1, node1, jedi1, null, 10);
	    });
	    
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(1, node1, jedi1, Caracteristique.VIE, null);
	    });
	}
	
	@Test
    public void testModificationCheminImage() {
        ModifyCharacterNode<String> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.CHEMINIMAGE, Configuration.cheminSith1Image);
        nodeDecore.display();
        assertEquals(Configuration.cheminSith1Image, jedi1.getCheminImage());
    }
	
	@Test
	public void testModificationCheminImageInvalide() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(1, node1, jedi1, Caracteristique.CHEMINIMAGE, 10);
	        nodeDecore.display();
	    });
	}
	
	@Test
    public void testModificationVie() {
        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.VIE, 10);
        nodeDecore.display();
        assertEquals(11, jedi1.getVie());
    }
	
	@Test
	public void testModificationVieInvalide() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<String> nodeDecore = new ModifyCharacterNode<>(1, node1, jedi1, Caracteristique.VIE, "99");
	        nodeDecore.display();
	    });
	}
	
	@Test
    public void testModificationAttaque() {
        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.ATTAQUE, 10);
        nodeDecore.display();
        assertEquals(12, jedi1.getAttaque());
    }
	
	@Test
    public void testModificationForce() {
        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.FORCE, 10);
        nodeDecore.display();
        assertEquals(13, jedi1.getForce());
    }
	
	@Test
    public void testModificationSabre() {
        ModifyCharacterNode<Sabre> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.SABRE, Sabre.VIOLET);
        nodeDecore.display();
        assertEquals(Sabre.VIOLET, jedi1.getSabre());
    }
	
	@Test
    public void testAjouterMaitre() {
		Personnage padawan1 = new Padawan("PadawanTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, null);
        ModifyCharacterNode<Maitre> nodeDecore = new ModifyCharacterNode<>(2, node1, padawan1, Caracteristique.MAITRE, Maitre.LUKE);
        nodeDecore.display();
        assertEquals(Maitre.LUKE, ((Padawan) padawan1).getMaitre());
    }
	
	@Test
    public void testModificationMaitre() {
        ModifyCharacterNode<Maitre> nodeDecore = new ModifyCharacterNode<>(2, node1, padawan1, Caracteristique.MAITRE, Maitre.YODA);
        nodeDecore.display();
        assertEquals(Maitre.YODA, ((Padawan) padawan1).getMaitre());
    }
	
	@Test
	public void testModificationMaitreInvalide() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<String> nodeDecore = new ModifyCharacterNode<>(1, node1, padawan1, Caracteristique.MAITRE, "99");
	        nodeDecore.display();
	    });
	}
	
	@Test
    public void testModificationPuissanceEclair() {
        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(2, node1, sith1, Caracteristique.PUISSANCEECLAIR, 10);
        nodeDecore.display();
        assertEquals(20, ((Sith) sith1).getPuissanceEclair());
    }
	
	@Test
	public void testModificationPuissanceEclairInvalide() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<String> nodeDecore = new ModifyCharacterNode<>(1, node1, sith1, Caracteristique.PUISSANCEECLAIR, "99");
	        nodeDecore.display();
	    });
	}
	
	@Test
    public void testModificationPuissanceLanceSabre() {
        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.PUISSANCELANCESABRE, 10);
        nodeDecore.display();
        assertEquals(14, ((Jedi) jedi1).getPuissanceLanceSabre());
    }
	
	@Test
	public void testModificationPuissanceLanceSabreInvalide() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(1, node1, sith1, Caracteristique.PUISSANCELANCESABRE, 99);
	        nodeDecore.display();
	    });
	}
	
	@Test
    public void testModificationTypePersonnage() {
        ModifyCharacterNode<Caracteristique> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.TYPEPERSONNAGE, Caracteristique.SITH);
        nodeDecore.display();
        Personnage nouveauPersonnage = nodeDecore.getPersonnage();
		nouveauPersonnage.setListeObservateurs(jedi1.getListeObservateurs());
		jedi1.notifierObservateurs(nouveauPersonnage);
		jedi1 = nouveauPersonnage;
        
        assertEquals(true, jedi1 instanceof Sith);
    }
	
	@Test
    public void testModificationTypePersonnageInvalide() {
		assertThrows(IllegalArgumentException.class, () -> {
			ModifyCharacterNode<String> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.TYPEPERSONNAGE, "Sith");
	        nodeDecore.display();
	        Personnage nouveauPersonnage = nodeDecore.getPersonnage();
			nouveauPersonnage.setListeObservateurs(jedi1.getListeObservateurs());
			jedi1.notifierObservateurs(nouveauPersonnage);
			jedi1 = nouveauPersonnage;
	    });
		
		assertThrows(IllegalArgumentException.class, () -> {
			ModifyCharacterNode<Caracteristique> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.TYPEPERSONNAGE, Caracteristique.TYPEPERSONNAGE);
	        nodeDecore.display();
	        Personnage nouveauPersonnage = nodeDecore.getPersonnage();
			nouveauPersonnage.setListeObservateurs(jedi1.getListeObservateurs());
			jedi1.notifierObservateurs(nouveauPersonnage);
			jedi1 = nouveauPersonnage;
	    });
        
    }
	
	@Test
    public void testSetPersonnage() {
        ModifyCharacterNode<Integer> nodeDecore = new ModifyCharacterNode<>(2, node1, jedi1, Caracteristique.PUISSANCELANCESABRE, 10);
        Personnage personnage = new Padawan("", "", 0, 0, 0, Sabre.ENTRAINEMENT, Maitre.OBIWAN);
        nodeDecore.setPersonnage(personnage);
        
        assertEquals(personnage, nodeDecore.getPersonnage());
    }
	
	@Test
    public void testEquals() {
        ModifyCharacterNode<Integer> mcn2 = new ModifyCharacterNode<>(1, node1, jedi1, Caracteristique.PUISSANCELANCESABRE, 10);
        assertEquals(true, mcn1.equals(mcn2));
        
        ModifyCharacterNode<Integer> mcn3 = new ModifyCharacterNode<>(3, node1, jedi1, Caracteristique.PUISSANCELANCESABRE, 10);
        assertEquals(false, mcn1.equals(mcn3));

        DecisionNode dn1 = new DecisionNode(1, "", "", new ArrayList<>(Arrays.asList(1, 2)));
        ModifyCharacterNode<Integer> mcn4 = new ModifyCharacterNode<>(1, dn1, jedi1, Caracteristique.PUISSANCELANCESABRE, 10);
        assertEquals(false, mcn1.equals(mcn4));

        Personnage p1 = new Padawan("", "", 0, 0, 0, Sabre.ENTRAINEMENT, Maitre.OBIWAN);
        ModifyCharacterNode<Integer> mcn5 = new ModifyCharacterNode<>(1, node1, p1, Caracteristique.PUISSANCELANCESABRE, 10);
        assertEquals(false, mcn1.equals(mcn5));

        ModifyCharacterNode<Integer> mcn6 = new ModifyCharacterNode<>(1, node1, jedi1, Caracteristique.VIE, 10);
        assertEquals(false, mcn1.equals(mcn6));
        
        ModifyCharacterNode<Integer> mcn7 = new ModifyCharacterNode<>(1, node1, jedi1, Caracteristique.PUISSANCELANCESABRE, 99);
        assertEquals(false, mcn1.equals(mcn7));

    }
}
