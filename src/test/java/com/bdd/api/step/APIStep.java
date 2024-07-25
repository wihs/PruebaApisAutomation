package com.bdd.api.step;

import com.bdd.lib.ServiceDOM;
import com.bdd.util.UtilApi;
import cucumber.api.DataTable;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.io.IOException;

import static com.bdd.Constants.DIR_REQUEST_JSON;

public class APIStep extends ServiceDOM {
    private String bodyRequest;
    private Headers cabecera;
    private String formato;
    private String nombreServicio;
    private Response response;

    public void configuroElBodyDelServicio(String servicio, DataTable dataTable) {
        String pathService = DIR_REQUEST_JSON + servicio + ".json";
        bodyRequest = configurerBodyRequest(pathService, dataTable);
    }

    public void configurarLasCabeceras(DataTable dataTable) {
        cabecera = configurerHeaders(dataTable);
    }

    public void ejecutarElServicioConLaConfiguracionDelBodyYCabeceras(DataTable dataTable) {
        String url = UtilApi.getValueFromDataTable(dataTable, "url");
        String path = UtilApi.getValueFromDataTable(dataTable, "path");
        String metodo = UtilApi.getValueFromDataTable(dataTable, "metodo");
        this.formato = UtilApi.getValueFromDataTable(dataTable, "formato");
        this.nombreServicio = UtilApi.getValueFromDataTable(dataTable, "servicio");

        System.out.println(cabecera);

        if (cabecera == null) {
            cabecera = new Headers();
        }
        if (bodyRequest == null) {
            bodyRequest = "{}";
        }

        response = ejecutarApiBuilder(apiBuilder()
                .withApiType(url)
                .withApiUrl(path)
                .withApiMethod(metodo)
                .withHeaders(cabecera)
                .withBody(bodyRequest)
                .build()
        );
    }

    public boolean validarQueElCodigoDeRespuestaSea(int codRespuesta) {
        System.out.println("El codigo de respuesta es: " + response.getStatusCode());
        if (codRespuesta != 200) {
            System.out.println("ESTADO ERRONEO");
        }
        return response.getStatusCode() == codRespuesta;
    }

    public void guardarLaRespuetaDeLaEjecucion() throws IOException {
        UtilApi.createFileByFormat(this.nombreServicio, this.formato, response.getBody().prettyPrint());
    }

    public void ejecutarElServicioSinLaConfiguracionDelBodyYCabeceras(DataTable dataTable) {
        String tipo = UtilApi.getValueFromDataTable(dataTable, "tipo");
        String url = UtilApi.getValueFromDataTable(dataTable, "url");
        String metodo = UtilApi.getValueFromDataTable(dataTable, "metodo");
        this.formato = UtilApi.getValueFromDataTable(dataTable, "formato");
        this.nombreServicio = UtilApi.getValueFromDataTable(dataTable, "servicio");
        cabecera = new Headers();
        bodyRequest = "";
        response = ejecutarApiBuilder(apiBuilder()
                .withApiType(tipo)
                .withApiUrl(url)
                .withApiMethod(metodo)
                .withHeaders(cabecera)
                .withBody(bodyRequest)
                .build()
        );
    }
}

