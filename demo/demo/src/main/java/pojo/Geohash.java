package pojo;

import java.util.BitSet;

public class Geohash {
	private static int numbits = 3 * 5;
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	public String encode(double lat, double lon) {
		BitSet latbits = getBits(lat, -90, 90);
		BitSet lonbits = getBits(lon, -180, 180);
		StringBuilder buffer = new StringBuilder();
		//经纬度合并
		for (int i = 0; i < numbits; i++) {
			buffer.append(lonbits.get(i) ? '1' : '0');
			buffer.append(latbits.get(i) ? '1' : '0');
		}
		return base32(Long.parseLong(buffer.toString(), 2));
	}
    //将经纬度变成二进制。
	private BitSet getBits(double d, double min, double max) {
		BitSet buffer = new BitSet(numbits);
		for (int i = 0; i < numbits; i++) {
			double mid = (min + max) / 2;
			if (d >= mid) {
				buffer.set(i);
				min = mid;
			} else {
				max = mid;
			}
		}
		return buffer;
	}
	//按照Base32进行编码
	private static String base32(long i) {
		char[] buf = new char[65];
		int charPos = 64;
		boolean negative = (i < 0);
		if (!negative)
			i = -i;
		while (i <= -32) {
			buf[charPos--] = digits[(int) (-(i % 32))];
			i /= 32;
		}
		buf[charPos] = digits[(int) (-i)];

		if (negative)
			buf[--charPos] = '-';
		return new String(buf, charPos, (65 - charPos));

	}
}
