package competition.subsystems.drive.autonomous;

import java.util.function.Supplier;

import com.google.inject.Inject;

import org.apache.log4j.Logger;

import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// add smartdashboard into here
// along with the autonomous command selectors
// import BaseDriveSubsystem
// use .addcommand commands?

public class SlalomAutonomousPathCommand extends SequentialCommandGroup {

    private static Logger log = Logger.getLogger(SlalomAutonomousPathCommand.class);

    @Inject
    SlalomAutonomousPathCommand(DriveSubsystem drive){
        
    }
    
    @Override
    public void initialize(){
        log.info("Initializing");
    }
    
    @Override
    public void execute(){
    }

}