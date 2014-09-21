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

import org.eclipse.egit.github.core.event.Event;

import android.util.Log;

import com.github.mobile.core.ResourcePager;

/**
 * Pager over events
 */
public abstract class EventPager extends ResourcePager<Event> {

    private static final String TAG = "EventPager";

    @Override
    protected Object getId(Event resource) {
        return resource.getId();
    }

    @Override
    protected Event register(Event resource) {
        return NewsListAdapter.isValid(resource) ? resource : null;
    }

}
