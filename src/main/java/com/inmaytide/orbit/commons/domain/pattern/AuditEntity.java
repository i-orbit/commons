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
    private String createdBy;

    @TableField(exist = false)
    @Schema(title = "创建人姓名")
    private String createdByName;

    @Schema(title = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Instant createdAt;

    @Schema(title = "最后修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;

    @TableField(exist = false)
    @Schema(title = "最后修改人姓名")
    private String updatedByName;

    @Schema(title = "最后修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Instant updatedAt;

    @Version
    @Schema(title = "数据版本(修改次数,乐观锁)")
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
