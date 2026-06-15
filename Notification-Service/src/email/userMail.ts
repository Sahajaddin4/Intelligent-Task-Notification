import path from "path";
const  ejs = require("ejs");
import type { SignUpMailData } from "../type/signupMailData.js";

const userMail = async (transporter:any,data:SignUpMailData)=>{
    try {
        const {name} = data;
        const {email} = data;

        const emailTemplateFilePath = path.join(__dirname,"../template","signuptemplate.ejs"); 
        const html = await ejs.renderFile(emailTemplateFilePath,{name})
       const info = await transporter.sendMail({
        from:process.env.SMTP_MAIL,
        to:email,
        subject:"Account creation success mail",
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

export default userMail;