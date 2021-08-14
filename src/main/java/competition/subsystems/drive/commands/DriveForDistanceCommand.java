package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveForDistanceCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pos;
    DoubleProperty targetDistance;
    double targetGoal;
    double power = 1.0;

    @Inject
    public DriveForDistanceCommand(DriveSubsystem driveSubsystem, PoseSubsystem pos) {
        this.drive = driveSubsystem;
        this.pos = pos;
    }
    
    public void setTargetPosition(DoubleProperty position) {
        targetDistance = position; // basically sets the target position and stores it as a value
    }
    
    @Override
    public void initialize() {
        targetGoal = pos.totalDriveDistance() + targetDistance.get();
        log.info("Initializing Drive" + targetGoal);
    }

    @Override
    public void execute() {
 
        // if(targetDistance.get() == 100){ //should increase the speed TODO
        //     drive.tankDrive(power, power); 
        // }

        drive.tankDrive(power, power);
    }
    
    @Override
    public boolean isFinished() {
        double currentPosition = pos.totalDriveDistance();
        boolean check = currentPosition >= targetGoal;

        if(check){
            log.info("End DriveForDistance " + targetGoal);
            
        }

        return check;
    }
}