package in.marsExplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

public class SimulatorRunner {
    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] a)
    {

        InputStream is = null;
        try {
            if (a == null || a.length==0)
                is = System.in;
            else {
                try {
                    File initialFile = new File(a[0]);
                    is = new FileInputStream(initialFile);
                }
                catch (Exception e)
                {
                    LOG.severe(e.getMessage());
                    return;
                }
            }
            MarsExplorerSimulator marsExplorerSimulator = new MarsExplorerSimulator(is);
            marsExplorerSimulator.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.severe(e.getMessage());
        }
    }
}
