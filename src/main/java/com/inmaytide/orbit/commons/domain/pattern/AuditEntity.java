package com.inmaytide.orbit.commons.domain.pattern;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.time.Instant;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Schema(title = "包含审计信息的数据实体基类")
public abstract class AuditEntity extends Entity {

    @Serial
    private static final long serialVersionUID = com.inmaytide.orbit.Version.SERIAL_VERSION_UID;

    @Schema(title = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @TableField(exist = false)
    @Schema(title = "创建人姓名")
    private String createdByName;

    @Schema(title = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Instant createdTime;

    @Schema(title = "最后修改人")
    @TableField(fill = FieldFill.UPDATE)
    private Long modifiedBy;

    @TableField(exist = false)
    @Schema(title = "最后修改人姓名")
    private String modifiedByName;

    @Schema(title = "最后修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Instant modifiedTime;

    @Version
    @Schema(title = "数据版本(修改次数,乐观锁)")
    @TableField(fill = FieldFill.INSERT)
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
