package imp;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.ScenarioDataStore;
import helper.WaitHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WaitImp extends WaitHelper {

    private final Logger log = LogManager.getLogger(VariableImp.class);
    private static final String LOG_INFO = "Waited {} ms";
    @Step({"Wait <ms> ms"})
    public void waitTimeImp(int ms) {
        waitSomeTime(ms);
        log.info(LOG_INFO, ms);
    }

}
