/*===============================================================================================================================
        CLASS Name:    ERP_utilDecodeBase
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to call decode GI_Interface file                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package Common_Utility;
//package org.apache.commons.codec.binary;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.util.Base64;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ERP_utilDecodeBase {
   
    public  String utilDecodeBase(String GIfile_fp) throws Exception {
        
        // Enter the filename as input
    	  
    //   String Filname =   "C:\\Users\\koushik\\Documents\\Oracle Fusion\\GlInterface.zip";
       //String Filname = "235859:Welcome1";
        
        File br = new File(GIfile_fp);
        // Convert the file into Byte 
        byte[] bytes = loadFile(br);

        // Call the api for Base64 encoding
        String encoded = Base64.encode(bytes);
        String encStr = new String(encoded);
        // Print the file
        System.out.println(encStr);
		return encStr;
        
    }

    private static byte[] getByteArray(String fileName) {
        File file = new File(fileName);
        FileInputStream is = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        try {
            is = new FileInputStream(file);
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            System.out.println("In getByteArray:IO Exception");
            e.printStackTrace();
        }
        return buffer.toByteArray();
    }


    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length &&
               (numRead = is.read(bytes, offset, bytes.length - offset)) >=
               0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " +
                                  file.getName());
        }

        is.close();
        return bytes;
    }

}
