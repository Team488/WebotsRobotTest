package competition.subsystems.vision;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class VisionSubsystem extends BaseSubsystem {

    private final DoubleProperty markerCount;

    @Inject
    public VisionSubsystem(XScheduler scheduler, PropertyFactory pf) {
        log.info("Creating VisionSubsystem");

        pf.setPrefix(this);
        this.markerCount = pf.createEphemeralProperty("Marker Count", 0);

        scheduler.registerSubsystem(this);
    }

    @Override
    public void periodic() {
        double markerCount = this.markerCount.get();
    }
}