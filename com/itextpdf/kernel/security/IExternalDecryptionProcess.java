package com.itextpdf.kernel.security;

import java.io.Serializable;
import org.bouncycastle.cms.Recipient;
import org.bouncycastle.cms.RecipientId;

public interface IExternalDecryptionProcess extends Serializable {
  RecipientId getCmsRecipientId();
  
  Recipient getCmsRecipient();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/security/IExternalDecryptionProcess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */