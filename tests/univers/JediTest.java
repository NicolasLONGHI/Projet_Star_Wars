package univers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import configuration.Configuration;
import representation.Caracteristique;
import representation.ModifyCharacterNode;

class JediTest {

	private Personnage jedi1;

	@BeforeEach
	void setUp() throws Exception {
		jedi1 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, 4);
	}
	
	@Test
	void testConstructeurValeurInvalide() {
		 assertThrows(IllegalArgumentException.class, () -> {
		        Personnage personnage = new Jedi("", "", -1, 0, 0, Sabre.VERT, 0);
		    });
		 
		 assertThrows(IllegalArgumentException.class, () -> {
		        Personnage personnage = new Jedi("", "", 0, -1, 0, Sabre.VERT, 0);
		    });
		 
		 assertThrows(IllegalArgumentException.class, () -> {
		        Personnage personnage = new Jedi("", "", 0, 0, -1, Sabre.VERT, 0);
		    });
		 
		 assertThrows(IllegalArgumentException.class, () -> {
		        Personnage personnage = new Jedi("", "", 0, 0, 0, Sabre.VERT, -1);
		    });
	}
	
	@Test
	void testGetPseudo() {
		assertEquals("JediTest", jedi1.getPseudo());
	}
	
	@Test
	void testPuissanceLanceSabre() {
		((Jedi) jedi1).setPuissanceLanceSabre(20);
		assertEquals(20, ((Jedi) jedi1).getPuissanceLanceSabre());
	}
	
	@Test
	void testEquals() {
		Jedi jedi2 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, 4);
		assertEquals(true, jedi1.equals(jedi2));
		
		jedi2 = new Jedi("unAutreNom", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, 4);
		assertEquals(false, jedi1.equals(jedi2));
		
		jedi2 = new Jedi("JediTest", Configuration.cheminSith1Image, 1, 2, 3, Sabre.VERT, 4);
		assertEquals(false, jedi1.equals(jedi2));
		
		jedi2 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 99, 2, 3, Sabre.VERT, 4);
		assertEquals(false, jedi1.equals(jedi2));
		
		jedi2 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 99, 3, Sabre.VERT, 4);
		assertEquals(false, jedi1.equals(jedi2));
		
		jedi2 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 2, 99, Sabre.VERT, 4);
		assertEquals(false, jedi1.equals(jedi2));
		
		jedi2 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.BLEU, 4);
		assertEquals(false, jedi1.equals(jedi2));
		
		jedi2 = new Jedi("JediTest", Configuration.cheminPadawan1Image, 1, 2, 3, Sabre.VERT, 99);
		assertEquals(false, jedi1.equals(jedi2));
	}

}
