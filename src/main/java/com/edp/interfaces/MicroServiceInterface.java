package com.edp.interfaces;

public interface MicroServiceInterface extends Runnable{

     MicroServiceInterface start();
     void schedule();
     void stop();
}
