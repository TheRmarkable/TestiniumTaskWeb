package imp;


import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.datastore.ScenarioDataStore;
import com.thoughtworks.gauge.datastore.SpecDataStore;
import com.thoughtworks.gauge.datastore.SuiteDataStore;
import enums.RequestInfo;
import exceptions.NullResponse;
import exceptions.NullValue;
import exceptions.RequestNotDefined;
import helper.DocumentHelper;
import helper.FileHelper;
import helper.RequestBodyHelper;
import helper.ResponseBodyHelper;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import utils.StoreApiInfo;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static enums.RequestInfo.REQUEST;
import static enums.RequestInfo.RESPONSE;

public class RequestBodyImp extends RequestBodyHelper {

    private final Logger log = LogManager.getLogger(RequestBodyImp.class);

    @Step({"Add payload as String from resource <file name>",
            "Add body as String from resource <file name>",
            "Dosyayadan String tipinde istek body'si ekle <dosya adı>"})
    public void addBodyAsString(String fileName) throws RequestNotDefined {
        FileHelper fileHelper = new FileHelper();
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getPath();
        String payLoad = fileHelper.readFileAsString(filePath);
        addBody(payLoad);
    }

    @Step({"Add payload as file from resource <file name>",
            "Add body as file from resource <file name>",
            "Dosya tipinde istek body'si ekle <dosya adı>"})
    public void addBodyAsFile(String fileName) throws RequestNotDefined {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getPath();
        File file = new File(filePath);
        addBody(file);
    }

    @Step({"Add payload as map <table>",
            "Add body as map <table>",
            "Tablodan istek body'si ekle <table>"})
    public void addBodyAsFile(Table table) throws RequestNotDefined {
        List<TableRow> rows = table.getTableRows();
        HashMap<Object, Object> body = new HashMap<>();
        for (TableRow row : rows) {
            body.put(row.getCellValues().get(0), row.getCellValues().get(1));
        }
        addBody(body);
    }

    @Step({"Add body as map with types <table>"})
    public void addBodyAsFileWithTypes(Table table) throws RequestNotDefined {
        List<TableRow> rows = table.getTableRows();
        HashMap<Object, Object> body = new HashMap<>();
        for (TableRow row : rows) {
            if (row.getCellValues().get(2).equals("int")) {
                body.put(row.getCellValues().get(0), Integer.parseInt(row.getCellValues().get(1)));

            } else if (row.getCellValues().get(2).equals("str")) {

                body.put(row.getCellValues().get(0), row.getCellValues().get(1));
            } else {
                body.put(row.getCellValues().get(0), row.getCellValues().get(1));
            }

        }
        addBody(body);
    }

    public void addBodyAsFile(Map<String, Object> body) throws RequestNotDefined {
        addBody(body);
    }

    @Step({"Add payload from scenario store with <key>",
            "Add body from scenario store with <key>",
            "Senaryo kayıtlı verisinden istek body'si ekle, kayıt anahtarı <key>"})
    public void addBodyFromScenarioStore(String key) throws RequestNotDefined {
        System.out.println("KEYYYYYYY" +ScenarioDataStore.get(key));
        addBody(ScenarioDataStore.get(key));
    }

    @Step({"Add payload from scenario store with <key> in txt format",
            "Add body from scenario store with <key> in txt format",
            "Senaryo kayıtlı verisinden istek body'sini txt olarak ekle, kayıt anahtarı <key>"})
    public void addBodyFromScenarioStoreAsTxt(String key) throws RequestNotDefined {
        String body = String.valueOf(ScenarioDataStore.get(key));
        addBody(body);
    }

    @Step({"Add payload from suite store with <key>",
            "Add body from suite store with <key>",
            "Suit kayıtlı verisinden istek body'si ekle, kayıt anahtarı <key>"})
    public void addBodyFromScenarioSuite(String key) throws RequestNotDefined {
        addBody(SuiteDataStore.get(key));
    }

    @Step({"Add payload from spec store with <key>",
            "Add body from spec store with <key>",
            "Spec kayıtlı verisinden istek body'si ekle, kayıt anahtarı <key>"})
    public void addBodyFromScenarioSpec(String key) throws RequestNotDefined {
        addBody(SpecDataStore.get(key));
    }

    @Step({"Get body with <key> from store and update <selector> as <key1> from scenario data",
            "<key> anahtarı ile saklanan body'den, <selector> değerini al, kayıtlı <key1>'in değeri ile güncelle"})
    public void updateBodyFromScenarioData(String key, String selector, String key1) {
        DocumentHelper documentHelper = new DocumentHelper();
        String body = String.valueOf(Utils.getFromStoreData(key));
        String newValue = String.valueOf(ScenarioDataStore.get(key1));
        newValue = newValue.equalsIgnoreCase("null") ? null : newValue;
        Object newBody = documentHelper.updateDocument(body, selector, newValue);
        ScenarioDataStore.put(key, newBody);
        log.info("\"{}\" is update as \"{}\" from \"{}\" in scenario store", selector, newValue, key);
    }

