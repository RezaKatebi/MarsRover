package space.exploration.mars.rover.kernel;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import space.exploration.spice.utilities.EphemerisConversionUtil;

import java.util.concurrent.TimeUnit;

@Ignore
public class EphemerisUtilTest extends TestCase {
    private EphemerisConversionUtil ephemerisConversionUtil = new EphemerisConversionUtil("ephemerisLib.out");

    @Test
    public void testEphemerisConversion() {
        double ephemerisTime = 507526737.000000d;
        for (int i = 0; i < 10; i++) {

            try {
                Thread.sleep(1000l);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ephemerisConversionUtil.updateClock(Double.toString(ephemerisTime));
            assertEquals(1239, ephemerisConversionUtil.getSol());
            ephemerisTime += TimeUnit.MINUTES.toSeconds(1l);
            System.out.println(ephemerisTime);
            System.out.println(ephemerisConversionUtil.getSclkTime());
        }
    }
}
