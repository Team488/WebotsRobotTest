package competition.subsystems.drive.commands;

import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.simulation.WebotsClient;

import xbot.common.command.BaseCommand;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;


public class DriveForwardCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;
    final WebotsClient webots;
    final PIDManager pid;

    private double leftEncoder = 0;
    private double rightEncoder = 0;
    private double goalDistance = 0;
    private double encoder = 0;
    



    @Inject
    public DriveForwardCommand(OperatorInterface oi, DriveSubsystem driveSubsystem, WebotsClient webots, PIDFactory pf) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.webots = webots;
        this.addRequirements(driveSubsystem);

        this.pid = pf.createPIDManager("DriveToPosition");
        pid.setEnableErrorThreshold(true);
        pid.setErrorThreshold(0.1);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.1);
    }
    
    @Override
    public void initialize() {
        // pid.setP(0.5);
        // pid.setD(1);
        pid.reset();
        log.info("Initializing");
    }

    @Override
    public void execute() {
        // double power = pid.calculate(goalDistance, driveSubsystem.getPosition();
        double power = pid.calculate(goalDistance, driveSubsystem.leftLeader.getSelectedSensorPosition(0));
        driveSubsystem.tankDrive(power, power);

    }

    public boolean isFinished() {
        return pid.isOnTarget();

    }

    public void setDistance(double distance) {
        this.goalDistance = distance;
    }

    
}
