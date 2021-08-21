package competition.subsystems.drive;

import java.util.ArrayList;

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
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.properties.XPropertyManager;
import xbot.common.subsystems.drive.BaseDriveSubsystem;

@Singleton
public class DriveSubsystem extends BaseDriveSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    public final XCANTalon leftLeader;
    public final XCANTalon rightLeader;

    public final XAnalogDistanceSensor distanceSensor;
    public final XAnalogDistanceSensor distanceSensor2;

    private final PIDManager positionalPid;
    private final PIDManager rotationalPid;

    private final DoubleProperty smoothingHistoryLength;

    int i;
    private final double simulatedEncoderFactor = 256.0 * 39.3701; //256 "ticks" per meter, and ~39 inches in a meter

    @Inject
    public DriveSubsystem(CommonLibFactory factory, PropertyFactory propertyFactory) {
        log.info("Creating DriveSubsystem");

        propertyFactory.setPrefix(this);
        smoothingHistoryLength = propertyFactory.createPersistentProperty("SmoothingHistoryLength", 10);

        this.leftLeader = factory
                .createCANTalon(new CANTalonInfo(1, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));
        this.rightLeader = factory
                .createCANTalon(new CANTalonInfo(2, true, FeedbackDevice.CTRE_MagEncoder_Absolute, true, simulatedEncoderFactor));

        this.distanceSensor = factory.createAnalogDistanceSensor(1, VoltageMaps::sharp0A51SK, this.getPrefix());
        this.distanceSensor2 = factory.createAnalogDistanceSensor(2, VoltageMaps::sharp0A51SK, this.getPrefix());

        leftLeader.createTelemetryProperties(this.getPrefix(), "LeftLeader");
        rightLeader.createTelemetryProperties(this.getPrefix(), "RightLeader");

        positionalPid = factory.createPIDManager(this.getPrefix() + "PositionPID", 1, 0, 0);
        rotationalPid = factory.createPIDManager(this.getPrefix() + "RotationPID", 1, 0, 0);

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
        return positionalPid;
    }

    @Override
    public PIDManager getRotateToHeadingPid() {
        return rotationalPid;
    }

    @Override
    public PIDManager getRotateDecayPid() {
        return null;
    }

    public double getSmoothedPower(double newPower, ArrayList<Double> history) {
        while (history.size() > (int)smoothingHistoryLength.get()) {
            history.remove(0);
        }
        history.add(newPower);
        return history.stream().mapToDouble(Double::doubleValue).sum() / history.size();
    }

    ArrayList<Double> leftHistory = new ArrayList<Double>();
    ArrayList<Double> rightHistory = new ArrayList<Double>();
    

    @Override
    public void move(XYPair translate, double rotate) {
        double left = translate.y - rotate;
        double right = translate.y + rotate;

        double smoothLeft = getSmoothedPower(left, leftHistory);
        double smoothRight = getSmoothedPower(right, rightHistory);

        this.leftLeader.simpleSet(smoothLeft);
        this.rightLeader.simpleSet(smoothRight);
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
