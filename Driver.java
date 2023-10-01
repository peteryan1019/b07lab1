import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.io.PrintStream;
// import java.util.Scanner;


public class Driver {

    public static void  check(Polynomial s, double [] correct_coef, int [] correct_exp){
    for(int i=0;i<s.coefficients.length;i++)
        {
            if (s.coefficients[i]!= correct_coef[i] || s.exponents[i]!=correct_exp[i])
            {
                System.out.println("function is not correct, your coef: ");
        for(int k=0;i<s.coefficients.length;i++)
        {
            System.out.println(s.coefficients[k]+ " ");
        }
        System.out.println();
        System.out.println("your exp: ");
        for(int l=0;i<s.coefficients.length;i++)
        {
            System.out.println(s.exponents[l]+ " ");
        }
            }
        }
        System.out.println("function is correct");
    }
    public static void main(String [] args) {
        

        // if(s.hasRoot(0))
        //     System.out.println("0 is a root of s");
        // else
        //     System.out.println("0 is not a root of s"); 
        // test the multiply method
        double [] c1 = {1};
        int [] e1 = {0};
        double [] c2 = {-2};
        int [] e2 = {0};
        Polynomial p1 = new Polynomial(c1,e1);
        Polynomial p2 = new Polynomial(c2,e2);
        Polynomial result = p1.multiply(p2);
        double [] correct_coef = {-2};
        int [] correct_exp = {0};
        check(result, correct_coef, correct_exp); 
        
        File file = new File("output.txt");
        result.SavetoFile(file.getAbsolutePath());
        // try{
        // BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
        // writer.write("1x2-2");
        // writer.close();

        // } catch(IOException e){e.printStackTrace();}        
        // Polynomial p = new Polynomial(file);
        // double [] correct_c = {1,-2};
        // int [] correct_e = {2,0};
        // System.out.println("checking the new constructor \n");
        // check(p, correct_c, correct_e);
    }
}