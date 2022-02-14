package com.meyling.zeubor.core.io;

import com.meyling.zeubor.core.log.Log;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Input or output utilities.
 */
public class IoUtility  {
    
    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
        {
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1;) {
                os.write(buffer, 0, len);
            }    
            os.flush();

            return os.toByteArray();
        }
    }
    
    public static class StringData {
        public String data;
        public String filePath;
        public String modificationTimestamp;
        public String md5;
    }
    
    public static StringData getStringData(final String filePath) {
        final StringData result = new StringData();
        result.filePath = filePath;
        InputStream input = null; 
        try {
            final String resource = "/" + filePath;
            input = IoUtility.class.getResourceAsStream(resource);
            result.data = new String(getBytesFromInputStream(input), "UTF8");
            final URL url = IoUtility.class.getResource(resource);
            result.filePath = url.toExternalForm();
            URLConnection connection = null;
            try {
                connection = url.openConnection();
                result.modificationTimestamp = getIsoTimestamp(new Date(connection.getLastModified()));
            } finally {
                if (connection != null) {
                    try {
                        connection.getInputStream().close();
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
            }
        }
        if (result.data == null) {
            Path path = null;
            try {
                path = new File(filePath).toPath();
                result.data = new String(Files.readAllBytes(path), "UTF8");
                final FileTime time = Files.readAttributes(path, BasicFileAttributes.class).lastModifiedTime();
                result.modificationTimestamp = time.toString();
            } catch (IOException e) {
                try {
                    throw new RuntimeException("we didn't find " + path.normalize().toRealPath(), e);
                } catch (IOException ex) {
                    throw new RuntimeException("we didn't find " + path , e);
                }
            }
        }
        if (result.data != null) {
            result.md5 = md5(result.data);
        }    
        return result;
    }

	public final static String getIsoTimestamp() {
	    return getIsoTimestamp(new Date());
	}

    public final static String getIsoTimestamp(final Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        df.setTimeZone(tz);
        return df.format(date);
    }

	public final static String getUTCTimestamp() {
	    return getUTCTimestamp(new Date());
	}

    public final static String getUTCTimestamp(final Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HHmm");
        df.setTimeZone(tz);
        return df.format(date);
    }

    public final static void save(final String fileName, final JSONObject object) {
        IoUtility.save(fileName, object.toString(2));
	}

	public final static void save(final String fileName, final String json) {
		save(new File(fileName), json);
	}

	public final static void save(final File file, final String json) {
	    FileWriter writer = null;
	    try { 
	        writer = new FileWriter(file);
	        writer.write(json);
	    } catch (IOException e) {
            Log.error("saving '" + file + "' failed", e);
	        throw new RuntimeException(e);
        } finally {
	        if (writer != null) {
	            try {
	                writer.close();
	            } catch (Exception e) {
	            }
	        }    
	    }    
	}

	public static String md5(final String text) {
	    MessageDigest m;
	    try {
	        m = MessageDigest.getInstance("MD5");
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    }
	    m.reset();
	    m.update(text.getBytes());
	    byte[] digest = m.digest();
	    BigInteger bigInt = new BigInteger(1,digest);
	    return bigInt.toString(16);
	}

}
