package tech.haonan.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private Integer responseCode;
    private String message;
    private T data;

    public CommonResult(Integer responseCode, String message) {
        this(responseCode,message,null);
    }

}
