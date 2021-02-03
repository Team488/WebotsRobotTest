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

    // each wheel revolution is 256 ticks, and the wheels are 6 inches in diameter.
    // this means each revolution would move the robot (if not slipping at all) 6*pi inches. 
    // So, one tick would move us (6*pi)/256 inches
    // Conversely, we can 
    private double scalingFactorFromTicksToInches = 6.0 * Math.PI / 256.0;

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