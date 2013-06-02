package hypeerweb;

import hypeerweb.broadcast.BroadcastVisitorBlackBoxTests;
import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run as JUnit Test. Runs all black box tests for the HyPeerWeb Project.
 * @author Craig Jacobson
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	BroadcastVisitorBlackBoxTests.class
})

public final class BlackBoxTests extends TestCase{
}
