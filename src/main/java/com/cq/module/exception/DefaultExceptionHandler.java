package com.cq.module.exception;

import com.cq.module.common.MessageConstant;
import com.cq.module.config.ResultData;
import com.cq.module.config.ResultViewModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Yang
 * 异常处理类
 * controller层异常无法捕获处理，需要自己处理
 * Created at 2018/8/27.
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 处理参数校验异常
     *
     * @param e 参数校验异常
     * @return 参数异常提示
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultViewModel handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        if (e.getBindingResult().getFieldError() == null) {
            return ResultData.error(1, "参数错误");
        }
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultData.errors(1, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    private static String getRequestData(HttpServletRequest request) {
        List<String> params = new ArrayList<>();
        Map<String, String[]> paramsMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            params.add(entry.getKey() + ":" + String.join(",", entry.getValue()));
        }
        return params.toString();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResultViewModel handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException e) {
        String uri = req.getRequestURI();
        String params = getRequestData(req);
        log.error("==MySQLException== {} {} {}", uri, params, e.getCause().getCause().getMessage());
        return ResultData.error(1, e.getCause().getCause().getMessage());
    }

    @ExceptionHandler(ModuleException.class)
    public ResultViewModel handleModuleException(HttpServletRequest req, ModuleException e) {
        String uri = req.getRequestURI();
        String params = getRequestData(req);
        log.error("==ModuleException== {} {} {} {}", uri,params, e.getMessage(),e.getCode());
        e.printStackTrace();
        return ResultData.error(1, StringUtils.isEmpty(e.getMessage())? MessageConstant.DO_ERROR:e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultViewModel handleRuntimeException(HttpServletRequest req, RuntimeException e) {
        String uri = req.getRequestURI();
        String params = getRequestData(req);
        log.error("==RuntimeException== {} {} {}", uri,params, e.getMessage());
        e.printStackTrace();
        return ResultData.error(1, StringUtils.isEmpty(e.getMessage())? MessageConstant.DO_ERROR:e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultViewModel handleException(HttpServletRequest req, Exception e) {
        String uri = req.getRequestURI();
        String params = getRequestData(req);
        log.error("==Exception== {} {} {}", uri, params, e.getMessage());
        e.printStackTrace();
        return ResultData.error(1, MessageConstant.DO_ERROR);
    }

}
