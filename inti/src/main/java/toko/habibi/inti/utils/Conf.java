package toko.habibi.inti.utils;

import java.sql.SQLException;

public class Conf {
	private String host;
	private Integer port;
	private String name, user, pass;

	public Conf() {
		super();
		host = null;
		port = null;
		name = null;
		user = null;
		pass = null;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Conf(String host, Integer port, String name, String user, String pass) {
		super();
		this.host = host;
		this.port = port;
		this.name = name;
		this.user = user;
		this.pass = pass;
	}

	public Db dbne() throws SQLException {
		return new Db(host, port, name, user, pass);
	}
}
