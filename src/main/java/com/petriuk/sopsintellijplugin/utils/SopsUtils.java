package com.petriuk.sopsintellijplugin.utils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.petriuk.sopsintellijplugin.state.SopsSettingsState;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class SopsUtils {
  private static final String SOPS_COMMAND = "sops";
  private static final String ENCRYPT_PARAM = "-e";
  private static final String DECRYPT_PARAM = "-d";
  private static final String AWS_PROFILE_PARAM = "--aws-profile";

  public static void encrypt(VirtualFile file, Project project) {
    execute(ENCRYPT_PARAM, file, project);
  }

  public static void decrypt(VirtualFile file, Project project) {
    execute(DECRYPT_PARAM, file, project);
  }

  @SneakyThrows
  private static void execute(String actionParam, VirtualFile file, Project project) {
    var sopsSettingsState = SopsSettingsState.getInstance(project);

    var command = new GeneralCommandLine(SOPS_COMMAND);
    var path = file.getParent();
    if (path != null) {
      command.setWorkDirectory(path.getPath());
    }
    command.addParameters(actionParam, "-i", file.getName());
    Optional.ofNullable(sopsSettingsState.getAwsProfile()).ifPresent(
        profile -> command.addParameters(AWS_PROFILE_PARAM, profile)
    );
    command.setCharset(StandardCharsets.UTF_8);

    var processHandler = new OSProcessHandler(command);
    processHandler.startNotify();

    processHandler.addProcessListener(new ProcessAdapter() {
      @Override
      public void processTerminated(@NotNull ProcessEvent event) {
        VfsUtil.markDirtyAndRefresh(false, false, false, file);
      }
    });
  }
}
