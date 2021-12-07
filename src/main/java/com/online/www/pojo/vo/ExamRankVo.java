package com.online.www.pojo.vo;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-12-07
 */
@Data
public class ExamRankVo {
    @ApiModelProperty(value = "科目id")
    private Integer subjectId;
    @ApiModelProperty(value = "科目名称")
    private String subjectName;
    @ApiModelProperty(value = "我的排名（-1表示未上榜）")
    private Integer myRank;
    @ApiModelProperty(value = "我的分数")
    private Double myScore;
    @ApiModelProperty(value = "排名分页（前50）")
    List<ExamRankPage> examRankPage;
}
