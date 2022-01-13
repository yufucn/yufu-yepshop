package com.yufu.yepshop.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wang
 * @date 2022/1/6 21:24
 */
@Data
public class Result<T> implements Serializable {

    private String code;

    private T data;

    private String msg;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {

        return result("0", data, "成功");
    }

    public static <T> Result<T> fail(String code,String msg) {
        return result(code, null, msg);
    }

    public static <T> Result<T> fail() {
        return result("-1", null, "失败");
    }

    public static <T> Result<T> fail(String msg) {
        return result("-1", null, msg);
    }

    private static <T> Result<T> result(String code, T data, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

}
