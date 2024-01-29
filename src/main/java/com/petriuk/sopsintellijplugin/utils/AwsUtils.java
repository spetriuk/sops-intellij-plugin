package com.petriuk.sopsintellijplugin.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ScriptRunnerUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AwsUtils {
  private static final String AWS_CLI_COMMAND = "aws";
  private static final String CONFIGURE_PARAM = "configure";
  private static final String LIST_PROFILES_PARAM = "list-profiles";

  @SneakyThrows
  public static List<String> getAwsProfiles() {
    var command = new GeneralCommandLine(AWS_CLI_COMMAND);
    command.addParameter(CONFIGURE_PARAM);
    command.addParameter(LIST_PROFILES_PARAM);

    var output = ScriptRunnerUtil.getProcessOutput(command);

    return Optional.ofNullable(output)
        .map(String::trim)
        .map(string -> string.split("\n"))
        .map(Arrays::asList)
        .orElse(Collections.emptyList());
  }
}