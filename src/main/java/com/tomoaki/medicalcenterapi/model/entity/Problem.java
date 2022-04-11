package com.tomoaki.medicalcenterapi.model.entity;

import com.tomoaki.medicalcenterapi.model.enums.Difficulty;
import com.tomoaki.medicalcenterapi.model.enums.ProblemType;

public class Problem {
	private int problemId;
	private String problemTitle;
	private Difficulty difficulty;
	private ProblemType problemType;
	
	public Problem(int problemId, String problemTitle, Difficulty difficulty, ProblemType problemType) {
		this.problemId = problemId;
		this.problemTitle = problemTitle;
		this.difficulty = difficulty;
		this.problemType = problemType;
	}
	
	public int getProblemId() {
		return problemId;
	}
	
	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}
	
	public String getProblemTitle() {
		return problemTitle;
	}
	
	public void setProblemTitle(String problemTitle) {
		this.problemTitle = problemTitle;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public ProblemType getProblemType() {
		return problemType;
	}
	
	public void setProblemType(ProblemType problemType) {
		this.problemType = problemType;
	}
}
