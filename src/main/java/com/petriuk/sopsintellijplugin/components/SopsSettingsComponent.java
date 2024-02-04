package com.petriuk.sopsintellijplugin.components;

import static com.intellij.util.ui.FormBuilder.createFormBuilder;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import com.intellij.ui.TitledSeparator;

public class SopsSettingsComponent {
  private static final JPanel AWS_SETTINGS_SECTION_SEPARATOR = new TitledSeparator("AWS Settings");
  private static final String AWS_PROFILE_LABEL = "AWS profile:";

  private final JPanel contentPanel;
  private final ComboBoxWithLoader profilesComboBox;

  public SopsSettingsComponent() {
    profilesComboBox = new ComboBoxWithLoader();

    contentPanel = createFormBuilder()
        .addComponent(AWS_SETTINGS_SECTION_SEPARATOR)
        .addLabeledComponent(AWS_PROFILE_LABEL, profilesComboBox.getContent())
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();
  }

  public String getSelectedAwsProfile() {
    return profilesComboBox.getSelectedElement();
  }

  public void setSelectedAwsProfile(String selectedProfile) {
    profilesComboBox.setSelectedElement(selectedProfile);
  }

  public void setAwsProfiles(List<String> awsProfiles) {
    profilesComboBox.setElements(new ArrayList<>(awsProfiles));
  }

  public JPanel getContent() {
    return contentPanel;
  }
}
