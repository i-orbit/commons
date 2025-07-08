package com.inmaytide.orbit.commons.domain.dto.params;

import com.inmaytide.orbit.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 批量保存指定业务对象附属信息接口的通用请求参数实体
 *
 * @author inmaytide
 * @since 2024/5/16
 */
public class BatchUpdate<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    /**
     * 业务对象唯一标识<br/>
     * 不能为空
     */
    @NotNull
    private String businessDataId;

    /**
     * 附属信息对象列表<br/>
     * 不能为空
     */
    @Valid
    private List<T> elements;

    public BatchUpdate() {

    }

    public BatchUpdate(@NotNull String businessDataId, @NotNull List<T> elements) {
        Assert.notEmpty(elements, "elements cannot be empty");
        this.businessDataId = Objects.requireNonNull(businessDataId);
        this.elements = elements;
    }

    public String getBusinessDataId() {
        return businessDataId;
    }

    public void setBusinessDataId(String businessDataId) {
        this.businessDataId = businessDataId;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}
