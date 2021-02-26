package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

// init(initialized) is first, then isFinished goes before execute method

public class TurnLeft90DegreesCommand extends BaseCommand {
    
    DriveSubsystem drive; // write class-level variables here
    double leftPower;
    double speed;
    double GoalYaw;
    double currentYaw;
    double oldYaw;
    PoseSubsystem pose;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }
    
    @Override
    public void initialize() { 
        GoalYaw = pose.getCurrentFieldPose().getHeading().clone().shiftValue(90).getValue(); // keeps the goalyaw between -180 and 180
    }
    
    @Override
    public void execute() { // the main part of the code 
        currentYaw = pose.getCurrentFieldPose().getHeading().getValue();
        double speed = currentYaw - oldYaw;

        double GoalDistYaw = Math.abs(GoalYaw - currentYaw);  // the distance between the Goal and Current Yaw

        leftPower = GoalDistYaw/90 * 5 - speed*1; // the closer you get, the power goes lower | subtracted by the velocity

        drive.tankDrive(-leftPower, leftPower);
        
        oldYaw = currentYaw;
    }

    public boolean isFinished(){ //suggestion: need to add an isFinished here?
        boolean nearGoal = Math.abs( pose.getCurrentFieldPose().getHeading().getValue() - GoalYaw) < 0.7;
        boolean movingSlow = Math.abs(speed) < 0.6;

       if(nearGoal && movingSlow){
            return true;
       }

        return false;
    }
    
    public static double To90Angle(double Angle){ // for HelpTurnLeftTest
        if(Math.abs(Angle) > 180){
            return((Angle % 180) - 180);
        }
        return Angle;
    }

}

