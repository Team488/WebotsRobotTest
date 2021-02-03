package competition.subsystems.drive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.log4j.Logger;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.controls.sensors.XAnalogDistanceSensor;
import xbot.common.controls.sensors.XAnalogDistanceSensor.VoltageMaps;
import xbot.common.injection.electrical_contract.CANTalonInfo;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.PIDManager;
import xbot.common.math.XYPair;
import xbot.common.properties.XPropertyManager;
import xbot.common.subsystems.drive.BaseDriveSubsystem;

@Singleton
public class DriveSubsystem extends BaseDriveSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    public final XCANTalon leftLeader;
    public final XCANTalon leftFollower1;
    public final XCANTalon leftFollower2;
    public final XCANTalon rightLeader;
    public final XCANTalon rightFollower1;
    public final XCANTalon rightFollower2;

    public final XAnalogDistanceSensor distanceSensor;
    public final XAnalogDistanceSensor distanceSensor2;

    int i;
    private final double simulatedEncoderFactor = 256.0 / Math.PI;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager) {
        log.info("Creating DriveSubsystem");

        this.leftLeader = factory
                .createCANTalon(new CANTalonInfo(1, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.leftFollower1 = factory
                .createCANTalon(new CANTalonInfo(3, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.leftFollower2= factory
                .createCANTalon(new CANTalonInfo(5, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        
        this.rightLeader = factory
                .createCANTalon(new CANTalonInfo(2, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.rightFollower1 = factory
                .createCANTalon(new CANTalonInfo(4, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.rightFollower2 = factory
                .createCANTalon(new CANTalonInfo(6, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));

        this.distanceSensor = factory.createAnalogDistanceSensor(1, VoltageMaps::sharp0A51SK);
        this.distanceSensor2 = factory.createAnalogDistanceSensor(2, VoltageMaps::sharp0A51SK);

        XCANTalon.configureMotorTeam(this.getPrefix(), "LeftLeader", leftLeader, leftFollower1, leftFollower2, true, true, true, true);
        XCANTalon.configureMotorTeam(this.getPrefix(), "RightLeader", rightLeader, rightFollower1, rightFollower2, true, true, true, true);

        this.register();
    }

    public void tankDrive(double leftPower, double rightPower) {    
        this.leftLeader.simpleSet(leftPower);
        this.rightLeader.simpleSet(rightPower);
        /*
         * i++; if (i % 25 == 0) { System.out.println("LeftPower:" + leftPower);
         * System.out.println("RightPower:" + rightPower); }
         */
    }

    @Override
    public PIDManager getPositionalPid() {
        return null;
    }

    @Override
    public PIDManager getRotateToHeadingPid() {
        return null;
    }

    @Override
    public PIDManager getRotateDecayPid() {
        return null;
    }

    @Override
    public void move(XYPair translate, double rotate) {
        double left = translate.y - rotate;
        double right = translate.y + rotate;

        this.leftLeader.simpleSet(left);
        this.rightLeader.simpleSet(right);
    }

    @Override
    public double getLeftTotalDistance() {
        return 0;
    }

    @Override
    public double getRightTotalDistance() {
        return 0;
    }

    @Override
    public double getTransverseDistance() {
        return 0;
    }

    @Override
    public void periodic() {
        leftLeader.updateTelemetryProperties();
        rightLeader.updateTelemetryProperties();
    }
}
