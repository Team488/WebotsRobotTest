package competition.subsystems.drive.commands;

import com.google.inject.Inject;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.math.XYPair;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.simulation.WebotsClient;
import xbot.common.subsystems.drive.control_logic.HeadingModule;

public class CircleDriveCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    WebotsClient webots;
    HeadingModule headingModule;

    DoubleProperty drivePower;
    DoubleProperty spiralFactor;
    DoubleProperty spiralMax;

    @Inject
    public CircleDriveCommand(DriveSubsystem drive, PoseSubsystem pose, WebotsClient webots, CommonLibFactory clf, PropertyFactory propertyFactory) {
        this.drive = drive;
        this.pose = pose;
        this.webots = webots;

        this.headingModule = clf.createHeadingModule(drive.getRotateToHeadingPid());

        propertyFactory.setPrefix(this);
        drivePower = propertyFactory.createPersistentProperty("DrivePower", 0.25);
        spiralFactor = propertyFactory.createPersistentProperty("SpiralFactor", 1);
        spiralMax = propertyFactory.createPersistentProperty("SpiralMax", 15);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        // Assume there is a circle centered at 0,0, with radius 5 feet. We want to constantly circle around that point at that radius.
        double circleAngle = webots.getTruePosition().clone().getAngle();
        double idealAngle = circleAngle + 90;
        double distanceFromCenter = webots.getTruePosition().getDistanceToPoint(new XYPair(0,0));
        double spiralAdjust = (distanceFromCenter - 5*12)*spiralFactor.get();
        spiralAdjust = MathUtils.constrainDouble(spiralAdjust, -spiralMax.get(), spiralMax.get());

        double headingPower = headingModule.calculateHeadingPower(idealAngle + spiralAdjust);
        drive.arcadeDrive(drivePower.get(), headingPower);
    }
}