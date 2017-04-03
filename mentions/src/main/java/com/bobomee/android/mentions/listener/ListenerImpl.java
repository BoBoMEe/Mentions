/*
 * Copyright (C) 2017.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.mentions.listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/12/18.下午6:41.
 *
 * @author bobomee.
 */

public class ListenerImpl<ListenerType> implements IListener<ListenerType> {

  private List<ListenerType> mListenerTypes;

  @Override public void addListener(ListenerType _listenerType) {

    if (null == mListenerTypes) {
      mListenerTypes = new ArrayList<>();
    }
    if (!mListenerTypes.contains(_listenerType)) mListenerTypes.add(_listenerType);
  }

  @Override public boolean removeListener(ListenerType _listenerType) {
    return mListenerTypes.remove(_listenerType);
  }

  public List<ListenerType> from() {
    return mListenerTypes;
  }

  public boolean hasListener() {
    return null != mListenerTypes && mListenerTypes.size() > 0;
  }
}
