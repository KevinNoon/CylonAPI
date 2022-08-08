package com.non.kevin.cylonapi.controllers;

import com.non.kevin.cylonapi.models.Datalog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.non.kevin.cylonapi.controllers.GetPoints.*;

@RestController
public class DataLogController {
    @RequestMapping("/datalogs/")
    public ArrayList<Datalog> getDataLogs(@RequestParam String ip,@RequestParam int netAddress
            ,@RequestParam int subnetAddress,@RequestParam int datalogNo){
        ArrayList<Datalog> datalogs = GetDatalogs.getLogs(ip,(byte) netAddress,(byte) subnetAddress,(byte) datalogNo);
    return datalogs;
    }
    @RequestMapping("/points/uc32/hardware")
    public Float getUC32HWPoints(@RequestParam String ip,@RequestParam int netAddress
            ,@RequestParam int subnetAddress,@RequestParam int pointNo){
        Float point = getUC32HardwarePoint(ip,4950,(byte) netAddress
                ,(byte) subnetAddress,pointNo);
        return point;
    }
    @RequestMapping("/points/uc32/virtual/analogue")
    public Float getUC32VirtualPoints(@RequestParam String ip,@RequestParam int netAddress
            ,@RequestParam int subnetAddress,@RequestParam int pointNo){
        Float point = getUC32VirtualAnaloguePoint(ip,4950,(byte) netAddress
                ,(byte) subnetAddress,pointNo);
        return point;
    }
    @RequestMapping("/points/uc32/virtual/digital")
    public Float getUC32VirtualPointDigital(@RequestParam String ip,@RequestParam int netAddress
            ,@RequestParam int subnetAddress,@RequestParam int pointNo){
        Float point = getUC32VirtualDigitalPoint(ip,4950,(byte) netAddress
                ,(byte) subnetAddress, pointNo);
        return point;
    }
}
