package com.inmaytide.orbit.commons.domain.pattern;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@ApiModel("包含审计信息的数据实体基类")
public class AuditEntity extends Entity {

    @ApiModelProperty("创建人")
    private Long createdBy;

    @TableField(exist = false)
    @ApiModelProperty("创建人姓名")
    private String createdByName;

    @ApiModelProperty("创建时间")
    private Instant createdTime;

    @ApiModelProperty("最后修改人")
    private Long modifiedBy;

    @TableField(exist = false)
    @ApiModelProperty("最后修改人姓名")
    private String modifiedByName;

    @ApiModelProperty("最后修改时间")
    private Instant modifiedTime;

    @Version
    @ApiModelProperty("数据版本(修改次数,乐观锁)")
    private Integer version;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getModifiedByName() {
        return modifiedByName;
    }

    public void setModifiedByName(String modifiedByName) {
        this.modifiedByName = modifiedByName;
    }
}
