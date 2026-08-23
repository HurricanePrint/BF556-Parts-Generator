/*      */ package com.itextpdf.signatures;
/*      */ 
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.NoSuchProviderException;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PublicKey;
/*      */ import java.security.Signature;
/*      */ import java.security.SignatureException;
/*      */ import java.security.cert.CRL;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509CRL;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import org.bouncycastle.asn1.ASN1Encodable;
/*      */ import org.bouncycastle.asn1.ASN1EncodableVector;
/*      */ import org.bouncycastle.asn1.ASN1Enumerated;
/*      */ import org.bouncycastle.asn1.ASN1InputStream;
/*      */ import org.bouncycastle.asn1.ASN1Integer;
/*      */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*      */ import org.bouncycastle.asn1.ASN1OctetString;
/*      */ import org.bouncycastle.asn1.ASN1OutputStream;
/*      */ import org.bouncycastle.asn1.ASN1Primitive;
/*      */ import org.bouncycastle.asn1.ASN1Sequence;
/*      */ import org.bouncycastle.asn1.ASN1Set;
/*      */ import org.bouncycastle.asn1.ASN1TaggedObject;
/*      */ import org.bouncycastle.asn1.DERNull;
/*      */ import org.bouncycastle.asn1.DEROctetString;
/*      */ import org.bouncycastle.asn1.DERSequence;
/*      */ import org.bouncycastle.asn1.DERSet;
/*      */ import org.bouncycastle.asn1.DERTaggedObject;
/*      */ import org.bouncycastle.asn1.cms.Attribute;
/*      */ import org.bouncycastle.asn1.cms.AttributeTable;
/*      */ import org.bouncycastle.asn1.cms.ContentInfo;
/*      */ import org.bouncycastle.asn1.esf.SignaturePolicyIdentifier;
/*      */ import org.bouncycastle.asn1.ess.ESSCertID;
/*      */ import org.bouncycastle.asn1.ess.ESSCertIDv2;
/*      */ import org.bouncycastle.asn1.ess.SigningCertificate;
/*      */ import org.bouncycastle.asn1.ess.SigningCertificateV2;
/*      */ import org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
/*      */ import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
/*      */ import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
/*      */ import org.bouncycastle.asn1.tsp.MessageImprint;
/*      */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*      */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*      */ import org.bouncycastle.cert.ocsp.CertificateID;
/*      */ import org.bouncycastle.cert.ocsp.SingleResp;
/*      */ import org.bouncycastle.jce.X509Principal;
/*      */ import org.bouncycastle.tsp.TimeStampToken;
/*      */ import org.bouncycastle.tsp.TimeStampTokenInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PdfPKCS7
/*      */ {
/*      */   private SignaturePolicyIdentifier signaturePolicyIdentifier;
/*      */   private String provider;
/*      */   private String signName;
/*      */   private String reason;
/*      */   private String location;
/*      */   private Calendar signDate;
/*      */   private int version;
/*      */   private int signerversion;
/*      */   private String digestAlgorithmOid;
/*      */   private MessageDigest messageDigest;
/*      */   private Set<String> digestalgos;
/*      */   private byte[] digestAttr;
/*      */   private PdfName filterSubtype;
/*      */   private String digestEncryptionAlgorithmOid;
/*      */   private IExternalDigest interfaceDigest;
/*      */   private byte[] externalDigest;
/*      */   private byte[] externalRsaData;
/*      */   private Signature sig;
/*      */   private byte[] digest;
/*      */   private byte[] rsaData;
/*      */   private byte[] sigAttr;
/*      */   private byte[] sigAttrDer;
/*      */   private MessageDigest encContDigest;
/*      */   private boolean verified;
/*      */   private boolean verifyResult;
/*      */   private Collection<Certificate> certs;
/*      */   private Collection<Certificate> signCerts;
/*      */   private X509Certificate signCert;
/*      */   private Collection<CRL> crls;
/*      */   private BasicOCSPResp basicResp;
/*      */   private boolean isTsp;
/*      */   private boolean isCades;
/*      */   private TimeStampToken timeStampToken;
/*      */   
/*      */   public PdfPKCS7(PrivateKey privKey, Certificate[] certChain, String hashAlgorithm, String provider, IExternalDigest interfaceDigest, boolean hasRSAdata) throws InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
/*  562 */     this.version = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  567 */     this.signerversion = 1; this.provider = provider; this.interfaceDigest = interfaceDigest; this.digestAlgorithmOid = DigestAlgorithms.getAllowedDigest(hashAlgorithm); if (this.digestAlgorithmOid == null) throw (new PdfException("Unknown hash algorithm: {0}.")).setMessageParams(new Object[] { hashAlgorithm });  this.signCert = (X509Certificate)certChain[0]; this.certs = new ArrayList<>(); for (Certificate element : certChain) this.certs.add(element);  this.digestalgos = new HashSet<>(); this.digestalgos.add(this.digestAlgorithmOid); if (privKey != null) { this.digestEncryptionAlgorithmOid = SignUtils.getPrivateKeyAlgorithm(privKey); if (this.digestEncryptionAlgorithmOid.equals("RSA")) { this.digestEncryptionAlgorithmOid = "1.2.840.113549.1.1.1"; } else if (this.digestEncryptionAlgorithmOid.equals("DSA")) { this.digestEncryptionAlgorithmOid = "1.2.840.10040.4.1"; } else { throw (new PdfException("Unknown key algorithm: {0}.")).setMessageParams(new Object[] { this.digestEncryptionAlgorithmOid }); }  }  if (hasRSAdata) { this.rsaData = new byte[0]; this.messageDigest = DigestAlgorithms.getMessageDigest(getHashAlgorithm(), provider); }  if (privKey != null) this.sig = initSignature(privKey);  } public PdfPKCS7(byte[] contentsKey, byte[] certsKey, String provider) { this.version = 1; this.signerversion = 1; try { this.provider = provider; this.certs = SignUtils.readAllCerts(certsKey); this.signCerts = this.certs; this.signCert = (X509Certificate)SignUtils.<Certificate>getFirstElement(this.certs); this.crls = new ArrayList<>(); ASN1InputStream in = new ASN1InputStream(new ByteArrayInputStream(contentsKey)); this.digest = ((ASN1OctetString)in.readObject()).getOctets(); this.sig = SignUtils.getSignatureHelper("SHA1withRSA", provider); this.sig.initVerify(this.signCert.getPublicKey()); this.digestAlgorithmOid = "1.2.840.10040.4.3"; this.digestEncryptionAlgorithmOid = "1.3.36.3.3.1.2"; } catch (Exception e) { throw new PdfException(e); }  } public PdfPKCS7(byte[] contentsKey, PdfName filterSubtype, String provider) { this.version = 1; this.signerversion = 1; this.filterSubtype = filterSubtype; this.isTsp = PdfName.ETSI_RFC3161.equals(filterSubtype); this.isCades = PdfName.ETSI_CAdES_DETACHED.equals(filterSubtype); try { ASN1Primitive pkcs; this.provider = provider; ASN1InputStream din = new ASN1InputStream(new ByteArrayInputStream(contentsKey)); try { pkcs = din.readObject(); } catch (IOException iOException) { throw new IllegalArgumentException("Cannot decode PKCS#7 SignedData object."); }  if (!(pkcs instanceof ASN1Sequence)) throw new IllegalArgumentException("Not a valid PKCS#7 object - not a sequence");  ASN1Sequence signedData = (ASN1Sequence)pkcs; ASN1ObjectIdentifier objId = (ASN1ObjectIdentifier)signedData.getObjectAt(0); if (!objId.getId().equals("1.2.840.113549.1.7.2")) throw new IllegalArgumentException("Not a valid PKCS#7 object - not signed data.");  ASN1Sequence content = (ASN1Sequence)((ASN1TaggedObject)signedData.getObjectAt(1)).getObject(); this.version = ((ASN1Integer)content.getObjectAt(0)).getValue().intValue(); this.digestalgos = new HashSet<>(); Enumeration<ASN1Sequence> e = ((ASN1Set)content.getObjectAt(1)).getObjects(); while (e.hasMoreElements()) { ASN1Sequence s = e.nextElement(); ASN1ObjectIdentifier o = (ASN1ObjectIdentifier)s.getObjectAt(0); this.digestalgos.add(o.getId()); }  ASN1Sequence rsaData = (ASN1Sequence)content.getObjectAt(2); if (rsaData.size() > 1) { ASN1OctetString rsaDataContent = (ASN1OctetString)((ASN1TaggedObject)rsaData.getObjectAt(1)).getObject(); this.rsaData = rsaDataContent.getOctets(); }  int next = 3; while (content.getObjectAt(next) instanceof ASN1TaggedObject) next++;  this.certs = SignUtils.readAllCerts(contentsKey); ASN1Set signerInfos = (ASN1Set)content.getObjectAt(next); if (signerInfos.size() != 1)
/*      */         throw new IllegalArgumentException("This PKCS#7 object has multiple SignerInfos. Only one is supported at this time.");  ASN1Sequence signerInfo = (ASN1Sequence)signerInfos.getObjectAt(0); this.signerversion = ((ASN1Integer)signerInfo.getObjectAt(0)).getValue().intValue(); ASN1Sequence issuerAndSerialNumber = (ASN1Sequence)signerInfo.getObjectAt(1); X509Principal issuer = SignUtils.getIssuerX509Name(issuerAndSerialNumber); BigInteger serialNumber = ((ASN1Integer)issuerAndSerialNumber.getObjectAt(1)).getValue(); for (Certificate element : this.certs) { X509Certificate cert = (X509Certificate)element; if (cert.getIssuerDN().equals(issuer) && serialNumber.equals(cert.getSerialNumber())) { this.signCert = cert; break; }  }  if (this.signCert == null)
/*      */         throw (new PdfException("Cannot find signing certificate with serial {0}.")).setMessageParams(new Object[] { issuer.getName() + " / " + serialNumber.toString(16) });  signCertificateChain(); this.digestAlgorithmOid = ((ASN1ObjectIdentifier)((ASN1Sequence)signerInfo.getObjectAt(2)).getObjectAt(0)).getId(); next = 3; boolean foundCades = false; if (signerInfo.getObjectAt(next) instanceof ASN1TaggedObject) { ASN1TaggedObject tagsig = (ASN1TaggedObject)signerInfo.getObjectAt(next); ASN1Set sseq = ASN1Set.getInstance(tagsig, false); this.sigAttr = sseq.getEncoded(); this.sigAttrDer = sseq.getEncoded("DER"); for (int k = 0; k < sseq.size(); k++) { ASN1Sequence seq2 = (ASN1Sequence)sseq.getObjectAt(k); String idSeq2 = ((ASN1ObjectIdentifier)seq2.getObjectAt(0)).getId(); if (idSeq2.equals("1.2.840.113549.1.9.4")) { ASN1Set set = (ASN1Set)seq2.getObjectAt(1); this.digestAttr = ((ASN1OctetString)set.getObjectAt(0)).getOctets(); } else if (idSeq2.equals("1.2.840.113583.1.1.8")) { ASN1Set setout = (ASN1Set)seq2.getObjectAt(1); ASN1Sequence seqout = (ASN1Sequence)setout.getObjectAt(0); for (int j = 0; j < seqout.size(); j++) { ASN1TaggedObject tg = (ASN1TaggedObject)seqout.getObjectAt(j); if (tg.getTagNo() == 0) { ASN1Sequence seqin = (ASN1Sequence)tg.getObject(); findCRL(seqin); }  if (tg.getTagNo() == 1) { ASN1Sequence seqin = (ASN1Sequence)tg.getObject(); findOcsp(seqin); }  }  } else if (this.isCades && idSeq2.equals("1.2.840.113549.1.9.16.2.12")) { ASN1Set setout = (ASN1Set)seq2.getObjectAt(1); ASN1Sequence seqout = (ASN1Sequence)setout.getObjectAt(0); SigningCertificate sv2 = SigningCertificate.getInstance(seqout); ESSCertID[] cerv2m = sv2.getCerts(); ESSCertID cerv2 = cerv2m[0]; byte[] enc2 = this.signCert.getEncoded(); MessageDigest m2 = SignUtils.getMessageDigest("SHA-1"); byte[] signCertHash = m2.digest(enc2); byte[] hs2 = cerv2.getCertHash(); if (!Arrays.equals(signCertHash, hs2))
/*      */               throw new IllegalArgumentException("Signing certificate doesn't match the ESS information.");  foundCades = true; } else if (this.isCades && idSeq2.equals("1.2.840.113549.1.9.16.2.47")) { ASN1Set setout = (ASN1Set)seq2.getObjectAt(1); ASN1Sequence seqout = (ASN1Sequence)setout.getObjectAt(0); SigningCertificateV2 sv2 = SigningCertificateV2.getInstance(seqout); ESSCertIDv2[] cerv2m = sv2.getCerts(); ESSCertIDv2 cerv2 = cerv2m[0]; AlgorithmIdentifier ai2 = cerv2.getHashAlgorithm(); byte[] enc2 = this.signCert.getEncoded(); MessageDigest m2 = SignUtils.getMessageDigest(DigestAlgorithms.getDigest(ai2.getAlgorithm().getId())); byte[] signCertHash = m2.digest(enc2); byte[] hs2 = cerv2.getCertHash(); if (!Arrays.equals(signCertHash, hs2))
/*      */               throw new IllegalArgumentException("Signing certificate doesn't match the ESS information.");  foundCades = true; }  }  if (this.digestAttr == null)
/*      */           throw new IllegalArgumentException("Authenticated attribute is missing the digest.");  next++; }  if (this.isCades && !foundCades)
/*      */         throw new IllegalArgumentException("CAdES ESS information missing.");  this.digestEncryptionAlgorithmOid = ((ASN1ObjectIdentifier)((ASN1Sequence)signerInfo.getObjectAt(next++)).getObjectAt(0)).getId(); this.digest = ((ASN1OctetString)signerInfo.getObjectAt(next++)).getOctets(); if (next < signerInfo.size() && signerInfo.getObjectAt(next) instanceof ASN1TaggedObject) { ASN1TaggedObject taggedObject = (ASN1TaggedObject)signerInfo.getObjectAt(next); ASN1Set unat = ASN1Set.getInstance(taggedObject, false); AttributeTable attble = new AttributeTable(unat); Attribute ts = attble.get(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken); if (ts != null && ts.getAttrValues().size() > 0) { ASN1Set attributeValues = ts.getAttrValues(); ASN1Sequence tokenSequence = ASN1Sequence.getInstance(attributeValues.getObjectAt(0)); ContentInfo contentInfo = ContentInfo.getInstance(tokenSequence); this.timeStampToken = new TimeStampToken(contentInfo); }  }  if (this.isTsp) { ContentInfo contentInfoTsp = ContentInfo.getInstance(signedData); this.timeStampToken = new TimeStampToken(contentInfoTsp); TimeStampTokenInfo info = this.timeStampToken.getTimeStampInfo(); String algOID = info.getHashAlgorithm().getAlgorithm().getId(); this.messageDigest = DigestAlgorithms.getMessageDigestFromOid(algOID, null); } else { if (this.rsaData != null || this.digestAttr != null) { if (PdfName.Adbe_pkcs7_sha1.equals(getFilterSubtype())) { this.messageDigest = DigestAlgorithms.getMessageDigest("SHA1", provider); } else { this.messageDigest = DigestAlgorithms.getMessageDigest(getHashAlgorithm(), provider); }  this.encContDigest = DigestAlgorithms.getMessageDigest(getHashAlgorithm(), provider); }  this.sig = initSignature(this.signCert.getPublicKey()); }  } catch (Exception e) { throw new PdfException(e); }  }
/*      */   public void setSignaturePolicy(SignaturePolicyInfo signaturePolicy) { this.signaturePolicyIdentifier = signaturePolicy.toSignaturePolicyIdentifier(); }
/*  575 */   public void setSignaturePolicy(SignaturePolicyIdentifier signaturePolicy) { this.signaturePolicyIdentifier = signaturePolicy; } public int getVersion() { return this.version; }
/*      */   public String getSignName() { return this.signName; }
/*      */   public void setSignName(String signName) { this.signName = signName; }
/*      */   public String getReason() { return this.reason; }
/*      */   public void setReason(String reason) { this.reason = reason; }
/*      */   public String getLocation() { return this.location; }
/*      */   public void setLocation(String location) { this.location = location; }
/*      */   public Calendar getSignDate() { Calendar dt = getTimeStampDate(); if (dt == TimestampConstants.UNDEFINED_TIMESTAMP_DATE)
/*      */       return this.signDate;  return dt; }
/*  584 */   public void setSignDate(Calendar signDate) { this.signDate = signDate; } public int getSigningInfoVersion() { return this.signerversion; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDigestAlgorithmOid() {
/*  615 */     return this.digestAlgorithmOid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHashAlgorithm() {
/*  624 */     return DigestAlgorithms.getDigest(this.digestAlgorithmOid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDigestEncryptionAlgorithmOid() {
/*  638 */     return this.digestEncryptionAlgorithmOid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDigestAlgorithm() {
/*  647 */     return getHashAlgorithm() + "with" + getEncryptionAlgorithm();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExternalDigest(byte[] digest, byte[] rsaData, String digestEncryptionAlgorithm) {
/*  678 */     this.externalDigest = digest;
/*  679 */     this.externalRsaData = rsaData;
/*  680 */     if (digestEncryptionAlgorithm != null) {
/*  681 */       if (digestEncryptionAlgorithm.equals("RSA")) {
/*  682 */         this.digestEncryptionAlgorithmOid = "1.2.840.113549.1.1.1";
/*  683 */       } else if (digestEncryptionAlgorithm.equals("DSA")) {
/*  684 */         this.digestEncryptionAlgorithmOid = "1.2.840.10040.4.1";
/*  685 */       } else if (digestEncryptionAlgorithm.equals("ECDSA")) {
/*  686 */         this.digestEncryptionAlgorithmOid = "1.2.840.10045.2.1";
/*      */       } else {
/*  688 */         throw (new PdfException("Unknown key algorithm: {0}.")).setMessageParams(new Object[] { digestEncryptionAlgorithm });
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Signature initSignature(PrivateKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
/*  712 */     Signature signature = SignUtils.getSignatureHelper(getDigestAlgorithm(), this.provider);
/*  713 */     signature.initSign(key);
/*  714 */     return signature;
/*      */   }
/*      */   
/*      */   private Signature initSignature(PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
/*  718 */     String digestAlgorithm = getDigestAlgorithm();
/*  719 */     if (PdfName.Adbe_x509_rsa_sha1.equals(getFilterSubtype()))
/*  720 */       digestAlgorithm = "SHA1withRSA"; 
/*  721 */     Signature signature = SignUtils.getSignatureHelper(digestAlgorithm, this.provider);
/*  722 */     signature.initVerify(key);
/*  723 */     return signature;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(byte[] buf, int off, int len) throws SignatureException {
/*  736 */     if (this.rsaData != null || this.digestAttr != null || this.isTsp) {
/*  737 */       this.messageDigest.update(buf, off, len);
/*      */     } else {
/*  739 */       this.sig.update(buf, off, len);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncodedPKCS1() {
/*      */     try {
/*  751 */       if (this.externalDigest != null) {
/*  752 */         this.digest = this.externalDigest;
/*      */       } else {
/*  754 */         this.digest = this.sig.sign();
/*  755 */       }  ByteArrayOutputStream bOut = new ByteArrayOutputStream();
/*      */       
/*  757 */       ASN1OutputStream dout = new ASN1OutputStream(bOut);
/*  758 */       dout.writeObject((ASN1Primitive)new DEROctetString(this.digest));
/*  759 */       dout.close();
/*      */       
/*  761 */       return bOut.toByteArray();
/*  762 */     } catch (Exception e) {
/*  763 */       throw new PdfException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncodedPKCS7() {
/*  775 */     return getEncodedPKCS7((byte[])null, (ITSAClient)null, (byte[])null, (Collection<byte[]>)null, PdfSigner.CryptoStandard.CMS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncodedPKCS7(byte[] secondDigest) {
/*  786 */     return getEncodedPKCS7(secondDigest, (ITSAClient)null, (byte[])null, (Collection<byte[]>)null, PdfSigner.CryptoStandard.CMS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public byte[] getEncodedPKCS7(byte[] secondDigest, ITSAClient tsaClient, byte[] ocsp, Collection<byte[]> crlBytes, PdfSigner.CryptoStandard sigtype) {
/*  804 */     return getEncodedPKCS7(secondDigest, sigtype, tsaClient, (ocsp != null) ? (Collection)Collections.<byte[]>singleton(ocsp) : null, crlBytes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncodedPKCS7(byte[] secondDigest, PdfSigner.CryptoStandard sigtype, ITSAClient tsaClient, Collection<byte[]> ocsp, Collection<byte[]> crlBytes) {
/*      */     try {
/*  821 */       if (this.externalDigest != null) {
/*  822 */         this.digest = this.externalDigest;
/*  823 */         if (this.rsaData != null)
/*  824 */           this.rsaData = this.externalRsaData; 
/*  825 */       } else if (this.externalRsaData != null && this.rsaData != null) {
/*  826 */         this.rsaData = this.externalRsaData;
/*  827 */         this.sig.update(this.rsaData);
/*  828 */         this.digest = this.sig.sign();
/*      */       } else {
/*  830 */         if (this.rsaData != null) {
/*  831 */           this.rsaData = this.messageDigest.digest();
/*  832 */           this.sig.update(this.rsaData);
/*      */         } 
/*  834 */         this.digest = this.sig.sign();
/*      */       } 
/*      */ 
/*      */       
/*  838 */       ASN1EncodableVector digestAlgorithms = new ASN1EncodableVector();
/*  839 */       for (String element : this.digestalgos) {
/*  840 */         ASN1EncodableVector algos = new ASN1EncodableVector();
/*  841 */         algos.add((ASN1Encodable)new ASN1ObjectIdentifier(element));
/*  842 */         algos.add((ASN1Encodable)DERNull.INSTANCE);
/*  843 */         digestAlgorithms.add((ASN1Encodable)new DERSequence(algos));
/*      */       } 
/*      */ 
/*      */       
/*  847 */       ASN1EncodableVector v = new ASN1EncodableVector();
/*  848 */       v.add((ASN1Encodable)new ASN1ObjectIdentifier("1.2.840.113549.1.7.1"));
/*  849 */       if (this.rsaData != null)
/*  850 */         v.add((ASN1Encodable)new DERTaggedObject(0, (ASN1Encodable)new DEROctetString(this.rsaData))); 
/*  851 */       DERSequence contentinfo = new DERSequence(v);
/*      */ 
/*      */ 
/*      */       
/*  855 */       v = new ASN1EncodableVector();
/*  856 */       for (Certificate element : this.certs) {
/*  857 */         ASN1InputStream tempstream = new ASN1InputStream(new ByteArrayInputStream(((X509Certificate)element).getEncoded()));
/*  858 */         v.add((ASN1Encodable)tempstream.readObject());
/*      */       } 
/*      */       
/*  861 */       DERSet dercertificates = new DERSet(v);
/*      */ 
/*      */ 
/*      */       
/*  865 */       ASN1EncodableVector signerinfo = new ASN1EncodableVector();
/*      */ 
/*      */ 
/*      */       
/*  869 */       signerinfo.add((ASN1Encodable)new ASN1Integer(this.signerversion));
/*      */       
/*  871 */       v = new ASN1EncodableVector();
/*  872 */       v.add((ASN1Encodable)CertificateInfo.getIssuer(this.signCert.getTBSCertificate()));
/*  873 */       v.add((ASN1Encodable)new ASN1Integer(this.signCert.getSerialNumber()));
/*  874 */       signerinfo.add((ASN1Encodable)new DERSequence(v));
/*      */ 
/*      */       
/*  877 */       v = new ASN1EncodableVector();
/*  878 */       v.add((ASN1Encodable)new ASN1ObjectIdentifier(this.digestAlgorithmOid));
/*  879 */       v.add((ASN1Encodable)DERNull.INSTANCE);
/*  880 */       signerinfo.add((ASN1Encodable)new DERSequence(v));
/*      */ 
/*      */       
/*  883 */       if (secondDigest != null) {
/*  884 */         signerinfo.add((ASN1Encodable)new DERTaggedObject(false, 0, (ASN1Encodable)getAuthenticatedAttributeSet(secondDigest, ocsp, crlBytes, sigtype)));
/*      */       }
/*      */       
/*  887 */       v = new ASN1EncodableVector();
/*  888 */       v.add((ASN1Encodable)new ASN1ObjectIdentifier(this.digestEncryptionAlgorithmOid));
/*  889 */       v.add((ASN1Encodable)DERNull.INSTANCE);
/*  890 */       signerinfo.add((ASN1Encodable)new DERSequence(v));
/*      */ 
/*      */       
/*  893 */       signerinfo.add((ASN1Encodable)new DEROctetString(this.digest));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  898 */       if (tsaClient != null) {
/*  899 */         byte[] tsImprint = tsaClient.getMessageDigest().digest(this.digest);
/*  900 */         byte[] tsToken = tsaClient.getTimeStampToken(tsImprint);
/*  901 */         if (tsToken != null) {
/*  902 */           ASN1EncodableVector unauthAttributes = buildUnauthenticatedAttributes(tsToken);
/*  903 */           if (unauthAttributes != null) {
/*  904 */             signerinfo.add((ASN1Encodable)new DERTaggedObject(false, 1, (ASN1Encodable)new DERSet(unauthAttributes)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  910 */       ASN1EncodableVector body = new ASN1EncodableVector();
/*  911 */       body.add((ASN1Encodable)new ASN1Integer(this.version));
/*  912 */       body.add((ASN1Encodable)new DERSet(digestAlgorithms));
/*  913 */       body.add((ASN1Encodable)contentinfo);
/*  914 */       body.add((ASN1Encodable)new DERTaggedObject(false, 0, (ASN1Encodable)dercertificates));
/*      */ 
/*      */       
/*  917 */       body.add((ASN1Encodable)new DERSet((ASN1Encodable)new DERSequence(signerinfo)));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  922 */       ASN1EncodableVector whole = new ASN1EncodableVector();
/*  923 */       whole.add((ASN1Encodable)new ASN1ObjectIdentifier("1.2.840.113549.1.7.2"));
/*  924 */       whole.add((ASN1Encodable)new DERTaggedObject(0, (ASN1Encodable)new DERSequence(body)));
/*      */       
/*  926 */       ByteArrayOutputStream bOut = new ByteArrayOutputStream();
/*      */       
/*  928 */       ASN1OutputStream dout = new ASN1OutputStream(bOut);
/*  929 */       dout.writeObject((ASN1Primitive)new DERSequence(whole));
/*  930 */       dout.close();
/*      */       
/*  932 */       return bOut.toByteArray();
/*  933 */     } catch (Exception e) {
/*  934 */       throw new PdfException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ASN1EncodableVector buildUnauthenticatedAttributes(byte[] timeStampToken) throws IOException {
/*  949 */     if (timeStampToken == null) {
/*  950 */       return null;
/*      */     }
/*      */     
/*  953 */     String ID_TIME_STAMP_TOKEN = "1.2.840.113549.1.9.16.2.14";
/*      */     
/*  955 */     ASN1InputStream tempstream = new ASN1InputStream(new ByteArrayInputStream(timeStampToken));
/*  956 */     ASN1EncodableVector unauthAttributes = new ASN1EncodableVector();
/*      */     
/*  958 */     ASN1EncodableVector v = new ASN1EncodableVector();
/*  959 */     v.add((ASN1Encodable)new ASN1ObjectIdentifier(ID_TIME_STAMP_TOKEN));
/*  960 */     ASN1Sequence seq = (ASN1Sequence)tempstream.readObject();
/*  961 */     v.add((ASN1Encodable)new DERSet((ASN1Encodable)seq));
/*      */     
/*  963 */     unauthAttributes.add((ASN1Encodable)new DERSequence(v));
/*  964 */     return unauthAttributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public byte[] getAuthenticatedAttributeBytes(byte[] secondDigest, byte[] ocsp, Collection<byte[]> crlBytes, PdfSigner.CryptoStandard sigtype) {
/* 1002 */     return getAuthenticatedAttributeBytes(secondDigest, sigtype, (ocsp != null) ? (Collection)Collections.<byte[]>singleton(ocsp) : null, crlBytes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getAuthenticatedAttributeBytes(byte[] secondDigest, PdfSigner.CryptoStandard sigtype, Collection<byte[]> ocsp, Collection<byte[]> crlBytes) {
/*      */     try {
/* 1037 */       return getAuthenticatedAttributeSet(secondDigest, ocsp, crlBytes, sigtype).getEncoded("DER");
/* 1038 */     } catch (Exception e) {
/* 1039 */       throw new PdfException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DERSet getAuthenticatedAttributeSet(byte[] secondDigest, Collection<byte[]> ocsp, Collection<byte[]> crlBytes, PdfSigner.CryptoStandard sigtype) {
/*      */     try {
/* 1052 */       ASN1EncodableVector attribute = new ASN1EncodableVector();
/* 1053 */       ASN1EncodableVector v = new ASN1EncodableVector();
/* 1054 */       v.add((ASN1Encodable)new ASN1ObjectIdentifier("1.2.840.113549.1.9.3"));
/* 1055 */       v.add((ASN1Encodable)new DERSet((ASN1Encodable)new ASN1ObjectIdentifier("1.2.840.113549.1.7.1")));
/* 1056 */       attribute.add((ASN1Encodable)new DERSequence(v));
/* 1057 */       v = new ASN1EncodableVector();
/* 1058 */       v.add((ASN1Encodable)new ASN1ObjectIdentifier("1.2.840.113549.1.9.4"));
/* 1059 */       v.add((ASN1Encodable)new DERSet((ASN1Encodable)new DEROctetString(secondDigest)));
/* 1060 */       attribute.add((ASN1Encodable)new DERSequence(v));
/* 1061 */       boolean haveCrl = false;
/* 1062 */       if (crlBytes != null) {
/* 1063 */         for (byte[] bCrl : crlBytes) {
/* 1064 */           if (bCrl != null) {
/* 1065 */             haveCrl = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 1070 */       if ((ocsp != null && !ocsp.isEmpty()) || haveCrl) {
/* 1071 */         v = new ASN1EncodableVector();
/* 1072 */         v.add((ASN1Encodable)new ASN1ObjectIdentifier("1.2.840.113583.1.1.8"));
/*      */         
/* 1074 */         ASN1EncodableVector revocationV = new ASN1EncodableVector();
/*      */         
/* 1076 */         if (haveCrl) {
/* 1077 */           ASN1EncodableVector v2 = new ASN1EncodableVector();
/* 1078 */           for (byte[] bCrl : crlBytes) {
/* 1079 */             if (bCrl == null)
/*      */               continue; 
/* 1081 */             ASN1InputStream t = new ASN1InputStream(new ByteArrayInputStream(bCrl));
/* 1082 */             v2.add((ASN1Encodable)t.readObject());
/*      */           } 
/* 1084 */           revocationV.add((ASN1Encodable)new DERTaggedObject(true, 0, (ASN1Encodable)new DERSequence(v2)));
/*      */         } 
/*      */         
/* 1087 */         if (ocsp != null && !ocsp.isEmpty()) {
/* 1088 */           ASN1EncodableVector vo1 = new ASN1EncodableVector();
/* 1089 */           for (byte[] ocspBytes : ocsp) {
/* 1090 */             DEROctetString doctet = new DEROctetString(ocspBytes);
/* 1091 */             ASN1EncodableVector v2 = new ASN1EncodableVector();
/* 1092 */             v2.add((ASN1Encodable)OCSPObjectIdentifiers.id_pkix_ocsp_basic);
/* 1093 */             v2.add((ASN1Encodable)doctet);
/* 1094 */             ASN1Enumerated den = new ASN1Enumerated(0);
/* 1095 */             ASN1EncodableVector v3 = new ASN1EncodableVector();
/* 1096 */             v3.add((ASN1Encodable)den);
/* 1097 */             v3.add((ASN1Encodable)new DERTaggedObject(true, 0, (ASN1Encodable)new DERSequence(v2)));
/* 1098 */             vo1.add((ASN1Encodable)new DERSequence(v3));
/*      */           } 
/* 1100 */           revocationV.add((ASN1Encodable)new DERTaggedObject(true, 1, (ASN1Encodable)new DERSequence(vo1)));
/*      */         } 
/*      */         
/* 1103 */         v.add((ASN1Encodable)new DERSet((ASN1Encodable)new DERSequence(revocationV)));
/* 1104 */         attribute.add((ASN1Encodable)new DERSequence(v));
/*      */       } 
/* 1106 */       if (sigtype == PdfSigner.CryptoStandard.CADES) {
/* 1107 */         v = new ASN1EncodableVector();
/* 1108 */         v.add((ASN1Encodable)new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.47"));
/*      */         
/* 1110 */         ASN1EncodableVector aaV2 = new ASN1EncodableVector();
/* 1111 */         AlgorithmIdentifier algoId = new AlgorithmIdentifier(new ASN1ObjectIdentifier(this.digestAlgorithmOid), null);
/* 1112 */         aaV2.add((ASN1Encodable)algoId);
/* 1113 */         MessageDigest md = SignUtils.getMessageDigest(getHashAlgorithm(), this.interfaceDigest);
/* 1114 */         byte[] dig = md.digest(this.signCert.getEncoded());
/* 1115 */         aaV2.add((ASN1Encodable)new DEROctetString(dig));
/*      */         
/* 1117 */         v.add((ASN1Encodable)new DERSet((ASN1Encodable)new DERSequence((ASN1Encodable)new DERSequence((ASN1Encodable)new DERSequence(aaV2)))));
/* 1118 */         attribute.add((ASN1Encodable)new DERSequence(v));
/*      */       } 
/*      */       
/* 1121 */       if (this.signaturePolicyIdentifier != null) {
/* 1122 */         attribute.add((ASN1Encodable)new Attribute(PKCSObjectIdentifiers.id_aa_ets_sigPolicyId, (ASN1Set)new DERSet((ASN1Encodable)this.signaturePolicyIdentifier)));
/*      */       }
/*      */       
/* 1125 */       return new DERSet(attribute);
/* 1126 */     } catch (Exception e) {
/* 1127 */       throw new PdfException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean verify() throws GeneralSecurityException {
/* 1174 */     return verifySignatureIntegrityAndAuthenticity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean verifySignatureIntegrityAndAuthenticity() throws GeneralSecurityException {
/* 1194 */     if (this.verified)
/* 1195 */       return this.verifyResult; 
/* 1196 */     if (this.isTsp) {
/* 1197 */       TimeStampTokenInfo info = this.timeStampToken.getTimeStampInfo();
/* 1198 */       MessageImprint imprint = info.toASN1Structure().getMessageImprint();
/* 1199 */       byte[] md = this.messageDigest.digest();
/* 1200 */       byte[] imphashed = imprint.getHashedMessage();
/* 1201 */       this.verifyResult = Arrays.equals(md, imphashed);
/*      */     }
/* 1203 */     else if (this.sigAttr != null || this.sigAttrDer != null) {
/* 1204 */       byte[] msgDigestBytes = this.messageDigest.digest();
/* 1205 */       boolean verifyRSAdata = true;
/*      */       
/* 1207 */       boolean encContDigestCompare = false;
/* 1208 */       if (this.rsaData != null) {
/* 1209 */         verifyRSAdata = Arrays.equals(msgDigestBytes, this.rsaData);
/* 1210 */         this.encContDigest.update(this.rsaData);
/* 1211 */         encContDigestCompare = Arrays.equals(this.encContDigest.digest(), this.digestAttr);
/*      */       } 
/* 1213 */       boolean absentEncContDigestCompare = Arrays.equals(msgDigestBytes, this.digestAttr);
/* 1214 */       boolean concludingDigestCompare = (absentEncContDigestCompare || encContDigestCompare);
/* 1215 */       boolean sigVerify = (verifySigAttributes(this.sigAttr) || verifySigAttributes(this.sigAttrDer));
/* 1216 */       this.verifyResult = (concludingDigestCompare && sigVerify && verifyRSAdata);
/*      */     } else {
/* 1218 */       if (this.rsaData != null)
/* 1219 */         this.sig.update(this.messageDigest.digest()); 
/* 1220 */       this.verifyResult = this.sig.verify(this.digest);
/*      */     } 
/*      */     
/* 1223 */     this.verified = true;
/* 1224 */     return this.verifyResult;
/*      */   }
/*      */   
/*      */   private boolean verifySigAttributes(byte[] attr) throws GeneralSecurityException {
/* 1228 */     Signature signature = initSignature(this.signCert.getPublicKey());
/* 1229 */     signature.update(attr);
/* 1230 */     return signature.verify(this.digest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean verifyTimestampImprint() throws GeneralSecurityException {
/* 1241 */     if (this.timeStampToken == null)
/* 1242 */       return false; 
/* 1243 */     TimeStampTokenInfo info = this.timeStampToken.getTimeStampInfo();
/* 1244 */     MessageImprint imprint = info.toASN1Structure().getMessageImprint();
/* 1245 */     String algOID = info.getHashAlgorithm().getAlgorithm().getId();
/* 1246 */     byte[] md = SignUtils.getMessageDigest(DigestAlgorithms.getDigest(algOID)).digest(this.digest);
/* 1247 */     byte[] imphashed = imprint.getHashedMessage();
/* 1248 */     return Arrays.equals(md, imphashed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Certificate[] getCertificates() {
/* 1275 */     return this.certs.<Certificate>toArray((Certificate[])new X509Certificate[this.certs.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Certificate[] getSignCertificateChain() {
/* 1286 */     return this.signCerts.<Certificate>toArray((Certificate[])new X509Certificate[this.signCerts.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getSigningCertificate() {
/* 1295 */     return this.signCert;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void signCertificateChain() {
/* 1304 */     List<Certificate> cc = new ArrayList<>();
/* 1305 */     cc.add(this.signCert);
/* 1306 */     List<Certificate> oc = new ArrayList<>(this.certs);
/* 1307 */     for (int k = 0; k < oc.size(); k++) {
/* 1308 */       if (this.signCert.equals(oc.get(k))) {
/* 1309 */         oc.remove(k);
/* 1310 */         k--;
/*      */       } 
/*      */     } 
/* 1313 */     boolean found = true;
/* 1314 */     while (found) {
/* 1315 */       X509Certificate v = (X509Certificate)cc.get(cc.size() - 1);
/* 1316 */       found = false;
/* 1317 */       for (int i = 0; i < oc.size(); i++) {
/* 1318 */         X509Certificate issuer = (X509Certificate)oc.get(i);
/* 1319 */         if (SignUtils.verifyCertificateSignature(v, issuer.getPublicKey(), this.provider)) {
/* 1320 */           found = true;
/* 1321 */           cc.add(oc.get(i));
/* 1322 */           oc.remove(i);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1327 */     this.signCerts = cc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<CRL> getCRLs() {
/* 1340 */     return this.crls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void findCRL(ASN1Sequence seq) {
/*      */     try {
/* 1348 */       this.crls = new ArrayList<>();
/* 1349 */       for (int k = 0; k < seq.size(); k++) {
/* 1350 */         ByteArrayInputStream ar = new ByteArrayInputStream(seq.getObjectAt(k).toASN1Primitive().getEncoded("DER"));
/* 1351 */         X509CRL crl = (X509CRL)SignUtils.parseCrlFromStream(ar);
/* 1352 */         this.crls.add(crl);
/*      */       } 
/* 1354 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicOCSPResp getOcsp() {
/* 1372 */     return this.basicResp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRevocationValid() {
/* 1381 */     if (this.basicResp == null)
/* 1382 */       return false; 
/* 1383 */     if (this.signCerts.size() < 2)
/* 1384 */       return false; 
/*      */     try {
/* 1386 */       X509Certificate[] cs = (X509Certificate[])getSignCertificateChain();
/* 1387 */       SingleResp sr = this.basicResp.getResponses()[0];
/* 1388 */       CertificateID cid = sr.getCertID();
/* 1389 */       X509Certificate sigcer = getSigningCertificate();
/* 1390 */       X509Certificate isscer = cs[1];
/* 1391 */       CertificateID tis = SignUtils.generateCertificateId(isscer, sigcer.getSerialNumber(), cid.getHashAlgOID());
/* 1392 */       return tis.equals(cid);
/* 1393 */     } catch (Exception exception) {
/*      */       
/* 1395 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void findOcsp(ASN1Sequence seq) throws IOException {
/* 1405 */     this.basicResp = (BasicOCSPResp)null;
/* 1406 */     boolean ret = false;
/*      */     
/* 1408 */     while (!(seq.getObjectAt(0) instanceof ASN1ObjectIdentifier) || 
/* 1409 */       !((ASN1ObjectIdentifier)seq.getObjectAt(0)).getId().equals(OCSPObjectIdentifiers.id_pkix_ocsp_basic.getId())) {
/*      */ 
/*      */       
/* 1412 */       ret = true;
/* 1413 */       for (int k = 0; k < seq.size(); k++) {
/* 1414 */         if (seq.getObjectAt(k) instanceof ASN1Sequence) {
/* 1415 */           seq = (ASN1Sequence)seq.getObjectAt(0);
/* 1416 */           ret = false;
/*      */           break;
/*      */         } 
/* 1419 */         if (seq.getObjectAt(k) instanceof ASN1TaggedObject) {
/* 1420 */           ASN1TaggedObject tag = (ASN1TaggedObject)seq.getObjectAt(k);
/* 1421 */           if (tag.getObject() instanceof ASN1Sequence) {
/* 1422 */             seq = (ASN1Sequence)tag.getObject();
/* 1423 */             ret = false;
/*      */             break;
/*      */           } 
/*      */           return;
/*      */         } 
/*      */       } 
/* 1429 */       if (ret)
/*      */         return; 
/*      */     } 
/* 1432 */     ASN1OctetString os = (ASN1OctetString)seq.getObjectAt(1);
/* 1433 */     ASN1InputStream inp = new ASN1InputStream(os.getOctets());
/* 1434 */     BasicOCSPResponse resp = BasicOCSPResponse.getInstance(inp.readObject());
/* 1435 */     this.basicResp = new BasicOCSPResp(resp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTsp() {
/* 1461 */     return this.isTsp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeStampToken getTimeStampToken() {
/* 1470 */     return this.timeStampToken;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getTimeStampDate() {
/* 1482 */     if (this.timeStampToken == null) {
/* 1483 */       return (Calendar)TimestampConstants.UNDEFINED_TIMESTAMP_DATE;
/*      */     }
/* 1485 */     return SignUtils.getTimeStampDate(this.timeStampToken);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfName getFilterSubtype() {
/* 1492 */     return this.filterSubtype;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncryptionAlgorithm() {
/* 1501 */     String encryptAlgo = EncryptionAlgorithms.getAlgorithm(this.digestEncryptionAlgorithmOid);
/* 1502 */     if (encryptAlgo == null)
/* 1503 */       encryptAlgo = this.digestEncryptionAlgorithmOid; 
/* 1504 */     return encryptAlgo;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/PdfPKCS7.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */