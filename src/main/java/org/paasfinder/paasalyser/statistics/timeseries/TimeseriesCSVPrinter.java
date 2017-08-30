package org.paasfinder.paasalyser.statistics.timeseries;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeseriesCSVPrinter {

	private final Logger logger = LoggerFactory.getLogger(TimeseriesCSVPrinter.class);
	private final String pathOfTimeseriesReports;

	public TimeseriesCSVPrinter(String pathOfTimeseriesReports) {
		super();
		this.pathOfTimeseriesReports = pathOfTimeseriesReports;
	}

	/**
	 * This method prints a List of String[] to a .csv file with the given
	 * filename
	 * 
	 * @param fileName
	 *            Name of the file that shall be printed
	 * @param toPrint
	 *            List of String[] to print
	 * @return true if no errors occurred
	 */
	public void printToCSVFile(String fileName, List<String[]> toPrint) {
		logger.info("Printing " + fileName);
		try {
			Files.deleteIfExists(Paths.get(pathOfTimeseriesReports + "/" + fileName + ".csv"));
		} catch (IOException e) {
			logger.error("IOException occured during deleting exisiting report", e);
			return;
		}

		try (//
				BufferedWriter writer = Files.newBufferedWriter(
						Paths.get(pathOfTimeseriesReports + "/" + fileName + ".csv"), Charset.defaultCharset(),
						StandardOpenOption.CREATE_NEW);
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL.withDelimiter(':'))) {
			csvPrinter.printRecords(toPrint);
		} catch (IOException e) {
			logger.error("IOException occured during writing to file", e);
		}
		logger.info("Printing successful");
	}

}
