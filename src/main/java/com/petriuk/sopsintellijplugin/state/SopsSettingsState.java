package com.petriuk.sopsintellijplugin.state;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@State(
    name = "com.petriuk.sopsintellijplugin.state.SopsSettingsState",
    storages = @Storage("SopsSettingsPlugin.xml")
)
@Service(Service.Level.PROJECT)
public final class SopsSettingsState implements PersistentStateComponent<SopsSettingsState> {

  private String awsProfile;

  public static SopsSettingsState getInstance(Project project) {
    return project.getService(SopsSettingsState.class);
  }

  @Override
  public SopsSettingsState getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull SopsSettingsState state) {
    XmlSerializerUtil.copyBean(state, this);
  }
}
