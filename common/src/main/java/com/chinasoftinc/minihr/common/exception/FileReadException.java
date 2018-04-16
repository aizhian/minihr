package com.chinasoftinc.minihr.common.exception;

/**
 * Created by Aizhanglin on 2018/4/16.
 * 文件读取异常
 */
public class FileReadException extends RuntimeException{
    public FileReadException(){
        super();
    }
    public FileReadException(String msg){
        super(msg);
    }
    public FileReadException(Throwable e){
        super(e);
    }
    public FileReadException(String msg, Throwable e){
        super(msg, e);
    }
}
