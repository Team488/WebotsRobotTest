package competition.subsystems.drive.commands;

import java.util.ArrayList;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.drive.control_logic.HeadingModule;



public class DriveSpinCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    double initialPosition; 
    double distance;
    double goalAngle;
    double maintainingAngle;
    double currentPosition;
    PIDManager pidD;
    PIDManager pidS;
    HeadingModule headingModule;
    int i = 0;
    ArrayList<Double> pastPowerLeft = new ArrayList<Double>();
    ArrayList<Double> pastPowerRight = new ArrayList<Double>();
    DoubleProperty movingAverageSizeProp;
    DoubleProperty spinCapProp;
    DoubleProperty distanceTillRotate;
    PoseSubsystem pose;
    @Inject
    public DriveSpinCommand(DriveSubsystem driveSubsystem, PIDFactory pf, PoseSubsystem pose,  CommonLibFactory clf, PropertyFactory prof) {
        
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(this.driveSubsystem);

        this.pose = pose;

        // Creates PID for computing spinning power
        pidS = pf.createPIDManager("Rotate");
        headingModule = clf.createHeadingModule(pidS); 
        pidS.setEnableErrorThreshold(true); 
        pidS.setErrorThreshold(0.1);
        pidS.setEnableDerivativeThreshold(true);
        pidS.setDerivativeThreshold(0.1);
        pidS.setP(0.015);
        //pidS.setD(0.2);

        // Creates PID for computing foward power
        pidD = pf.createPIDManager("DriveDistance");
        pidD.setEnableErrorThreshold(true); 
        pidD.setErrorThreshold(0.1);
        pidD.setEnableDerivativeThreshold(false);
        pidD.setP(.1);
        //pidD.setD(.);

        prof.setPrefix(this);
        movingAverageSizeProp = prof.createPersistentProperty("Moving Average Size", 4);
        spinCapProp = prof.createPersistentProperty("Cap of Spin Power", 0.8);
        distanceTillRotate = prof.createPersistentProperty("Distance Remaining Till Rotate", 15);
    }

    @Override
    public void initialize() {
        
        this.initialPosition = driveSubsystem.leftLeader.getSelectedSensorPosition(0)  / 256.0;

        pastPowerLeft = new ArrayList<Double>();
        pastPowerRight = new ArrayList<Double>();

        for (int i = 0; i < (int)movingAverageSizeProp.get(); i++) {

            pastPowerLeft.add(0.0);
            pastPowerRight.add(0.0);
        }

        pidS.reset();
        pidD.reset();

        log.info("Initializing");
    }

    @Override
    public void execute() {

        currentPosition = (driveSubsystem.leftLeader.getSelectedSensorPosition(0) +driveSubsystem.leftLeader.getSelectedSensorPosition(0))/ (2*256.0);        

        // Gets the forward power (assumes the goal is 4 beyond the actual goal)
        double powerForward = pidD.calculate(initialPosition+distance+4,currentPosition);

        // Gets the spin power
        double powerSpin;

        // will change angle when it is distanceTillRotate away from goal
        if (distance - Math.abs(initialPosition - currentPosition) < distanceTillRotate.get()) { 

            powerSpin = headingModule.calculateHeadingPower(goalAngle); // attempts to change angle

        } else {

            powerSpin = headingModule.calculateHeadingPower(maintainingAngle); // maintains initial angle

        }
        
        // caps the spin power 
        if (powerSpin > spinCapProp.get()) powerSpin = spinCapProp.get();

        // updates the moving average
        pastPowerLeft.add(powerForward-powerSpin);
        pastPowerLeft.remove(0);
        pastPowerRight.add(powerForward+powerSpin);
        pastPowerRight.remove(0);

        // sums the past velocties

        double sumLeft=0;
        double sumRight=0;
        for (int i =0; i < pastPowerLeft.size();i++) {
            sumLeft += pastPowerLeft.get(i);
            sumRight += pastPowerRight.get(i);
        }
        
        // finds average of past velocities
        sumLeft /= pastPowerLeft.size();
        sumRight /= pastPowerRight.size();

       
        if (this.i % 50 == 0) System.out.println("PastLeft: "+pastPowerLeft + "\nPastRight: " + pastPowerRight);
        this.i ++;
        // drives 
        driveSubsystem.tankDrive(sumLeft,sumRight);
   

    }

    /**
     * sets the distance to travel, angle to maintain during travel, and final angle 
     */
    public void setDistanceAndAngle(double distance, double goalAngle, double maintainingAngle) {

        this.distance = distance;
        this.goalAngle = goalAngle;
        this.maintainingAngle = maintainingAngle;

    }

    

    @Override
    public boolean isFinished() {
        
        // is finished when robot is within 0.1 from the goal
        return distance - Math.abs(initialPosition - currentPosition) < 0.1;
        
    }
}
 