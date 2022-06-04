package cn.projects.team.demo.model;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by wanglei on 2016/12/11.
 */

public class BaseModel<T> implements IModel {
    protected boolean error=false;
    protected boolean success ;
    protected int code ;
    protected String message ;

    public T data;
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public boolean isNull() {
        if( data instanceof  Datas ){
            Datas  mydata =   (Datas) data ;
            return null == mydata.data || mydata.data.isEmpty() ;

        }
        return data == null ;
    }

    @Override
    public boolean isAuthError() {
        return code == 700;
    }

    @Override
    public boolean isSuccessError() {
        return success;
    }

    @Override
    public String getErrorMsg() {
        return message;
    }

    @Override
    public boolean isFrozen() {
        return code == 1000;
    }
}
