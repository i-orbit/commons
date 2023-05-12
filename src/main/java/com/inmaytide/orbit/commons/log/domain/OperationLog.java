package com.inmaytide.orbit.commons.log.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import com.inmaytide.orbit.commons.log.OperateResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serial;
import java.time.Instant;

@ApiModel("用户操作日志")
public class OperationLog extends Entity {

    @Serial
    private static final long serialVersionUID = 5092215481098694779L;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    @TableField(exist = false)
    private String operatorName;

    @ApiModelProperty("操作时间")
    private Instant operateTime;

    @ApiModelProperty("操作结果")
    private OperateResult result;

    @ApiModelProperty("操作结果中文描述")
    @TableField(exist = false)
    private String resultName;

    @ApiModelProperty("执行操作客户端信息")
    private String clientDescription;

    @ApiModelProperty("操作人ip地址")
    private String ipAddress;

    @ApiModelProperty("操作描述")
    private String description;

    @ApiModelProperty("业务描述")
    private String business;

    @ApiModelProperty("输入参数")
    private String arguments;

    @ApiModelProperty("输出内容")
    private String response;

    @ApiModelProperty("操作记录链标识")
    private String chain;

    @ApiModelProperty("客户端系统平台")
    private String platform;

    @ApiModelProperty("接口请求地址")
    private String url;

    @ApiModelProperty("接口请求HTTP_METHOD")
    private String httpMethod;

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

    public Instant getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Instant operateTime) {
        this.operateTime = operateTime;
    }

    public OperateResult getResult() {
        return result;
    }

    public void setResult(OperateResult result) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getResultName() {
        if (result != null) {
            resultName = result.getName();
        }
        return resultName;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + getId() +
                ", operator=" + operator +
                ", operatorName='" + operatorName + '\'' +
                ", operateTime=" + operateTime +
                ", result=" + result +
                ", clientDescription='" + clientDescription + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", description='" + description + '\'' +
                ", business='" + business + '\'' +
                ", arguments='" + arguments + '\'' +
                ", response='" + response + '\'' +
                ", chain='" + chain + '\'' +
                ", platform='" + platform + '\'' +
                ", url='" + url + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }
}
