package com.monitoring.fmonitoringsys.service;

import com.monitoring.fmonitoringsys.to.InfoFileTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileMonitoringService implements IFileMonitoring {

    private static final String FOLDER_PATH = "src/main/resources/checkFolder";

    @Override
    public List<InfoFileTO> monitorFolder() {
        List<InfoFileTO> filesInfo = new ArrayList<>();
        File folder = new File(FOLDER_PATH);

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {
                InfoFileTO fileInfo = new InfoFileTO();
                fileInfo.setPath(file.getAbsolutePath());
                fileInfo.setName(file.getName());
                fileInfo.setByteSize(file.length());
                fileInfo.setLastModify(LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(file.lastModified()),
                        ZoneId.systemDefault()));
                fileInfo.setHashMd5(generateMD5(file));
                filesInfo.add(fileInfo);
            }
        }
        return filesInfo;
    }

    private String generateMD5(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] fileData = Files.readAllBytes(file.toPath());
            byte[] digest = md.digest(fileData);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Scheduled(fixedRate = 3000)
    public void scheduledMonitoring() {
        List<InfoFileTO> filesInfo = monitorFolder();
        new FileInfoJsonToWrite().writeFilesInfo(filesInfo);
    }
}
