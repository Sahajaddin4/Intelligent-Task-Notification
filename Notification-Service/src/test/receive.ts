import amplib from "amqplib";

import handleUserCreatedConsumer from "../consumers/userCreatedConsumer.js";
import taskReminderConsumer from "../consumers/taskReminderConsumer.js";
const receive = async () =>{
    const connection = await amplib.connect("amqp://localhost",
    {recovery: {
        initialDelay: 200, 
        maxDelay: 5000, 
        factor: 2,
        jitter: 0.2,
        maxRetries: Infinity}}
        );
    connection.on("error",error=>{
        console.error("Connection error:",error);
    });
    const channel = await connection.createChannel();
    channel.on("error",error=>{
        console.error("Error at creating channel",error);
    });

    await handleUserCreatedConsumer(channel);
    await taskReminderConsumer(channel);
    
}


export default receive;