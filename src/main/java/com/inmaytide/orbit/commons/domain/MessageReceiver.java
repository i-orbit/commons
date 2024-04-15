package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.constants.MessageReadingStatus;
import com.inmaytide.orbit.commons.constants.MessageSendingMode;
import com.inmaytide.orbit.commons.constants.MessageSendingStatus;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author inmaytide
 * @since 2024/2/28
 */
public class MessageReceiver extends Entity {

    @Serial
    private static final long serialVersionUID = -7529195872053007367L;

    private MessageSendingMode sendingMode;

    private Long receiver;


    public MessageReceiver() {
    }

    public MessageReceiver(MessageSendingMode sendingMode, Long receiver) {
        this.sendingMode = sendingMode;
        this.receiver = receiver;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public MessageSendingMode getSendingMode() {
        return sendingMode;
    }

    public void setSendingMode(MessageSendingMode sendingMode) {
        this.sendingMode = sendingMode;
    }
}
