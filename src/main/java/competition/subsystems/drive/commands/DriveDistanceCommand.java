package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.math.XYPair;



public class DriveDistanceCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    double initialPosition; 
    double distance;
    final PoseSubsystem pose;
    int i = 0;
    PIDManager pid;
    



    @Inject
    public DriveDistanceCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDFactory pf) {
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(this.driveSubsystem);
        this.pose = pose;
        this.pid = pf.createPIDManager("DriveDistance");

        pid.setEnableErrorThreshold(true); 
        pid.setErrorThreshold(0.05); // was 0.05
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.05); // was 0.05
        
        pid.setP(.1); // was .8
        //pid.setD(.1); // was .8

    }

    @Override
    public void initialize() {
        this.initialPosition = driveSubsystem.leftLeader.getSelectedSensorPosition(0)/ 256.0;
        pid.reset();
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double currentPosition = driveSubsystem.leftLeader.getSelectedSensorPosition(0)  / 256.0;
        double power = pid.calculate(initialPosition+distance,currentPosition);
        

        driveSubsystem.tankDrive(power,power);

        if (i % 10 ==0) {
            
            System.out.println("Goal: " + (initialPosition+distance)+  "\nInitial: " + initialPosition+"\nCurrent: "+ currentPosition + " \nDistance: " + ( currentPosition-initialPosition) );
        
            System.out.println("Power: "+power +"\n");
        }
            i++;

        /*if (i % 50 == 0) {
            System.out.println("initial position: "+initialPosition + "\ncurrent position: " + currentPosition);
            System.out.println("angular position: " +pose.getCurrentHeading().getValue());
        }

        i++;
        */
    }

    public void setDistance(double distance) {

        this.distance = distance;

    }

    @Override
    public boolean isFinished() {

        return pid.isOnTarget();

        //double currentPosition = driveSubsystem.leftLeader.getSelectedSensorPosition(0) * 6.0 * Math.PI / 256.0;
        //return (0.1>Math.abs((Math.abs((currentPosition - initialPosition)) - distance)));
        
    }
}
 