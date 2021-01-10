package competition;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.injection.UnitTestModule;
import xbot.common.subsystems.pose.BasePoseSubsystem;


public class TemplateSimulationModule extends UnitTestModule {

    boolean isPractice;

    public TemplateSimulationModule() {
        this.isPractice = true;
    }
    
    @Override
    protected void configure() {
        super.configure();
        this.bind(BasePoseSubsystem.class).to(PoseSubsystem.class);
    }
}
