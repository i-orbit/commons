package com.inmaytide.orbit.commons.domain.pattern;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private static final long serialVersionUID = -2907777165945798043L;

    @NotNull(groups = {Update.class})
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(title = "唯一标识")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
