package com.online.www.pojo.bo;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-23
 */
@Data
public class CreateExamBo {
    @ApiModelProperty(value = "考试名称")
    private String examName;
    @ApiModelProperty(value = "考试开始时间")
    private LocalDateTime startTime = LocalDateTime.now();
    @ApiModelProperty(value = "考试持续时间", required = true)
    private Integer duration;
    @ApiModelProperty(value = "考试科目", required = true)
    @NotNull(message = "科目信息不能为空")
    private Integer subjectId;
}
