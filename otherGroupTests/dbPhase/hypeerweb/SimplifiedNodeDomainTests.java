package dbPhase.hypeerweb;

import static org.junit.Assert.*;

import hypeerweb.node.SimplifiedNodeDomain;

import java.sql.ResultSet;
import java.util.HashSet;


import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class SimplifiedNodeDomainTests {

	/**
	 * Ensures that constructor initializes as expected
	 */
	@Test
	public void testConstructor() {
		SimplifiedNodeDomain nodeDomain = new SimplifiedNodeDomain(0, 0, 0, 0, 0, 0);
		assertTrue(nodeDomain.getWebId() == 0);
		assertTrue(nodeDomain.getHeight() == 0);
		assertTrue(nodeDomain.getFold() == 0);
		assertTrue(nodeDomain.getState() == 0);
		assertTrue(nodeDomain.getInverseSurrogateFold() == 0);
		assertTrue(nodeDomain.getSurrogateFold() == 0);
		assertTrue(nodeDomain.getNeighbors() == null);
		assertTrue(nodeDomain.getUpPointers() == null);
		assertTrue(nodeDomain.getDownPointers() == null);
	}
	
	/**
	 * Black Box test to make sure that distances are measured properly 
	 */
	@Test
	public void testDistanceTo() {
		assertTrue(SimplifiedNodeDomain.distanceTo(0, 0) == 0);
		assertTrue(SimplifiedNodeDomain.distanceTo(0, 1) == 1);
		assertTrue(SimplifiedNodeDomain.distanceTo(0, 2) == 1);
		assertTrue(SimplifiedNodeDomain.distanceTo(0, 4) == 1);
		assertTrue(SimplifiedNodeDomain.distanceTo(0, 3) == 2);
		assertTrue(SimplifiedNodeDomain.distanceTo(0, 7) == 3);
		
		assertTrue(SimplifiedNodeDomain.distanceTo(7, 7) == 0);
		assertTrue(SimplifiedNodeDomain.distanceTo(7, 6) == 1);
		assertTrue(SimplifiedNodeDomain.distanceTo(7, 5) == 1);
		assertTrue(SimplifiedNodeDomain.distanceTo(7, 3) == 1);
		assertTrue(SimplifiedNodeDomain.distanceTo(7, 4) == 2);
		assertTrue(SimplifiedNodeDomain.distanceTo(7, 0) == 3);
	}
	
	/**
	 * Black Box test to make sure that closer nodes can be found if they exist
	 */
	@Test
	public void testContainsCloserNode() {
		HashSet<Integer> neighbors = new HashSet<Integer>();
		for (int i = 1; i < 32; i++) {
			neighbors.add(i);
		}
		
		SimplifiedNodeDomain nodeDomain = new SimplifiedNodeDomain(0, 5, neighbors, new HashSet<Integer>(), new HashSet<Integer>(), 0, 0, 0, 0);
		assertTrue(nodeDomain.containsCloserNode(0, 31));
		
		nodeDomain = new SimplifiedNodeDomain(0, 5, new HashSet<Integer>(), neighbors, new HashSet<Integer>(), 0, 0, 0, 0);
		assertTrue(nodeDomain.containsCloserNode(0, 31));
		
		nodeDomain = new SimplifiedNodeDomain(0, 5, new HashSet<Integer>(), new HashSet<Integer>(), neighbors, 0, 0, 0, 0);
		assertTrue(nodeDomain.containsCloserNode(0, 31));
		
		nodeDomain = new SimplifiedNodeDomain(0, 5, new HashSet<Integer>(), new HashSet<Integer>(), new HashSet<Integer>(), 31, 0, 0, 0);
		assertTrue(nodeDomain.containsCloserNode(0, 31));
		
		nodeDomain = new SimplifiedNodeDomain(0, 5, new HashSet<Integer>(), new HashSet<Integer>(), new HashSet<Integer>(), 0, 31, 0, 0);
		assertTrue(nodeDomain.containsCloserNode(0, 31));
		
		nodeDomain = new SimplifiedNodeDomain(0, 5, new HashSet<Integer>(), new HashSet<Integer>(), new HashSet<Integer>(), 0, 0, 31, 0);
		assertTrue(nodeDomain.containsCloserNode(0, 31));
	}
	
	/**
	 * Black Box test on the equals method
	 */
	@Test
	public void testEquals() {
		HashSet<Integer> neighbors = new HashSet<Integer>();
		for (int i = 1; i < 32; i++) {
			neighbors.add(i);
		}
		
		SimplifiedNodeDomain domain1 = new SimplifiedNodeDomain(0, 0, neighbors, neighbors, neighbors, 0, 0, 0, 0);
		SimplifiedNodeDomain domain2 = new SimplifiedNodeDomain(0, 0, neighbors, neighbors, neighbors, 0, 0, 0, 0);
		
		assertTrue(domain1.equals(domain2));
	}
	
	/**
	 * Black Box test on the to string methods
	 */
	@Test
	public void testToString() {
		HashSet<Integer> neighbors = new HashSet<Integer>();
		for (int i = 1; i < 32; i++) {
			neighbors.add(i);
		}
		SimplifiedNodeDomain domain1 = new SimplifiedNodeDomain(0, 0, neighbors, neighbors, neighbors, 0, 0, 0, 1);
		String string = domain1.toString();
		String expectedResults = "Node:                   0\nHeight:                 0\nNeighbors:              1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31\nUpPointers:             1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31\nDownPointers:           1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31\nFold:                   0\nSurrogate Fold:         0\nInverse Surrogate Fold: 0\nState: Standard Node State";
		assertTrue(string.equals(expectedResults));
	}
	
	/**
	 * Simple test to insure setters are working properly
	 */
	@Test
	public void testSetters() {
		HashSet<Integer> neighbors = new HashSet<Integer>();
		for (int i = 1; i < 32; i++) {
			neighbors.add(i);
		}
		
		SimplifiedNodeDomain nodeDomain = new SimplifiedNodeDomain(0, 0, 0, 0, 0, 0);
		assertTrue(nodeDomain.getDownPointers() == null);
		assertTrue(nodeDomain.getUpPointers() == null);
		assertTrue(nodeDomain.getNeighbors() == null);
		
		nodeDomain.setNeighbors(neighbors);
		assertTrue(nodeDomain.getNeighbors() == neighbors);
		
		nodeDomain.setDownPointers(neighbors);
		assertTrue(nodeDomain.getDownPointers() == neighbors);
		
		nodeDomain.setUpPointers(neighbors);
		assertTrue(nodeDomain.getUpPointers() == neighbors);
	}
}
