
import java.util.*;
public class CodeforcesProblem631B
{
    public static void main(String args[])
    {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k= in.nextInt();
        int /* BigInteger */ rows [] =new int[n];
        int /* BigInteger */ timestamp_rows [] = new int[n];
        int /* BigInteger */ timestamp_cols [] = new int[m];
        int /* BigInteger */ cols [] = new int[m];
        for(int i = 0;i<k;i++)
        {
            int /* BigInteger */ type = in.nextInt();
            int /* BigInteger */ rc = in.nextInt();
            int /* BigInteger */ color = in.nextInt();
            if(type == 1)
            {
                rows[rc-1]= color;
                timestamp_rows[rc-1] = i+1;
            }
            else
            {
                cols[rc-1] = color;
                timestamp_cols[rc-1] = i+1;
            }
        }
        for(int i = 0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(timestamp_rows[i] > timestamp_cols[j])
                {
                    System.out.print(rows[i]+" ");
                }
                else
                {
                    System.out.print(cols[j] + " ");
                }

            }
            System.out.println();
        }
    }
}