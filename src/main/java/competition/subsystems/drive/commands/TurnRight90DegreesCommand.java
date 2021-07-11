package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.math.PIDFactory;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

// make P and D setters
// make a way to set to turn right 90 mode?

public class TurnRight90DegreesCommand extends BaseCommand {
    
    DriveSubsystem drive;
    PIDManager pid;
    double goalyaw;
    PoseSubsystem pose;
    DoubleProperty goalangleprop;

    @Inject
    public TurnRight90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDFactory pf, PropertyFactory propf) {
        this.drive = driveSubsystem;
        this.pose = pose;
        this.pid = pf.createPIDManager("TurnLeft90");
        propf.setPrefix(this);
        this.goalangleprop = propf.createEphemeralProperty("Goal Angle", 0);

        pid.setEnableErrorThreshold(true);
        pid.setErrorThreshold(1);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.1);

        // manually adjust these values to adjust the action
        pid.setP(0.02);
        pid.setD(0.0);
    }
    
    @Override
    public void initialize() { 
        goalyaw = pose.getCurrentFieldPose().getHeading().clone().shiftValue(-90).getValue(); // keeps the goalyaw between -180 and 180
        goalangleprop.set(goalyaw);
        pid.reset();
    }
    
    @Override
    public void execute() {
        double currentError = pose.getCurrentFieldPose().getHeading().difference(goalyaw);
        double power = -pid.calculate(0, currentError);
        drive.tankDrive(-power, power);
    }

    public boolean isFinished(){ 
        return pid.isOnTarget();
    }

}
