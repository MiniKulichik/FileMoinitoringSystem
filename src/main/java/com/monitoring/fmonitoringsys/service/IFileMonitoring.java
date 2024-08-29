package com.monitoring.fmonitoringsys.service;

import com.monitoring.fmonitoringsys.to.InfoFileTO;

import java.util.List;

public interface IFileMonitoring {
    List<InfoFileTO> monitorFolder();
}
