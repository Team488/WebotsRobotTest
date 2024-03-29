package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;
import xbot.common.math.XYPair;

public class TankDriveWithJoysticksCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;

    @Inject
    public TankDriveWithJoysticksCommand(OperatorInterface oi, DriveSubsystem driveSubsystem) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(this.driveSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        driveSubsystem.tankDrive(
            MathUtils.deadband(oi.gamepad.getLeftVector().y, 0.15), 
            MathUtils.deadband(oi.gamepad.getRightVector().y, 0.15)
        );
/*
        double y = MathUtils.deadband(oi.gamepad.getLeftVector().y, 0.15);
        double w = MathUtils.deadband(oi.gamepad.getRightVector().x, 0.15);
*/
        //driveSubsystem.drive(new XYPair(0, y), w);
    }
}
