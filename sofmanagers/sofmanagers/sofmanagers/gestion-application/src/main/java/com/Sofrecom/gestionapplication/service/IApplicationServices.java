package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.AppRequest;

import java.util.List;

public interface IApplicationServices {
    Long addApp(AppRequest appRequest);
    Long updateApp(Long appId, AppRequest appRequest);
    void deleteApplication(Long applicationId);
    AppRequest getApplicationWithDetails(Long applicationId);
    List<AppRequest> getAllApplications();
    List<AppRequest> getAllApplicationsHistory();
    void TerminateAppStatus(Long appId, AppRequest appRequest);
}
