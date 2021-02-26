package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    double targetGoal; // target goal
    double currentPos; // class level variable
    double speed;
    double oldPos;
    PoseSubsystem pose;
    
    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }
    
    public void setTargetPosition(double position) {
        targetGoal = position; // basically sets the target position and stores it as a value
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
    }
    
    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position 
        // - Hint: use drive.distanceSensor.get() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the target position
        currentPos = drive.getTotalDistanceFromSensors(); //TODO: USE FROM POSE SUBSYSTEM RIGHTLEADER AND LEFT LEADER
        speed = oldPos - currentPos;
        
        double power = 0;
        if(currentPos > targetGoal){ // makes the blue circle move and tracks the loops it goes through
            power = -0.3;
        }
        else{
            power = 0.6;
        }
        drive.tankDrive(power, power);
        // How you do this is up to you. If you get stuck, ask a mentor or student for some hints!
        
        oldPos = currentPos; // overrites the position after it moves onto a new pos
    }
    
    @Override
    public boolean isFinished() {
        boolean nearGoal = Math.abs(currentPos - targetGoal) < 0.1;  // if the difference is less than 0.1, then true
        boolean movingSlow = Math.abs(speed) < 0.2; // if current speed is < 0.2, then true
        // the decimal numbers = the velocity/speed


        if(nearGoal && movingSlow){ 
            return true;
        }
        /* Modify this to RETURN TRUE once you have met your goal, 
         and you're moving fairly slowly (ideally stopped) */
         
        return false;
    }
}
