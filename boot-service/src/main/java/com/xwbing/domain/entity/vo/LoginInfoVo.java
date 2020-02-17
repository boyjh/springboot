package com.xwbing.domain.entity.vo;

import com.xwbing.domain.entity.model.LoginInfo;
import com.xwbing.domain.entity.sys.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiangwb
 * @date 20/2/17 17:23
 */
@Data
@ApiModel
public class LoginInfoVo extends RestMessageVo {
    @ApiModelProperty(value = "返回数据")
    private LoginInfo data;
}
