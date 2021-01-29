package competition.simulation;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;

public class ResetPositionCommand extends BaseCommand {

    final WebotsClient webots;

    @Inject
    public ResetPositionCommand(WebotsClient webots) {
        this.webots = webots;
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        webots.resetPosition();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void execute() {
    }
}
