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
 * @TableName ms_navigation
 */
@TableName(value ="ms_navigation")
@Data
public class Navigation implements Serializable {
    /**
     * 栏目编号
     */
    @TableId
    private Long nid;

    /**
     * 栏目名
     */
    private String navName;

    /**
     * 栏目级别
     */
    private Integer level;

    /**
     * 父栏目编号
     */
    private Long parentId;

    /**
     * 栏目图片路径
     */
    private String image;

    /**
     * 栏目描述
     */
    private String description;

    /**
     * 是否允许删除
     */
    private Boolean allowDel;

    /**
     * 栏目类型(0父栏目, 1页面, 2文章)
     */
    private Integer navType;

    /**
     * 访问路径
     */
    private String path;

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

    /**
     * 是否可用
     */
    private Boolean used;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    /**
     * 是否首页展示
     */
    private Boolean showed;

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
        Navigation other = (Navigation) that;
        return (this.getNid() == null ? other.getNid() == null : this.getNid().equals(other.getNid()))
            && (this.getNavName() == null ? other.getNavName() == null : this.getNavName().equals(other.getNavName()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getAllowDel() == null ? other.getAllowDel() == null : this.getAllowDel().equals(other.getAllowDel()))
            && (this.getNavType() == null ? other.getNavType() == null : this.getNavType().equals(other.getNavType()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getOrderSeed() == null ? other.getOrderSeed() == null : this.getOrderSeed().equals(other.getOrderSeed()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUsed() == null ? other.getUsed() == null : this.getUsed().equals(other.getUsed()))
            && (this.getDisabled() == null ? other.getDisabled() == null : this.getDisabled().equals(other.getDisabled()))
            && (this.getShowed() == null ? other.getShowed() == null : this.getShowed().equals(other.getShowed()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNid() == null) ? 0 : getNid().hashCode());
        result = prime * result + ((getNavName() == null) ? 0 : getNavName().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getAllowDel() == null) ? 0 : getAllowDel().hashCode());
        result = prime * result + ((getNavType() == null) ? 0 : getNavType().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getOrderSeed() == null) ? 0 : getOrderSeed().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUsed() == null) ? 0 : getUsed().hashCode());
        result = prime * result + ((getDisabled() == null) ? 0 : getDisabled().hashCode());
        result = prime * result + ((getShowed() == null) ? 0 : getShowed().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", navName=").append(navName);
        sb.append(", level=").append(level);
        sb.append(", parentId=").append(parentId);
        sb.append(", image=").append(image);
        sb.append(", description=").append(description);
        sb.append(", allowDel=").append(allowDel);
        sb.append(", navType=").append(navType);
        sb.append(", path=").append(path);
        sb.append(", orderSeed=").append(orderSeed);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", used=").append(used);
        sb.append(", disabled=").append(disabled);
        sb.append(", showed=").append(showed);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}