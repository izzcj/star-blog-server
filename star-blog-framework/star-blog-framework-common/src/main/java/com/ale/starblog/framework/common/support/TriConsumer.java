package com.ale.starblog.framework.common.support;

/**
 * 三元函数式接口
 *
 * @param <K> 参数1类型
 * @param <V> 参数2类型
 * @param <S> 参数3类型
 * @author Ale
 * @version 1.0.0 2025/10/15 15:17
 */
@FunctionalInterface
public interface TriConsumer<K, V, S> {

    /**
     * 执行逻辑
     *
     * @param k 第一个输入参数
     * @param v 第二个输入参数
     * @param s 第三个输入参数
     */
    void accept(K k, V v, S s);
}
