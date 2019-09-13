package edu.chalmers.projecttemplate;

import edu.chalmers.projecttemplate.controller.ProjectController;
import edu.chalmers.projecttemplate.model.Project;
import edu.chalmers.projecttemplate.view.ProjectView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;

public final class ProjectTemplate extends Application {
	private ProjectTemplate() {
		/* No instances allowed! */
	}

	@Override
	public void start(Stage stage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("storageSystem.fxml"));

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.show();
	}//memo

	public static void main(String[] args) {
		/*SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final Project project = new Project();
				final ProjectView projectView = new ProjectView(project);

				ProjectController.create(project, projectView);
				projectView.setVisible(true);
			}
		});*/

		launch(args);
	}
}
