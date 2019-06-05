package com.edp.system;


import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AttachmentService {

//    @Autowired
//    GridFsTemplate gridFsTemplate;
//
//    public GridFSFile getFileById(String id){
//        ObjectId objectId = new ObjectId(id);
//        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(objectId)));
//        return gridFsFile;
//    }
//
////    public GridFSFile getFileByFilename(String filename){
////        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
////        return gridFsFile;
////    }
//
//
//    public Optional<GridFsResource> getFileAsResource(final String id) {
//        return Stream.of(gridFsTemplate.getResources(getFileById(id).getFilename() + "*"))
//                .filter(gridFsResource -> gridFsResource.getId().toString().equals(id))
//                .findAny();
//    }
//
//
//    public String storeFile(final MultipartFile file) {
//
//        String filename = file.getOriginalFilename();
//
//        try {
//
//            InputStream inputStream = null;
//            inputStream = file.getInputStream();
//
//            ObjectId fileId = gridFsTemplate.store(inputStream, filename, file.getContentType());
//
//            return fileId.toString();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return null;
//
//    }
//
//    public String storeFile(String filename,String contentType ,final InputStream file) {
//
//        ObjectId fileId = gridFsTemplate.store(file, filename,contentType);
//
//        return fileId.toString();
//
//
//    }
//
//    public void delete(final String id) {
//        ObjectId objectId = new ObjectId(id);
//        gridFsTemplate.delete(new Query(Criteria.where("_id").is(objectId)));
//    }


}
