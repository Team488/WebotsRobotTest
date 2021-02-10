package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;
import xbot.common.math.XYPair;

public class ArcadeDriveCommand extends BaseCommand {

    DriveSubsystem drive;
    OperatorInterface oi;

    @Inject
    public ArcadeDriveCommand(DriveSubsystem drive, OperatorInterface oi) {
        this.drive = drive;
        this.oi = oi;
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        // Ok, first we need to get our inputs
        double rawX = oi.gamepad.getRightVector().x;
        double rawY = oi.gamepad.getLeftVector().y;

        // Scale and deadband them
        double transformedX = MathUtils.deadband(rawX, 0.15, (input) -> MathUtils.exponentAndRetainSign(input, 3));
        double transformedY = MathUtils.deadband(rawY, 0.15, (input) -> MathUtils.exponentAndRetainSign(input, 3));

        drive.move(
            new XYPair(0, transformedY), 
            transformedX
        );
    }
    
}