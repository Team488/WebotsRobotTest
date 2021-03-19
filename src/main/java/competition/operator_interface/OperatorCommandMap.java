package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.SlalomPathAutonomousCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import xbot.common.command.SmartDashboardCommandPutter;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.FieldPose;
import xbot.common.math.XYPair;
import xbot.common.simulation.ResetSimulatorPositionCommand;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;


/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {
    
    @Inject
    SmartDashboardCommandPutter commandPutter;

    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading,
            SlalomPathAutonomousCommand slalam)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).whenPressed(resetHeading);
        SmartDashboard.putData(slalam);
    }

    @Inject
    public void setupSimulationCommands(
        OperatorInterface operatorInterface,
        ResetSimulatorPositionCommand resetToCenter,
        ResetSimulatorPositionCommand resetToStartOfSlalom
    ) {
        FieldPose slalomStart = new FieldPose(150, 38, PoseSubsystem.FACING_AWAY_FROM_DRIVERS); 
        resetToStartOfSlalom.setTargetPose(slalomStart);
        resetToCenter.setTargetPose(new FieldPose(80, 150, PoseSubsystem.FACING_AWAY_FROM_DRIVERS));

        operatorInterface.gamepad.getifAvailable(2).whenPressed(resetToCenter);
        operatorInterface.gamepad.getifAvailable(3).whenPressed(resetToStartOfSlalom);
        resetToCenter.includeOnSmartDashboard();
    }
}
