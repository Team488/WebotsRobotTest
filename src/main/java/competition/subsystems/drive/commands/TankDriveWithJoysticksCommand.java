package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;

public class TankDriveWithJoysticksCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;
    int i;

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
        i++;
        if (i % 25 == 0) {
            log.info("L:"+oi.gamepad.getLeftVector().y+",R:"+oi.gamepad.getRightVector().y);
        }
        driveSubsystem.tankDrive(
            oi.gamepad.getLeftVector().y, 
            oi.gamepad.getRightVector().y);
    }
}
