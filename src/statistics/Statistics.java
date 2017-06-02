package statistics;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import models.PaasProfile;

public class Statistics {

	public Statistics() {

	}

	public static List<EvaluationResult> evalRevision(List<PaasProfile> profiles) {
		List<EvaluationResult> results = new LinkedList<EvaluationResult>();
		results.add(new EvaluationResult("latest", Long.MAX_VALUE));
		results.add(new EvaluationResult("oldest", Long.MIN_VALUE));
		results.add(new EvaluationResult("mean", 0));

		profiles.forEach(profile -> {
			long interval = 0;
			try {
				interval = ChronoUnit.DAYS.between(LocalDate.parse(profile.getRevision().substring(0, 10)),
						LocalDate.now());
				if (interval != 0 && interval < results.get(0).getNumber()) {
					results.set(0, new EvaluationResult("latest", interval));
				}
				if (interval != 0 && interval > results.get(1).getNumber()) {
					results.set(1, new EvaluationResult("oldest", interval));
				}
				results.set(2, new EvaluationResult("mean", results.get(2).getNumber() + interval));
			} catch (DateTimeParseException e) {
				System.out.println("x");
				System.out.println(e.getMessage());
			}
		});
		if (results.get(2).getNumber() != 0) {
			results.set(2, new EvaluationResult("mean", results.get(2).getNumber() / profiles.size()));
		}

		return results;
	}

	public static List<EvaluationResult> evalStatus(List<PaasProfile> profiles) {
		List<EvaluationResult> resultList = new LinkedList<EvaluationResult>();
		resultList.add(new EvaluationResult("production", 0));
		resultList.add(new EvaluationResult("alpha", 0));
		resultList.add(new EvaluationResult("beta", 0));
		profiles.forEach(profile -> {
			if (profile.getStatus().equalsIgnoreCase("production")) {
				resultList.set(0, new EvaluationResult("producation", resultList.get(0).getNumber() + 1));
			} else if (profile.getStatus().equalsIgnoreCase("alpha")) {
				resultList.set(1, new EvaluationResult("alpha", resultList.get(1).getNumber() + 1));
			} else if (profile.getStatus().equalsIgnoreCase("beta")) {
				resultList.set(2, new EvaluationResult("beta", resultList.get(2).getNumber() + 1));
			}
		});
		resultList.sort((r1, r2) -> ((Long) r2.getNumber()).compareTo((Long) r1.getNumber()));
		return resultList;
	}

	public static List<EvaluationResult> evalStatusSince(List<PaasProfile> profiles) {
		List<EvaluationResult> results = new LinkedList<EvaluationResult>();
		results.add(new EvaluationResult("latest", Long.MAX_VALUE));
		results.add(new EvaluationResult("oldest", Long.MIN_VALUE));
		results.add(new EvaluationResult("mean", 0));

		profiles.forEach(profile -> {
			long interval = 0;
			if (!profile.getStatusSince().equalsIgnoreCase("null")) {
				try {
					interval = ChronoUnit.DAYS.between(LocalDate.parse(profile.getStatusSince().substring(0, 10)),
							LocalDate.now());
					if (interval != 0 && interval < results.get(0).getNumber()) {
						results.set(0, new EvaluationResult("latest", interval));
					}
					if (interval != 0 && interval > results.get(1).getNumber()) {
						results.set(1, new EvaluationResult("oldest", interval));
					}
					results.set(2, new EvaluationResult("mean", results.get(2).getNumber() + interval));
				} catch (DateTimeParseException e) {
					System.out.println("x");
					System.out.println(e.getMessage());
				}
			}
		});
		results.set(2, new EvaluationResult("mean", results.get(2).getNumber() / profiles.size()));
		return results;
	}

	public static List<EvaluationResult> evalType(List<PaasProfile> profiles) {
		List<EvaluationResult> resultList = new LinkedList<EvaluationResult>();
		resultList.add(new EvaluationResult("SaaS-centric", 0));
		resultList.add(new EvaluationResult("Generic", 0));
		resultList.add(new EvaluationResult("IaaS-centric", 0));
		profiles.forEach(profile -> {
			if (profile.getType().equalsIgnoreCase("SaaS-centric")) {
				resultList.set(0, new EvaluationResult("SaaS-centric", resultList.get(0).getNumber() + 1));
			} else if (profile.getType().equalsIgnoreCase("Generic")) {
				resultList.set(1, new EvaluationResult("Generic", resultList.get(1).getNumber() + 1));
			} else if (profile.getType().equalsIgnoreCase("IaaS-centric")) {
				resultList.set(2, new EvaluationResult("IaaS-centric", resultList.get(2).getNumber() + 1));
			}
		});
		resultList.sort((r1, r2) -> ((Long) r2.getNumber()).compareTo((Long) r1.getNumber()));
		return resultList;
	}

	public static List<EvaluationResult> evalHosting(List<PaasProfile> profiles) {
		List<EvaluationResult> resultList = new LinkedList<EvaluationResult>();
		resultList.add(new EvaluationResult("public", 0));
		resultList.add(new EvaluationResult("private", 0));
		resultList.add(new EvaluationResult("virtual_private", 0));
		profiles.forEach(profile -> {
			if (profile.getHosting().getPublic()) {
				resultList.set(0, new EvaluationResult("public", resultList.get(0).getNumber() + 1));
			} else if (profile.getHosting().getPrivate()) {
				resultList.set(1, new EvaluationResult("private", resultList.get(1).getNumber() + 1));
			} else if (profile.getHosting().getVirtualPrivate()) {
				resultList.set(2, new EvaluationResult("virtual_private", resultList.get(2).getNumber() + 1));
			}
		});
		resultList.sort((r1, r2) -> ((Long) r2.getNumber()).compareTo((Long) r1.getNumber()));
		return resultList;
	}

}
