package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pos;
    PIDManager pid;
    double targetGoal;
  
    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PIDFactory pf, PoseSubsystem pos) {
        this.drive = driveSubsystem;
        this.pid = pf.createPIDManager("DriveToPoint");
        this.pos = pos;

        pid.setEnableErrorThreshold(true); // Turn on distance checking
        pid.setErrorThreshold(0.1);
        pid.setEnableDerivativeThreshold(true); // Turn on speed checking
        pid.setDerivativeThreshold(0.1);

        // manually adjust these values to adjust the action
        pid.setP(0.2);
        pid.setD(0.5);
    }
    
    public void setTargetPosition(double position) {
        targetGoal = position; // basically sets the target position and stores it as a value
    }
    
    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        pid.reset();
    }

    @Override
    public void execute() {
        double currentPosition = pos.totalDriveDistance();
        double power = pid.calculate(targetGoal, currentPosition);
        drive.tankDrive(power, power);
    }
    
    @Override
    public boolean isFinished() {
        return pid.isOnTarget();
    }
}