package com.inmaytide.orbit.commons.business.id;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/4/4
 */
public interface IdGenerator<T> {

    T generate();

    List<T> batchGenerate(int number);

}
