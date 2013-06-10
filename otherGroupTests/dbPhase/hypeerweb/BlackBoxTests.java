package dbPhase.hypeerweb;



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
	SendVisitorBlackBoxTests.class,
	BroadcastVisitorBlackBoxTests.class,
	ParametersBlackBoxTests.class,
	ContentsBlackBoxTests.class,
	NodeBlackBoxTests.class
})

public final class BlackBoxTests extends TestCase{
}
