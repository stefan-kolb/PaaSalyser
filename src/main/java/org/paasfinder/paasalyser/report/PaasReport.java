package org.paasfinder.paasalyser.report;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.paasfinder.paasalyser.report.models.BusinessInfo;
import org.paasfinder.paasalyser.report.models.EconomicInfo;
import org.paasfinder.paasalyser.report.models.MetaInfo;
import org.paasfinder.paasalyser.statistics.report.ReportStatistics;

@Entity("paasreports")
@Indexes({ @Index(fields = @Field("id")), @Index(fields = @Field("commitHash")),
		@Index(fields = @Field("metainfos.date")) })
public class PaasReport {

	@Id
	private ObjectId id;
	private String commitHash;
	@Embedded("metainfos")
	private MetaInfo metaInfo;
	@Embedded("businessinfos")
	private BusinessInfo businessInfo;
	@Embedded("economicinfos")
	private EconomicInfo economicInfo;

	/**
	 * Generate a Report out of the statistics
	 * 
	 * @param statistics
	 *            Statistics to generate the report
	 * @throws IllegalStateException
	 *             If input parameter is null
	 */
	public PaasReport(String commitHash, LocalDate date, ReportStatistics statistics) throws IllegalStateException {
		if (commitHash == null || commitHash.isEmpty() || date == null || statistics == null) {
			throw new IllegalStateException("Input parameter waas null or empty");
		}
		this.commitHash = commitHash;
		metaInfo = new MetaInfo(date, statistics.getProfilesCount(), statistics.getEolProfilesCount(),
				statistics.getInvalidProfilesCount(), statistics.getRevision(), statistics.getType());
		businessInfo = new BusinessInfo(statistics.getStatus(), statistics.getPricing());
		economicInfo = new EconomicInfo(statistics.getHosting(), statistics.getPlatform(), statistics.getScaling(),
				statistics.getRuntimes(), statistics.getServices(), statistics.getExtensible(),
				statistics.getInfrastructures());
	}

	/**
	 * Generate an empty report.
	 */
	public PaasReport() {
		super();
	}

	public String getCommitHash() {
		return commitHash;
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}

	public EconomicInfo getEconomicInfo() {
		return economicInfo;
	}

	@Override
	public String toString() {
		return "PaasReport [id=" + id + ", commitHash=" + commitHash + ", metaInfo=" + metaInfo + ", businessInfo="
				+ businessInfo + ", economicInfo=" + economicInfo + "]";
	}

}
