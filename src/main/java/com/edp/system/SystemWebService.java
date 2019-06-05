package com.edp.system;


import com.edp.fileservice.AttachmentService;
import com.edp.organization.OrganizationDataService;
import com.edp.organization.models.Company;
import com.edp.organization.models.SecUser;
import com.edp.system.models.Task;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
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
import java.util.Optional;


@Service
public class SystemWebService {

    @Autowired
    private SystemDataService systemDataService;
    @Autowired
    private OrganizationDataService organizationDataService;
    @Autowired
    private AttachmentService attachmentService;

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
                filePart.transferTo(dest);
                InputStream inputStream = new FileInputStream(dest);
                DBObject metadata = new BasicDBObject();
                String objectId = attachmentService.storeFile(inputStream,filePart.filename(),file.get("file").headers().getContentType().toString(),metadata);
                System.out.println(objectId);
                return Mono.just(objectId);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }), String.class);
    }

    public Mono<ServerResponse> download(ServerRequest request) {

        String fileId = request.pathVariable("id");
        GridFSFile file = attachmentService.getFileById(fileId);
        System.out.println(new GridFsResource(file));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(file.toString()),String.class);
    }


}
