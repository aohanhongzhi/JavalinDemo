package hxy.javalin.JavalinDemo.entity;

/**
 * @author eric
 * @program JavalinDemo
 * @description
 * @date 2022/1/26
 */
public class BaseResponse<T> {
    int code;
    String msg;
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    BaseResponse(int code, String msg, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static <T> BaseResponse success(T data) {
        return new BaseResponse(200, "success", data);
    }
    public static <T> BaseResponse notFound(T data) {
        return new BaseResponse(404, "not found", data);
    }
}
