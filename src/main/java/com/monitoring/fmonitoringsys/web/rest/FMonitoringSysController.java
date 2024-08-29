package com.monitoring.fmonitoringsys.web.rest;

import com.monitoring.fmonitoringsys.service.FileMonitoringService;
import com.monitoring.fmonitoringsys.to.InfoFileTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FMonitoringSysController {

    @Autowired
    private FileMonitoringService fileMonitoringService;

    @GetMapping("/filesinfo")
    public List<InfoFileTO> getFilesInfo() {
        return fileMonitoringService.monitorFolder();
    }

    @GetMapping("/file")
    public InfoFileTO getFileFromMD5(@RequestParam String md5) {
        return fileMonitoringService.monitorFolder().stream()
                .filter(file -> file.getHashMd5().equals(md5))
                .findFirst()
                .orElse(null);
    }
}
