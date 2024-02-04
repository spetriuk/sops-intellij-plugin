package com.petriuk.sopsintellijplugin.utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
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
  public static final String AWS_PROFILE_ENV = "AWS_PROFILE";

  public static void encrypt(VirtualFile file, Project project) {
    execute(ENCRYPT_PARAM, file, project);
  }

  public static void decrypt(VirtualFile file, Project project) {
    execute(DECRYPT_PARAM, file, project);
  }

  @SneakyThrows
  private static void execute(String actionParam, VirtualFile file, Project project) {
    var command = new GeneralCommandLine(SOPS_COMMAND);
    var path = file.getParent();
    if (path != null) {
      command.setWorkDirectory(path.getPath());
    }
    command.addParameters(actionParam, "-i", file.getName());
    command.setCharset(StandardCharsets.UTF_8);
    command.getEnvironment().putAll(getEnvironmentVariables(project));

    var processHandler = new OSProcessHandler(command);
    processHandler.startNotify();

    processHandler.addProcessListener(new ProcessAdapter() {
      @Override
      public void processTerminated(@NotNull ProcessEvent event) {
        VfsUtil.markDirtyAndRefresh(false, false, false, file);
      }
    });
  }

  private static Map<String, String> getEnvironmentVariables(Project project) {
    var variables = new HashMap<String, String>();
    var sopsSettingsState = SopsSettingsState.getInstance(project);

    Optional.ofNullable(sopsSettingsState.getAwsProfile()).ifPresent(
        profile -> variables.put(AWS_PROFILE_ENV, profile)
    );

    return variables;
  }
}