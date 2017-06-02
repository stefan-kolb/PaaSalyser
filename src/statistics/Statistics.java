package statistics;

import java.util.LinkedList;
import java.util.List;

import models.PaasProfile;

public class Statistics {

	public Statistics() {

	}

	public static List<EvaluationResult> evalStatus(List<PaasProfile> profiles) {
		List<EvaluationResult> resultList = new LinkedList<EvaluationResult>();
		resultList.add(new EvaluationResult("production", 0));
		resultList.add(new EvaluationResult("alpha", 1));
		resultList.add(new EvaluationResult("beta", 2));
		profiles.forEach(element -> {
			if (element.getStatus().equalsIgnoreCase("production")) {
				resultList.set(0, new EvaluationResult("producation", resultList.get(0).getNumber() + 1));
			} else if (element.getStatus().equalsIgnoreCase("alpha")) {
				resultList.set(1, new EvaluationResult("alpha", resultList.get(1).getNumber() + 1));
			} else if (element.getStatus().equalsIgnoreCase("beta")) {
				resultList.set(2, new EvaluationResult("beta", resultList.get(2).getNumber() + 1));
			}
		});
		resultList.sort((r1, r2) -> ((Integer) r2.getNumber()).compareTo((Integer) r1.getNumber()));
		return resultList;
	}

	public static List<EvaluationResult> evalType(List<PaasProfile> profiles) {
		List<EvaluationResult> resultList = new LinkedList<EvaluationResult>();
		resultList.add(new EvaluationResult("SaaS-centric", 0));
		resultList.add(new EvaluationResult("Generic", 1));
		resultList.add(new EvaluationResult("IaaS-centric", 2));
		profiles.forEach(element -> {
			if (element.getType().equalsIgnoreCase("SaaS-centric")) {
				resultList.set(0, new EvaluationResult("SaaS-centric", resultList.get(0).getNumber() + 1));
			} else if (element.getType().equalsIgnoreCase("Generic")) {
				resultList.set(1, new EvaluationResult("Generic", resultList.get(1).getNumber() + 1));
			} else if (element.getType().equalsIgnoreCase("IaaS-centric")) {
				resultList.set(2, new EvaluationResult("IaaS-centric", resultList.get(2).getNumber() + 1));
			}
		});
		resultList.sort((r1, r2) -> ((Integer) r2.getNumber()).compareTo((Integer) r1.getNumber()));
		return resultList;
	}
	
	public static List<EvaluationResult> evalHosting(List<PaasProfile> profiles) {
		List<EvaluationResult> resultList = new LinkedList<EvaluationResult>();
		resultList.add(new EvaluationResult("public", 0));
		resultList.add(new EvaluationResult("private", 1));
		resultList.add(new EvaluationResult("virtual_private", 2));
		profiles.forEach(element -> {
			if (element.getHosting().equalsIgnoreCase("public")) {
				resultList.set(0, new EvaluationResult("SaaS-centric", resultList.get(0).getNumber() + 1));
			} else if (element.getType().equalsIgnoreCase("Generic")) {
				resultList.set(1, new EvaluationResult("Generic", resultList.get(1).getNumber() + 1));
			} else if (element.getType().equalsIgnoreCase("IaaS-centric")) {
				resultList.set(2, new EvaluationResult("IaaS-centric", resultList.get(2).getNumber() + 1));
			}
		});
		resultList.sort((r1, r2) -> ((Integer) r2.getNumber()).compareTo((Integer) r1.getNumber()));
		return resultList;
	}

}
