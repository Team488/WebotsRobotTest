package competition;

import org.junit.Test;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;

public class RobotInitTest extends CompetitionTest {
    @Test
    public void testDefaultSystem() {
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
    }
}