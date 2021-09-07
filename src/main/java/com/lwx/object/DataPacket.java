package com.lwx.object;
/*
    自定义数据协议包
 */
public class DataPacket {
    //参数除action其他可以为空
    private String action = null;       //操作
    private Object data = null;         //传输的对象
    private String datatext = null;         //传输的文本
    private String datatext2 = null;
    private boolean result = false;     //传输的布尔值

    public Object getData() {
        return data;
    }

    public String getDatatext2() {
        return datatext2;
    }

    public void setDatatext2(String datatext2) {
        this.datatext2 = datatext2;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public String getDatatext() {
        return datatext;
    }

    public void setDatatext(String datatext) {
        this.datatext = datatext;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public DataPacket(String action, Object data, String datatext, boolean result) {
        this.action = action;
        this.data = data;
        this.datatext = datatext;
        this.result = result;
    }

    @Override
    public String toString() {
        return "DataPacket{" +
                "action='" + action + '\'' +
                ", data=" + data +
                ", datatext='" + datatext + '\'' +
                ", datatext2='" + datatext2 + '\'' +
                ", result=" + result +
                '}';
    }

    public DataPacket(String action, Object data, String datatext) {
        this.action = action;
        this.data = data;
        this.datatext = datatext;
    }

    public DataPacket(String action, boolean result) {
        this.action = action;
        this.result = result;
    }

    public DataPacket(String action, String datatext) {
        this.action = action;
        this.datatext = datatext;
    }

    public DataPacket(String action, Object data) {
        this.action = action;
        this.data = data;
    }
    public DataPacket(){

    }

    public void setData(Object data) {
        this.data = data;
    }
}
