import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class SHA1Wrapper {
    private MessageDigest d;

    public SHA1Wrapper() throws NoSuchAlgorithmException {
        d = MessageDigest.getInstance("SHA-1");
    }

    private static String byte_arr_to_binary_str(byte[] arr){
        StringBuilder sb = new StringBuilder();
        for (byte elem: arr){
            sb.append(String.format("%8s", Integer.toBinaryString(elem & 0xff)).replace(' ', '0'));
        }
        return sb.toString();
    }

    public long hash(byte[] in, long num_bits){ //get the first num_bits bits from the hash binary string
        byte[] out = d.digest(in);
        return Long.parseLong(byte_arr_to_binary_str(out).substring(0, (int) num_bits), 2);
    }
}
