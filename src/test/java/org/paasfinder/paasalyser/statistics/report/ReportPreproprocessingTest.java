package org.paasfinder.paasalyser.statistics.report;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.paasfinder.paasalyser.gsonutility.GsonAdapter;
import org.paasfinder.paasalyser.profile.PaasProfile;

public class ReportPreproprocessingTest {

	private static List<PaasProfile> profiles;
	private static ReportPreprocessing reportPreprocessing;

	@BeforeClass
	public static void buildup() {
		try {
			profiles = new GsonAdapter().scanDirectoryForJsonFiles(Paths.get("test-profiles"));
		} catch (IOException e) {
			System.out.println("IOException occurred during setup");
			e.printStackTrace();
		}
		reportPreprocessing = new ReportPreprocessing(profiles);
	}

	@AfterClass
	public static void tearDown() {

	}

	@Test
	public void testValidProfilesAmount() {
		Assert.assertEquals(5, reportPreprocessing.getProfiles().size());
	}

	@Test
	public void testInvalidProfilesAmount() {
		Assert.assertEquals(0, reportPreprocessing.getInvalidProfilesCount());
	}

	@Test
	public void testEolProfilesAmount() {
		Assert.assertEquals(1, reportPreprocessing.getStatusData().getEol());
	}

	@Test
	public void testRevision() {
		Map<String, String> revisionStrings = new HashMap<>(5);
		revisionStrings.put("Atos Cloud Foundry", "2016-11-28");
		revisionStrings.put("Bluemix", "2015-06-10");
		revisionStrings.put("Heroku", "2017-07-10");
		revisionStrings.put("SAP HANA Cloud Platform", "2016-03-29");
		revisionStrings.put("Platform.sh", "2017-07-03");

		Map<String, Long> revisions = new HashMap<>(5);
		for (Entry<String, String> entry : revisionStrings.entrySet()) {
			revisions.put(entry.getKey(),
					ChronoUnit.DAYS.between(LocalDate.parse(entry.getValue().substring(0, 10)), LocalDate.now()));
		}

		for (Entry<String, Long> entry : reportPreprocessing.getRevisionData().getRevisions().entrySet()) {
			Assert.assertEquals(revisions.get(entry.getKey()), entry.getValue());
		}
	}

	@Test
	public void testStatus() {
		Assert.assertEquals(1, reportPreprocessing.getStatusData().getEol());
		Assert.assertEquals(0, reportPreprocessing.getStatusData().getAlpha());
		Assert.assertEquals(0, reportPreprocessing.getStatusData().getBeta());
		Assert.assertEquals(5, reportPreprocessing.getStatusData().getProduction());

		Assert.assertTrue(reportPreprocessing.getStatusData().getStatusSince().containsKey("Atos Cloud Foundry"));
		Assert.assertTrue(reportPreprocessing.getStatusData().getStatusSince().containsKey("Bluemix"));
		Assert.assertTrue(reportPreprocessing.getStatusData().getStatusSince().containsKey("Heroku"));
		Assert.assertTrue(reportPreprocessing.getStatusData().getStatusSince().containsKey("SAP HANA Cloud Platform"));
		Assert.assertTrue(reportPreprocessing.getStatusData().getStatusSince().containsKey("Platform.sh"));

		Map<String, String> statusSinceStrings = new HashMap<>(5);
		statusSinceStrings.put("Atos Cloud Foundry", "2015-01-07");
		statusSinceStrings.put("Bluemix", "2014-06-30");
		statusSinceStrings.put("Heroku", "2009-04-24");
		statusSinceStrings.put("SAP HANA Cloud Platform", "2012-05-01");
		statusSinceStrings.put("Platform.sh", "2014-09-01");

		Map<String, Long> statusSince = new HashMap<>(5);
		for (Entry<String, String> entry : statusSinceStrings.entrySet()) {
			statusSince.put(entry.getKey(),
					ChronoUnit.DAYS.between(LocalDate.parse(entry.getValue().substring(0, 10)), LocalDate.now()));
		}

		for (Entry<String, Long> entry : reportPreprocessing.getStatusData().getStatusSince().entrySet()) {
			Assert.assertEquals(statusSince.get(entry.getKey()), entry.getValue());
		}
	}

