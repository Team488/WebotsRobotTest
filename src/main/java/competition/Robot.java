
package competition;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import edu.wpi.first.hal.sim.DriverStationSim;
import xbot.common.command.BaseRobot;
import xbot.common.math.FieldPose;
import xbot.common.subsystems.pose.BasePoseSubsystem;

public class Robot extends BaseRobot {

    DriverStationSim dsSim;

    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
        this.injector.getInstance(VisionSubsystem.class);
    }

    @Override
    protected void setupInjectionModule() {
        if (BaseRobot.isReal()) {
            this.injectionModule = new CompetitionModule(true);
        } else {
            this.injectionModule = new TemplateSimulationModule();
        }
    }

    @Override
    public void simulationInit() {
        super.simulationInit();

        var ds = new DriverStationSim();
        ds.setEnabled(true);
        
        webots.setFieldPoseOffset(
            new FieldPose(
                -2.33*PoseSubsystem.INCHES_IN_A_METER, 
                -4.58*PoseSubsystem.INCHES_IN_A_METER,
                BasePoseSubsystem.FACING_TOWARDS_DRIVERS
                )
        );
    }
}
