package competition.subsystems.drive.autonomous;

import java.util.function.Supplier;

import com.google.inject.Inject;
import com.google.inject.Provider;

import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import competition.subsystems.drive.commands.DriveForDistanceCommand;
import competition.subsystems.drive.commands.DriveToPositionCommand;
import competition.subsystems.drive.commands.TurnCommand;
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
    DoubleProperty turnLeft;
    DoubleProperty turnRight;

    @Inject
    SlalomAutonomousPathCommand(PropertyFactory pf, DriveToPositionCommand drivePoint, 
    Provider<DriveToPositionCommand> driveToPosProvider, Provider<TurnCommand> turnProvider, Provider<DriveForDistanceCommand> driveDistanceProvider){ 
        pf.setPrefix(this.getName());
        horizontalDistance = pf.createPersistentProperty("HD", 2);
        verticalDistance = pf.createPersistentProperty("VD", 28);
        verticalLongDistance = pf.createPersistentProperty("VLD", 100);
        miniVerticalTurn =  pf.createPersistentProperty("MVT", 10);
        turnLeft = pf.createPersistentProperty("TL", 90); // need to delete networktable to set
        turnRight = pf.createPersistentProperty("TR", -90);

        waitTimeProp = pf.createPersistentProperty("Wait Time", 0.1); // how long it should wait
        externalWaitSupplier = () -> waitTimeProp.get(); // lambda function - it supplies a double - fancy code

        DriveForDistanceCommand testRun = driveDistanceProvider.get();
        testRun.setTargetPosition(verticalDistance);
        this.addCommands(testRun);

        TurnCommand turn1 = turnProvider.get();
        turn1.setTargetGoal(turnLeft);
        this.addCommands(turn1);

        DriveForDistanceCommand testRun2 = driveDistanceProvider.get();
        testRun2.setTargetPosition(horizontalDistance);
        this.addCommands(testRun2);
        
        TurnCommand turn2 = turnProvider.get();
        turn2.setTargetGoal(turnRight);
        this.addCommands(turn2);

        DriveForDistanceCommand testRun3 = driveDistanceProvider.get(); // Straight Drive
        testRun3.setTargetPosition(verticalLongDistance);
        this.addCommands(testRun3);

        TurnCommand turn3 = turnProvider.get();
        turn3.setTargetGoal(turnRight);
        this.addCommands(turn3);

        DriveForDistanceCommand testRun4 = driveDistanceProvider.get();
        testRun4.setTargetPosition(horizontalDistance);
        this.addCommands(testRun4);

        TurnCommand turn4 = turnProvider.get();
        turn4.setTargetGoal(turnLeft);
        this.addCommands(turn4);

        DriveForDistanceCommand testRun5 = driveDistanceProvider.get();
        testRun5.setTargetPosition(miniVerticalTurn);
        this.addCommands(testRun5);
        
        TurnCommand turn5 = turnProvider.get();
        turn5.setTargetGoal(turnLeft);
        this.addCommands(turn5);

        DriveForDistanceCommand testRun6 = driveDistanceProvider.get();
        testRun6.setTargetPosition(horizontalDistance);
        this.addCommands(testRun6);

        TurnCommand turn6 = turnProvider.get();
        turn6.setTargetGoal(turnLeft);
        this.addCommands(turn6);

        DriveForDistanceCommand testRun7 = driveDistanceProvider.get();
        testRun7.setTargetPosition(miniVerticalTurn);
        this.addCommands(testRun7);

        TurnCommand turn7 = turnProvider.get();
        turn7.setTargetGoal(turnLeft);
        this.addCommands(turn7);

        DriveForDistanceCommand testRun8 = driveDistanceProvider.get();
        testRun8.setTargetPosition(horizontalDistance);
        this.addCommands(testRun8);

        TurnCommand turn8 = turnProvider.get();
        turn8.setTargetGoal(turnRight);
        this.addCommands(turn8);

        DriveForDistanceCommand testRun9 = driveDistanceProvider.get(); // Straight Drive 2
        testRun9.setTargetPosition(verticalLongDistance);
        this.addCommands(testRun9);

        TurnCommand turn9 = turnProvider.get();
        turn9.setTargetGoal(turnRight);
        this.addCommands(turn9);

        DriveForDistanceCommand testRun10 = driveDistanceProvider.get();
        testRun10.setTargetPosition(horizontalDistance);
        this.addCommands(testRun10);

        TurnCommand turn10 = turnProvider.get();
        turn10.setTargetGoal(turnLeft);
        this.addCommands(turn10);
        
        DriveForDistanceCommand testRun11 = driveDistanceProvider.get();
        testRun11.setTargetPosition(miniVerticalTurn);
        this.addCommands(testRun11);
    }
    
    @Override
    public void initialize(){
        super.initialize();
    }

}