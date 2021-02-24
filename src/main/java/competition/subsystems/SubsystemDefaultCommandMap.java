package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.commandgroups.SlalomPathCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.DriveForwardCommand;
import competition.subsystems.drive.commands.TurnToAngleCommand;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems

    @Inject
    public void setupDriveSubsystem(DriveSubsystem driveSubsystem, ArcadeDriveCommand command) {
        driveSubsystem.setDefaultCommand(command);
    }
}
