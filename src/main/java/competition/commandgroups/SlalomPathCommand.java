package competition.commandgroups;

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
import competition.simulation.ResetPositionCommand;
import competition.subsystems.drive.commands.DriveForwardCommand;
import competition.subsystems.drive.commands.TurnToAngleCommand;


public class SlalomPathCommand extends SequentialCommandGroup {

    private final DoubleProperty waitTimeProp;
    Supplier<Double> externalWaitSupplier;


    @Inject
    SlalomPathCommand(DriveForwardCommand driveForwardCommand, TurnToAngleCommand turnAngleCommand, PropertyFactory pf, Provider<DriveForwardCommand> driveForwardProvider, Provider<TurnToAngleCommand> turnAngleProvider) {
        pf.setPrefix(this.getName());
        waitTimeProp = pf.createPersistentProperty("Max Wait Time", 5);
        DriveForwardCommand drive1 = driveForwardProvider.get();
        TurnToAngleCommand turn1 = turnAngleProvider.get();
        DriveForwardCommand drive2 = driveForwardProvider.get();
        TurnToAngleCommand turn2 = turnAngleProvider.get();
        DriveForwardCommand drive3 = driveForwardProvider.get();
        TurnToAngleCommand turn3 = turnAngleProvider.get();
        DriveForwardCommand drive4 = driveForwardProvider.get();
        TurnToAngleCommand turn4 = turnAngleProvider.get();




        drive1.setDistance(15);
        addCommands(drive1);

        // turn1.setGoal(0);
        // addCommands(turn1);

        // drive2.setDistance(15);
        // addCommands(drive2);

        // turn2.setGoal(270);
        // addCommands(turn2);

        // drive3.setDistance(600);
        // addCommands(drive3);

        // turn3.setGoal(180);
        // addCommands(turn3);

        // drive4.setDistance(150);
        // addCommands(drive4);

        // turn4.setGoal(270);
        // addCommands(turn4);

    }

    public void setWaitTime(Supplier<Double> externalWaitSupplier) {
        this.externalWaitSupplier = externalWaitSupplier;
    }

    private Supplier<Double> getWaitTime() {
        if (externalWaitSupplier == null) {
            return () -> waitTimeProp.get();
        }
        return externalWaitSupplier;
    }

    @Override
    public void initialize() {
        super.initialize();
    }
}
