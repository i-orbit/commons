package com.inmaytide.orbit.commons.domain.dto.params;

import com.inmaytide.orbit.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author inmaytide
 * @since 2024/5/16
 */
public class BatchUpdate<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    @NotNull
    private Long businessDataId;

    @Valid
    private List<T> elements;

    public Long getBusinessDataId() {
        return businessDataId;
    }

    public void setBusinessDataId(Long businessDataId) {
        this.businessDataId = businessDataId;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}
