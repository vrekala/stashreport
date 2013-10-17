package com.evault.form;

public class Containers {
	public String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String href;
	public String size;

	public Containers() {
	}

	public Containers(String name, String href, String size) {
		this.name = name;
		this.href = href;
		this.size = size;
	}
}
