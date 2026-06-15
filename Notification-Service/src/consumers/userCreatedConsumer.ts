import { sendMail } from "../config/createRequire.mjs";
import type { Channel, ConsumeMessage } from "amqplib";

const handleUserCreatedConsumer = async (channel:Channel)=>{
    const queue = "signup_queue";
    channel.consume(queue, async (data:ConsumeMessage | null)=>{
        console.log("consuming data...");
        console.log("log incoming resonse data: ",data);
        if(data){
           try {
            const res = JSON.parse(data.content.toString());
            console.log("log json data : ",res);
            res.mailType = "user_created";
            await  sendMail(res);
            channel.ack(data);
           } catch (error) {
              console.error("Error happended " ,error);
           }
        }
    });
}

export default handleUserCreatedConsumer;