package com.tomoaki.medicalcenterapi.model.yaml;

import com.tomoaki.medicalcenterapi.model.enums.SQLCommand;
import java.util.List;
import java.util.Map;

public class SqlCommandRestrictionModel {
	private Map<SQLCommand, List<String>> forbiddenTablesBySqlCommand;
	private Map<String, List<String>> accessForbiddenFieldByTable;
	
	
}
