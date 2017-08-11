package org.paasfinder.paasalyser.gsonutility;

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
import com.google.gson.JsonSyntaxException;
import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.report.Report;

public class GsonAdapter {

	private Gson gson;

	public GsonAdapter() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public void walkAndPrintFileTree(Path rootDirectory) throws IOException {
		if (!Files.isDirectory(rootDirectory)) {
			throw new IOException(rootDirectory + " is no existing directory.");
		} else {
			Files.walk(rootDirectory).filter(path -> path.toString().endsWith("json"))
					.peek(entry -> System.out.println(entry));
		}
	}

	public List<PaasProfile> scanDirectoryForJsonFiles(Path rootDirectory) throws IOException {
		if (!Files.isDirectory(rootDirectory)) {
			throw new IOException(rootDirectory + " is no existing directory.");
		} else {
			return Files.walk(rootDirectory).filter(path -> path.toString().endsWith("json")).map(path -> {
				try (InputStream in = Files.newInputStream(path);
						BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
					PaasProfile profile = gson.fromJson(reader, PaasProfile.class);
					return profile;
				} catch (IOException | JsonSyntaxException e) {
					// TODO Error Logging
					// Initial Commit is: 7f132fabb4e220f794b4926309dc8d48c794768c
					return new PaasProfile(true);
				}
				// TODO Logging Parsing Exceptions
			}).collect(Collectors.toCollection(LinkedList::new));
		}
	}

	public void createReportAsJsonFile(Report report, Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(),
				StandardOpenOption.CREATE)) {
			try {
				gson.toJson(report, writer);
			} catch (JsonIOException e) {
				throw new IOException(e);
			}
		}
	}

	public List<PaasProfile> debugScanDirectoryForJsonFiles(Path rootDirectory) throws IOException {
		if (!Files.isDirectory(rootDirectory)) {
			throw new IOException(rootDirectory + " is no existing directory.");
		} else {
			return Files.walk(rootDirectory).filter(path -> path.toString().endsWith("json")).map(path -> {
				try (InputStream in = Files.newInputStream(path);
						BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
					System.out.println("Current path is: " + path);
					PaasProfile profile = gson.fromJson(reader, PaasProfile.class);
					return profile;
				} catch (IOException | JsonSyntaxException e) {
					// TODO logging
					return new PaasProfile(true);
				}
			}).collect(Collectors.toCollection(LinkedList::new));
		}
	}

}
