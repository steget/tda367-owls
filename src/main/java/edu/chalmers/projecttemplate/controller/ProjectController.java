package edu.chalmers.projecttemplate.controller;

import edu.chalmers.projecttemplate.model.Project;
import edu.chalmers.projecttemplate.view.ProjectView;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {
  //private final Project project;
  //private final ProjectView projectView;
  public static final int KO = 1;

  /*public static ProjectController create(Project project, ProjectView projectView) {
    return new ProjectController(project, projectView);
  }*/



  private class ProjectButtonPressed implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
  //    project.incrementPresses();
  //    projectView.getPressesLabel().setText(String.valueOf(project.getPresses()));
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {



  }
}
