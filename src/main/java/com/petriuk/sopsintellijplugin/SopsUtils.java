package com.petriuk.sopsintellijplugin;

import java.nio.charset.StandardCharsets;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class SopsUtils {
  private static final String SOPS_COMMAND = "sops";
  public static final String ENCRYPT_PARAM = "-e";
  public static final String DECRYPT_PARAM = "-d";

  public static void encrypt(VirtualFile file) {
    execute(ENCRYPT_PARAM, file);
  }

  public static void decrypt(VirtualFile file) {
    execute(DECRYPT_PARAM, file);
  }

  @SneakyThrows
  private static void execute(String actionParam, VirtualFile file) {
    var command = new GeneralCommandLine(SOPS_COMMAND);
    command.addParameters(actionParam, "-i", file.getPath());
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
