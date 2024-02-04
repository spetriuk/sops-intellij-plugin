package com.petriuk.sopsintellijplugin.components;

import static java.awt.FlowLayout.LEFT;
import static java.util.Collections.emptyList;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.AnimatedIcon;
import com.intellij.ui.CollectionComboBoxModel;

public class ComboBoxWithLoader {
  private static final String NONE_ELEMENT = "<none>";

  private final JComponent component;
  private final ComboBox<String> comboBox;
  private final JLabel loaderLabel;

  public ComboBoxWithLoader() {
    component = new JPanel(new FlowLayout(LEFT));
    comboBox = new ComboBox<>(new CollectionComboBoxModel<>(emptyList()));
    loaderLabel = new JLabel(new AnimatedIcon.Default());

    component.add(comboBox);
    component.add(loaderLabel);
  }

  public String getSelectedElement() {
    var selectedValue = (String) comboBox.getSelectedItem();
    return NONE_ELEMENT.equals(selectedValue) ? null : selectedValue;
  }

  public void setSelectedElement(String selectedElement) {
    comboBox.setSelectedItem(defaultIfNull(selectedElement, NONE_ELEMENT));
  }

  public void setElements(ArrayList<String> elements) {
    elements.add(0, NONE_ELEMENT);
    loaderLabel.setVisible(false);
    comboBox.setModel(new CollectionComboBoxModel<>(elements));
  }

  public JComponent getContent() {
    return component;
  }
}
