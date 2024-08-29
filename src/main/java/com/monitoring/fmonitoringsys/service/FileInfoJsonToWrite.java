package com.monitoring.fmonitoringsys.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoring.fmonitoringsys.to.InfoFileTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileInfoJsonToWrite implements IFileInfoWritable {

    private static final String LOG_FILE_PATH = "src/main/resources/logs/logFile.json";

    @Override
    public void writeFilesInfo(List<InfoFileTO> filesInfo) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(LOG_FILE_PATH), filesInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}