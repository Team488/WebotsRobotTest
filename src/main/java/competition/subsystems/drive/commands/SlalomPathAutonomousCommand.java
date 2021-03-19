package competition.subsystems.drive.commands;

import java.util.function.Supplier;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.apache.log4j.Logger;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.command.SimpleWaitForMaintainerCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.simulation.ResetSimulatorPositionCommand;
import xbot.common.subsystems.drive.ConfigurablePurePursuitCommand;
import xbot.common.subsystems.drive.PurePursuitCommand;
import xbot.common.subsystems.drive.RabbitPoint;
import xbot.common.subsystems.drive.PurePursuitCommand.PointLoadingMode;
import xbot.common.subsystems.drive.RabbitPoint.PointTerminatingType;
import xbot.common.subsystems.drive.RabbitPoint.PointType;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.FieldPose;
import xbot.common.math.XYPair;

public class SlalomPathAutonomousCommand extends SequentialCommandGroup {

    private static Logger log = Logger.getLogger("");
    

    @Inject
    SlalomPathAutonomousCommand( ResetSimulatorPositionCommand resetToStartOfSlalom, PropertyFactory pf, 
    Provider<DriveDistanceCommand> driveProvider,Provider<SpinCommand> spinProvider, 
    Provider<DriveSpinCommand> dsProvider, Provider<ConfigurablePurePursuitCommand> pureProvider)
    {
        

        FieldPose slalomStart = new FieldPose(new XYPair(1.52, -3.81), new ContiguousHeading(0)); 
        resetToStartOfSlalom.setTargetPose(slalomStart);
        addCommands(resetToStartOfSlalom);

        //normalDrive(driveProvider, spinProvider);
        //advancedDrive(dsProvider);

        ConfigurablePurePursuitCommand pure =pureProvider.get();
        pure.setMode(PointLoadingMode.Absolute);
        pure.addPoint(new RabbitPoint(4.52, -3.81,90));
  
    }

    private void normalDrive(Provider<DriveDistanceCommand> driveProvider,Provider<SpinCommand> spinProvider) {

        int longDistance = (int)180;
        int shortDistance = (int)64;

        DriveDistanceCommand drive = driveProvider.get();
        DriveDistanceCommand drive2 = driveProvider.get();
        DriveDistanceCommand drive3 = driveProvider.get();
        DriveDistanceCommand drive4 = driveProvider.get();
        DriveDistanceCommand drive5 = driveProvider.get();
        DriveDistanceCommand drive6 = driveProvider.get();
        DriveDistanceCommand drive7 = driveProvider.get();
        DriveDistanceCommand drive8 = driveProvider.get();
        DriveDistanceCommand drive9 = driveProvider.get();
        DriveDistanceCommand drive10 = driveProvider.get();
        DriveDistanceCommand drive11 = driveProvider.get();
        
        SpinCommand spin = spinProvider.get();
        SpinCommand spin2 = spinProvider.get();
        SpinCommand spin3 = spinProvider.get();
        SpinCommand spin4 = spinProvider.get();
        SpinCommand spin5 = spinProvider.get();
        SpinCommand spin6 = spinProvider.get();
        SpinCommand spin7 = spinProvider.get();
        SpinCommand spin8 = spinProvider.get();
        SpinCommand spin9 = spinProvider.get();
        SpinCommand spin10 = spinProvider.get();
        
        drive.setDistance(shortDistance);
        addCommands(drive);
        
        spin.setAngle(0);
        addCommands(spin);
        
        drive2.setDistance(shortDistance);
        addCommands(drive2);
        
        spin2.setAngle(-90);
        addCommands(spin2);
        
        drive3.setDistance(longDistance);
        addCommands(drive3);
        
        spin3.setAngle(180);
        addCommands(spin3);
        
        drive4.setDistance(shortDistance);
        addCommands(drive4);
        
        spin4.setAngle(-90);
        addCommands(spin4);
        
        drive5.setDistance(shortDistance);
        addCommands(drive5);
        
        spin5.setAngle(0);
        addCommands(spin5);
        
        drive6.setDistance(shortDistance);
        addCommands(drive6);
        
        spin6.setAngle(90);
        addCommands(spin6);

        drive7.setDistance(shortDistance);
        addCommands(drive7);
        
        spin7.setAngle(180);
        addCommands(spin7);
        
        drive8.setDistance(shortDistance);
        addCommands(drive8);
        
        spin8.setAngle(90);
        addCommands(spin8);
        
        drive9.setDistance(longDistance);
        addCommands(drive9);
        
        spin9.setAngle(0);
        addCommands(spin9);
        
        drive10.setDistance(shortDistance);
        addCommands(drive10);
        
        spin10.setAngle(90);
        addCommands(spin10);
        
        drive11.setDistance(shortDistance);
        addCommands(drive11);
    }

private void advancedDrive(Provider<DriveSpinCommand> dsProvider) {
    int longDistance = 190;
    int mediumDistance = 100;
    int ShortDistance = 55;
    int ShorterDistance =25;
    
    // > ^
    DriveSpinCommand ds1 = dsProvider.get();
    ds1.setDistanceAndAngle(ShortDistance, 0,-90);
    addCommands(ds1);
    
    // ^ >
    DriveSpinCommand ds2 = dsProvider.get();
    ds2.setDistanceAndAngle(ShorterDistance, -90, 0);
    addCommands(ds2);

    // > v
    DriveSpinCommand ds3 = dsProvider.get();
    ds3.setDistanceAndAngle(longDistance, 180, -90);
    addCommands(ds3);
    
    // v >
    DriveSpinCommand ds4 = dsProvider.get();
    ds4.setDistanceAndAngle(mediumDistance, -90, 180);
    addCommands(ds4);
    
    // > ^
    DriveSpinCommand ds5 = dsProvider.get();
    ds5.setDistanceAndAngle(ShorterDistance, 0, -90);
    addCommands(ds5);

    // ^ <
    DriveSpinCommand ds6 = dsProvider.get();
    ds6.setDistanceAndAngle(ShortDistance, 90, 0);
    addCommands(ds6);

    // < v
    DriveSpinCommand ds7 = dsProvider.get();
    ds7.setDistanceAndAngle(ShorterDistance, 180, 90);
    addCommands(ds7);

    // v <
    DriveSpinCommand ds8 = dsProvider.get();
    ds8.setDistanceAndAngle(ShortDistance, 90, 180);
    addCommands(ds8);

    // < ^
    DriveSpinCommand ds9 = dsProvider.get();
    ds9.setDistanceAndAngle(longDistance, 0, 90 );
    addCommands(ds9);

    // ^ <
    DriveSpinCommand ds10 = dsProvider.get();
    ds10.setDistanceAndAngle(mediumDistance,90, 0);
    addCommands(ds10);

    // < <
    DriveSpinCommand ds11 = dsProvider.get();
    ds11.setDistanceAndAngle(ShortDistance, 90, 90);
    addCommands(ds11);
}
    
    @Override
    public void initialize() {
        super.initialize();
        log.info("Initializing");
    }
}