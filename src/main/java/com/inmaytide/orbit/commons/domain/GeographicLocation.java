package com.inmaytide.orbit.commons.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author inmaytide
 * @since 2023/6/26
 */
@Schema(title = "地址位置信息(坐标点)")
public class GeographicLocation implements Serializable {

    @Schema(title = "归属实体对象唯一标识", nullable = false)
    private Long attribution;

    @Schema(title = "坐标点-经度", nullable = false)
    private BigDecimal longitude;

    @Schema(title = "坐标点-纬度", nullable = false)
    private BigDecimal latitude;

    public Long getAttribution() {
        return attribution;
    }

    public void setAttribution(Long attribution) {
        this.attribution = attribution;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
