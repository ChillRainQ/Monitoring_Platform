import {ElMessage} from "element-plus";

const message = {
    error: (msg: string, callback?: () => void) =>{
        ElMessage({
            type: "error",
            message: msg,
            duration: 2000,
            onClose: () => {
                if (callback){
                    callback();
                }
            }
        })
    },
    success: (msg: string, callback?: () => void) =>{
        ElMessage({
            type: "success",
            message: msg,
            duration: 2000,
            onClose: () => {
                if (callback){
                    callback();
                }
            }
        })
    },
    warning: (msg: string, callback?: () => void) =>{
        ElMessage({
            type: "warning",
            message: msg,
            duration: 2000,
            onClose: () => {
                if (callback){
                    callback();
                }
            }
        })
    },
}
export default message;