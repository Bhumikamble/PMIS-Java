
package com.example.demo.service;

import com.example.demo.dao.ServiceRecordDao;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuditService {

    private final ServiceRecordDao serviceRecordDao;

    public AuditService(ServiceRecordDao serviceRecordDao) {
        this.serviceRecordDao = serviceRecordDao;
    }

    public List<Map<String, Object>> getCostSummary() {
        return serviceRecordDao.getCostSummary();
    }
}
