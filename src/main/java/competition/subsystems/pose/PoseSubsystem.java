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

    @Inject
    public PoseSubsystem(CommonLibFactory clf, PropertyFactory propManager, DriveSubsystem drive) {
        super(clf, propManager);
        this.drive = drive;
    }


    @Override
    protected double getLeftDriveDistance() {
        return drive.leftLeader.getSelectedSensorPosition(0);
    }

    @Override
    protected double getRightDriveDistance() {
        return drive.rightLeader.getSelectedSensorPosition(0);
    }

}