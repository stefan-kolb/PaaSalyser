package org.paasfinder.paasalyser.statistics.timeseries;

import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeseriesStatistics {

	private final Logger logger = LoggerFactory.getLogger(TimeseriesStatistics.class);

	CSVFormat format = CSVFormat.EXCEL;

	public TimeseriesStatistics() {
		super();

		try (CSVPrinter printer = new CSVPrinter(System.out, CSVFormat.EXCEL)) {
			printer.printRecord("ID", "Name", "Role", "Salary");
			// for(Employee emp : emps){
			// List<String> empData = new ArrayList<String>();
			// empData.add(emp.getId());
			// empData.add(emp.getName());
			// empData.add(emp.getRole());
			// empData.add(emp.getSalary());
			// printer.printRecord(empData);
			// }
		} catch (IOException e) {
			logger.error("IOException occurred while printing CSV", e);
		}
	}

}
