package competition.subsystems.drive.autonomous;

import org.apache.log4j.Logger;
import java.util.function.Supplier;

import com.google.inject.Inject;
import com.google.inject.Provider;

import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

import competition.subsystems.drive.commands.DriveToPositionCommand;
import competition.subsystems.drive.commands.TurnLeft90DegreesCommand;
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
    Provider<DriveToPositionCommand> driveToPosProvider, Provider<TurnLeft90DegreesCommand> turnLeftProvider){ 
        pf.setPrefix(this.getName());
        waitTimeProp = pf.createPersistentProperty("Wait Time", 5);

        DriveToPositionCommand firstDrive = driveToPosProvider.get();
        
        //shuffleboard 
        // D = 0.6 P = 0.1
        // max = 0.7, min = -0.7
        firstDrive.setTargetPosition(65);
        this.addCommands(firstDrive);

        TurnLeft90DegreesCommand firstTurn = turnLeftProvider.get();
        this.addCommands(firstTurn);

        DriveToPositionCommand goForward = driveToPosProvider.get();
        
        goForward.setTargetPosition(65);
        this.addCommands(goForward);
    }

    @Override
    public void initialize(){
        super.initialize();
    }

}