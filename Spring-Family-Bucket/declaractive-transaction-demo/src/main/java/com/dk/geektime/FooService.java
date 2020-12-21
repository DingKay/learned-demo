package com.dk.geektime;

import com.dk.geektime.exception.RollbackException;

/**
 * @author Kay
 */
public interface FooService {
    /**
     * 无事务
     */
    void insertDataWithoutTransaction();

    /**
     * 有事务
     */
    void insertDataTransaction();

    /**
     * 有事务，并配置rollback条件：当异常时回滚
     *
     * @throws RollbackException 自定义异常
     */
    void insertDataWhenExceptionRollback() throws RollbackException;

    /**
     * 有事务
     *
     * @throws RollbackException 自定义异常
     */
    void insertData() throws RollbackException;

    /**
     * 无事务，直接调用有事务的方法
     * 此方法本身没有声明事务，直接调用有事务的方法并没有通过代理执行，所以执行带有事务的方法并不生效
     *
     * @throws RollbackException 自定义异常
     */
    void insertDataWithoutTransactionByInvokeOtherMethod() throws RollbackException;

    /**
     * 无事务，自身调用有事务的方法
     * 此方法本身没有声明事务，间接通过自己的实例对象调用带有事务的方法，通过代理执行。
     *
     * @throws RollbackException 自定义异常
     */
    void insertDataWithoutTransactionBySelfInvokeMethod() throws RollbackException;

    /**
     * 无事务，使用代理对象执行带有事务的方法
     * 获取当前代理类，手动使用代理执行
     *
     * @throws RollbackException 自定义异常
     */
    void insertDataByProxyObjInvoke() throws RollbackException;

    /**
     * 有事务，直接调用有事务的方法
     *
     * @throws RollbackException 自定义异常
     */
    void insertDataWithTransaction() throws RollbackException;
}
