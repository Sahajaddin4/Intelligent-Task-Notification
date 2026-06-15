import amplib from "amqplib";
import type {SignUpMailData} from "../type/signupMailData.ts";

const send = async (message:SignUpMailData) =>{
    const connection = await amplib.connect("amqp://localhost");
    const channel = await connection.createChannel();
    const queue = "test_mail";
    const exchange = "email_exchange";
    const routingKey = "email.send";
    await channel.assertExchange(exchange,"direct",{
        durable:true
    });
    await channel.assertQueue(queue,{durable:true});

    await channel.bindQueue(queue,exchange,routingKey);

    // const message = "Hi I am producer.";
    channel.publish(exchange,routingKey,Buffer.from(JSON.stringify(message))
    ,{
        persistent:true
    }
    );

    console.log("Message has been sent successfully");
    await channel.close();
    await connection.close();

}

export default send;