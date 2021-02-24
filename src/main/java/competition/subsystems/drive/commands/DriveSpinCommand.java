package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.simulation.WebotsClient;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.math.XYPair;
import xbot.common.subsystems.drive.control_logic.HeadingModule;



public class DriveSpinCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    double initialPosition; 
    double distance;
    double goalAngle;
    double initialAngle;
    final PoseSubsystem pose;
    int i = 0;
    PIDManager pidD;
    PIDManager pidS;
    HeadingModule headingModule;

    @Inject
    public DriveSpinCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDFactory pf, CommonLibFactory clf) {
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(this.driveSubsystem);
        this.pose = pose;
        
        pidS = pf.createPIDManager("Rotate");
        headingModule = clf.createHeadingModule(pidS); 

        pidS.setEnableErrorThreshold(true); 
        pidS.setErrorThreshold(0.1);
        pidS.setEnableDerivativeThreshold(true);
        pidS.setDerivativeThreshold(0.1);
    
        pidS.setP(0.01);
        pidS.setD(0.02);


        pidD = pf.createPIDManager("DriveDistance");
        
        pidD.setEnableErrorThreshold(true); 
        pidD.setErrorThreshold(0.05);
        pidD.setEnableDerivativeThreshold(true);
        pidD.setDerivativeThreshold(0.05);
        
        pidD.setP(.5);
        pidD.setD(.5);
    }

    @Override
    public void initialize() {
        this.initialPosition = driveSubsystem.leftLeader.getSelectedSensorPosition(0) * 6.0 * Math.PI / 256.0;
        pidS.reset();
        pidD.reset();
        log.info("Initializing");
    }

    @Override
    public void execute() {
        // Gets the forward power
        double currentPosition = driveSubsystem.leftLeader.getSelectedSensorPosition(0) * 6.0 * Math.PI / 256.0;
        double powerForward = pidD.calculate(initialPosition+distance,currentPosition);
        double powerSpin;

        if (distance - Math.abs(initialPosition - currentPosition) < 2) {
            
            // attempts to change angle
            powerSpin = headingModule.calculateHeadingPower(goalAngle);

        } else {

            // maintains initial angle
            powerSpin = headingModule.calculateHeadingPower(initialAngle);

        }
        
        driveSubsystem.tankDrive(powerForward - powerSpin,powerForward + powerSpin);
        headingModule.calculateHeadingPower(goalAngle);
    }

    public void setDistanceAndAngle(double distance, double angle, double initialAngle) {

        this.distance = distance;
        this.goalAngle = angle;
        this.initialAngle = initialAngle;

    }

    

    @Override
    public boolean isFinished() {

        return pidS.isOnTarget();

        //double currentPosition = driveSubsystem.leftLeader.getSelectedSensorPosition(0) * 6.0 * Math.PI / 256.0;
        //return (0.1>Math.abs((Math.abs((currentPosition - initialPosition)) - distance)));
        
    }
}
 