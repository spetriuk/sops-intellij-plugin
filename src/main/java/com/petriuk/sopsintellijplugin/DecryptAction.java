package com.petriuk.sopsintellijplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import org.jetbrains.annotations.NotNull;

public class DecryptAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    SopsUtils.decrypt(file);
  }

  @Override
  public boolean isDumbAware() {
    return false;
  }

}
