package com.tomoaki.medicalcenterapi.model.yaml;

import com.tomoaki.medicalcenterapi.model.enums.SQLCommand;
import java.util.List;
import java.util.Map;

public class SqlCommandRestrictionModel {
	private Map<SQLCommand, List<String>> forbiddenTablesBySqlCommand;
	private Map<String, List<String>> accessForbiddenFieldByTable;
	
	public SqlCommandRestrictionModel() {
	
	}
	
	public SqlCommandRestrictionModel(
		Map<SQLCommand, List<String>> forbiddenTablesBySqlCommand,
		Map<String, List<String>> accessForbiddenFieldByTable
	) {
		this.forbiddenTablesBySqlCommand = forbiddenTablesBySqlCommand;
		this.accessForbiddenFieldByTable = accessForbiddenFieldByTable;
	}
	
	public Map<SQLCommand, List<String>> getForbiddenTablesBySqlCommand() {
		return forbiddenTablesBySqlCommand;
	}
	
	public void setForbiddenTablesBySqlCommand(
		Map<SQLCommand, List<String>> forbiddenTablesBySqlCommand) {
		this.forbiddenTablesBySqlCommand = forbiddenTablesBySqlCommand;
	}
	
	public Map<String, List<String>> getAccessForbiddenFieldByTable() {
		return accessForbiddenFieldByTable;
	}
	
	public void setAccessForbiddenFieldByTable(
		Map<String, List<String>> accessForbiddenFieldByTable) {
		this.accessForbiddenFieldByTable = accessForbiddenFieldByTable;
	}
}
