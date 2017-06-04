package space.exploration.mars.rover.Radar;

import space.exploration.mars.rover.animation.RadarAnimationEngine;
import space.exploration.mars.rover.bootstrap.MatrixCreation;
import space.exploration.mars.rover.environment.RadarContactCell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by sanket on 6/3/17.
 */
public class RadarAnimationTest {

    public static void main(String[] args) throws Exception {
        Properties                       marsConfig           = MatrixCreation.getMatrixConfig();
        RadarAnimationEngine             radarAnimationEngine = new RadarAnimationEngine(marsConfig);
        java.util.List<RadarContactCell> contacts             = new ArrayList<>();
        contacts.add(new RadarContactCell(marsConfig, new Point(175, 175), Color.black, 8));
        contacts.add(new RadarContactCell(marsConfig, new Point(200, 100), Color.black, 8));
        contacts.add(new RadarContactCell(marsConfig, new Point(225, 150), Color.green, 8));
        contacts.add(new RadarContactCell(marsConfig, new Point(100, 150), Color.green, 8));
        contacts.add(new RadarContactCell(marsConfig, new Point(350, 250), Color.green, 8));
        contacts.add(new RadarContactCell(marsConfig, new Point(250, 250), Color.green, 8));
        contacts.add(new RadarContactCell(marsConfig, new Point(450, 250), Color.green, 8));
        contacts.add(new RadarContactCell(marsConfig, new Point(550, 550), Color.green, 10));
        contacts.add(new RadarContactCell(marsConfig, new Point(175, 400), Color.green, 10));
        radarAnimationEngine.setContacts(contacts);
        radarAnimationEngine.renderLaserAnimation();
    }
}
