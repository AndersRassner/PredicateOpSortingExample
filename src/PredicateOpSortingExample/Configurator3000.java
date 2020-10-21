package PredicateOpSortingExample;

import java.util.ArrayList;

/**
 * 
 * Simple class to easily be able to build an array of operations.
 *
 */
public class Configurator3000 {
    ArrayList<ConfigurationOp> configOps;

    public Configurator3000() {
        configOps = new ArrayList<ConfigurationOp>();
    }
    public void addOp(ConfigurationOp op) {
        configOps.add(op);
    }
    public ConfigurationOp[] getOps() {
        return configOps.toArray(new ConfigurationOp[0]);
    }
    
}
