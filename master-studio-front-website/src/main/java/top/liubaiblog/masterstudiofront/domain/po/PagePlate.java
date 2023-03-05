package top.liubaiblog.masterstudiofront.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 页面板块表
 * @TableName ms_page_plate
 */
@TableName(value ="ms_page_plate")
@Data
public class PagePlate implements Serializable {
    /**
     * 板块编号
     */
    @TableId
    private Long pid;

    /**
     * 板块标题
     */
    private String plateName;

    /**
     * 板块描述
     */
    private String description;

    /**
     * 板块图片
     */
    private String image;

    /**
     * 板块链接
     */
    private String link;

    /**
     * 板块类型(0左对齐, 1居中, 2右对齐)
     */
    private Integer plateType;

    /**
     * 绑定的栏目编号
     */
    private Long bindNav;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    /**
     * 是否发布
     */
    private Boolean released;

    /**
     * 排序种子
     */
    private Integer orderSeed;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}