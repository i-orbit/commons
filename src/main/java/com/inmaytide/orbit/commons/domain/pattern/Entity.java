package com.inmaytide.orbit.commons.domain.pattern;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Schema(title = "数据实体基类")
public class Entity implements Serializable {

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
