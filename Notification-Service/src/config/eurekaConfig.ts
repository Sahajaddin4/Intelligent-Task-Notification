import { Eureka } from "eureka-ts-client";
import "dotenv/config";
export const eurekaCLient = new Eureka({
    instance:{
        app: "NOTIFICATION-SERVICE",
        hostName: "localhost",
        ipAddr: "127.0.0.1",
        port: {
            "$": process.env.PORT,
            "@enabled": true
        },
        vipAddress: "NOTIFICATION-SERVICE",
        dataCenterInfo: {
            "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
            name: "MyOwn"
        }
        
    },
    eureka:{
        host:"localhost",
        port:8761,
        servicePath:"/eureka/apps"
    }
})