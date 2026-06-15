import { createRequire } from "module";

const require = createRequire(import.meta.url);
const sendMail = require("../email/sendEmail.cts");


export {sendMail};