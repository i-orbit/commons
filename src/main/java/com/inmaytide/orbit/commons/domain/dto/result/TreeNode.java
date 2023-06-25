package com.inmaytide.orbit.commons.domain.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/6/16
 */
@Schema(title = "树结构数据节点实体")
public class TreeNode<T> implements Serializable {

    @Schema(title = "唯一标识", description = "一般等于关联数据实例对象的唯一标识")
    private Serializable id;

    @Schema(title = "关联数据实例对象")
    private T entity;

    @Schema(title = "显示名称")
    private String name;

    @Schema(title = "节点关联数据实例对象类型标识", description = "用于多类型数据实例混合组成树时, 标识某个节点的实例对象类型", example = "organization")
    private String symbol;

    @Schema(title = "当前用户是否有关联数据实例对象的权限")
    private boolean authorized;

    @Schema(title = "节点处于树结构数据中的层级", description = "从 1 开始")
    private Integer level;

    @Schema(title = "子节点列表")
    private List<TreeNode<T>> children;

    @Schema(title = "节点父级表示", description = "根据需要可设置为父级节点唯一标识或父级节点数据对象")
    private Object parent;

    public TreeNode() {
        this.children = new ArrayList<>();
    }

    public TreeNode(T entity) {
        this.entity = entity;
        this.children = new ArrayList<>();
    }

    /**
     * 将节点转换成非泛型的树节点, 用于多类型数据实例混合组成树
     */
    public TreeNode<Object> toNonGenericNode() {
        TreeNode<Object> node = new TreeNode<>();
        BeanUtils.copyProperties(this, node, "children");
        node.setChildren(getChildren().stream().map(TreeNode::toNonGenericNode).collect(Collectors.toList()));
        return node;
    }


    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }
}
