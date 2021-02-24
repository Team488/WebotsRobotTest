package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.subsystems.drive.control_logic.HeadingModule;

public class SpinCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final PoseSubsystem pose;
    PIDManager pid;
    double angle;
    HeadingModule headingModule;

    @Inject
    public SpinCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, CommonLibFactory clf, PIDFactory pf) {
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(this.driveSubsystem);
        this.pose = pose;
        this.pid = pf.createPIDManager("Rotate");
        this.headingModule = clf.createHeadingModule(pid); 

        pid.setEnableErrorThreshold(true); 
        pid.setErrorThreshold(0.1);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.1);
    
        pid.setP(0.01);
        pid.setD(0.02);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        
    }

    @Override
    public void execute() {
        
        double power = headingModule.calculateHeadingPower(angle);

        driveSubsystem.tankDrive(-power, power);
        
    }

    public void setAngle(double angle) {

        this.angle = angle;

    }

    @Override
    public boolean isFinished() {
       
        return pid.isOnTarget();
        
    }

}
