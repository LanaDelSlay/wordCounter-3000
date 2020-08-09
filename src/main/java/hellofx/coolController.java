package hellofx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class coolController {

	List<File> fileList = new ArrayList<>();

	@FXML
	private Button loadButton;

	@FXML
	private Button countButton;

	@FXML
	public TextArea wordBox;

	@FXML
	private ScrollPane scrolly;

	@FXML
	void countDoc(ActionEvent event) throws IOException, InterruptedException, ExecutionException {
		long begin = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		Set<Future<String>> set = new HashSet<Future<String>>();
		// Thread.sleep(5000);
		for (File f : fileList) {
			executor.submit(new wordCount(f));
			Future<String> result = executor.submit(new wordCount(f));
			set.add(result);
			if (result.toString().contains("contains")) {
				wordBox.setText(wordBox.getText() + result);
			}
		}
		for (Future<String> future : set) {
			wordBox.appendText(future.get());
		}

		long end = System.currentTimeMillis();
		System.out.println("Time be: " + (end - begin));
		event.consume();
		executor.shutdown();
	}

	@FXML
	void loadDoc(ActionEvent event) {
		Window window = ((Node) (event.getSource())).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(window);
		fileList.add(file);
		event.consume();
	}

}
