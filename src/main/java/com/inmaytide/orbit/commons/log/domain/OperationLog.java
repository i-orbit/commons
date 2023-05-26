package com.inmaytide.orbit.commons.log.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.time.Instant;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Schema(title = "用户操作日志")
public class OperationLog extends Entity {
    @Serial
    private static final long serialVersionUID = 3471044645864428769L;

    @Schema(title = "所属租户")
    private Long tenantId;

    @Schema(title = "操作人", nullable = true, description = "为NULL时表示操作人未登录或登录失败")
    private Long operator;

    @TableField(exist = false)
    @Schema(title = "操作人姓名", accessMode = Schema.AccessMode.READ_ONLY, description = "查询时系统自动填入")
    private String operatorName;

    @Schema(title = "操作时间")
    private Instant operationTime;

    @Schema(title = "操作结果")
    private Is result;

    @Schema(title = "客户端信息", description = "通过HttpHeaders[User-Agent]获取, 包含系统/浏览器版本等信息")
    private String clientDescription;

    @Schema(title = "客户端IP及其归属地", description = "格式IP(归属地)")
    private String ipAddress;

    @Schema(title = "接口所在服务名称")
    private String service;

    @Schema(title = "业务描述")
    private String business;

    @Schema(title = "操作描述")
    private String description;

    @Schema(title = "请求参数")
    private String arguments;

    @Schema(title = "响应内容/错误描述")
    private String response;

    @Schema(title = "接口调用链标识")
    private String chain;

    @Schema(title = "客户端平台")
    private String platform;

    @Schema(title = "请求地址")
    private String path;

    @Schema(title = "HttpMethod")
    private String httpMethod;

    public OperationLog() {
        this.operationTime = Instant.now();
        this.tenantId = Constants.NON_TENANT_ID;
        try {
            this.setService(ApplicationContextHolder.getInstance().getProperty("spring.application.name"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Instant getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Instant operationTime) {
        this.operationTime = operationTime;
    }

    public Is getResult() {
        return result;
    }

    public void setResult(Is result) {
        this.result = result;
    }

    public String getClientDescription() {
        return clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
