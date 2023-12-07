package br.com.sulamerica.contasmedicas.util;

import java.math.BigInteger;
import java.util.UUID;

public class RandomData {

	
	public static Long uniquePositiveNumber() {
		String generateUUIDNo = String.format("%010d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
		// To decide length of unique positive long number generateUUIDNo.length() - uniqueNoSize is being used
        return Long.parseLong(generateUUIDNo.substring( generateUUIDNo.length() - 10));
	}
}
