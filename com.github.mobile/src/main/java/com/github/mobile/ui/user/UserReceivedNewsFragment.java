/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mobile.ui.user;

import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;

/**
 * News that a given user has received
 * 用户接收到的新鲜事的Fragment
 * 可在此做缓存：将数据离线下来
 * 判断一次：
 *      若有网络，则正常请求
 *      若无网络，则使用离线数据
 */
public class UserReceivedNewsFragment extends UserNewsFragment {


    /*
     *  1：返回一个github中的资源Pager:ResourcePager
     *  2：service是其父类UserNewsFragment的父类NewsFragment中声明的github中的服务：
     *      protected EventService service;
     *  3：此service调用的是github.core.jar包中的方法
     *      pageUserReceivedEvents中调用的方法：createUserReceivedEventRequest
     */
    @Override
    protected ResourcePager<Event> createPager() {
        return new EventPager() {

            @Override
            public PageIterator<Event> createIterator(int page, int size) {
                //TODO:离线并判断网络，若无网络，则将离线好的数据返回
                //参考/调用github.core.jar中的解析网络返回的数据的方法应该能够做到。
                return service.pageUserReceivedEvents(org.getLogin(), false,
                        page, size);
            }
        };
    }
}


