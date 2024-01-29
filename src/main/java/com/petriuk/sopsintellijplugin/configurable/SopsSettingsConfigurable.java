package com.petriuk.sopsintellijplugin.configurable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JComponent;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.petriuk.sopsintellijplugin.state.SopsSettingsState;
import com.petriuk.sopsintellijplugin.utils.AwsUtils;

public class SopsSettingsConfigurable implements Configurable {
  private final Project project;
  private SopsSettingsComponent component;

  public SopsSettingsConfigurable(Project project) {
    this.project = project;
  }

  @Override
  public String getDisplayName() {
    return "SOPS Settings";
  }

  @Override
  public JComponent createComponent() {
    component = new SopsSettingsComponent();
    fetchAwsProfiles();

    return component.getContent();
  }

  @Override
  public boolean isModified() {
    var sopsSettingsState = SopsSettingsState.getInstance();
    var selected = component.getSelectedAwsProfile();

    return !Objects.equals(selected, sopsSettingsState.getAwsProfile());
  }

  @Override
  public void apply() {
    var sopsSettingsState = SopsSettingsState.getInstance();

    var selected = component.getSelectedAwsProfile();
    sopsSettingsState.setAwsProfile(selected);
  }

  @Override
  public void reset() {
    var sopsSettingsState = SopsSettingsState.getInstance();
    component.setSelectedAwsProfile(sopsSettingsState.getAwsProfile());
  }

  private void fetchAwsProfiles() {
    var task = new Task.Backgroundable(project, "Fetching AWS profiles") {
      List<String> fetchedProfiles = new ArrayList<>();

      @Override
      public void run(ProgressIndicator indicator) {
        this.fetchedProfiles = AwsUtils.getAwsProfiles();
      }

      @Override
      public void onSuccess() {
        var sopsSettingsState = SopsSettingsState.getInstance();

        component.setAwsProfiles(fetchedProfiles);

        if (fetchedProfiles.contains(sopsSettingsState.getAwsProfile())) {
          component.setSelectedAwsProfile(sopsSettingsState.getAwsProfile());
        }
      }
    };
    task.queue();
  }


}
