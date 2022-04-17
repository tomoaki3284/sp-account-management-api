package com.tomoaki.medicalcenterapi.model.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class SqlRestrictionModel {
	
	@JsonProperty("DELETE")
	private List<String> deleteForbiddenTable;
	
	@JsonProperty("INSERT")
	private List<String> insertForbiddenTable;
	
	@JsonProperty("UPDATE")
	private List<String> updateForbiddenTable;
	
	@JsonProperty("accessForbiddenFieldByTable")
	private Map<String, List<String>> accessForbiddenFieldByTable;
	
	public SqlRestrictionModel(List<String> deleteForbiddenTable,
		List<String> insertForbiddenTable, List<String> updateForbiddenTable,
		Map<String, List<String>> accessForbiddenFieldByTable) {
		this.deleteForbiddenTable = deleteForbiddenTable;
		this.insertForbiddenTable = insertForbiddenTable;
		this.updateForbiddenTable = updateForbiddenTable;
		this.accessForbiddenFieldByTable = accessForbiddenFieldByTable;
	}
	
	public SqlRestrictionModel() {
	
	}
	
	public List<String> getForbiddenTableBySqlCommand(String sqlCommand) {
		if (sqlCommand.equals("DELETE")) {
			return deleteForbiddenTable;
		} else if (sqlCommand.equals("UPDATE")) {
			return updateForbiddenTable;
		} else {
			return insertForbiddenTable;
		}
	}
	
	public List<String> getDeleteForbiddenTable() {
		return deleteForbiddenTable;
	}
	
	public void setDeleteForbiddenTable(List<String> deleteForbiddenTable) {
		this.deleteForbiddenTable = deleteForbiddenTable;
	}
	
	public List<String> getInsertForbiddenTable() {
		return insertForbiddenTable;
	}
	
	public void setInsertForbiddenTable(List<String> insertForbiddenTable) {
		this.insertForbiddenTable = insertForbiddenTable;
	}
	
	public List<String> getUpdateForbiddenTable() {
		return updateForbiddenTable;
	}
	
	public void setUpdateForbiddenTable(List<String> updateForbiddenTable) {
		this.updateForbiddenTable = updateForbiddenTable;
	}
	
	public Map<String, List<String>> getAccessForbiddenFieldByTable() {
		return accessForbiddenFieldByTable;
	}

	public void setAccessForbiddenFieldByTable(
		Map<String, List<String>> accessForbiddenFieldByTable) {
		this.accessForbiddenFieldByTable = accessForbiddenFieldByTable;
	}
}
