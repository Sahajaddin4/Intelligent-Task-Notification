import express from 'express';
import "dotenv/config";
import { eurekaCLient } from './config/eurekaConfig.js';
import send from './test/send.js';
import receive from './test/receive.js';

const app = express();

const PORT = process.env.PORT || 5000;
//allow json
app.use(express.json());

//Allow form-data
app.use(express.urlencoded({extended:true}));



app.get("/notify/api/",(req,res)=>{
    console.log(req)
    res.send("Hello from notification service");
})

//Register with Eureka 
eurekaCLient.start();
// for(let i = 0 ; i< 3 ;i++){
//     send(`Message count ${i}`);
// }
// send({name:"riju",email:"rijusk700@gmail.com"});
receive();
app.listen(PORT,(err)=>{
    console.log(`Server is running on ${PORT}`);
});

