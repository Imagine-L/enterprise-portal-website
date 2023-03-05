package top.liubaiblog.masterstudiofront.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 栏目表
 * @TableName ms_home_chart
 */
@TableName(value ="ms_home_chart")
@Data
public class HomeChart implements Serializable {
    /**
     * 轮播图编号
     */
    @TableId
    private Long hid;

    /**
     * 轮播图片
     */
    private String image;

    /**
     * 轮播图链接
     */
    private String link;

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