package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;

public class PauseDriveCommand extends BaseCommand{
    
    DriveSubsystem drive;

    @Inject
    public PauseDriveCommand(DriveSubsystem driveSub){
        this.drive = driveSub;
    }

    @Override
    public void initialize(){
        
        drive.setScalingFactor(0);

    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished(){

        if(drive.getScalingFactor() == 0){
            drive.setScalingFactor(0.25);
            return true;
        }

        return false;
        
    }

}
