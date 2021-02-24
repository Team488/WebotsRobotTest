package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.subsystems.drive.control_logic.HeadingModule;

public class TurnToAngleCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final PIDManager pid;
    final HeadingModule headingModule;
    final OperatorInterface oi;
    final PoseSubsystem pose;

    private double goal;


    @Inject
    public TurnToAngleCommand(OperatorInterface oi, DriveSubsystem driveSubsystem, CommonLibFactory clf, PIDFactory pf, PoseSubsystem pose) {
        this.oi = oi;
        this.pid = pf.createPIDManager("Rotate");
        this.driveSubsystem = driveSubsystem;
        headingModule = clf.createHeadingModule(pid);
        this.pose = pose;
    }
    
    @Override
    public void initialize() {
        log.info("Initializing");
        this.pid.setP(0.015);
        this.pid.setD(0.015);


    }

    @Override
    public void execute() {
        double power = headingModule.calculateHeadingPower(goal);
        driveSubsystem.tankDrive(-power, power);

    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    @Override
    public boolean isFinished() {
        if(0.25 > Math.abs(goal - pose.getCurrentHeading().getValue()) % 360) {
            return true;
        }
        return false;
    }
    
}