	@Test
	public void testType() {
		Assert.assertEquals(1, reportPreprocessing.getTypeData().getSaasCentric());
		Assert.assertEquals(4, reportPreprocessing.getTypeData().getGeneric());
		Assert.assertEquals(0, reportPreprocessing.getTypeData().getIaasCentric());
	}

	@Test
	public void testPlatform() {
		Assert.assertEquals(2, reportPreprocessing.getPlatformData().getplatformProfiles());

		Assert.assertTrue(reportPreprocessing.getPlatformData().getPlatforms().containsKey("Cloud Foundry"));
		Assert.assertEquals(2, reportPreprocessing.getPlatformData().getPlatforms().get("Cloud Foundry").longValue());
	}

	@Test
	public void testHosting() {
		Assert.assertEquals(3, reportPreprocessing.getHostingData().getPrivate());
		Assert.assertEquals(5, reportPreprocessing.getHostingData().getPublic());
		Assert.assertEquals(1, reportPreprocessing.getHostingData().getVirtualPrivate());
	}

	@Test
	public void testPricing() {
		Assert.assertEquals(0, reportPreprocessing.getPricingData().getZeroModels());
		Assert.assertEquals(3, reportPreprocessing.getPricingData().getOneModel());
		Assert.assertEquals(2, reportPreprocessing.getPricingData().getTwoModels());
		Assert.assertEquals(0, reportPreprocessing.getPricingData().getThreeModels());
		Assert.assertEquals(0, reportPreprocessing.getPricingData().getFourModels());

		Assert.assertEquals(1, reportPreprocessing.getPricingData().getFreeModel());
		Assert.assertEquals(2, reportPreprocessing.getPricingData().getFixedModel());
		Assert.assertEquals(3, reportPreprocessing.getPricingData().getMeteredModel());
		Assert.assertEquals(1, reportPreprocessing.getPricingData().getHybridModel());
		Assert.assertEquals(0, reportPreprocessing.getPricingData().getEmptyModel());

		Assert.assertEquals(0, reportPreprocessing.getPricingData().getDailyPeriod());
		Assert.assertEquals(6, reportPreprocessing.getPricingData().getMonthlyPeriod());
		Assert.assertEquals(0, reportPreprocessing.getPricingData().getAnnuallyPariod());
		Assert.assertEquals(0, reportPreprocessing.getPricingData().getEmptyPeriod());
	}

	@Test
	public void testScaling() {
		Assert.assertEquals(5, reportPreprocessing.getScalingData().getHorizontal());
		Assert.assertEquals(5, reportPreprocessing.getScalingData().getVertical());
		Assert.assertEquals(3, reportPreprocessing.getScalingData().getAuto());
	}

	@Test
	public void testRuntimes() {
		Map<String, Long> runtimesPerProfile = new HashMap<>(5);
		runtimesPerProfile.put("Atos Cloud Foundry", (long) 12);
		runtimesPerProfile.put("Bluemix", (long) 6);
		runtimesPerProfile.put("Heroku", (long) 9);
		runtimesPerProfile.put("SAP HANA Cloud Platform", (long) 2);
		runtimesPerProfile.put("Platform.sh", (long) 7);

		for (Entry<String, Long> entry : reportPreprocessing.getRuntimesData().getNumberPerProfile().entrySet()) {
			Assert.assertEquals(runtimesPerProfile.get(entry.getKey()), entry.getValue());
		}

		Map<String, Long> runtimes = new HashMap<>(13);
		runtimes.put("python", (long) 4);
		runtimes.put("dotnet", (long) 1);
		runtimes.put("scala", (long) 2);
		runtimes.put("clojure", (long) 2);
		runtimes.put("hhvm", (long) 2);
		runtimes.put("go", (long) 4);
		runtimes.put("ruby", (long) 4);
		runtimes.put("node", (long) 4);
		runtimes.put("groovy", (long) 2);
		runtimes.put("java", (long) 5);
		runtimes.put("php", (long) 4);
		runtimes.put("xsjs", (long) 1);
		runtimes.put("swift", (long) 1);

		for (Entry<String, Long> entry : reportPreprocessing.getRuntimesData().getRuntimes().entrySet()) {
			Assert.assertEquals(runtimes.get(entry.getKey()), entry.getValue());
		}
	}

	@Test
	public void testServices() {
		Assert.fail();
	}

	@Test
	public void testExtensible() {
		Assert.fail();
	}

	@Test
	public void testInfrastructures() {
		Assert.fail();
	}
}
