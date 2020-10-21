package PredicateOpSortingExample;

import java.util.Optional;
import java.util.function.Predicate;


public class Configurator3000Testdrive {
    public static void main(String[] args) {
        Configurator3000 testObject = new Configurator3000();
        populateConfigurator(testObject);
        ConfigurationOp[] configOps = testObject.getOps();
        
        /*
         * Generic device require us to delete /a/{name}/c with dependencies on a /b
         * before possibly deleting that /b. For the same reason, /b is required to be
         * created before any, possibly dependant, /a/{name}/c are created.
         */
        Predicate<ConfigurationOp> deleteAFirst = (op) -> {
            return op.getOperation() == ConfigurationOp.DELETED && op.getPath().toString().contains(":a{");
        };
        Predicate<ConfigurationOp> createBFirst = (op) -> {
            return op.getOperation() == ConfigurationOp.CREATED && op.getPath().toString().contains(":b{");
        };
        
        try {
            applyOps(configOps, Optional.of(deleteAFirst));
            applyOps(configOps, Optional.of(createBFirst));
            applyOps(configOps, Optional.empty());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void applyOps(ConfigurationOp[] ops, Optional<Predicate<ConfigurationOp>> predOptional) throws Exception {
        Predicate<ConfigurationOp> pred = predOptional.orElse((ConfigurationOp) -> {return true;});
        int i = 0;
        for(ConfigurationOp op : ops) {
            if(op.getOpDone()) {
                i++;
                continue;
            }
            if( pred.negate().test(op) ) {
                i++;
                continue;
            }
            System.out.println("Doing operation " + op.toString() + "with index: " + i);
            i++;
            op.setOpDone();
        }
    }

    
    public static void populateConfigurator(Configurator3000 testObject) {
        testObject.addOp(new ConfigurationOp(ConfigurationOp.CREATED, ":a{things}", ""));
        testObject.addOp(new ConfigurationOp(ConfigurationOp.CREATED, ":b{noonecaresaboutyoub}", ""));
        testObject.addOp(new ConfigurationOp(ConfigurationOp.CREATED, ":a{stuff}", ""));
        testObject.addOp(new ConfigurationOp(ConfigurationOp.CREATED, ":a{stuff}:c{BoopFoop}", "dep : blooperfooper"));
        testObject.addOp(new ConfigurationOp(ConfigurationOp.CREATED, ":b{blooperfooper}", ""));
        testObject.addOp(new ConfigurationOp(ConfigurationOp.DELETED, ":b{blooperfooper}", ""));
        testObject.addOp(new ConfigurationOp(ConfigurationOp.DELETED, ":a{stuff}", ""));
        testObject.addOp(new ConfigurationOp(ConfigurationOp.CREATED, ":a{otherstuffthings}", ""));
    }
}
