/***
  Copyright (c) 2011 CommonsWare, LLC
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.commonsware.android.actionmode;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ActionModeHelper
  implements ActionMode.Callback, AdapterView.OnItemLongClickListener {
  ActionModeDemo host;
  ActionMode activeMode;
  ListView modeView;
  int lastPosition=-1;

  ActionModeHelper(final ActionModeDemo host, ListView modeView) {
    this.host=host;
    this.modeView=modeView;
  }
  
  @Override
  public boolean onItemLongClick(AdapterView<?> view, View row,
                                 int position, long id) {
    lastPosition=position;
    modeView.clearChoices();
    modeView.setItemChecked(lastPosition, true);

    if (activeMode==null) {
      activeMode=host.startActionMode(this);
    }

    return(true);
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    MenuInflater inflater=host.getMenuInflater();

    inflater.inflate(R.menu.context, menu);
    mode.setTitle(R.string.context_title);

    return(true);
  }

  @Override
  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    return(false);
  }

  @Override
  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    boolean result=host.performAction(item, lastPosition);
    
    if (item.getItemId()==R.id.remove) {
      activeMode.finish();
    }
    
    return(result);
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {
    activeMode=null;
    modeView.clearChoices();
  }
}