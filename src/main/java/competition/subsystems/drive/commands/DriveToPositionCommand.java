package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;

// make P and D setters
// make a function to set distance? (find where distance is set)

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pos;
    PIDManager pid;
    double targetDistance;
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
        // D = 0.0 P = 0.1
        // only increase D SLIGHTLY, too much can cause it to 'rock' back and forth
        pid.setP(0.045);
        pid.setD(0.0);
        pid.setMaxOutput(1);
    }
    
    public void setTargetPosition(double position) {
        targetDistance = position; // basically sets the target position and stores it as a value
    }
    
    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        targetGoal = pos.totalDriveDistance() + targetDistance;
        log.info("Initializing Drive" + targetGoal);
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
        boolean check = pid.isOnTarget();

        if(check){
            log.info("End Drive " + pos.totalDriveDistance());
        }

        return check;
    }
}