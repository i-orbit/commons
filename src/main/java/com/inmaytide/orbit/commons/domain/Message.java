package com.inmaytide.orbit.commons.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.inmaytide.exception.web.BadRequestException;
import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.constants.MessageSendingMode;
import com.inmaytide.orbit.commons.domain.pattern.TombstoneEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author inmaytide
 * @since 2024/2/27
 */
@Schema(title = "系统消息")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private String title;

    private String content;

    private String business;

    private String url;

    private List<FileMeta> attachments;

    private List<MessageReceiver> receivers;

    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FileMeta> getAttachments() {
        if (attachments == null) {
            return new ArrayList<>();
        }
        return attachments;
    }

    public void setAttachments(List<FileMeta> attachments) {
        this.attachments = attachments;
    }

    public List<MessageReceiver> getReceivers() {
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        return receivers;
    }

    public void setReceivers(List<MessageReceiver> receivers) {
        this.receivers = receivers;
    }


    public static class Builder {

        private String title;

        private String content;

        private String business;

        private String url;

        private final List<MessageReceiver> receivers;

        public Builder() {
            receivers = new ArrayList<>();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder business(String business) {
            this.business = business;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder receiver(MessageSendingMode sendingMode, Long userId) {
            this.receivers.add(new MessageReceiver(sendingMode, userId));
            return this;
        }

        public Message build() {
            if (StringUtils.isBlank(this.title) || StringUtils.isNotBlank(this.business) || StringUtils.isNotBlank(this.content)) {
                throw new BadRequestException();
            }
            if (CollectionUtils.isEmpty(this.receivers)) {
                throw new BadRequestException();
            }
            Message message = new Message();
            message.setTitle(this.title);
            message.setContent(this.content);
            message.setBusiness(this.business);
            message.setUrl(this.url);
            message.setReceivers(this.receivers);
            return message;
        }

    }
}
