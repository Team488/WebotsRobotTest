package competition.subsystems.vision;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class VisionSubsystem extends BaseSubsystem {

    private final DoubleProperty markerCountSent;
    private final DoubleProperty markerCountReceived;

    @Inject
    public VisionSubsystem(XScheduler scheduler, PropertyFactory pf) {
        log.info("Creating VisionSubsystem");

        pf.setPrefix(this);
        this.markerCountSent = pf.createEphemeralProperty("Marker Count Sent", 0);
        this.markerCountReceived = pf.createEphemeralProperty("Marker Count Received", 0);

        scheduler.registerSubsystem(this);
    }

    @Override
    public void periodic() {
        double markerCount = this.markerCountSent.get();
        this.markerCountReceived.set(markerCount);
    }
}