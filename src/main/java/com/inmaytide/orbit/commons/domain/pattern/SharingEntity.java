package com.inmaytide.orbit.commons.domain.pattern;

import com.inmaytide.orbit.commons.constants.Sharing;
import com.inmaytide.orbit.commons.domain.SystemUser;

import java.util.Objects;

/**
 * @author inmaytide
 * @since 2024/4/16
 */
public interface SharingEntity {

    Sharing getSharing();

    Long getTenant();

    Long getOrganization();

    Long getArea();

    Long getCreatedBy();

    default boolean isAuthorized(SystemUser user) {
        return switch (getSharing()) {
            case GENERIC -> true;
            case TENANT -> Objects.equals(getTenant(), user.getTenant());
            case ORGANIZATION -> user.getPerspective().getOrganizations().contains(getOrganization());
            case AREA -> user.getPerspective().getAreas().contains(getArea());
            case PRIVATE -> Objects.equals(getCreatedBy(), user.getId());
            case null -> Objects.equals(getCreatedBy(), user.getId());
        };
    }

}
