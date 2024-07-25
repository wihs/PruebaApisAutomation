package com.bdd.api.stepDefinition;

import com.bdd.api.step.APIStep;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.io.IOException;

public class APIStepDefinition {
    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Steps
    private APIStep apiStep;

    @Dado("^que configuro el body del servicio: \"([^\"]*)\"$")
    public void configuroElBodyDelServicio(String servicio, DataTable dataTable) {
        apiStep.configuroElBodyDelServicio(servicio, dataTable);
    }

    @Y("^configuro las cabeceras$")
    public void configuroLasCabeceras(DataTable dataTable) {
        apiStep.configurarLasCabeceras(dataTable);
    }

    @Cuando("^ejecuto el servicio con la configuracion del body y cabeceras$")
    public void ejecutoElServicioConLaConfiguracionDelBodyYCabeceras(DataTable dataTable) {
        apiStep.ejecutarElServicioConLaConfiguracionDelBodyYCabeceras(dataTable);
    }

    @Entonces("^valido que el codigo de respuesta sea \"([^\"]*)\"$")
    public void validoQueElCodigoDeRespuestaSea(int codRespuesta) {
        Assert.assertTrue("El codigo de respuesta no es el esperado...",
                apiStep.validarQueElCodigoDeRespuestaSea(codRespuesta));
    }

    @Y("^guardo la respuesta de la ejecucion$")
    public void guardoLaRespuestaDeLaEjecucion() throws IOException {
        apiStep.guardarLaRespuetaDeLaEjecucion();
    }

    @Cuando("^ejecuto el servicio sin configuraciones$")
    public void ejecutoElServicioSinConfiguraciones(DataTable dataTable) {
        apiStep.ejecutarElServicioSinLaConfiguracionDelBodyYCabeceras(dataTable);
    }
}
