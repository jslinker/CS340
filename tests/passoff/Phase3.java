package passoff;

import hypeerweb.HyPeerWebSegment;
import hypeerweb.node.ExpectedResult;
import hypeerweb.node.Node;
import hypeerweb.node.SimplifiedNodeDomain;

public class Phase3 {
    public static HyPeerWebSegment hypeerweb = HyPeerWebSegment.getSingleton();
    private static final int HYPEERWEB_SIZE = 32;
    private static final boolean VERBOSE = true;
    private static final int PHASE_3_TEST_COUNT = 10912;
    private static int errorCount = 0;

    public static void main(String[] args) {
        Phase1.run();
        Phase2.run();
        Phase3.run();
    }
    
    public static void run() {
        testPhase3();
    }
    
    private static void testPhase3() {
        System.out.println("\nTesting Phase 3");
        hypeerweb.clear();
        errorCount = 0;
        
        boolean deletionError = false;
        System.out.print("    ");
        
        // For every web of size 2 to 32...(the specs say we can always assume 2 nodes in the web)
        for (int size = 2; size <= HYPEERWEB_SIZE; size++) {
            
        	if(VERBOSE) {
                System.out.println("Testing Node Deletion on all nodes with a HyPeerWeb size of " + size);
            } else {
                System.out.print(size + " ");
            }

        	// Test delete on every node in the web...
        	for (int deleteNodeId = 0; deleteNodeId < size; deleteNodeId++) {

        		createHyPeerWebWith(size);
        		Node deleteNode = hypeerweb.getNode(deleteNodeId);
        		hypeerweb.removeFromHyPeerWeb(deleteNode);

        		// Verify the size of web is one less than before
        		if(hypeerweb.size() != size - 1) {
        			System.err.println("After removing node, websize should be "
        					+ (size - 1) + " but was " + hypeerweb.size());
        		}

        		// Verify all the nodes in the remaining web
        		for(int i = 0; i < size - 1; i++) {

        			SimplifiedNodeDomain actual = hypeerweb.getNode(i).constructSimplifiedNodeDomain();
        			ExpectedResult expected = new ExpectedResult(size - 1, i);

        			if ( ! actual.equals(expected)) {
        				deletionError = true;
        				printErrorMessage(size, deleteNode, actual, expected);
        			}

        		}
            }
        }
        
        if(!VERBOSE){
        	System.out.println();
        }

        if (deletionError) {
        	double decimalPercent = ((double)PHASE_3_TEST_COUNT - (double) errorCount) / (double) PHASE_3_TEST_COUNT;
        	int integerPercent = (int) (decimalPercent * 100.0d);
        	System.out.println("    Deletion Error: Phase 3 Score = " + integerPercent + "%");
        } else {
        	System.out.println("    No Deletion Errors: Phase 3 Score = 100%");
        }
    }

    private static void printErrorMessage(int size, Node deleteNode, SimplifiedNodeDomain actual, ExpectedResult expected) {
    	errorCount++;

    	System.out.println("-------------------------------------------------------------");
    	System.out.println("DELETION ERROR when HyPeerWebSize = " + size + ", Trying to delete: " + deleteNode);
    	System.out.println("Node Information:");
    	System.out.println(actual);
    	System.out.println();
    	System.out.println("Expected Information");
    	System.out.println(expected);
    }

    private static void createHyPeerWebWith(int numberOfNodes) {
    	hypeerweb.clear();
    	Node node0 = new Node(0);
    	hypeerweb.addToHyPeerWeb(node0, null);

    	for (int i = 1; i < numberOfNodes; i++) {
    		Node node = new Node(0);
    		hypeerweb.addToHyPeerWeb(node, node0);
    	}
    }
}