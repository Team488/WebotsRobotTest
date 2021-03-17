package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.math.PIDFactory;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {
    
    DriveSubsystem drive;
    PIDManager pid;
    double leftPower;
    double speed;
    double GoalYaw;
    double currentYaw;
    double oldYaw;
    PoseSubsystem pose;
    DoubleProperty goalangleprop;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDFactory pf, PropertyFactory propf) {
        this.drive = driveSubsystem;
        this.pose = pose;
        this.pid = pf.createPIDManager("TurnLeft90");
        propf.setPrefix(this);
        this.goalangleprop = propf.createEphemeralProperty("Goal Angle", 0);

        pid.setEnableErrorThreshold(true); // Turn on distance checking
        pid.setErrorThreshold(1);
        pid.setEnableDerivativeThreshold(true); // Turn on speed checking
        pid.setDerivativeThreshold(0.1);

        // manually adjust these values to adjust the action
        pid.setP(0.02);
        pid.setD(0.0);
    }
    
    @Override
    public void initialize() { 
        GoalYaw = pose.getCurrentFieldPose().getHeading().clone().shiftValue(90).getValue(); // keeps the goalyaw between -180 and 180
        goalangleprop.set(GoalYaw);
        pid.reset();
    }
    
    @Override
    public void execute() {
        double currentPosition = pose.getCurrentFieldPose().getHeading().difference(-(GoalYaw)); // TODO: use .difference function
        // do some math to find the smallest difference between two degrees aka shortest value out
        double power = pid.calculate(GoalYaw, currentPosition);
        drive.tankDrive(-power, power);
    }

    public boolean isFinished(){ 
        return pid.isOnTarget();
    }

}

