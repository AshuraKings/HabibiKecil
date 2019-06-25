package toko.habibi.inti.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Work {
	public static java.io.File f = new java.io.File(System.getProperty("user.home") + "/.habibi.conf.xml");

	public static void hindar(Exception e) {
		Time t = Time.valueOf(LocalTime.now());
		Date d = Date.valueOf(LocalDate.now());
		java.io.File f = new java.io.File(System.getProperty("user.home") + "/.habibi/error/" + d + '/' + t + ".log");
		if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
		if (f.exists()) f.delete();
		try {
			java.io.PrintWriter o = new java.io.PrintWriter(f);
			e.printStackTrace(o);
			o.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static String bytesStr(byte[] e) {
		java.math.BigInteger i = new java.math.BigInteger(e);
		return i.toString(32);
	}

	public static byte[] StrBytes(String e) {
		java.math.BigInteger i = new java.math.BigInteger(e, 32);
		return i.toByteArray();
	}

	public static String md5(String s) throws GeneralSecurityException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(s.getBytes());
		return Work.bytesStr(b);
	}

	public static RSA loadRSA() throws GeneralSecurityException, IOException {
		return new RSA(new java.io.File(System.getProperty("user.home") + "/.habibi/kunci/khusus"), 
				new java.io.File(System.getProperty("user.home") + "/.habibi/kunci/umum"));
	}

	public static void saveConf(Conf c) throws ParserConfigurationException, GeneralSecurityException, IOException, DOMException, 
	ClassNotFoundException, TransformerException {
		Document d = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root = d.createElement("connection");
		addIsi(d, root, "host", c.getHost());
		addIsi(d, root, "port", "" + c.getPort());
		addIsi(d, root, "name", c.getName());
		addIsi(d, root, "user", c.getUser());
		addIsi(d, root, "password", c.getPass());
		d.appendChild(root);
		savingXml(d);
	}

	public static Conf loadConf() throws SAXException, IOException, ParserConfigurationException, GeneralSecurityException, ClassNotFoundException {
		Conf c = new Conf();
		Document d = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		RSA r = loadRSA();
		org.w3c.dom.NodeList nl = d.getElementsByTagName("isi");
		for (int x = 0; x < nl.getLength(); x++) if (nl.item(x).getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) nl.item(x);
			if (md5("host") == e.getAttribute("kode")) c.setHost(r.decrypt(e.getAttribute("nama")));
			else if (md5("port") == e.getAttribute("kode")) c.setPort(Integer.parseInt(r.decrypt(e.getAttribute("nama"))));
			else if (md5("name") == e.getAttribute("kode")) c.setName(r.decrypt(e.getAttribute("nama")));
			else if (md5("user") == e.getAttribute("kode")) c.setUser(r.decrypt(e.getAttribute("nama")));
			else if (md5("password") == e.getAttribute("kode")) c.setPass(r.decrypt(e.getAttribute("nama")));
		} return c;
	}

	private static void savingXml(Document d) throws TransformerException {
		if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
		if (f.exists()) f.delete();
		javax.xml.transform.stream.StreamResult sr = new javax.xml.transform.stream.StreamResult(f);
		javax.xml.transform.dom.DOMSource ds = new javax.xml.transform.dom.DOMSource(d);
		javax.xml.transform.Transformer t = TransformerFactory.newInstance().newTransformer();
		t.transform(ds, sr);
	}

	private static void addIsi(Document d, Element root, String n, String v) throws GeneralSecurityException, IOException, DOMException, 
	ClassNotFoundException {
		RSA r = loadRSA();
		Element e = d.createElement("isi");
		e.setAttribute("kode", md5(n));
		e.setAttribute("nama", r.encrypt(v));
		root.appendChild(e);
	}
}
