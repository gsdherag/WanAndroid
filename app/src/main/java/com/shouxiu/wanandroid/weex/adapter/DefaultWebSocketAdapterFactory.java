package com.shouxiu.wanandroid.weex.adapter;

import com.taobao.weex.appfram.websocket.IWebSocketAdapter;
import com.taobao.weex.appfram.websocket.IWebSocketAdapterFactory;


public class DefaultWebSocketAdapterFactory implements IWebSocketAdapterFactory {

    @Override
    public IWebSocketAdapter createWebSocketAdapter() {
        return new DefaultWebSocketAdapter();
    }

}