package com.inmaytide.orbit.commons.domain.dto.result;

import com.inmaytide.orbit.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/6/16
 */
@Schema(title = "树结构数据节点实体")
public class TreeNode<T> implements Serializable, Comparable<TreeNode<T>> {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

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
    private TreeSet<TreeNode<T>> children;

    @Schema(title = "节点父级标识", description = "根据需要可设置为父级节点唯一标识或父级节点数据对象")
    private Object parent;

    @Schema(title = "排序字段")
    private Integer sequence;

    public TreeNode() {
        this.children = new TreeSet<>();
    }

    public TreeNode(T entity) {
        this.entity = entity;
        this.children = new TreeSet<>();
    }

    /**
     * 将节点转换成非泛型的树节点, 用于多类型数据实例混合组成树
     */
    public TreeNode<Object> toNonGenericNode() {
        TreeNode<Object> node = new TreeNode<>();
        BeanUtils.copyProperties(this, node, "children");
        node.setChildren(getChildren().stream().map(TreeNode::toNonGenericNode).collect(Collectors.toCollection(TreeSet::new)));
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

    public TreeSet<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(TreeSet<TreeNode<T>> children) {
        this.children = children;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public @NonNull Integer getSequence() {
        if (sequence == null) {
            return Integer.MAX_VALUE;
        }
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public int compareTo(@NotNull TreeNode<T> o) {
        return this.getSequence().compareTo(Objects.requireNonNull(o).getSequence());
    }
}
