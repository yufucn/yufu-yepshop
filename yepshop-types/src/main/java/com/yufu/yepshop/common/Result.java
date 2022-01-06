package com.yufu.yepshop.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wang
 * @date 2022/1/6 21:24
 */
@Data
public class Result<T> implements Serializable {

    private T data;

    public Result(T data) {
        this.data = data;
    }

    public static Result success(boolean data) {
        return new Result(data);
    }
}