    @Step({"Update <selector>=<value> json from stored scenario with key <key>"})
    public void updateBody(String selector, String newValue, String key) {
        DocumentHelper documentHelper = new DocumentHelper();
        String body = String.valueOf(Utils.getFromStoreData(key));
        newValue = newValue.equalsIgnoreCase("null") ? null : newValue;


        Object newBody = documentHelper.updateDocument(body, selector, newValue);
        ScenarioDataStore.put(key, newBody);
        log.info("\"{}\" is update as \"{}\" from \"{}\" in scenario store", selector, newValue, key);
    }

    @Step({"Update <selector>=<value> text from stored scenario with key <key>"})
    public void updateTxtBody(String selector, String newValue, String key) {
        String body = String.valueOf(Utils.getFromStoreData(key));

        newValue = newValue.equalsIgnoreCase("null") ? "" : newValue;

        String updatedBody = body.replaceAll("(?<=" + selector + "=)[^&]*", newValue);

        ScenarioDataStore.put(key, updatedBody);

        log.info("\"{}\" is updated as \"{}\" from \"{}\" in scenario store", selector, newValue, key);
    }



    @Step({"Get json file <file> and update as <selector>=<new> then add request",
            "Resource'dan json dosyasını <filename> oku ve <selector>=<value> olarak güncele sonra requeste ekle "})
    public void updateAndAdd(String fileName, String selector, String newValue) throws RequestNotDefined {
        DocumentHelper documentHelper = new DocumentHelper();
        FileHelper fileHelper = new FileHelper();
        var filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getPath();

        var body = fileHelper.readFileAsString(filePath);
        newValue = newValue.equalsIgnoreCase("null") ? null : newValue;
        Object newBody = documentHelper.updateDocument(body, selector, newValue);
        addBody(newBody);
    }

    @Step({"Get json file <file> and increase the value of <selector> by one then add to request"})
    public void updateAndAdd(String file, String selector) throws RequestNotDefined {
        DocumentHelper documentHelper = new DocumentHelper();
        FileHelper fileHelper = new FileHelper();
        var filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getPath();
        var body = fileHelper.readFileAsString(filePath);
        int oldValue = Integer.parseInt(documentHelper.getValueByName(body, selector));
        int newValue = oldValue + 1; // Increase the value by 1
        Object newBody = documentHelper.updateDocument(body, selector, String.valueOf(newValue));

        // Write the updated content back to the file
        fileHelper.writeStringToFile(filePath, newBody.toString());

        addBody(newBody);
    }

    @Step({"Get json file <file> and increase the value of <selector> by one"})
    public void updateValue(String file, String selector) throws RequestNotDefined {
        DocumentHelper documentHelper = new DocumentHelper();
        FileHelper fileHelper = new FileHelper();
        var filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getPath();
        var body = fileHelper.readFileAsString(filePath);
        int oldValue = Integer.parseInt(documentHelper.getValueByName(body, selector));
        int newValue = oldValue + 1; // Increase the value by 1
        Object newBody = documentHelper.updateDocument(body, selector, String.valueOf(newValue));

        // Write the updated content back to the file
        fileHelper.writeStringToFile(filePath, newBody.toString());

        addBody(newBody);
    }

    @Step({"Update body in scenario store with key <bodyKey> by setting <field> to value from scenario store key <valueKey>",
            "Senaryo store'da kayıtlı body'nin <bodyKey> anahtarıyla kayıtlı body'nin <field> alanını, senaryo store'dan <valueKey> anahtarıyla alınan değer ile güncelle"})
    public void updateBodyFieldFromScenarioStore(String bodyKey, String field, String valueKey) {
        // ScenarioDataStore'dan body'yi al
        String body = String.valueOf(ScenarioDataStore.get(bodyKey));

        // ScenarioDataStore'dan değeri al
        String newValue = String.valueOf(ScenarioDataStore.get(valueKey));
        newValue = newValue.equalsIgnoreCase("null") ? null : newValue;

        // Body'yi güncelle
        DocumentHelper documentHelper = new DocumentHelper();
        Object updatedBody = documentHelper.updateDocument(body, field, newValue);

        // Güncellenmiş body'yi tekrar ScenarioDataStore'a kaydet
        ScenarioDataStore.put(bodyKey, updatedBody);

        log.info("Updated \"{}\" field in body stored with key \"{}\" to value from key \"{}\"", field, bodyKey, valueKey);
    }




}
