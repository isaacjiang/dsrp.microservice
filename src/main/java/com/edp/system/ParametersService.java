package com.edp.system;


import com.edp.interfaces.MicroServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


@Service
public class ParametersService implements MicroServiceInterface {

    @Autowired
    private Environment environment;




    private String localIpAddress ="127.0.0.1";

    public ParametersService() {
    }

    @Override
    public MicroServiceInterface start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@Parameters");
        thr.start();
        return this;
    }

    @Override
    public void schedule() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {

    }

    public String getLocalIp() {
        if(localIpAddress.equals("127.0.0.1")) {
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                localIpAddress = socket.getLocalAddress().getHostAddress();
            } catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return localIpAddress;
    }


    public String getParameters(String key) {
        return this.environment.getProperty(key);
    }




//    public HashMap getLockStatus(String tableName, String oid) {
//        HashMap result = new HashMap();
//        Iterable<Map.Entry<String, DataLock>> dataLockList = this.DATA_LOCK_LIST.entries();
//        dataLockList.forEach((dataLockEntry) -> {
//            if (dataLockEntry.getValue().equals(new DataLock(tableName, oid))) {
//                result.put(dataLockEntry.getKey(), dataLockEntry.getValue().getStatus());
//            }
//        });
//
//
//        return result;
//    }
//
//    public void setLockStatus(String userId, String tableName, String oid, String status) {
//        final int[] exist = {0};
//        this.DATA_LOCK_LIST.get(userId).forEach(((DataLock dataLock) -> {
//            if(dataLock.equals(new DataLock(tableName,oid))){
//                this.DATA_LOCK_LIST.replaceValues(userId, Collections.singleton(dataLock.setStatus(status)));
//                exist[0] +=1;
//            }
//        }));
//        if(exist[0]==0){
//            this.DATA_LOCK_LIST.put(userId, new DataLock(tableName, oid).setStatus(status));
//        }
//    }


}
