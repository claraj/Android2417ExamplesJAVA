package com.clara.listview_todo_list;

import java.util.Date;

/** Represents the data about one item in a To Do list */

public class ToDoItem {

	private String text;
	private Date created;

	public ToDoItem(String text) {
		this.text = text;
		this.created = new Date();   //Defaults to right now
	}

	public Date getCreated(){
		return created;
	}

	public String getText() {
		return text;
	}
}



