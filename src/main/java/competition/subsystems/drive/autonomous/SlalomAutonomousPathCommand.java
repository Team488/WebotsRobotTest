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
    DoubleProperty turnLeft93;
    DoubleProperty turnRightM;

    @Inject
    SlalomAutonomousPathCommand(PropertyFactory pf, DriveToPositionCommand drivePoint, 
    Provider<DriveToPositionCommand> driveToPosProvider, Provider<TurnCommand> turnProvider, Provider<DriveForDistanceCommand> driveDistanceProvider){ 
        pf.setPrefix(this.getName());
        // need to delete networktable to set
        horizontalDistance = pf.createPersistentProperty("HD", 30);
        verticalDistance = pf.createPersistentProperty("VD", 13);
        verticalLongDistance = pf.createPersistentProperty("VLD", 72);
        miniVerticalTurn =  pf.createPersistentProperty("MVT", 10);
        turnLeft = pf.createPersistentProperty("TL", 55);
        turnRight = pf.createPersistentProperty("TR", -55);
        turnLeft93 = pf.createPersistentProperty("TL93", 93);
        turnRightM = pf.createPersistentProperty("TRM", -5);


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
        
        TurnCommand turn5 = turnProvider.get();
        turn5.setTargetGoal(turnLeft93);
        this.addCommands(turn5);

        TurnCommand turn6 = turnProvider.get();
        turn6.setTargetGoal(turnLeft93);
        this.addCommands(turn6);

        TurnCommand turn7 = turnProvider.get();
        turn7.setTargetGoal(turnLeft);
        this.addCommands(turn7);

        DriveForDistanceCommand testRun8 = driveDistanceProvider.get();
        testRun8.setTargetPosition(miniVerticalTurn);
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

        TurnCommand turn10 = turnProvider.get();
        turn10.setTargetGoal(turnRightM);
        this.addCommands(turn10);

        DriveForDistanceCommand testRun10 = driveDistanceProvider.get();
        testRun10.setTargetPosition(miniVerticalTurn);
        this.addCommands(testRun10);

        TurnCommand turn11 = turnProvider.get();
        turn11.setTargetGoal(turnLeft);
        this.addCommands(turn11);
    }
    
    @Override
    public void initialize(){
        super.initialize();
    }

}