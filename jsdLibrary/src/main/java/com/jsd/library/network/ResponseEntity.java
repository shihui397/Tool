package com.jsd.library.network;

/**
 *  响应体
 */

public class ResponseEntity<T> {
    private String resultCode;
    private String message;
    private T record;
    private T records;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getRecords() {
        return records;
    }

    public void setRecords(T records) {
        this.records = records;
    }

    public T getRecord() {

        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "resultCode='" + resultCode + '\'' +
                ", message='" + message + '\'' +
                ", record=" + record +
                ", records=" + records +
                '}';
    }
}
