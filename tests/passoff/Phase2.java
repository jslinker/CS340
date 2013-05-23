package passoff;

import hypeerweb.HyPeerWeb;
import node.ExpectedResult;
import node.Node;
import node.SimplifiedNodeDomain;

public class Phase2 {
    public static HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
    private static final int PHASE_2_TEST_COUNT = 10912;
    private static int errorCount = 0;

    public static void main(String[] args) {
        Phase1.run();
        Phase2.run();
    }
    
    public static void run() {
        testPhase2();
    }
    
    static private void testPhase2() {
        System.out.println("\nTesting Phase 2");
        hypeerweb.clear();
        errorCount = 0;
        
        boolean insertionError = false;
        System.out.print("    ");
        
        for (int size = 1; size <= HYPEERWEB_SIZE; size++) {
            if(VERBOSE) {
                System.out.println("Testing Node Insertion on HyPeerWeb of size " + size + "/" + HYPEERWEB_SIZE);
            } else {
                System.out.print(size + " ");
            }
            
            hypeerweb.clear();
            Node node0 = new Node(0);
            hypeerweb.addToHyPeerWeb(node0, null);
            Node firstNode = hypeerweb.getNode(0);
            SimplifiedNodeDomain simplifiedNodeDomain = firstNode.constructSimplifiedNodeDomain();
            ExpectedResult expectedResult = new ExpectedResult(1, 0);

            if (!simplifiedNodeDomain.equals(expectedResult)) {
                insertionError = true;
                printErrorMessage(size, null, simplifiedNodeDomain, expectedResult);
            }
            
            for (int startNodeId = 0; startNodeId < size - 1; startNodeId++) {
                createHyPeerWebWith(size - 1);
                Node node = new Node(0);
                Node startNode = hypeerweb.getNode(startNodeId);
                hypeerweb.addToHyPeerWeb(node, startNode);
                
                for (int i = 0; i < size; i++) {
                	
                    Node nodei = hypeerweb.getNode(i);
                    simplifiedNodeDomain = nodei.constructSimplifiedNodeDomain();
                    expectedResult = new ExpectedResult(size, i);

                    if (!simplifiedNodeDomain.equals(expectedResult)) {
                        insertionError = true;
                        printErrorMessage(size, startNode, simplifiedNodeDomain, expectedResult);
                    }
                }
            }
        }
        if(!VERBOSE){
            System.out.println();
        }
        
        if (insertionError) {
            double decimalPercent = ((double)PHASE_2_TEST_COUNT - (double) errorCount) / (double) PHASE_2_TEST_COUNT;
            int integerPercent = (int) (decimalPercent * 100.0d);
            System.out.println("    Insertion Error: Phase 2 Score = " + integerPercent + "%");
        } else {
            System.out.println("    No Insertion Errors: Phase 2 Score = 100%");
        }
    }
    
    private static void printErrorMessage(int size, Node startNode, SimplifiedNodeDomain simplifiedNodeDomain, ExpectedResult expectedResult) {
        errorCount++;

        System.out.println("-------------------------------------------------------------");
        System.out.println("INSERTION ERROR when HyPeerWebSize = " + size + ", Start Location: " + startNode);
        System.out.println("Node Information:");
        System.out.println(simplifiedNodeDomain);
        System.out.println();
        System.out.println("Expected Information");
        System.out.println(expectedResult);
    }

    static private void createHyPeerWebWith(int numberOfNodes) {
        hypeerweb.clear();
        Node node0 = new Node(0);
        hypeerweb.addToHyPeerWeb(node0, null);

        for (int i = 1; i < numberOfNodes; i++) {
            Node node = new Node(0);
            node0.addToHyPeerWeb(node);
        }
    }
    
    private static final int HYPEERWEB_SIZE = 32;
    private static final boolean VERBOSE = true;
}
