package space.exploration.mars.rover.kernel;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yammer.metrics.core.Meter;
import communications.protocol.ModuleDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.exploration.communications.protocol.InstructionPayloadOuterClass;
import space.exploration.communications.protocol.communication.RoverStatusOuterClass;
import space.exploration.communications.protocol.service.WeatherQueryOuterClass;
import space.exploration.mars.rover.animation.SleepBreather;
import space.exploration.mars.rover.animation.SleepingAnimationEngine;
import space.exploration.mars.rover.environment.Cell;
import space.exploration.mars.rover.environment.MarsArchitect;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class DANSensingState implements State {
    private Logger                  logger             = LoggerFactory.getLogger(DANSensingState.class);
    private Meter                   requests           = null;
    private Rover                   rover              = null;
    private SleepingAnimationEngine danAnimationEngine = null;

    public DANSensingState(Rover rover) {
        this.rover = rover;
        requests = this.rover.getMetrics().newMeter(DANSensingState.class, getStateName(), "requests", TimeUnit
                .HOURS);
    }

    @Override
    public void receiveMessage(byte[] message) {
        logger.info("Adding message to the instruction Queue, current length = " + rover.getInstructionQueue().size());
        rover.reflectRoverState();
        rover.getInstructionQueue().add(message);
        try {
            rover.writeSystemLog(InstructionPayloadOuterClass.InstructionPayload.parseFrom(message), rover
                    .getInstructionQueue().size());
        } catch (InvalidProtocolBufferException ipe) {
            rover.writeErrorLog("Invalid Protocol Buffer Exception", ipe);
        }
    }

    @Override
    public void shootNeutrons() {
        requests.mark();
        rover.reflectRoverState();
        MarsArchitect marsArchitect = rover.getMarsArchitect();
        Cell          robot         = marsArchitect.getRobot();
        Color         robotColor    = robot.getColor();

        /* perform animations here. */
        SleepBreather sleepBreather = new SleepBreather(robot, new Color(102, 0, 102), new Color(153, 204, 255));
        danAnimationEngine = new SleepingAnimationEngine(rover, sleepBreather);
        danAnimationEngine.sleep();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        danAnimationEngine.wakeupRover();
        robot.setColor(robotColor);

        RoverStatusOuterClass.RoverStatus.Location.Builder lBuilder = RoverStatusOuterClass.RoverStatus.Location
                .newBuilder().setX(robot.getLocation().x).setY(robot.getLocation
                        ().y);

        RoverStatusOuterClass.RoverStatus.Builder rBuilder = RoverStatusOuterClass.RoverStatus.newBuilder();
        RoverStatusOuterClass.RoverStatus status = rBuilder.setBatteryLevel(rover.getBattery().getPrimaryPowerUnits())
                .setSolNumber(rover.getSpacecraftClock().getSol())
                .setLocation(lBuilder.build()).setNotes("DAN Spectroscope engaged!")
                .setModuleMessage(rover.getDanSpectrometer().scanForWater().toByteString())
                .setSCET(System.currentTimeMillis()).setModuleReporting(ModuleDirectory.Module.DAN_SPECTROMETER
                                                                                .getValue())
                .build();

        rover.state = rover.transmittingState;
        rover.transmitMessage(status.toByteArray());
    }

    @Override
    public void transmitMessage(byte[] message) {

    }

    @Override
    public void exploreArea() {

    }

    @Override
    public void move(InstructionPayloadOuterClass.InstructionPayload.TargetPackage payload) {

    }

    @Override
    public void hibernate() {

    }

    @Override
    public void senseWeather(WeatherQueryOuterClass.WeatherQuery weatherQuery) {

    }

    @Override
    public void scanSurroundings() {

    }

    @Override
    public void activateCameraById(String camId) {

    }

    @Override
    public void performRadarScan() {

    }

    @Override
    public void sleep() {

    }

    @Override
    public void wakeUp() {

    }

    @Override
    public void getSclkInformation() {

    }

    @Override
    public String getStateName() {
        return "DANSensing State";
    }

    @Override
    public void synchronizeClocks(String utcTime) {

    }

    @Override
    public void gracefulShutdown() {

    }

    @Override
    public Meter getRequests() {
        return requests;
    }
}
