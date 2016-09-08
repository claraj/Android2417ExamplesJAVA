package com.clara.listview_todo_list;


import java.util.Date;

public class ToDoItem {
	String text;
	Date created;

	public ToDoItem(String text) {
		this.text = text;
		this.created = new Date();   //Default is current date and time
	}

	public Date getCreated(){ return created; }
	public String getText() {return text; }
}


