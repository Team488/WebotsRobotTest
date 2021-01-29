
package competition;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import competition.operator_interface.OperatorCommandMap;
import competition.simulation.WebotsClient;
import competition.subsystems.SubsystemDefaultCommandMap;
import edu.wpi.first.hal.sim.DriverStationSim;
import xbot.common.command.BaseRobot;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;
import xbot.common.injection.wpi_factories.DevicePolice;
import xbot.common.simulation.SimulationPayloadDistributor;

public class Robot extends BaseRobot {

    DriverStationSim dsSim;
    WebotsClient webots;
    DevicePolice devicePolice;
    SimulationPayloadDistributor distributor;

    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
        this.devicePolice = this.injector.getInstance(DevicePolice.class);
        this.distributor = this.injector.getInstance(SimulationPayloadDistributor.class);
    }

    @Override
    protected void setupInjectionModule() {
        if (BaseRobot.isReal()) {
            this.injectionModule = new CompetitionModule(true);
        } else {
            this.injectionModule = new TemplateSimulationModule();
        }
    }

    @Override
    public void simulationInit() {
        // Mark robot as active in teleop if we can
        dsSim = new DriverStationSim();
        dsSim.setEnabled(true);

        webots = this.injector.getInstance(WebotsClient.class);
        webots.initialize();
        
    }

    @Override
    public void simulationPeriodic() {
        // Feed watchdogs
        dsSim.notifyNewData();

        // find all CANTalons
        List<MockCANTalon> talons = new ArrayList<MockCANTalon>();        
        for (Object o : devicePolice.registeredChannels.values()) {
            if(o instanceof MockCANTalon) {
                talons.add((MockCANTalon)o);
            }
        }

        JSONObject response = webots.sendMotors(talons);

        distributor.distributeSimulationPayload(response);
    }
}
