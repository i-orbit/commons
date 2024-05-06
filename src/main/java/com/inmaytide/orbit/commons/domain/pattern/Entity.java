package com.inmaytide.orbit.commons.domain.pattern;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.domain.validation.groups.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Schema(title = "数据实体基类")
public abstract class Entity implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    @Schema(title = "唯一标识")
    @NotNull(groups = {Update.class})
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
