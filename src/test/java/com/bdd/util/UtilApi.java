package com.bdd.util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.spi.json.JsonOrgJsonProvider;
import com.jayway.jsonpath.spi.mapper.JsonOrgMappingProvider;
import com.sun.istack.NotNull;
import cucumber.api.DataTable;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;
import java.util.logging.Logger;

public class UtilApi {

    public static Logger logger(@NotNull Class clase) {
        return Logger.getLogger(clase.getName());
    }

    public static String updateValueInJson(String jsonString, String keyPath, String value) {
        Configuration configuration = Configuration.builder().jsonProvider(new JsonOrgJsonProvider()).mappingProvider(new JsonOrgMappingProvider()).build();
        return JsonPath.using(configuration).parse(jsonString).set("$." + keyPath, value, new Predicate[0]).jsonString();
    }

    public static String getValueFromDataTable(DataTable dataTable, String title) {
        List<Map<String, String>> mapList = dataTable.asMaps(String.class, String.class);
        return (String) ((Map) mapList.get(0)).get(title);
    }

    public static void createFileByFormat (String serviceName, String formatFile, String response) throws IOException {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyMMddHHmmss.SSS");
        LocalDateTime now = LocalDateTime.now();

        File filePath = new File("response/api/" + serviceName + "/" + formatFile);
        filePath.mkdirs();

        String finalFileName = serviceName + "-" + dft.format(now) + "." + formatFile;
        File file = new File(filePath + "/" + finalFileName);
        System.out.println("finalFinalName: " + finalFileName);


        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(response);
        fileWriter.close();
    }

}
