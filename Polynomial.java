import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
// import java.util.Scanner;

class Polynomial {
    double coefficients [];
    int exponents [];
    public Polynomial(){
    }
    public Polynomial(double [] coefficients, int [] exponents){
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

public Polynomial(File doc) {
    try {
        BufferedReader reader = new BufferedReader(new FileReader(doc.getAbsolutePath()));
        String line = reader.readLine();
        String line1 = line.replace("-", "+-");
    String [] sub = line1.split("\\+");
    Object [][] array = new Object[sub.length][2];
    for(int i=0;i<sub.length;i++)
    {
        String sub_element = sub[i];
        if(sub_element.contains("x"))
        {
            String [] split_result = sub_element.split("x");
            array[i]=split_result;
        }
        else{
            array[i]=new String[] {sub_element};
        }
    }

    int k= array.length;
    double [] c = new double[k];
    int [] e = new int[k];
    for(int i=0;i<k;i++)
    {
        if(array[i].length==1)
        {
            c[i]=Double.parseDouble(array[i][0].toString());
            e[i]=0;
        }
        else if(array[i].length==2)
        {
            c[i]=Double.parseDouble(array[i][0].toString());
            e[i]=Integer.parseInt(array[i][1].toString());
        }
    }
    this.coefficients=c;
    this.exponents=e;
    } catch (IOException e) {
        // Handle potential IOException
        e.printStackTrace();
    }
    
        
}

    public int get_nonzero_index(double [] coef, int start_index)
    {
        for(int i=start_index;i<coef.length;i++)
        {
            if (coef[i]!=0)
            {
                return i;
            }
        }
        return -1;
    }

    public int find_max_exponent(int [] e)
    {
        int cur_max = -1;
        for(int i=0;i<e.length;i++)
        {
            if(e[i]>cur_max)
            {
                cur_max = e[i];
            }
        }
        return cur_max;
    }

    public int get_index(int [] e, int exponent)
    {
        for(int i=0;i<e.length; i++)
        {
            if(e[i]==exponent)
            {
                return i;
            }
        }
        return -1;
    }

    public int num_nonzeros(double [] c)
    {
        int count = 0;
        for(int i=0;i<c.length;i++)
        {
            if (c[i]!=0)
            {
                count=count+1;
            }
        }
        return count;
    }

    public Polynomial add(Polynomial polynomial){

        int [] e1 = new int[this.exponents.length];
        int [] e2 = new int[polynomial.exponents.length];
        double [] c1 = new double[this.coefficients.length];
        double [] c2 = new double[polynomial.coefficients.length];
        int max_deg_1 = find_max_exponent(this.exponents);
        int max_deg_2 = find_max_exponent(polynomial.exponents);
        int max_deg_3 = Math.max(max_deg_1,max_deg_2);
        int [] e3 = new int[max_deg_3 + 1];
        double [] c3 = new double[max_deg_3 + 1];

        //order the exponents
        for(int i=0;i<=max_deg_3;i++)
        {
            e3[i]=i;
        }

        // update the coefficients
        for(int i=0;i<=max_deg_3;i++)
        {
            int j = get_index(e1, i);
            if (j!=-1)
            {
                c3[i]+=c1[j];
            }
            int k=get_index(e2, i);
            if(k!=-1)
            {
                c3[i]+=c2[k];
            }
        }
        //now get rid of the 0s
        int k=num_nonzeros(c3);
        if(k==0)
        {
            Polynomial null_result = new Polynomial();
            return null_result;
        }
        double [] c4=new double[k];
        int [] e4 = new int[k];

        for(int i=0;i<k;i++)
        {
            int m=get_nonzero_index(c3, i);
            c4[i]=c3[m];
            e4[i]=e3[m];
        }

        Polynomial p3=new Polynomial(c4, e4);
        return p3;
    }

    public double evaluate(double x){

        double result = 0;
        int length = this.coefficients.length;
        for(int i=0; i<length; i++)
        {
            double a = this.coefficients[i];
            int n= this.exponents[i];
            result = result + a * Math.pow(x, n);
        }
        return result;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x)==0;
    }

    public Polynomial multiply(Polynomial polynomial)
    {
        int [] e1= this.exponents;
        double [] c1=this.coefficients;
        int [] e2= polynomial.exponents;
        double [] c2=polynomial.coefficients;
        int k = e1.length * e2.length;
        int [] e3 = new int[k];
        double [] c3=new double[k];

        // get the raw product
        int index=0;
        for(int i=0;i<e1.length;i++)
        {
            for(int j=0;j<e2.length;j++)
            {
                e3[index]=e1[i]+e2[j];
                c3[index]=c1[i]*c2[j];
                index++;
            }
        }

        // do some preparation 
        int [] already_checked = new int[k];
        for(int i=0;i<already_checked.length;i++)
        {
            already_checked[i]=-1;
        }

        double [] looped_coefficients = new double[k];

        // simplify the product by combining like terms
        index=0;
        for(int i=0;i<k;i++)
        {
            if(get_index(already_checked, e3[i])==-1)
            {
                already_checked[index]=e3[i];
                looped_coefficients[index]=c3[i];
                for(int j=0;j<k;j++)
                    {
                        if(e3[i]==e3[j])
                        {
                            if(i!=j)
                            {looped_coefficients[index]+=c3[j];}
                        }
                    }
                index++;
            }    
        }
        // now clean those up by using a smaller array without 0s
        int size= num_nonzeros(looped_coefficients);
        int [] e4= new int[size];
        double [] c4 = new double[size];

        // fill it in
        for(int i=0;i<size;i++)
        {
            int m=get_nonzero_index(looped_coefficients, i);
            c4[i]=looped_coefficients[m];
            e4[i]=already_checked[m];
        }

        //build a new polynomial
        Polynomial result = new Polynomial(c4, e4);
        return result;
    }

    public void SavetoFile(String file_name)
    {
        String p = "";
        int [] e = this.exponents;
        double [] c = this.coefficients;

        for(int i=0;i<c.length;i++)
        {
            if(e[i]!=0)
            {
                if(c[i]>=0)
                {
                    if(i!=0)
                    {p = p + "+" + Double.toString(c[i]) + "x" + Integer.toString(e[i]);}
                    else {p=p + Double.toString(c[i]) + "x" + Integer.toString(e[i]);}
                }
                else{
                    p = p + Double.toString(c[i]) + "x" + Integer.toString(e[i]);
                }
            }
            else
            {
                if(c[i]>=0)
                {
                    if(i!=0)
                    {p=p+ "+" + Double.toString(c[i]);}
                    else{
                        p=p + Double.toString(c[i]);
                    }
                }
                else{
                    p=p + Double.toString(c[i]);
                }
            }
        }

        try{
        PrintStream ps = new PrintStream(file_name);
        ps.println(p);
        ps.close();
        } catch(IOException d){d.printStackTrace();}
    }
}
