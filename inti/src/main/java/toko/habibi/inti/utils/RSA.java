package toko.habibi.inti.utils;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSA {
	private File pri, pub;

	RSA(File pri, File pub) throws GeneralSecurityException, IOException {
		super();
		this.pri = pri;
		this.pub = pub;
		if (!pri.exists() || !pub.exists())
			buatKunci();
	}

	public String encrypt(String s) throws GeneralSecurityException, IOException, ClassNotFoundException {
		Cipher c = Cipher.getInstance("RSA");
		PublicKey k = loadPub();
		c.init(Cipher.ENCRYPT_MODE, k);
		byte[] e = c.doFinal(s.getBytes());
		return Work.bytesStr(e);
	}

	public String decrypt(String e) throws GeneralSecurityException, IOException, ClassNotFoundException {
		byte[] b = Work.StrBytes(e);
		Cipher c = Cipher.getInstance("RSA");
		PrivateKey k = loadPri();
		c.init(Cipher.DECRYPT_MODE, k);
		return new String(c.doFinal(b));
	}

	private PrivateKey loadPri() throws IOException, ClassNotFoundException {
		java.io.FileInputStream f = new java.io.FileInputStream(pri);
		java.io.ObjectInputStream i = new java.io.ObjectInputStream(f);
		PrivateKey k = (PrivateKey) i.readObject();
		i.close();
		f.close();
		return k;
	}

	private PublicKey loadPub() throws IOException, ClassNotFoundException {
		java.io.FileInputStream f = new java.io.FileInputStream(pub);
		java.io.ObjectInputStream i = new java.io.ObjectInputStream(f);
		PublicKey k = (PublicKey) i.readObject();
		i.close();
		f.close();
		return k;
	}

	private void buatKunci() throws GeneralSecurityException, IOException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2707);
		java.security.KeyPair kp = kpg.generateKeyPair();
		savePri(kp.getPrivate());
		savePub(kp.getPublic());
	}

	private void savePub(PublicKey k) throws IOException {
		if (!pub.getParentFile().exists()) pub.getParentFile().mkdirs();
		if (pub.exists()) pub.delete();
		java.io.FileOutputStream f = new java.io.FileOutputStream(pub);
		java.io.ObjectOutputStream o = new java.io.ObjectOutputStream(f);
		o.writeObject(k);
		o.close();
		f.close();
	}

	private void savePri(PrivateKey k) throws IOException {
		if (!pri.getParentFile().exists()) pri.getParentFile().mkdirs();
		if (pri.exists()) pri.delete();
		java.io.FileOutputStream f = new java.io.FileOutputStream(pri);
		java.io.ObjectOutputStream o = new java.io.ObjectOutputStream(f);
		o.writeObject(k);
		o.close();
		f.close();
	}

}
