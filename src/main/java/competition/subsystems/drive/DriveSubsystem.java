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
import xbot.common.properties.XPropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    public final XCANTalon leftMaster;
    public final XCANTalon leftFollower;
    public final XCANTalon rightMaster;
    public final XCANTalon rightFollower;

    public final XAnalogDistanceSensor distanceSensor;
    public final XAnalogDistanceSensor distanceSensor2;

    int i;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager) {
        log.info("Creating DriveSubsystem");

        this.leftMaster = factory.createCANTalon(new CANTalonInfo(1, false, FeedbackDevice.CTRE_MagEncoder_Absolute, false, 1));
        this.leftFollower = factory.createCANTalon(new CANTalonInfo(3, false, FeedbackDevice.CTRE_MagEncoder_Absolute, false, 1));
        this.rightMaster = factory.createCANTalon(new CANTalonInfo(2, false, FeedbackDevice.CTRE_MagEncoder_Absolute, false, 1));
        this.rightFollower = factory.createCANTalon(new CANTalonInfo(4, false, FeedbackDevice.CTRE_MagEncoder_Absolute, false, 1));

        this.distanceSensor = factory.createAnalogDistanceSensor(1, VoltageMaps::sharp0A51SK);
        this.distanceSensor2 = factory.createAnalogDistanceSensor(2, VoltageMaps::sharp0A51SK);

        XCANTalon.configureMotorTeam("LeftDrive", "LeftMaster", leftMaster, leftFollower, 
        true, true, false);
        XCANTalon.configureMotorTeam("RightDrive", "RightMaster", rightMaster, rightFollower, 
        false, false, false);
    }

    public void tankDrive(double leftPower, double rightPower) {
        this.leftMaster.simpleSet(leftPower);
        this.rightMaster.simpleSet(rightPower);

        i++;
        if (i % 25 == 0) {
            System.out.println("LeftPower:" + leftPower);
            System.out.println("RightPower:" + rightPower);
        }
    }
}
