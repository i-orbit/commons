package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.constants.Sharing;
import com.inmaytide.orbit.commons.domain.pattern.TombstoneEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author inmaytide
 * @since 2024/3/4
 */
@Schema(title = "文件库文件基础信息")
public class FileMeta extends TombstoneEntity {

    @Serial
    private static final long serialVersionUID = -2683208285740852201L;

    @Schema(title = "文件名称")
    private String name;

    @Schema(title = "文件类型(后缀名)")
    private String extension;

    @Schema(title = "文件大小(bytes)")
    private BigDecimal size;

    @Schema(title = "文件存储路径")
    private String address;

    @Schema(title = "图片/视频类型的文件缩略图")
    private String thumbnailAddress;

    @Schema(title = "文件共享级别")
    private Sharing sharing;

    @Schema(title = "SHA265值")
    private String sha256;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getThumbnailAddress() {
        return thumbnailAddress;
    }

    public void setThumbnailAddress(String thumbnailAddress) {
        this.thumbnailAddress = thumbnailAddress;
    }

    public Sharing getSharing() {
        return sharing;
    }

    public void setSharing(Sharing sharing) {
        this.sharing = sharing;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }
}
