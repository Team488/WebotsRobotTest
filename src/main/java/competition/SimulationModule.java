package competition;

import org.junit.Ignore;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.injection.SimulatorModule;
import xbot.common.subsystems.pose.BasePoseSubsystem;

@Ignore
public class SimulationModule extends SimulatorModule {

    @Override
    protected void configure() {
        super.configure();

        this.bind(BasePoseSubsystem.class).to(PoseSubsystem.class);
    }
}