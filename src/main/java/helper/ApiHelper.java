package helper;

import enums.RequestInfo;
import exceptions.RequestNotDefined;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.StoreApiInfo;

import static enums.RequestInfo.REQUEST;

public class ApiHelper {

    private final Logger log = LogManager.getLogger(ApiHelper.class);
    private static ApiHelper instance;
    private RequestSpecification requestSpecification;

    private ApiHelper() {
        init();
    }

    public static ApiHelper getInstance() {
        if (instance == null) {
            instance = new ApiHelper();
        }
        return instance;
    }

    public RequestSpecification getRequestSpecification() {
        return (RequestSpecification) StoreApiInfo.get(RequestInfo.REQUEST.info);
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response getResponse() {
        return (Response) StoreApiInfo.get(RequestInfo.RESPONSE.info);
    }

    public void setResponse(Response response) {
        StoreApiInfo.put(RequestInfo.RESPONSE.info, response);
    }

    public void init() {
        StoreApiInfo.put(RequestInfo.REQUEST.info, RestAssured.given().relaxedHTTPSValidation());
    }

    public void defineNewRequest() {
        init();
    }


}
