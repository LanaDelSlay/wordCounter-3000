package hellofx;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;

class wordCount implements Callable<String> {
	File f;

	public wordCount(File f) {
		this.f = f;
	}

	@Override
	public String call() throws Exception {
		int count = 0;
		FileInputStream fis;
		fis = new FileInputStream(f);
		byte[] bytesArray = new byte[(int) f.length()];
		fis.read(bytesArray);
		String s = new String(bytesArray);
		String[] data = s.split(" ");
		for (int i = 0; i < data.length; i++) {
			count++;
		}
		fis.close();
		String line = f.getName() + " contains " + count + " words.\n";
		// System.out.println(line);
		return line;
	}

}