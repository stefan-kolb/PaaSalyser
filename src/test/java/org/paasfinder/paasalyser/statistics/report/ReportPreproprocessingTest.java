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
		reportPreprocessing = new ReportPreprocessing(LocalDate.now(), profiles);
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
		Assert.assertEquals(1, reportPreprocessing.getInvalidProfilesCount());
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
		Assert.assertEquals(1, reportPreprocessing.getStatusData().getAlpha());
		Assert.assertEquals(1, reportPreprocessing.getStatusData().getBeta());
		Assert.assertEquals(2, reportPreprocessing.getStatusData().getProduction());

		Assert.assertTrue(reportPreprocessing.getStatusData().getStatusSince().containsKey("Bluemix"));
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
		Assert.assertEquals(2, reportPreprocessing.getTypeData().getGeneric());
		Assert.assertEquals(1, reportPreprocessing.getTypeData().getIaasCentric());
	}

	@Test
	public void testPlatform() {
		Assert.assertEquals(1, reportPreprocessing.getPlatformData().getplatformProfiles());

		Assert.assertTrue(reportPreprocessing.getPlatformData().getPlatforms().containsKey("Cloud Foundry"));
		Assert.assertEquals(1, reportPreprocessing.getPlatformData().getPlatforms().get("Cloud Foundry").longValue());
	}

	@Test
	public void testHosting() {
		Assert.assertEquals(3, reportPreprocessing.getHostingData().getPrivate());
		Assert.assertEquals(5, reportPreprocessing.getHostingData().getPublic());
		Assert.assertEquals(1, reportPreprocessing.getHostingData().getVirtualPrivate());
	}

	@Test
	public void testPricing() {
		// zeroModels=1, oneModel=1, twoModels=1, threeModels=1, fourModels=1,
		// freeModel=3, fixedModel=4, meteredModel=1, hybridModel=2,
		// emptyModel=0, dailyPeriod=1, monthlyPeriod=5, annuallyPariod=1,
		// emptyPeriod=0

		Assert.assertEquals(1, reportPreprocessing.getPricingData().getZeroModels());
		Assert.assertEquals(1, reportPreprocessing.getPricingData().getOneModel());
		Assert.assertEquals(1, reportPreprocessing.getPricingData().getTwoModels());
		Assert.assertEquals(1, reportPreprocessing.getPricingData().getThreeModels());
		Assert.assertEquals(1, reportPreprocessing.getPricingData().getFourModels());

		Assert.assertEquals(3, reportPreprocessing.getPricingData().getFreeModel());
		Assert.assertEquals(4, reportPreprocessing.getPricingData().getFixedModel());
		Assert.assertEquals(1, reportPreprocessing.getPricingData().getMeteredModel());
		Assert.assertEquals(2, reportPreprocessing.getPricingData().getHybridModel());
		Assert.assertEquals(0, reportPreprocessing.getPricingData().getEmptyModel());

		Assert.assertEquals(1, reportPreprocessing.getPricingData().getDailyPeriod());
		Assert.assertEquals(5, reportPreprocessing.getPricingData().getMonthlyPeriod());
		Assert.assertEquals(1, reportPreprocessing.getPricingData().getAnnuallyPariod());
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
		Map<String, Long> profilesWithNativeServices = new HashMap<>(5);
		profilesWithNativeServices.put("Atos Cloud Foundry", (long) 1);
		profilesWithNativeServices.put("Bluemix", (long) 1);
		profilesWithNativeServices.put("Heroku", (long) 2);
		profilesWithNativeServices.put("SAP HANA Cloud Platform", (long) 1);
		profilesWithNativeServices.put("Platform.sh", (long) 12);

		for (Entry<String, Long> entry : reportPreprocessing.getServicesData().getProfilesWithNativeServices()
				.entrySet()) {
			Assert.assertEquals(profilesWithNativeServices.get(entry.getKey()), entry.getValue());
		}

		Map<String, Long> nativeServices = new HashMap<>();
		nativeServices.put("memcached", (long) 1);
		nativeServices.put("influxdb", (long) 1);
		nativeServices.put("rabbitmq", (long) 1);
		nativeServices.put("redis", (long) 2);
		nativeServices.put("filesystem", (long) 1);
		nativeServices.put("blob", (long) 1);
		nativeServices.put("postgresql", (long) 2);
		nativeServices.put("elasticsearch", (long) 1);
		nativeServices.put("solr", (long) 1);
		nativeServices.put("SAP HANA persistence Services", (long) 1);
		nativeServices.put("alchemyapi", (long) 2);
		nativeServices.put("mysql", (long) 1);
		nativeServices.put("mongodb", (long) 2);
		nativeServices.put("postgis", (long) 1);

		for (Entry<String, Long> entry : reportPreprocessing.getServicesData().getNativeServices().entrySet()) {
			Assert.assertEquals(nativeServices.get(entry.getKey()), entry.getValue());
		}

		Map<String, Long> typesOfNativeServices = new HashMap<>(5);
		typesOfNativeServices.put("cache", (long) 1);
		typesOfNativeServices.put("other", (long) 2);
		typesOfNativeServices.put("datastore", (long) 12);
		typesOfNativeServices.put("storage", (long) 1);
		typesOfNativeServices.put("empty", (long) 1);

		for (Entry<String, Long> entry : reportPreprocessing.getServicesData().getTypesOfNativeServices().entrySet()) {
			Assert.assertEquals(typesOfNativeServices.get(entry.getKey()), entry.getValue());
		}
	}

	@Test
	public void testExtensible() {
		Assert.assertEquals(3, reportPreprocessing.getExtensibleData().getTrue());
	}

	@Test
	public void testInfrastructures() {
		// infrastructuresPerProfile={Atos Cloud Foundry=1, Bluemix=1, Heroku=1,
		// SAP HANA Cloud Platform=1, Platform.sh=2}
		Map<String, Long> infrastructuresPerProfile = new HashMap<>(3);
		infrastructuresPerProfile.put("Bluemix", (long) 1);
		infrastructuresPerProfile.put("Heroku", (long) 1);
		infrastructuresPerProfile.put("Platform.sh", (long) 2);

		for (Entry<String, Long> entry : reportPreprocessing.getInfrastructuresData().getInfrastructuresPerProfile()
				.entrySet()) {
			Assert.assertEquals(infrastructuresPerProfile.get(entry.getKey()), entry.getValue());
		}

		// profileContinents={EU=2, AS=2, NA=1, OC=1}
		Map<String, Long> profileContinents = new HashMap<>(4);
		profileContinents.put("EU", (long) 1);
		profileContinents.put("AS", (long) 1);
		profileContinents.put("NA", (long) 1);
		profileContinents.put("OC", (long) 1);

		for (Entry<String, Long> entry : reportPreprocessing.getInfrastructuresData().getProfileContinents()
				.entrySet()) {
			Assert.assertEquals(profileContinents.get(entry.getKey()), entry.getValue());
		}

		// continent={EU=2, AS=2, NA=1, OC=1}
		Map<String, Long> continent = new HashMap<>(4);
		continent.put("EU", (long) 1);
		continent.put("AS", (long) 1);
		continent.put("NA", (long) 1);
		continent.put("OC", (long) 1);

		for (Entry<String, Long> entry : reportPreprocessing.getInfrastructuresData().getContinent().entrySet()) {
			Assert.assertEquals(continent.get(entry.getKey()), entry.getValue());
		}

		// country={DE=1, SG=1, AU=1, JP=1, IE=1, US=1}
		Map<String, Long> country = new HashMap<>(6);
		country.put("AU", (long) 1);
		country.put("JP", (long) 1);
		country.put("IE", (long) 1);
		country.put("US", (long) 1);

		for (Entry<String, Long> entry : reportPreprocessing.getInfrastructuresData().getCountry().entrySet()) {
			Assert.assertEquals(country.get(entry.getKey()), entry.getValue());
		}

		// region={Singapore=1, St. Leon-Rot=1, Tokyo=1, Dublin=1, Dallas=1,
		// Sydney=1}
		Map<String, Long> region = new HashMap<>(6);
		region.put("Tokyo", (long) 1);
		region.put("Dublin", (long) 1);
		region.put("Dallas", (long) 1);
		region.put("Sydney", (long) 1);

		for (Entry<String, Long> entry : reportPreprocessing.getInfrastructuresData().getRegion().entrySet()) {
			Assert.assertEquals(region.get(entry.getKey()), entry.getValue());
		}

		// provider={Softlayer=1, AWS=4}
		Map<String, Long> provider = new HashMap<>(2);
		provider.put("Softlayer", (long) 1);
		provider.put("AWS", (long) 3);

		for (Entry<String, Long> entry : reportPreprocessing.getInfrastructuresData().getProvider().entrySet()) {
			Assert.assertEquals(provider.get(entry.getKey()), entry.getValue());
		}
	}

}
