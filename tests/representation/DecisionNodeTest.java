package representation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DecisionNodeTest {

	private static DecisionNode node1;
	private static DecisionNode node2;
	private static DecisionNode node3;
		
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		node1 = new DecisionNode(1, "", "", new ArrayList<>(Arrays.asList(2, 3)));
		node2 = new DecisionNode(2, "", "", new ArrayList<>(Arrays.asList(4)));
        node3 = new DecisionNode(3, "", "", new ArrayList<>(Arrays.asList(5)));
        
        ArrayList<Event> listeNodes = new ArrayList<>();
        listeNodes.addAll(Arrays.asList(node1, node2, node3));
        for (int i = 0 ; i < listeNodes.size() ; i++) {
			if (listeNodes.get(i) instanceof InnerNode && ((InnerNode) listeNodes.get(i)).getIDProchaines() != null) {
				((InnerNode) listeNodes.get(i)).definirProchainesNodes(((InnerNode) listeNodes.get(i)).getIDProchaines(), listeNodes);
			}
		}
        ArrayList<Integer> listeIDAttendu = new ArrayList<>();
	}

	@Test
    public void testConstructeur() {
		DecisionNode node = new DecisionNode(5, "Une description", "Une petite description", new ArrayList<>(Arrays.asList(6)));
        assertEquals(5, node.getId());
        assertEquals("<html>Une description</html>", node.getDescription());
        assertEquals("Une petite description", node.getPetiteDescription());
        ArrayList<Integer> listeIDAttendu = new ArrayList<>();
        listeIDAttendu.add(6);
        assertEquals(listeIDAttendu, node.getIDProchaines());
    }
	
	@Test
	void testConstructeurValeurInvalide() {
		 assertThrows(IllegalArgumentException.class, () -> {
		        DecisionNode node = new DecisionNode(1, "", "", new ArrayList<>(Arrays.asList()));
		 });

		 assertThrows(IllegalArgumentException.class, () -> {
		        DecisionNode node = new DecisionNode(1, "", "", null);
		 });
		 
		 assertThrows(IllegalArgumentException.class, () -> {
		        DecisionNode node = new DecisionNode(-1, "", "", new ArrayList<>(Arrays.asList(1)));
		 });
	}
	
	@Test
    public void testDefinirProchainesNodes() {
        ArrayList<Integer> listeIDAttendu = new ArrayList<>();
        listeIDAttendu.addAll(Arrays.asList(2, 3));
        assertEquals(listeIDAttendu, node1.getIDProchaines());
    }
	
	@Test
    public void testChooseNext() {
        assertEquals(node2, node1.chooseNext(2));
    }

	@Test
    public void testEquals() {
		DecisionNode node4 = new DecisionNode(1, "", "", new ArrayList<>(Arrays.asList(2, 3)));
        assertEquals(true, node1.equals(node4));

		DecisionNode node5 = new DecisionNode(99, "", "", new ArrayList<>(Arrays.asList(2, 3)));
        assertEquals(false, node1.equals(node5));
        
		DecisionNode node6 = new DecisionNode(1, "Une description", "", new ArrayList<>(Arrays.asList(2, 3)));
        assertEquals(false, node1.equals(node6));
        
		DecisionNode node7 = new DecisionNode(1, "", "Une petite description", new ArrayList<>(Arrays.asList(2, 3)));
        assertEquals(false, node1.equals(node7));
        
		DecisionNode node8 = new DecisionNode(1, "", "", new ArrayList<>(Arrays.asList(1, 99)));
        assertEquals(false, node1.equals(node8));
    }
	
	@Test
    public void testToString() {
        DecisionNode node = new DecisionNode(1, "Description", "Petite Description", new ArrayList<>(Arrays.asList(1, 2)));
        String toStringAttendu = "DecisionNode [getId()=1, getDescription()=<html>Description</html>, getPetiteDescription()=Petite Description, getIDProchaines()=" + 
				"[1, 2], getProchainesNodes()=[]]";
        assertEquals(toStringAttendu, node.toString());
    }

}
