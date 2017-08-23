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

import org.paasfinder.paasalyser.gsonutility.deserializers.HostingDeserializer;
import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.profile.models.Hosting;
import org.paasfinder.paasalyser.report.PaasReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class GsonAdapter {

	private final Logger logger = LoggerFactory.getLogger(GsonAdapter.class);

	private GsonBuilder gsonBuilder;
	private Gson gson;

	public GsonAdapter() {
		gsonBuilder = createGsonBuilderWithCustomDeserializing();
		gson = gsonBuilder.setPrettyPrinting().create();
	}

	/**
	 * Scans the directory for PaasProfile json files and returns a list of
	 * those. If scanning a profile failed, the profile will be null.
	 * 
	 * @param rootDirectory
	 *            Directory to scan
	 * @return List of PaasProfiles in this directory. If an error occurred
	 *         during scanning, the according profile will be null.
	 * @throws IOException
	 *             If rootDirectory is not a directory
	 */
	public List<PaasProfile> scanDirectoryForJsonFiles(Path rootDirectory) throws IOException {
		if (!Files.isDirectory(rootDirectory)) {
			throw new IOException(rootDirectory + " is no existing directory");
		} else {
			return Files.walk(rootDirectory).filter(path -> path.toString().endsWith("json")).map(path -> {
				try (InputStream in = Files.newInputStream(path);
						BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
					PaasProfile profile = gson.fromJson(reader, PaasProfile.class);
					try {
						return profile.checkProfileValidity(path);
					} catch (NullPointerException e) {
						logger.error("Invalid Profile: " + path.getFileName().toString());
						return null;
					}
				} catch (IOException e) {
					logger.error("IOException occurred during scanning directory at: " + path.getFileName());
					return null;
				} catch (JsonSyntaxException e) {
					logger.error("JsonSyntaxException occurred during scanning directory at: " + path.getFileName());
					return null;
				}
			}).collect(Collectors.toCollection(LinkedList::new));
		}

	}

	/**
	 * Outputs a Report to the given path.
	 * 
	 * @param report
	 *            Report to output.
	 * @param path
	 *            Path where to output.
	 * @throws IOException
	 *             Gson was unable to stream or write to stream
	 */
	public void createReportAsJsonFile(PaasReport report, Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(),
				StandardOpenOption.CREATE)) {
			try {
				gson.toJson(report, writer);
			} catch (JsonIOException e) {
				throw new IOException("Failed to output to json", e);
			}
		}
	}

	// public Map<String, PaasReport> scanReportsFromDatastore(Path
	// rootDirectory) throws IOException {
	// if (!Files.isDirectory(rootDirectory)) {
	// throw new IOException(rootDirectory + " is no existing directory");
	// } else {
	// return Files.walk(rootDirectory).filter(path ->
	// path.toString().endsWith("json")).map(path -> {
	// try (InputStream in = Files.newInputStream(path);
	// BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
	// return new AbstractMap.SimpleEntry(path, gson.fromJson(reader,
	// PaasReport.class));
	// } catch (IOException e) {
	// logger.error("IOException occurred during scanning directory at: " +
	// path.getFileName()
	// + " | Message: " + e.getMessage());
	// return null;
	// } catch (JsonSyntaxException e) {
	// logger.error("JsonSyntaxException occurred during scanning directory at:
	// " + path.getFileName()
	// + " | Message: " + e.getMessage());
	// return null;
	// }
	// }).collect(Collectors.toCollection(HashMap::new));
	// }
	// }

	private GsonBuilder createGsonBuilderWithCustomDeserializing() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		// PaasProfileDeserializer paasProfileDeserializer = new
		// PaasProfileDeserializer();
		// PricingDeserializer pricingDeserializer = new PricingDeserializer();
		// ScalingDeserializer scalingDeserializer = new ScalingDeserializer();
		// RuntimeDeserializer runtimeDeserializer = new RuntimeDeserializer();
		// ServicesDeserializer servicesDeserializer = new
		// ServicesDeserializer();
		// InfrastructureDeserializer infrastructureDeserializer = new
		// InfrastructureDeserializer();

		// gsonBuilder.registerTypeAdapter(PaasProfile.class,
		// paasProfileDeserializer);
		gsonBuilder.registerTypeAdapter(Hosting.class, new HostingDeserializer());
		// gsonBuilder.registerTypeAdapter(Pricing.class, pricingDeserializer);
		// gsonBuilder.registerTypeAdapter(Scaling.class, scalingDeserializer);
		// gsonBuilder.registerTypeAdapter(Runtime.class, runtimeDeserializer);
		// gsonBuilder.registerTypeAdapter(Services.class,
		// servicesDeserializer);
		// gsonBuilder.registerTypeAdapter(Infrastructure.class,
		// infrastructureDeserializer);

		return gsonBuilder;
	}

}
