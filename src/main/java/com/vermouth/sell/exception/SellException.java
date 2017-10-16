package com.vermouth.sell.exception;

import com.vermouth.sell.enums.ResultEnum;

/**
 * 异常处理类
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super((resultEnum.getMessage()));
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
