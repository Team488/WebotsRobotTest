package competition;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Ignore;

import xbot.common.injection.BaseWPITest;

@Ignore
public class CompetitionTest extends BaseWPITest {

    @Override
    protected Injector createInjector() {
        return Guice.createInjector(new CompetitionTestModule());
    }
    
}