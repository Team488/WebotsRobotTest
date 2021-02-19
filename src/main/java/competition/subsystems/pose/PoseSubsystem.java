package competition.subsystems.pose;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

@Singleton
public class PoseSubsystem extends BasePoseSubsystem {

    DriveSubsystem drive;

    // The encoders are already reporting 256 ticks per inch, so if we divide by ticks we will get inches.
    private double scalingFactorFromTicksToInches = 1.0 / 256.0;

    @Inject
    public PoseSubsystem(CommonLibFactory clf, PropertyFactory propManager, DriveSubsystem drive) {
        super(clf, propManager);
        this.drive = drive;
    }

    @Override
    protected double getLeftDriveDistance() {
        return drive.leftLeader.getSelectedSensorPosition(0) * scalingFactorFromTicksToInches;
    }

    @Override
    protected double getRightDriveDistance() {
        return drive.rightLeader.getSelectedSensorPosition(0) * scalingFactorFromTicksToInches;
    }

}