package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.domain.pattern.TombstoneEntity;

import java.io.Serial;

/**
 * 业务数据对文件库文件引用记录
 *
 * @author inmaytide
 * @since 2024/4/7
 */
public class FileAssociation extends TombstoneEntity {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private Long fileId;

    private String business;

    private Long businessDataId;

    private String businessDataDescription;

    public static Builder builder() {
        return new Builder();
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Long getBusinessDataId() {
        return businessDataId;
    }

    public void setBusinessDataId(Long businessDataId) {
        this.businessDataId = businessDataId;
    }

    public String getBusinessDataDescription() {
        return businessDataDescription;
    }

    public void setBusinessDataDescription(String businessDataDescription) {
        this.businessDataDescription = businessDataDescription;
    }

    public static class Builder {

        private Long fileId;

        private String business;

        private Long businessDataId;

        private String businessDataDescription;

        public Builder fileId(Long fileId) {
            this.fileId = fileId;
            return this;
        }

        public Builder business(String business) {
            this.business = business;
            return this;
        }

        public Builder businessDataId(Long businessDataId) {
            this.businessDataId = businessDataId;
            return this;
        }

        public Builder businessDataDescription(String businessDataDescription) {
            this.businessDataDescription = businessDataDescription;
            return this;
        }

        public FileAssociation build() {
            FileAssociation association = new FileAssociation();
            association.setFileId(fileId);
            association.setBusiness(business);
            association.setBusinessDataId(businessDataId);
            association.setBusinessDataDescription(businessDataDescription);
            return association;
        }

    }
}
