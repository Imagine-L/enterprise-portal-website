package top.liubaiblog.masterstudiofront.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文章表
 * @TableName ms_article
 */
@TableName(value ="ms_article")
@Data
public class Article implements Serializable {
    /**
     * 文章编号
     */
    @TableId
    private Long aid;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章html格式正文
     */
    private String htmlContent;

    /**
     * 文章发布的栏目
     */
    private Long navId;

    /**
     * 排序种子
     */
    private Integer orderSeed;

    /**
     * 是否发布
     */
    private Boolean released;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Article other = (Article) that;
        return (this.getAid() == null ? other.getAid() == null : this.getAid().equals(other.getAid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getSummary() == null ? other.getSummary() == null : this.getSummary().equals(other.getSummary()))
            && (this.getHtmlContent() == null ? other.getHtmlContent() == null : this.getHtmlContent().equals(other.getHtmlContent()))
            && (this.getNavId() == null ? other.getNavId() == null : this.getNavId().equals(other.getNavId()))
            && (this.getOrderSeed() == null ? other.getOrderSeed() == null : this.getOrderSeed().equals(other.getOrderSeed()))
            && (this.getReleased() == null ? other.getReleased() == null : this.getReleased().equals(other.getReleased()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getDisabled() == null ? other.getDisabled() == null : this.getDisabled().equals(other.getDisabled()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAid() == null) ? 0 : getAid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getSummary() == null) ? 0 : getSummary().hashCode());
        result = prime * result + ((getHtmlContent() == null) ? 0 : getHtmlContent().hashCode());
        result = prime * result + ((getNavId() == null) ? 0 : getNavId().hashCode());
        result = prime * result + ((getOrderSeed() == null) ? 0 : getOrderSeed().hashCode());
        result = prime * result + ((getReleased() == null) ? 0 : getReleased().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getDisabled() == null) ? 0 : getDisabled().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", title=").append(title);
        sb.append(", summary=").append(summary);
        sb.append(", htmlContent=").append(htmlContent);
        sb.append(", navId=").append(navId);
        sb.append(", orderSeed=").append(orderSeed);
        sb.append(", released=").append(released);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", disabled=").append(disabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}