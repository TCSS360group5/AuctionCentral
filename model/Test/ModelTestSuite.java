package model.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AuctionModelTest.class,
	BidderModelTest.class,
	CalendarModelTest.class,
	ItemModelTest.class,
	NonProfitModelTest.class,
	UserModelTest.class
	})
public class ModelTestSuite
{

}
