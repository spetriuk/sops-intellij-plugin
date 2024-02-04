package com.petriuk.sopsintellijplugin.configurable;

import static java.util.Collections.emptyList;

import static org.apache.commons.lang3.ObjectUtils.notEqual;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.petriuk.sopsintellijplugin.components.SopsSettingsComponent;
import com.petriuk.sopsintellijplugin.state.SopsSettingsState;
import com.petriuk.sopsintellijplugin.utils.AwsUtils;
import org.jetbrains.annotations.NotNull;

public class SopsSettingsConfigurable implements Configurable {
  public static final String AWS_PROFILES_FETCH_PROGRESS_LABEL = "Fetching AWS profiles";
  public static final String SOPS_SETTINGS_LABEL = "SOPS Settings";

  private final Project project;
  private SopsSettingsComponent component;

  public SopsSettingsConfigurable(Project project) {
    this.project = project;
  }

  @Override
  public String getDisplayName() {
    return SOPS_SETTINGS_LABEL;
  }

  @Override
  public JComponent createComponent() {
    component = new SopsSettingsComponent();
    fetchAwsProfiles();

    return component.getContent();
  }

  @Override
  public boolean isModified() {
    var sopsSettingsState = SopsSettingsState.getInstance(project);
    var selected = component.getSelectedAwsProfile();

    return notEqual(selected, sopsSettingsState.getAwsProfile());
  }

  @Override
  public void apply() {
    var sopsSettingsState = SopsSettingsState.getInstance(project);
    sopsSettingsState.setAwsProfile(component.getSelectedAwsProfile());
  }

  @Override
  public void reset() {
    var sopsSettingsState = SopsSettingsState.getInstance(project);
    component.setSelectedAwsProfile(sopsSettingsState.getAwsProfile());
  }

  private void fetchAwsProfiles() {
    var task = new Task.Backgroundable(project, AWS_PROFILES_FETCH_PROGRESS_LABEL) {
      List<String> fetchedProfiles = new ArrayList<>();

      @Override
      public void run(@NotNull ProgressIndicator indicator) {
        this.fetchedProfiles = AwsUtils.getAwsProfiles();
      }

      @Override
      public void onSuccess() {
        var sopsSettingsState = SopsSettingsState.getInstance(project);

        component.setAwsProfiles(fetchedProfiles);

        if (fetchedProfiles.contains(sopsSettingsState.getAwsProfile())) {
          component.setSelectedAwsProfile(sopsSettingsState.getAwsProfile());
        }
      }

      @Override
      public void onThrowable(@NotNull Throwable error) {
        component.setAwsProfiles(emptyList());
      }
    };
    task.queue();
  }
}
