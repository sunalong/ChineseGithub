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
 * �û����յ��������µ�Fragment
 * ���ڴ������棺��������������
 * �ж�һ�Σ�
 *      �������磬����������
 *      �������磬��ʹ����������
 */
public class UserReceivedNewsFragment extends UserNewsFragment {


    /*
     *  1������һ��github�е���ԴPager:ResourcePager
     *  2��service���丸��UserNewsFragment�ĸ���NewsFragment��������github�еķ���
     *      protected EventService service;
     *  3����service���õ���github.core.jar���еķ���
     *      pageUserReceivedEvents�е��õķ�����createUserReceivedEventRequest
     */
    @Override
    protected ResourcePager<Event> createPager() {
        return new EventPager() {

            @Override
            public PageIterator<Event> createIterator(int page, int size) {
                //TODO:���߲��ж����磬�������磬�����ߺõ����ݷ���
                //�ο�/����github.core.jar�еĽ������緵�ص����ݵķ���Ӧ���ܹ�������
                return service.pageUserReceivedEvents(org.getLogin(), false,
                        page, size);
            }
        };
    }
}


