package com.heng.base.utils;/**
 * @author shkstart
 * @create 2023-03-01 19:28
 */

/**
 * @Auther:Jarvis
 * @Date:2023年03月2023/3/1日19:28
 * @Description:
 */
public class AjaxResult {
    private Boolean success = true;  //由于成功的概率较大，所以默认值给个true
    private String message = "操作成功";
    private Object resultObj;

    public static AjaxResult me() {
        return new AjaxResult();
    }

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public AjaxResult setResultObj(Object resultObj) {
        this.resultObj = resultObj;
        return this;
    }
}
