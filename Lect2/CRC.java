
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class CRC {
    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int[] data;
        int[] div;
        int[] divisor;
        int[] rem;
        int[] crc;
        int data_bits,divisor_bits,tot_length;
        System.out.println("Enter the no of databits");
        data_bits=Integer.parseInt(br.readLine());
        data =new int[data_bits];
        System.out.println("Enter the data bitz");
        for (int i = 0; i < data_bits; i++)
            data[i]=Integer.parseInt(br.readLine());
            System.out.println("Enter no of bits in divsor");
            divisor_bits=Integer.parseInt(br.readLine());
            divisor =new int[divisor_bits];
            System.out.println("Enter the divisor bits");
            for (int j = 0; j < divisor_bits; j++)
                divisor[j]=Integer.parseInt(br.readLine());
                tot_length=data_bits+divisor_bits-1;
                div=new int[tot_length];
                rem=new int[tot_length];
                crc=new int[tot_length];
                for(int i=0;i<data.length;i++)
                    div[i]=data[i];
                System.out.println("Dividend after applying 0's are: ");
                for(int i=0;i<div.length;i++)
                    System.out.print(div[i]);
                    System.out.println();
                for(int j=0;j<div.length;j++){
                    rem[j]=div[j];
                }
                rem=divide(div,divisor,rem);
                for(int i=0;i<div.length;i++){
                    crc[i]=(div[i]^rem[i]);
                }
                System.out.println();
                System.out.println("CRC CODE");
                for(int i=0;i<crc.length;i++){
                    System.out.print(crc[i]);
                }
                System.out.println("Enter error code"+tot_length+"bits:");
                for(int i=0;i<crc.length;i++){
                    crc[i]=Integer.parseInt(br.readLine());
                }
                for(int j=0;j<rem.length;j++){
                    rem[j]=crc[j];
                }
                rem=divide(div, divisor, rem);
                for(int i=0;i<rem.length;i++){
                    if(rem[i]!=0){
                        System.out.println("Error");
                        break;
                    }
                    if(i==rem.length-1)
                        System.out.println("no error");
                }
                System.out.println("THANK YOU");
    }
    static int[] divide(int[] div,int[]divisor,int[] rem){
        int cur=0;
        while (true) {
            for(int i=0;i<divisor.length;i++)
                rem[cur+i]=(rem[cur+i]^divisor[i]);
            while(rem[cur]==0&&cur!=rem.length-1)cur++;
            if((rem.length-cur)<divisor.length)
                break;
        }
        return rem;
    }
    
}
