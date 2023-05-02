package com.databull.api.utils.rest;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.Objects;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static JSONObject getConnectorConfig(String type, String subType) throws IOException {
        File file = new ClassPathResource("connectors/").getFile();
        File[] files = file.listFiles();
        File connectorConfigFile = null;
        for(File sourcefile: files) {
            if(sourcefile.getName().equals(type) && sourcefile.isDirectory()) {
                File[] connectorFiles = sourcefile.listFiles();
                for(File connectorFile: connectorFiles) {
                    if(connectorFile.getName().equals(subType + "connector.json")) {
                        connectorConfigFile = connectorFile;
                        break;
                    }
                }
            }
        }

        if(Objects.isNull(connectorConfigFile))
            throw new NoSuchFileException("No such file present with given types");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(connectorConfigFile, JSONObject.class);
    }


}
