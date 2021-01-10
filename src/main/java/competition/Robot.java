
package competition;

import java.util.ArrayList;
import java.util.List;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import edu.wpi.first.hal.sim.DriverStationSim;
import xbot.common.command.BaseRobot;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;
import xbot.common.injection.wpi_factories.DevicePolice;

public class Robot extends BaseRobot {

    DriverStationSim dsSim;
    WebotsClient webots;
    DevicePolice devicePolice;

    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
        this.devicePolice = this.injector.getInstance(DevicePolice.class);
    }

    @Override
    protected void setupInjectionModule() {
        if (BaseRobot.isReal()) {
            this.injectionModule = new CompetitionModule(true);
        } else {
            this.injectionModule = new SimulationModule();
        }
    }

    @Override
    public void simulationInit() {
        // Mark robot as active in teleop if we can
        dsSim = new DriverStationSim();
        dsSim.setEnabled(true);

        webots = new WebotsClient();
        webots.initialize();
        
    }

    @Override
    public void simulationPeriodic() {
        // Feed watchdogs
        dsSim.notifyNewData();

        // find all CANTalons
        List<MockCANTalon> talons = new ArrayList<MockCANTalon>();        
        for (Object o : devicePolice.registeredDevices.values()) {
            if(o instanceof MockCANTalon) {
                talons.add((MockCANTalon)o);
            }
        }

        webots.sendMotors(talons);
    }
}
