package hypeerweb;

import junit.framework.TestCase;
import node.NodeTests;
import node.WebIdTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import utilities.BitManipulationTests;
import database.HyPeerWebDatabaseTests;

/**
 * Run as JUnit Test. Runs all of the tests for the HyPeerWeb Project.
 * @author Craig Jacobson
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	WebIdTests.class,
	BitManipulationTests.class,
	NodeTests.class, 
	HyPeerWebDatabaseTests.class,
	HyPeerWebTests.class
})

public final class MasterTester extends TestCase{
}
