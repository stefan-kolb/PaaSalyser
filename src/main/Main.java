package main;

import java.io.IOException;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		DirectoryScanner scanner = new DirectoryScanner();
		Gson gson = new Gson();
		try {
			scanner.scanDirectoryForJsonFiles(Paths.get("PaasProfiles")).forEach(profile -> {
				System.out.println("Profile - " + profile.getName());
				String json = gson.toJson(profile);
				System.out.println(json);
			});
		} catch (IOException e) {
			System.out.println("Failed");
		}
	}
}
