package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.constants.Bool;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

import java.io.Serial;
import java.time.Instant;

/**
 * 文件库文件元数据
 *
 * @author inmaytide
 * @since 2024/4/7
 */
public class FileMetadata extends Entity {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private String name;

    private String extension;

    private Long size;

    private String address;

    private String thumbnailAddress;

    private String sha256;

    private Bool verified;

    private Bool deleted;

    private Instant deleteTime;

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
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

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Bool getVerified() {
        return verified;
    }

    public void setVerified(Bool verified) {
        this.verified = verified;
    }

    public Bool getDeleted() {
        return deleted;
    }

    public void setDeleted(Bool deleted) {
        this.deleted = deleted;
    }

    public Instant getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Instant deleteTime) {
        this.deleteTime = deleteTime;
    }

}
