package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.autonomous.SlalomAutonomousPathCommand;
import competition.subsystems.drive.commands.DriveToPositionCommand;
import competition.subsystems.drive.commands.PauseDriveCommand;
import competition.subsystems.drive.commands.TurnCommand;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.controls.sensors.XXboxController.XboxButton;
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
    
    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).whenPressed(resetHeading);
    }

    @Inject
    public void setupSimulationCommands(
        OperatorInterface operatorInterface,
        ResetSimulatorPositionCommand resetToCenter,
        ResetSimulatorPositionCommand resetToStartOfSlalom,
        TurnCommand turn,
        DriveToPositionCommand driveToPosition,
        PauseDriveCommand pause,
        SlalomAutonomousPathCommand slalomAuto
    ) {
        FieldPose slalomStart = new FieldPose(150, 38, PoseSubsystem.FACING_AWAY_FROM_DRIVERS); 
        resetToStartOfSlalom.setTargetPose(slalomStart);
        resetToCenter.setTargetPose(new FieldPose(80, 150, PoseSubsystem.FACING_AWAY_FROM_DRIVERS));

        operatorInterface.gamepad.getifAvailable(XboxButton.A).whileHeld(pause);
        operatorInterface.gamepad.getifAvailable(XboxButton.B).whenPressed(resetToCenter);
        operatorInterface.gamepad.getifAvailable(XboxButton.X).whenPressed(resetToStartOfSlalom);
        operatorInterface.gamepad.getifAvailable(XboxButton.Y).whenPressed(turn);
        operatorInterface.gamepad.getifAvailable(XboxButton.RightBumper).whileHeld(slalomAuto);

        resetToCenter.includeOnSmartDashboard();
    }
}
