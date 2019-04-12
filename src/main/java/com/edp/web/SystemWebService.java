package com.edp.web;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class SystemWebService {



    SystemWebService() {
    }

    public Mono<ServerResponse> getDataLockStatus(ServerRequest request) {
//        this.parametersService.setLockStatus("6669", "nodes", "996956954", "Reading");
//        this.parametersService.setLockStatus("6668", "nodes", "996956954", "Reading");
//        this.parametersService.setLockStatus("6669", "devices", "996956957", "Reading");
//        this.parametersService.setLockStatus("6669", "devices", "996956955", "Reading");
//        this.parametersService.setLockStatus("6669", "nodes", "996956950", "Editing");
//        this.parametersService.setLockStatus("6669", "nodes", "996956951", "Reading");
        String tableName = request.queryParams().get("tableName").toString().replace("[", "").replace("]", "");
        String oid = request.queryParams().get("oid").toString().replace("[", "").replace("]", "");
       // HashMap resp = this.parametersService.getLockStatus(tableName, oid);
//        HashMap resp = this.parametersService.getLockStatus("nodes", "996956950");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.just("dd"), String.class);
    }

    public Mono<ServerResponse> setDataLockStatus(ServerRequest request) {
        request.bodyToMono(String.class).subscribe(d -> {
            try{
            JSONObject data = new JSONObject(d);
            String userId = data.getString("userId");
            String tableName = data.getString("tableName");
            String oid = data.getString("oid");
            String status = data.getString("status");
          //  this.parametersService.setLockStatus(userId, tableName, oid, status);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        JSONObject results = new JSONObject();
        results.put("results","done");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.just(results.toString()), String.class);
    }


}
