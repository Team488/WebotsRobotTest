package competition.subsystems.drive.autonomous;

import java.util.function.Supplier;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;

import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.simulation.ResetSimulatorPositionCommand;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import xbot.common.command.BaseCommand;
import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.command.SimpleWaitForMaintainerCommand;


// add smartdashboard into here
// along with the autonomous command selectors
// use .addcommand commands?
// 'get' commands included (getposition, getpower, etc)
// use ResetSimulatorPosition and perhaps import turnleft90command to become utilized in here
// Spin Drive Command?

public class SlalomAutonomousPathCommand extends SequentialCommandGroup {

    private static Logger log = Logger.getLogger(SlalomAutonomousPathCommand.class);


    @Inject
    SlalomAutonomousPathCommand(DriveSubsystem drive, PropertyFactory pf){ //PoseSubsystem pose,
        
    }
    
    @Override
    public void initialize(){
        super.initialize();
        log.info("Initializing");
    }
    
    @Override
    public void execute(){
    }

}