package competition.subsystems.drive.autonomous;

import java.util.function.Supplier;

import com.google.inject.Inject;
import com.google.inject.Provider;

import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

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

    @Inject
    SlalomAutonomousPathCommand(PropertyFactory pf, TurnLeft90DegreesCommand turnLeft, DriveToPositionCommand drivePoint, 
    Provider<DriveToPositionCommand> driveToPosProvider, Provider<TurnLeft90DegreesCommand> turnLeftProvider, Provider<TurnRight90DegreesCommand> turnRightProvider){ 
        pf.setPrefix(this.getName());
        waitTimeProp = pf.createPersistentProperty("Wait Time", 0.1); // how long it should wait
        externalWaitSupplier = () -> waitTimeProp.get(); // lambda function - it supplies a double - fancy code

        //shuffleboard 
        // D = 0.0 P = 0.1
        // only increase D SLIGHTLY, too much can cause it to 'rock' back and forth
        // max = 0.7, min = -0.7
        DriveToPositionCommand firstDrive = driveToPosProvider.get();
        firstDrive.setTargetPosition(50);
        this.addCommands(firstDrive);

        this.addCommands(turnLeftProvider.get());

        DriveToPositionCommand turnForward = driveToPosProvider.get();
        turnForward.setTargetPosition(50);
        this.addCommands(turnForward);

        this.addCommands(turnRightProvider.get());

        DriveToPositionCommand goForward = driveToPosProvider.get();
        goForward.setTargetPosition(50);
        this.addCommands(goForward);

        // this.addCommands(turnRightProvider.get());

        // DriveToPositionCommand turnForward2 = driveToPosProvider.get();
        // turnForward2.setTargetPosition(65);
        // this.addCommands(turnForward2);

        // this.addCommands(turnLeftProvider.get());

        // DriveToPositionCommand turnForward3 = driveToPosProvider.get();
        // turnForward3.setTargetPosition(65);
        // this.addCommands(turnForward3);

        // this.addCommands(turnLeftProvider.get());

        // DriveToPositionCommand turnForward4 = driveToPosProvider.get();
        // turnForward3.setTargetPosition(65);
        // this.addCommands(turnForward4);

        // DriveToPositionCommand turnForward5 = driveToPosProvider.get();
        // turnForward5.setTargetPosition(65);
        // this.addCommands(turnForward5);

        // this.addCommands(turnLeftProvider.get());

        // DriveToPositionCommand turnForward6 = driveToPosProvider.get();
        // turnForward6.setTargetPosition(65);
        // this.addCommands(turnForward6);

        // this.addCommands(turnLeftProvider.get());

        // DriveToPositionCommand turnForward7 = driveToPosProvider.get();
        // turnForward7.setTargetPosition(65);
        // this.addCommands(turnForward7);

        // this.addCommands(turnRightProvider.get());

        // DriveToPositionCommand goForward2 = driveToPosProvider.get();
        // goForward2.setTargetPosition(165);
        // this.addCommands(goForward2);

        // this.addCommands(turnRightProvider.get());

        // DriveToPositionCommand turnForward8 = driveToPosProvider.get();
        // turnForward8.setTargetPosition(65);
        // this.addCommands(turnForward8);

        // this.addCommands(turnLeftProvider.get());

        // DriveToPositionCommand turnForward9 = driveToPosProvider.get();
        // turnForward9.setTargetPosition(65);
        // this.addCommands(turnForward9);
    }
    
    @Override
    public void initialize(){
        super.initialize();
    }

}