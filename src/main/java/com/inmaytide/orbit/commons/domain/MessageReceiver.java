package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.constants.MessageSendingMode;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

import java.io.Serial;

/**
 * @author inmaytide
 * @since 2024/2/28
 */
public class MessageReceiver extends Entity {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private MessageSendingMode sendingMode;

    private String receiver;


    public MessageReceiver() {
    }

    public MessageReceiver(MessageSendingMode sendingMode, String receiver) {
        this.sendingMode = sendingMode;
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public MessageSendingMode getSendingMode() {
        return sendingMode;
    }

    public void setSendingMode(MessageSendingMode sendingMode) {
        this.sendingMode = sendingMode;
    }

}
