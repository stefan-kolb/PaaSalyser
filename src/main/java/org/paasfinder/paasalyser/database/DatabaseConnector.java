package org.paasfinder.paasalyser.database;

import java.util.List;

import org.paasfinder.paasalyser.report.PaasReport;
import org.paasfinder.paasalyser.report.models.ExtensibleReport;
import org.paasfinder.paasalyser.report.models.HostingReport;
import org.paasfinder.paasalyser.report.models.InfrastructuresReport;
import org.paasfinder.paasalyser.report.models.MetaInfo;
import org.paasfinder.paasalyser.report.models.PricingReport;
import org.paasfinder.paasalyser.report.models.RevisionReport;
import org.paasfinder.paasalyser.report.models.RuntimesReport;
import org.paasfinder.paasalyser.report.models.ScalingReport;
import org.paasfinder.paasalyser.report.models.ServicesReport;
import org.paasfinder.paasalyser.report.models.StatusReport;
import org.paasfinder.paasalyser.report.models.TypeReport;

public interface DatabaseConnector {

	/**
	 * Saves a {@link PaasReport} in the database.
	 * 
	 * @param paasReport
	 * @return
	 */
	public void savePaasReport(PaasReport paasReport);

	/**
	 * Checks if a {@link PaasReport} is present in the database.
	 * 
	 * @param paasReport
	 * @return true if present in the database
	 */
	public boolean contains(PaasReport paasReport);

	/**
	 * Checks if a {@link PaasReport} with the according git-commitHash is
	 * present in the database.
	 * 
	 * @param commitHash
	 *            to search for in the database
	 * @return true if present in the database
	 */
	public boolean contains(String commitHash);

	public List<PaasReport> getAllPaasReportsFromDatabase();

	public void deletePaasReport(String commitHash);

	/**
	 * Fetches date, numberOfProfiles and numberOfEolProfiles of a
	 * {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of{@link PaasReport} with date,
	 *         numberOfProfiles and numberOfEolProfiles
	 */
	public List<MetaInfo> getProfileAmounts();

	/**
	 * Fetches date and {@link RevisionReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link RevisionReport}
	 */
	public List<MetaInfo> getRevisionAmounts();

	/**
	 * Fetches date and {@link TypeReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link TypeReport}
	 */
	public List<MetaInfo> getTypeAmounts();

	/**
	 * Fetches date and {@link StatusReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link StatusReport}
	 */
	public List<PaasReport> getStatusAmounts();

	/**
	 * Fetches date and {@link PricingReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link PricingReport}
	 */
	public List<PaasReport> getPricingAmounts();

	/**
	 * Fetches date and {@link HostingReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link HostingReport}
	 */
	public List<PaasReport> getHostingAmounts();

	/**
	 * Fetches date and {@link ScalingReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link ScalingReport}
	 */
	public List<PaasReport> getScalingAmounts();

	/**
	 * Fetches date and {@link RuntimesReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link RuntimesReport}
	 */
	public List<PaasReport> getRuntimesAmounts();

	/**
	 * Fetches date and {@link ServicesReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link ServicesReport}
	 */
	public List<PaasReport> getServicesAmounts();

	/**
	 * Fetches date and {@link ExtensibleReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link ExtensibleReport}
	 */
	public List<PaasReport> getExtensibleAmounts();

	/**
	 * Fetches date and {@link InfrastructuresReport} of a {@link PaasReport}
	 * 
	 * @return An ascendingly ordered list of {@link PaasReport} with date and
	 *         {@link InfrastructuresReport}
	 */
	public List<PaasReport> getInfrastructuresAmounts();

}
