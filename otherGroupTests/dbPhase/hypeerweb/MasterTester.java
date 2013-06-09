package dbPhase.hypeerweb;

import hypeerweb.broadcast.BroadcastVisitorBlackBoxTests;
import hypeerweb.broadcast.ContentsBlackBoxTests;
import hypeerweb.broadcast.ParametersBlackBoxTests;
import hypeerweb.broadcast.SendVisitorBlackBoxTests;
import junit.framework.TestCase;

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
	NodeStateTests.class,
	HyPeerWebDatabaseTests.class,
	HyPeerWebTests.class,
	
	BroadcastVisitorBlackBoxTests.class,
	SendVisitorBlackBoxTests.class,
	ContentsBlackBoxTests.class,
	ParametersBlackBoxTests.class,
	NodeBlackBoxTests.class,
	
	//NZWhiteBoxConnectionsTests.class,
	SimplifiedNodeDomainTests.class
})

public final class MasterTester extends TestCase{
}
