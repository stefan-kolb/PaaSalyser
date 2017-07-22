package gsonUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import profile.PaasProfile;
import report.Report;

public class GsonAdapter {

	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static List<PaasProfile> scanDirectoryForJsonFiles(Path rootDirectory) throws IOException {
		if (!Files.isDirectory(rootDirectory)) {
			throw new IOException(rootDirectory + " is no existing directory.");
		} else {
			return Files.walk(rootDirectory)
					//
					.filter(path -> path.toString().endsWith("json"))
					//
					.map(path -> {
						try (InputStream in = Files.newInputStream(path);
								BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
							PaasProfile profile = gson.fromJson(reader, PaasProfile.class);
							return profile;
						} catch (IOException e) {
							return new PaasProfile(true);
						}
					}).collect(Collectors.toCollection(LinkedList::new));
		}
	}

	public static void createReportAsJsonFile(Report report, Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(),
				StandardOpenOption.CREATE)) {
			try {
				gson.toJson(report, writer);
			} catch (JsonIOException e) {
				e.printStackTrace();
			}
		}
	}

}
