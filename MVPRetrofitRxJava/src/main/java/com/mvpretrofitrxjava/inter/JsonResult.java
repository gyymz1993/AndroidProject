package com.mvpretrofitrxjava.inter;

/**
 * Created by Administrator on 2017/2/15.
 */
public class JsonResult {

    /**
     * data : {"tokenKey":"b783ab690a53f558aedd062b66ff890c"}
     * response :
     * error : 0
     * message : 登录成功
     */
    private DataEntity data;
    private String response;
    private int error;
    private String message;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public String getResponse() {
        return response;
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }


    public class DataEntity {
        /**
         * tokenKey : b783ab690a53f558aedd062b66ff890c
         */
        private String tokenKey;

        public void setTokenKey(String tokenKey) {
            this.tokenKey = tokenKey;
        }

        public String getTokenKey() {
            return tokenKey;
        }

        @Override
        public String toString() {
            return "DataEntity.toString {" +
                    "tokenKey='" + tokenKey + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "JsonResult.toString {" +
                "data=   xxx" + data .toString()+
                ", response='" + response + '\'' +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
