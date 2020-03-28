package dk.networking;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        String ipStr = "192.145.63.12";
        System.out.println((192 & 0xff));
        byte[] ip = new byte[] { (byte)194, (byte)145, 63, 12}; //extractIPasByteArr(ipStr);//
        System.out.println("parsed out: " + Arrays.toString(ip));

        ipStr = makeIPtoStr( ip );
        InetAddress inetAddress = InetAddress.getByAddress( ip );
        try {
            int icmp_seq = 1;
            while (icmp_seq > 0){
                long startTime = System.nanoTime();
                boolean isReached = inetAddress.isReachable(2_000);
                long endTime = System.nanoTime();
                double time_ms = (endTime - startTime) / 1_000_000.0;
                if(isReached){ String l = String.format(
                            "?? bytes from %s: icmp_seq= %d ttl=?? time=%.1f",
                            ipStr,icmp_seq++,time_ms);
                    System.out.println(l);
                }else {
                    System.out.println("Is not reachable!");
                    icmp_seq = -1;
                }
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    private static byte[] extractIPasByteArr(String ipStr) {
        String[] arr = ipStr.split("\\.");
        System.out.println(Arrays.toString(arr));
        byte[] ip = new byte[arr.length];
        for (int i = 0; i<arr.length; i++)
            ip[i] = (byte)Integer.parseInt(arr[i].strip());

        System.out.println("parsed: " + Arrays.toString(ip));
        System.out.println("parsed in hand: " + Arrays.toString(new byte[] { (byte)194, (byte)145, 63, 12}));
        return ip;
        //return new byte[] { (byte)194, (byte)145, 63, 12};
    }

    private static String makeIPtoStr(byte[] ip) {
        return Arrays.asList(ip).stream().map(b-> {
            try {
                return new String(b,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.joining("."));
    }
}
