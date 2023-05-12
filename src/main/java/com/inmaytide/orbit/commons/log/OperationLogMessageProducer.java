package com.inmaytide.orbit.commons.log;

import com.inmaytide.orbit.commons.log.domain.OperationLog;

public interface OperationLogMessageProducer {

    String ROUTE_KEY_OPERATION_LOG = "orbit.queue.operation.log";

    void produce(OperationLog log);

}
