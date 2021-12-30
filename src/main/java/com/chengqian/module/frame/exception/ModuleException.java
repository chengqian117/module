package com.chengqian.module.frame.exception;

import lombok.Data;

/**
 * @program: TMS
 * @description: 自定义异常
 * @author: Mr.Yang
 * @create: 2021-05-28 18:51
 **/
@Data
public class ModuleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public ModuleException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public ModuleException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }
    public ModuleException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
    public ModuleException(String msg, int code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

}
