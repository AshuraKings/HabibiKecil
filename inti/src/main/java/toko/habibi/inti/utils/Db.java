package toko.habibi.inti.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
	private String host;
	private int port;
	private String name, user, pass;
	private java.sql.Connection c;
	private java.sql.Statement s;

	Db(String host, int port, String name, String user, String pass) throws SQLException {
		super();
		this.host = host;
		this.port = port;
		this.name = name;
		this.user = user;
		this.pass = pass;
		try {
			org.postgresql.Driver.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			Work.hindar(e);
		} start();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) throws SQLException {
		close();
		this.host = host;
		start();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) throws SQLException {
		close();
		this.port = port;
		start();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws SQLException {
		close();
		this.name = name;
		start();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) throws SQLException {
		close();
		this.user = user;
		start();
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) throws SQLException {
		close();
		this.pass = pass;
		start();
	}

	private void start() throws SQLException {
		c = DriverManager.getConnection("jdbc:postgresql://" + host + ':' + port + '/' + name, user, pass);
		s = c.createStatement();
	}

	public void aksi(String sql) throws SQLException {
		s.execute(sql);
	}

	public void close() throws SQLException {
		s.close();
		c.close();
	}

	public java.sql.ResultSet hasil(String sql) throws SQLException {
		return s.executeQuery(sql);
	}

	public java.sql.PreparedStatement prep(String sql) throws SQLException {
		return c.prepareStatement(sql);
	}
}
