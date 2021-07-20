package project.loanprocessapplication.classes;

import java.util.HashMap;

public class CreditScoreCard {
	
	double totalScore;
	HashMap<String,Double> scoreCard;
	String strongestNegativeFeature;
	double lowestFeatureScore;
	
	public CreditScoreCard(){
		this.totalScore=0;
		this.scoreCard=new HashMap<String,Double>();
		this.strongestNegativeFeature=null;
		this.lowestFeatureScore=Double.MAX_VALUE;
	}
	
	public void addNewFeatureScore(String featureName, double featureScore) {
		this.scoreCard.put(featureName, featureScore);
		if(featureScore<this.lowestFeatureScore) {
			this.lowestFeatureScore=featureScore;
			this.strongestNegativeFeature=featureName;
		}
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public String getStrongestNegativeFeature() {
		return strongestNegativeFeature;
	}

	public void setStrongestNegativeFeature(String strongestNegativeFeature) {
		this.strongestNegativeFeature = strongestNegativeFeature;
	}

	public double getLowestFeatureScore() {
		return lowestFeatureScore;
	}

	public void setLowestFeatureScore(double lowestFeatureScore) {
		this.lowestFeatureScore = lowestFeatureScore;
	}
	
}
