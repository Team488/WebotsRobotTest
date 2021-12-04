package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.CircleDriveCommand;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.math.FieldPose;
import xbot.common.simulation.ResetSimulatorPositionCommand;
import xbot.common.subsystems.drive.RabbitPoint;
import xbot.common.subsystems.drive.RabbitPoint.PointTerminatingType;
import xbot.common.subsystems.drive.RabbitPoint.PointType;
import xbot.common.subsystems.drive.SimulatedPurePursuitCommand;
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
        SimulatedPurePursuitCommand pursuit,
        Provider<CircleDriveCommand> circleDriveSupplier,
        ArcadeDriveCommand aracdeDrive
    ) {
        FieldPose slalomStart = new FieldPose(150, 38, PoseSubsystem.FACING_AWAY_FROM_DRIVERS); 
        resetToStartOfSlalom.setTargetPose(slalomStart);
        resetToCenter.setTargetPose(new FieldPose(80, 150, PoseSubsystem.FACING_AWAY_FROM_DRIVERS));

        operatorInterface.gamepad.getifAvailable(2).whenPressed(aracdeDrive);
        operatorInterface.gamepad.getifAvailable(3).whenPressed(resetToStartOfSlalom);
        resetToCenter.includeOnSmartDashboard();

        pursuit.addPoint(new RabbitPoint(new FieldPose(122, 96, 135), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(93, 180, 90), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(120, 268, 45), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(150, 300, 90), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(120, 330, 180), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(95, 300, -90), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(120, 268, -45), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(150, 180, -90), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(122, 88, -135), PointType.PositionAndHeading, PointTerminatingType.Continue));
        pursuit.addPoint(new RabbitPoint(new FieldPose(90, 30, -90), PointType.PositionAndHeading, PointTerminatingType.Stop));

        CircleDriveCommand step1 = circleDriveSupplier.get();
        step1.setGoalPose(new FieldPose(122, 92, 160));
        CircleDriveCommand step2 = circleDriveSupplier.get();
        step2.setGoalPose(new FieldPose(93, 180, 90));
        CircleDriveCommand step3 = circleDriveSupplier.get();
        step3.setGoalPose(new FieldPose(120, 268, 45));
        CircleDriveCommand step4 = circleDriveSupplier.get();
        step4.setGoalPose(new FieldPose(150, 300, 90));

        SequentialCommandGroup circleRoute = new SequentialCommandGroup(step1, step2, step3, step4);
        operatorInterface.gamepad.getifAvailable(4).whenPressed(circleRoute);
    }
}
