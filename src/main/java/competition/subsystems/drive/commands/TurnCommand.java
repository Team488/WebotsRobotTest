package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.math.PIDFactory;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnCommand extends BaseCommand {
    
    DriveSubsystem drive;
    PIDManager pid;
    double goalyaw;
    PoseSubsystem pose;
    DoubleProperty goalangleprop;
    DoubleProperty shiftMagnitude; // raw goal value from shiftValue function

    @Inject
    public TurnCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDFactory pf, PropertyFactory propf){
        this.drive = driveSubsystem;
        this.pose = pose;
        this.pid = pf.createPIDManager("Turn");
        propf.setPrefix(this);
        this.shiftMagnitude = propf.createEphemeralProperty("Shift Magnitude", 0);
        this.goalangleprop = propf.createEphemeralProperty("Goal Angle", 0);

        pid.setEnableErrorThreshold(true);
        pid.setErrorThreshold(1);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.1);

        // manually adjust these values to adjust the action
        pid.setP(0.024);
        pid.setD(0.0);
        pid.setMaxOutput(0.5);
    }

    public void setTargetGoal(DoubleProperty degrees){ // 90 - left -90 -right
        shiftMagnitude = degrees;
    }

    @Override
    public void initialize() {
        log.info("Initializing Turn");
        
        goalyaw = shiftMagnitude.get(); //pose.getCurrentFieldPose().getHeading().clone().shiftValue(shiftMagnitude.get()).getValue(); // keeps the goalyaw between -180 and 180
        goalangleprop.set(goalyaw);
        pid.reset();
    }

    @Override
    public void execute() {
        double currentError = pose.getCurrentFieldPose().getHeading().difference(goalyaw);
        double power = -pid.calculate(0, currentError);
        drive.tankDrive(-power + 1.0, power + 1.0); // 0.5 makes it arc turn
    }

    public boolean isFinished(){
        // boolean check = pid.isOnTarget();
        
        // if(check){
        //     log.info("End Turn");
        // }

        return pid.isOnTarget();
    }

}
