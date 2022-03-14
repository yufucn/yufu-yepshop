package com.yufu.yepshop.dto;

import org.springframework.util.StringUtils;

/**
 * @author wang
 * @date 2022/3/3 22:30
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    public static ResultT genSuccessResult() {
        ResultT result = new ResultT();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static ResultT genSuccessResult(String message) {
        ResultT result = new ResultT();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(message);
        return result;
    }

    public static ResultT genSuccessResult(Object data) {
        ResultT result = new ResultT();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static ResultT genFailResult(String message) {
        ResultT result = new ResultT();
        result.setResultCode(RESULT_CODE_SERVER_ERROR);
        if (StringUtils.isEmpty(message)) {
            result.setMessage(DEFAULT_FAIL_MESSAGE);
        } else {
            result.setMessage(message);
        }
        return result;
    }

    public static ResultT genErrorResult(int code, String message) {
        ResultT result = new ResultT();
        result.setResultCode(code);
        result.setMessage(message);
        return result;
    }
}
