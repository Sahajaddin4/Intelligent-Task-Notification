import type { Channel } from "amqplib";
import { sendMail } from "../config/createRequire.mjs";


const taskReminderConsumer = async (channel:Channel)=>{
    const queue = "remind-task-queue";
    channel.consume(queue,async (data)=>{
        if(data){
            const payload = JSON.parse(data.content.toString());;
            console.log("payload:",payload)
            payload.mailType = "task_reminder";
            await sendMail(payload);
        }
    })
}

export default taskReminderConsumer;