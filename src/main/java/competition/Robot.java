
package competition;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import edu.wpi.first.hal.sim.DriverStationSim;
import xbot.common.command.BaseRobot;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;
import xbot.common.injection.wpi_factories.DevicePolice;
import xbot.common.simulation.SimulationPayloadDistributor;

public class Robot extends BaseRobot {

    DriverStationSim dsSim;

    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
    }

    @Override
    protected void setupInjectionModule() {
        if (BaseRobot.isReal()) {
            this.injectionModule = new CompetitionModule(true);
        } else {
            this.injectionModule = new TemplateSimulationModule();
        }
    }
}
