package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.simulation.ResetPositionCommand;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {
    
    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).whenPressed(resetHeading);
    }

    @Inject
    public void setupSimulationCommands(
        OperatorInterface operatorInterface,
        ResetPositionCommand resetPositionCommand
    ) {
        operatorInterface.gamepad.getifAvailable(2).whenPressed(resetPositionCommand);
    }
}
