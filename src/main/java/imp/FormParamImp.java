package imp;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import helper.FormParamsHelper;
import java.util.Map;
import utils.Utils;


public class FormParamImp extends FormParamsHelper {


    @Step({"Add form parameter <key> = <value>", "Form parametresi ekle <key> = <value>"})
    public void addFormParamToReq(String key, String value) {
        addFormParam(key, value);
    }

    @Step({"Add form parameter <key> = <value>", "Form parametresi ekle <key> = <value>"})
    public void addFormParamToReqWithObjValue(String key, Object value) {
        addFormParam(key, value);
    }

    @Step({"Add form parameters <key> = <object type value>", "Form parametrelerini ekleyin <key> = <object type value>"})
    public void addFormParamsToReq(String key, Object value) {
        addFormParams(key, value);
    }

    @Step({"Form parameters <table>", "Form parametrelerini ekle <table>"})
    public void addFormParamToReq(Table table) {
        Utils utils = new Utils();
        Map<String, Object> parameters = utils.gaugeDataTableToMap(table);
        addFormParams(parameters);
    }

    public void addFormParametersFromTable(Map<String, Object> map) {
        addFormParams(map);
    }

}
