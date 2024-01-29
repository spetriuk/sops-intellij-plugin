package com.petriuk.sopsintellijplugin.configurable;

import static java.awt.FlowLayout.LEFT;
import static java.util.Collections.emptyList;

import static com.intellij.util.ui.FormBuilder.createFormBuilder;

import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.AnimatedIcon;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.TitledSeparator;

public class SopsSettingsComponent {
  private static final String AWS_PROFILE_LABEL = "AWS profile";
  private static final JPanel AWS_SETTINGS_SECTION_SEPARATOR = new TitledSeparator("AWS Settings");

  private final JPanel contentPanel;
  private final ComboBox<String> profilesComboBox = new ComboBox<>(new CollectionComboBoxModel<>(emptyList()));
  private final JLabel loaderLabel = new JLabel(new AnimatedIcon.Default());

  public SopsSettingsComponent() {
    contentPanel = createFormBuilder()
        .addComponent(AWS_SETTINGS_SECTION_SEPARATOR)
        .addLabeledComponent(AWS_PROFILE_LABEL, createProfilesDropdown())
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();
  }

  private JComponent createProfilesDropdown() {
    var dropdown = new JPanel(new FlowLayout(LEFT));
    dropdown.add(profilesComboBox);
    dropdown.add(loaderLabel);

    return dropdown;
  }

  public String getSelectedAwsProfile() {
    return (String) profilesComboBox.getSelectedItem();
  }

  public void setSelectedAwsProfile(String selectedProfile) {
    profilesComboBox.setSelectedItem(selectedProfile);
  }

  public void setAwsProfiles(List<String> awsProfiles) {
    loaderLabel.setVisible(false);
    profilesComboBox.setModel(new CollectionComboBoxModel<>(awsProfiles));
  }

  public JPanel getContent() {
    return contentPanel;
  }
}
