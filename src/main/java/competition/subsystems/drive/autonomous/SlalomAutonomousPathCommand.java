package competition.subsystems.drive.autonomous;

import java.util.function.Supplier;

import com.google.inject.Inject;
import com.google.inject.Provider;

import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import competition.subsystems.drive.commands.DriveForDistanceCommand;
import competition.subsystems.drive.commands.DriveToPositionCommand;
import competition.subsystems.drive.commands.TurnLeft90DegreesCommand;
import competition.subsystems.drive.commands.TurnRight90DegreesCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// along with the autonomous command selectors
// use .addcommand commands?
// 'get' commands included (getposition, getpower, etc)
// use ResetSimulatorPosition?

public class SlalomAutonomousPathCommand extends SequentialCommandGroup {

    private final DoubleProperty waitTimeProp;
    Supplier<Double> externalWaitSupplier;
    DoubleProperty horizontalDistance; 
    DoubleProperty verticalDistance; 
    DoubleProperty verticalLongDistance; 
    DoubleProperty miniVerticalTurn;


    @Inject
    SlalomAutonomousPathCommand(PropertyFactory pf, TurnLeft90DegreesCommand turnLeft, DriveToPositionCommand drivePoint, 
    Provider<DriveToPositionCommand> driveToPosProvider, Provider<TurnLeft90DegreesCommand> turnLeftProvider, 
    Provider<TurnRight90DegreesCommand> turnRightProvider, Provider<DriveForDistanceCommand> driveDistanceProvider){ 
        pf.setPrefix(this.getName());
        horizontalDistance = pf.createPersistentProperty("HD", 10);
        verticalDistance = pf.createPersistentProperty("VD", 30);
        verticalLongDistance = pf.createPersistentProperty("VLD", 105);
        miniVerticalTurn =  pf.createPersistentProperty("MVT", 15);

        waitTimeProp = pf.createPersistentProperty("Wait Time", 0.1); // how long it should wait
        externalWaitSupplier = () -> waitTimeProp.get(); // lambda function - it supplies a double - fancy code

        //shuffleboard 
        // D = 0.0 P = 0.1
        // only increase D SLIGHTLY, too much can cause it to 'rock' back and forth
        // max = 0.7, min = -0.7

        DriveForDistanceCommand testRun = driveDistanceProvider.get();
        testRun.setTargetPosition(verticalDistance);
        this.addCommands(testRun);

        this.addCommands(turnLeftProvider.get());

        DriveForDistanceCommand testRun2 = driveDistanceProvider.get();
        testRun2.setTargetPosition(horizontalDistance);
        this.addCommands(testRun2);
        
        this.addCommands(turnRightProvider.get());

        DriveForDistanceCommand testRun3 = driveDistanceProvider.get();
        testRun3.setTargetPosition(verticalLongDistance);
        this.addCommands(testRun3);

        this.addCommands(turnRightProvider.get());

        DriveForDistanceCommand testRun4 = driveDistanceProvider.get();
        testRun4.setTargetPosition(horizontalDistance);
        this.addCommands(testRun4);

        this.addCommands(turnLeftProvider.get());

        DriveForDistanceCommand testRun5 = driveDistanceProvider.get();
        testRun5.setTargetPosition(miniVerticalTurn);
        this.addCommands(testRun5);
        
        this.addCommands(turnLeftProvider.get());

        DriveForDistanceCommand testRun6 = driveDistanceProvider.get();
        testRun6.setTargetPosition(horizontalDistance);
        this.addCommands(testRun6);

        this.addCommands(turnLeftProvider.get());

        DriveForDistanceCommand testRun7 = driveDistanceProvider.get();
        testRun7.setTargetPosition(horizontalDistance);
        this.addCommands(testRun7);

        this.addCommands(turnLeftProvider.get());

        DriveForDistanceCommand testRun8 = driveDistanceProvider.get();
        testRun8.setTargetPosition(horizontalDistance);
        this.addCommands(testRun8);

        this.addCommands(turnRightProvider.get());

    }
    
    @Override
    public void initialize(){
        super.initialize();
    }

}