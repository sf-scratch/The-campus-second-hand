package com.lh.springboot03.utils;

import java.io.Serializable;

public class WebSocketInstruct<T> implements Serializable {
    //指令码
    private Integer instruct;
    //指令信息
    private String message;
    //数据
    private T data;

    public WebSocketInstruct() {
    }

    public WebSocketInstruct(Integer instruct, String message, T data) {
        this.instruct = instruct;
        this.message = message;
        this.data = data;
    }

    public Integer getInstruct() {
        return instruct;
    }

    public void setInstruct(Integer instruct) {
        this.instruct = instruct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WebSocketInstruct{" +
                "instruct=" + instruct +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
