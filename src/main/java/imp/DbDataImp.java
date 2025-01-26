package imp;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.ScenarioDataStore;
import com.thoughtworks.gauge.datastore.SpecDataStore;
import com.thoughtworks.gauge.datastore.SuiteDataStore;
import helper.DbDataHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DbDataImp extends DbDataHelper {
    private final Logger log = LogManager.getLogger(DbDataImp.class);
    private static final String LOG_INFO = "Query result stored on scenario store key: {}, value: {}";

    @Step({"Get column <columnName> data from query <queryName> result and save in scenario store",
            "Query <queryName> sorgusu sonucundan <columnName> sütun verisini scenario deposunda sakla"})
    public void GetQueryResultAndSaveValueWithColumnName(String columnName, String queryName) throws SQLException, ClassNotFoundException, IOException {
        Map<String, Object> queryResults = getQueResult(queryName);
        queryResults.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(columnName))
                .forEach(entry -> {
                    ScenarioDataStore.put(entry.getKey(), entry.getValue());
                    log.info(LOG_INFO, entry.getKey(), entry.getValue());
                });
    }

    @Step({"Get column <columnName> data from query <queryName> result and save in spec store",
            "Query <queryName> sorgusu sonucundan <columnName> sütun verisini spec deposunda sakla"})
    public void GetQueryResultAndSaveValueWithColumnNameSpec(String columnName, String queryName) throws SQLException, ClassNotFoundException, IOException {
        Map<String, Object> queryResults = getQueResult(queryName);
        queryResults.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(columnName))
                .forEach(entry -> {
                    SpecDataStore.put(entry.getKey(), entry.getValue());
                    log.info(LOG_INFO, entry.getKey(), entry.getValue());
                });
    }

    @Step({"Get column <columnName> data from query <queryName> result and save in suit store",
            "Query <queryName> sorgusu sonucundan <columnName> sütun verisini suit deposunda sakla"})
    public void GetQueryResultAndSaveValueWithColumnNameSuit(String columnName, String queryName) throws SQLException, ClassNotFoundException, IOException {
        Map<String, Object> queryResults = getQueResult(queryName);
        queryResults.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(columnName))
                .forEach(entry -> {
                    SuiteDataStore.put(entry.getKey(), entry.getValue());
                    log.info(LOG_INFO, entry.getKey(), entry.getValue());
                });
    }

    @Step({"Get column data from query <queryName> result and save all column data in scenario store with column name",
            "Query <queryName> sorgusu sonucundan dönen tüm datayı kolon isimleriyle birlikte senaryo deposunda sakla"})
    public void GetQueryResultAndSaveValueForAllColumnAndStoreScenarioStore(String queryName) throws SQLException, ClassNotFoundException, IOException {
        Map<String, Object> queryResults = getQueResult(queryName);
        queryResults.forEach(ScenarioDataStore::put);
        queryResults.forEach((key, value) -> log.info(LOG_INFO, key, value));
    }

    @Step({"Get column data from query <queryName> result and save all column data in spec store with column name",
            "Query <queryName> sorgusu sonucundan dönen tüm datayı kolon isimleriyle birlikte spec deposunda sakla"})
    public void GetQueryResultAndSaveValueForAllColumnAndStoreSpecStore(String queryName) throws SQLException, ClassNotFoundException, IOException {
        Map<String, Object> queryResults = getQueResult(queryName);
        queryResults.forEach(SpecDataStore::put);
        queryResults.forEach((key, value) -> log.info(LOG_INFO, key, value));
    }

    @Step({"Get column data from query <queryName> result and save all column data in suit store with column name",
            "Query <queryName> sorgusu sonucundan dönen tüm datayı kolon isimleriyle birlikte spec deposunda sakla"})
    public void GetQueryResultAndSaveValueForAllColumnAndStoreSuitStore(String queryName) throws SQLException, ClassNotFoundException, IOException {
        Map<String, Object> queryResults = getQueResult(queryName);
        queryResults.forEach(SuiteDataStore::put);
        queryResults.forEach((key, value) -> log.info(LOG_INFO, key, value));
    }


    @Step({"Get top <limit> records from <tableName> where <whereClause> order by <orderByClause> descending and store in scenario"})
    public void getTopRecordsAndStoreInScenario(int limit, String tableName, String whereClause, String orderByClause) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> results = getTopRecords(tableName, whereClause, orderByClause, limit);
        ScenarioDataStore.put("queryResults", results);
        log.info("Stored {} records in scenario store under key 'queryResults'", results.size());

        // Optionally, you can also store individual columns or rows if needed
        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> row = results.get(i);
            ScenarioDataStore.put("row_" + i, row);
            log.info("Stored row {} in scenario store under key 'row_{}'", i, i);
        }
    }

}
