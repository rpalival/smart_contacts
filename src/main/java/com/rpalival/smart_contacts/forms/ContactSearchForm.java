package com.rpalival.smart_contacts.forms;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ContactSearchForm {
	private String field;
	private String value;
}
