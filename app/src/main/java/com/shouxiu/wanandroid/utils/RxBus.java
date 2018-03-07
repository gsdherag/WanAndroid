package com.shouxiu.wanandroid.utils;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @author yeping
 * @date 2018/3/6 16:29
 * @description ${TODO}
 */

public class RxBus {

    private static volatile RxBus rxBus;

    private final FlowableProcessor<Object> mBus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus(){
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getInstance(){
        if (rxBus == null){
            synchronized (RxBus.class){
                if (rxBus == null){
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    // 提供了一个新的事件
    public void post(Object o){
        mBus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T>Flowable<T> toFlowablle(Class<T> eventType){
        return mBus.ofType(eventType);
    }
}
