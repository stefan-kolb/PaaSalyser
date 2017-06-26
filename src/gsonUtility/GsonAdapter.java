package gsonUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.PaasProfile;
import models.Report;

public class GsonAdapter {

	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public GsonAdapter() {

	}

	public static List<PaasProfile> scanDirectoryForJsonFiles(Path rootDirectory) throws IOException {
		List<PaasProfile> profilesList = new LinkedList<PaasProfile>();
		if (!Files.isDirectory(rootDirectory)) {
			throw new IOException(rootDirectory + " is no existing directory.");
		} else {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootDirectory)) {
				stream.forEach(path -> {
					if (path.toString().endsWith("json")) {
						try (InputStream in = Files.newInputStream(path);
								BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
							PaasProfile profile = gson.fromJson(reader, PaasProfile.class);
							System.out.println("Profile - " + profile.getName());
							profilesList.add(profile);
						} catch (IOException e) {
							System.out.println("Failed");
							profilesList.add(new PaasProfile(true));
						}
					}
					System.out.println("Scanned - " + path.toString());
				});
			}
		}
		return profilesList;
	}

	public static void createReportAsJsonFile(Report report, Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(),
				StandardOpenOption.CREATE)) {
			gson.toJson(report, writer);
		}
	}

}
