const nodemailer = require("nodemailer");
require("dotenv").config();
import console = require("console");
import type {SignUpMailData} from "../type/signupMailData.ts";
import type {TaskReminderMailData} from "../type/taskReminderMailData.ts";
const userMail = require("../email/userMail.ts").default;
const taskMail = require("../email/taskMail.ts").default;

const transporter = nodemailer.createTransport({
    host:"smtp.gmail.com",
    port:587,
    secure:false,
    auth:{
        user:process.env.SMTP_MAIL,
        pass:process.env.SMTP_PASSWORD
    }
});

transporter.verify()
    .then(() => {
        console.log("Transporter is activated for mail sending");
    })
    .catch((err: Error | unknown )=> {
        console.error(err);
    });


    const sendMail = async (data:SignUpMailData | TaskReminderMailData )=>{
        console.log(data);
        if(data.mailType.toLowerCase() == "task_reminder"){
            await taskMail(transporter,data);
        }
        else if(data.mailType.toLowerCase() == "user_created"){
            await userMail(transporter,data);
        }
    }


module.exports = sendMail;    