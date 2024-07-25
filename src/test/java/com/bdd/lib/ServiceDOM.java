package com.bdd.lib;

import com.bdd.builder.ApiConfig;
import com.bdd.builder.ApiConfigBuilder;
import com.bdd.generic.ServiceDomImpl;
import com.bdd.util.UtilApi;
import cucumber.api.DataTable;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.steps.ScenarioSteps;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import net.thucydides.core.util.EnvironmentVariables;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class ServiceDOM extends ScenarioSteps implements ServiceDomImpl {

    private final String sourceClass = this.getClass().getName();
    private transient EnvironmentVariables enviromenntVariables;
    private transient RequestSpecification reqConfig = null;

    public String configurerBodyRequest(String pathServiceRequest, DataTable dataTableRequestValues) {
        File file = new File(pathServiceRequest);
        String bodyRequest = "";

        try {
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONObject jsonObject = new JSONObject(content);
            bodyRequest = jsonObject.toString();
            List<Map<String, String>> listBodyRequest = dataTableRequestValues.asMaps(String.class, String.class);

            System.out.println("List Body Request, de un mapa: \n" + listBodyRequest);
            String newJson;
            //usar un for mejorado para evitar colocar el hasNext
            for (Iterator var9 = listBodyRequest.iterator(); var9.hasNext(); bodyRequest = newJson) {
                Map<String, String> stringStringMap = (Map) var9.next();
                newJson = UtilApi.updateValueInJson(bodyRequest, (String) stringStringMap.get("key"),
                        ((String) stringStringMap.get("valor")).replace("%WHITE%", " ").replace("%BAR%", "|"));
            }
        } catch (IOException var11) {
            UtilApi.logger(this.getClass()).throwing(this.sourceClass, "configurerBodyRequest()", var11);
        }
        UtilApi.logger(this.getClass()).log(Level.INFO, "BODY-REQUEST: {0}", bodyRequest);
        return bodyRequest;
    }
    // voy a devolver en este método una cabecera configurada.
    public Headers configurerHeaders(DataTable dataTable) {
        List<Header> headerList = new LinkedList();
        // DataTable se convertirá en un mapa para poder recorrer los valores internos de este.
        List<Map<String, String>> listCabecera = dataTable.asMaps(String.class, String.class);
        Iterator var4 = listCabecera.iterator();

        // Recorrer el mapa de la var4 para buscar los valores de "cabeceras" y "valor"
        //usar un for mejorado para evitar colocar el hasNext
        while (var4.hasNext()) {
            Map<String, String> stringStringMap = (Map) var4.next();
            Header header = new Header((String) stringStringMap.get("cabeceras"), (String) stringStringMap.get("valor"));
            headerList.add(header);
        }

        Headers headers = new Headers(headerList);
        //Log de eventos en consola con el valor de la respuesta de la configuración
        UtilApi.logger(ServiceDOM.class).log(Level.INFO, "CABECERAS: {0}", headers);
        return headers;
    }

    //metodo para ejecutar el servicio
    public Response ejecutarApiBuilder(ApiConfig apiConfig) {
        SerenityRest.useRelaxedHTTPSValidation();
        Response response = null;
        Map<String, Object> pathParams = Collections.emptyMap();
        Map<String, Object> queryParams = Collections.emptyMap();
        Map<String, Object> formUrlEncodedParams = Collections.emptyMap();
        String body = "";
        String tipoMetodo = apiConfig.getMethod().toUpperCase();
        String tipoApi = apiConfig.getApiType();
        String apiURL = apiConfig.getApiUrl();
        Headers headers = apiConfig.getHeaders();

        if (!tipoApi.equals("junit")) {
            //System.out.println("apiURL: " +EnvironmentSpecificConfiguration.from(this.environmentVariables).);
            //apiURL = EnvironmentSpecificConfiguration.from(this.environmentVariables).getProperty("url.base.api." + tipoApi) + apiURL;
            apiURL = tipoApi + apiURL;
        }

        if (apiConfig.getPathVariables() != null) {
            pathParams = apiConfig.getPathVariables();
        }
        if (apiConfig.getParams() != null) {
            queryParams = apiConfig.getParams();
        }
        if (apiConfig.getFormUrlEncoded() != null) {
            formUrlEncodedParams = apiConfig.getFormUrlEncoded();
        }

        if (apiConfig.getBody() != null) {
            body = apiConfig.getBody();
        }

        try {
            String var11 = tipoMetodo;
            byte var12 = -1;
            switch (var11.hashCode()) {
                case 70454:
                    if (var11.equals("GET")) {
                        var12 = 0;
                    }
                    break;
                case 2461856:
                    if (var11.equals("POST")) {
                        System.out.println("ESTOY EN POST");
                        var12 = 1;
                    }
                    break;
                case 2012838315:
                    if (var11.equals("DELETE")) {
                        System.out.println("DELETE");
                        var12 = 2;
                    }
                    break;
                case 79599:
                    if (var11.equals("PUT")) {
                        System.out.println("ACTUALIZAR");
                        var12 = 3;
                    }
                    break;
                case 75900968:
                    if (var11.equals("PATCH")) {
                        System.out.println("MODIFICACION PARCIAL");
                        var12 = 4;
                    }
                    break;
            }
            switch (var12) {
                case 0:
                    this.reqConfig = SerenityRest.given().headers(headers).pathParams(pathParams).queryParams(queryParams);
                    response = (Response) ((RequestSpecification) this.reqConfig.when().log().all()).get(apiURL, new Object[0]);
                    break;

                case 1:
                    //seteamos los headers en nuestra configuración de requisito
                    this.reqConfig = SerenityRest.given().headers(headers);
                    if (!formUrlEncodedParams.isEmpty()) {
                        UtilApi.logger(this.getClass()).log(Level.INFO, "Enviando formParams (form-url-encoded)");
                        this.reqConfig.formParams(formUrlEncodedParams);
                    } else {
                        //si está vacío se manda pero sin ello
                        UtilApi.logger(this.getClass()).log(Level.INFO, "Enviando solo el body request");
                        // seteando el body en nuestra configuración
                        this.reqConfig.body(body);
                    }
                    //seteamos pathParams, queryParams
                    this.reqConfig.pathParams(pathParams);
                    this.reqConfig.queryParams(queryParams);
                    response = (Response) ((RequestSpecification) this.reqConfig.when().log().all()).post(apiURL, new Object[0]);
                    break;

                case 2:
                    this.reqConfig = SerenityRest.given().headers(headers);
                    if (!formUrlEncodedParams.isEmpty()) {
                        UtilApi.logger(this.getClass()).log(Level.INFO, "Enviando formParams (form-url-encoded)");
                        this.reqConfig.formParams(formUrlEncodedParams);
                    }
                    this.reqConfig.pathParams(pathParams);
                    this.reqConfig.queryParams(queryParams);
                    response = (Response) ((RequestSpecification) this.reqConfig.when().log().all()).delete(apiURL, new Object[0]);
                    break;

                case 3:

                    this.reqConfig = SerenityRest.given().headers(headers);

                    if (!formUrlEncodedParams.isEmpty()) {
                        UtilApi.logger(this.getClass()).log(Level.INFO,"Enviando formParams (form-url-encoded)");
                        this.reqConfig.formParams(formUrlEncodedParams);
                    }
                    else {
                        //si está vacío se manda pero sin ello
                        UtilApi.logger(this.getClass()).log(Level.INFO, "Enviando solo el body request");
                        // seteando el body en nuestra configuración
                        this.reqConfig.body(body);
                    }
                    this.reqConfig.pathParams(pathParams);
                    this.reqConfig.queryParams(queryParams);
                    response = (Response) ((RequestSpecification)this.reqConfig.when().log().all()).put(apiURL, new Object[0]);
                    break;

                case 4:
                    this.reqConfig = SerenityRest.given().headers(headers);
                    if (!formUrlEncodedParams.isEmpty()) {
                        UtilApi.logger(this.getClass()).log(Level.INFO, "Enviando formParams (form-url-encoded)");
                        this.reqConfig.formParams(formUrlEncodedParams);
                    }
                    else {
                        //si está vacío se manda pero sin ello
                        UtilApi.logger(this.getClass()).log(Level.INFO, "Enviando solo el body request");
                        // seteando el body en nuestra configuración
                        this.reqConfig.body(body);
                    }
                    this.reqConfig.pathParams(pathParams);
                    this.reqConfig.queryParams(queryParams);
                    response = (Response) ((RequestSpecification) this.reqConfig.when().log().all()).patch(apiURL,new Object[0]);
                    break;

                default:
                    UtilApi.logger(ServiceDOM.class).log(Level.INFO, "Método {0} no es soportado.", tipoMetodo);
            }
        } catch (Exception var13) {
            UtilApi.logger(ServiceDOM.class).log(Level.WARNING, "Mensaje: ", var13.getMessage());
        }

        // assertion que sirve para poder validar que realmente estamos obteniendo alguna respuesta.
        assert response != null;

        response.prettyPeek();
        return response;
    }

    protected ApiConfigBuilder apiBuilder() {
        return new ApiConfigBuilder();
    }

}
