package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.commandgroups.SlalomPathCommand;
import competition.simulation.ResetPositionCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.FieldPose;
import xbot.common.math.XYPair;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {
    
    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading, 
            ResetPositionCommand setSlalomPositionCommand,
            SlalomPathCommand slalomPathCommand)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).whenPressed(resetHeading);


        FieldPose slalomStart = new FieldPose(new XYPair(1.4, -3.5), new ContiguousHeading(0)); 
        setSlalomPositionCommand.setTargetPose(slalomStart);
        setSlalomPositionCommand.includeOnSmartDashboard("Set Slalom Command");

        SmartDashboard.putData(slalomPathCommand);

 
    }

    @Inject
    public void setupSimulationCommands(
        OperatorInterface operatorInterface,
        ResetPositionCommand resetToCenter,
        ResetPositionCommand resetToStartOfSlalom
    ) {
        FieldPose slalomStart = new FieldPose(new XYPair(1.4, -3.5), new ContiguousHeading(0)); 
        resetToStartOfSlalom.setTargetPose(slalomStart);

        operatorInterface.gamepad.getifAvailable(2).whenPressed(resetToCenter);
        operatorInterface.gamepad.getifAvailable(3).whenPressed(resetToStartOfSlalom);
        resetToCenter.includeOnSmartDashboard();
    }
}
