import path from "path";
const ejs = require("ejs");
import type { TaskReminderMailData } from "../type/taskReminderMailData.js";

const taskMail = async (transporter:any,data:TaskReminderMailData)=>{
    try {
        const {name} = data;
        const {email} = data;
        const {taskName} = data;
        const emailTemplateFilePath = path.join(__dirname,"../template","taskReminderTemplate.ejs"); 
        const html = await ejs.renderFile(emailTemplateFilePath,{name,taskName})
       const info = await transporter.sendMail({
        from:process.env.SMTP_MAIL,
        to:email,
        subject:"Reminder mail",
        html
       });
       console.log("Message sent:", info.messageId);

        if (info.rejected.length > 0) {
            console.warn("Some recipients were rejected:", info.rejected);
        }
       console.log("log email in send email:",email);
    } catch (error) {
        console.error(`error to send mail :${error}`)
    }
}

export default taskMail;