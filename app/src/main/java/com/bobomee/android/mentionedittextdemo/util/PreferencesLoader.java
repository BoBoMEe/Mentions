/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bobomee.android.mentionedittextdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 15/10/14 11:49
 */
public class PreferencesLoader {

  private SharedPreferences mSharedPreferences;
  private Context mContext;

  public PreferencesLoader(Context context) {
    mContext = context;
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  public int getInt(int keyResId) {
    String key = mContext.getString(keyResId);
    return getInt(key);
  }

  public int getInt(String key) {
    return mSharedPreferences.getInt(key, 0);
  }

  public void saveInt(int keyResId, int value) {
    String key = mContext.getString(keyResId);
    saveInt(key, value);
  }

  public void saveInt(String key, int value) {
    SharedPreferences.Editor editor = mSharedPreferences.edit();
    editor.putInt(key, value);
    editor.apply();
  }
}
