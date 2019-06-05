package com.edp.system;


import com.edp.organization.OrganizationDataService;
import com.edp.organization.models.Company;
import com.edp.organization.models.SecUser;
import com.edp.system.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


@Service
public class SystemWebService {

    @Autowired
    private SystemDataService systemDataService;
    @Autowired
    private OrganizationDataService organizationDataService;
//    @Autowired
//    private AttachmentService attachmentService;

    Mono<ServerResponse> userNotFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser()), SecUser.class);

    public SystemWebService() {
    }


    /**
     * GET ALL Users info from database
     */

    public Mono<ServerResponse> getAction(ServerRequest request) {
        String companyId = request.pathVariable("companyId");
        Company company = organizationDataService.getCompany(companyId);
        List<Task> actionsList = systemDataService.getActionByCompany(company.getCompanyType(), company.getInPeriod());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.fromIterable(actionsList), Task.class);
    }


    /***
     * File Upload
     */
    public Mono<ServerResponse> upload(ServerRequest request) {

        return ServerResponse.ok().body(request.body(BodyExtractors.toMultipartData()).flatMap(f -> {
            try {

                Map<String, Part> file = f.toSingleValueMap();
                FilePart filePart = (FilePart) file.get("file");
                Path tempFile = Files.createTempFile("upload_file", filePart.filename());
                File dest = tempFile.toFile();
                InputStream inputStream = new FileInputStream(dest);
//                attachmentService.storeFile(filePart.filename(), "image/jpeg", inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Mono.just("123");

        }), String.class);
    }


}
