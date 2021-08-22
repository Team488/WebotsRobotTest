package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.wpilibj.util.Color;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.FieldPose;
import xbot.common.math.XYPair;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.simulation.WebotsClient;
import xbot.common.subsystems.drive.control_logic.HeadingModule;
import xbot.common.subsystems.pose.BasePoseSubsystem;

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
        
        // Now instead get a circle based on a field pose
        FieldPose goalPoint = new FieldPose(0, 0, -180);
        FieldPose ahead = goalPoint.getPointAlongPoseLine(12);
        FieldPose behind = goalPoint.getPointAlongPoseLine(-12);

        webots.drawLine("GoalAhead", ahead.getPoint(), goalPoint.getPoint(), Color.kBlue, 60);
        webots.drawLine("GoalBehind", behind.getPoint(), goalPoint.getPoint(), Color.kRed, 60);

        XYPair circleCenter = goalPoint.getCenterOfCircleConnectingFieldPoseAndPoint(pose.getCurrentFieldPose().getPoint());
        
        FieldPose truePose = new FieldPose(webots.getTruePosition(), pose.getCurrentHeading().clone());
        double distanceFromCenter = webots.getTruePosition().getDistanceToPoint(circleCenter);

        webots.drawCircle("MainCircle", circleCenter, (float)(distanceFromCenter / BasePoseSubsystem.INCHES_IN_A_METER), Color.kGreen, 60);
        


        //double circleAngle = webots.getTruePosition().clone().getAngle();
        double circleAngle = circleCenter.getAngleToPoint(truePose.getPoint());
        double idealAngle = circleAngle + 90;
        //double distanceFromCenter = webots.getTruePosition().getDistanceToPoint(new XYPair(0,0));
        //double distanceFromCenter = webots.getTruePosition().getDistanceToPoint(circleCenter);
        double spiralAdjust = 0;//(distanceFromCenter - 5*12)*spiralFactor.get();
        //spiralAdjust = MathUtils.constrainDouble(spiralAdjust, -spiralMax.get(), spiralMax.get());

        double headingPower = headingModule.calculateHeadingPower(idealAngle + spiralAdjust);
        drive.arcadeDrive(drivePower.get(), headingPower);
    }
}