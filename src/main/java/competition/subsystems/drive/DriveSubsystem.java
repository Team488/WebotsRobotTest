package competition.subsystems.drive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.log4j.Logger;

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

    public final XCANTalon frontLeftWheel;
    public final XCANTalon frontRightWheel;
    public final XCANTalon backLeftWheel;
    public final XCANTalon backRightWheel;

    int i;
    private final double simulatedEncoderFactor = 256.0 * 39.3701; //256 "ticks" per meter, and ~39 inches in a meter

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager) {
        log.info("Creating DriveSubsystem");

        this.frontLeftWheel = factory
                .createCANTalon(new CANTalonInfo(1, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.frontRightWheel = factory
                .createCANTalon(new CANTalonInfo(2, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.backLeftWheel = factory
                .createCANTalon(new CANTalonInfo(3, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.backRightWheel = factory
                .createCANTalon(new CANTalonInfo(4, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        
        frontLeftWheel.createTelemetryProperties(this.getPrefix(), "frontLeftWheel");
        frontRightWheel.createTelemetryProperties(this.getPrefix(), "frontRightWheel");
        backLeftWheel.createTelemetryProperties(this.getPrefix(), "backLeftWheel");
        backRightWheel.createTelemetryProperties(this.getPrefix(), "backRightWheel");

        this.register();
    }

    public void tankDrive(double leftPower, double rightPower) {    
        this.frontLeftWheel.simpleSet(leftPower);
        this.backLeftWheel.simpleSet(leftPower);
        this.frontRightWheel.simpleSet(rightPower);
        this.backRightWheel.simpleSet(rightPower);
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

        tankDrive(left, right);
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
        frontLeftWheel.updateTelemetryProperties();
        frontRightWheel.updateTelemetryProperties();
        backLeftWheel.updateTelemetryProperties();
        backRightWheel.updateTelemetryProperties();
    }
}
