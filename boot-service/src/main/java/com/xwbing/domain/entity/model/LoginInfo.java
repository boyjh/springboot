package com.xwbing.domain.entity.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author xiangwb
 * @date 20/2/17 17:25
 */
@Data
@ApiModel
public class LoginInfo {
    private String token;
    private String userId;
}
