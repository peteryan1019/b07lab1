
class Polynomial {
    double coefficients [];
    public Polynomial(){
       coefficients = new double[1];
       coefficients[0]=0;
    }

    public Polynomial(double [] coefficients){
        this.coefficients = coefficients;
    }


    public Polynomial add(Polynomial polynomial){
        int length1 = polynomial.coefficients.length;
        int length2 = this.coefficients.length;
        int larger_length = Math.max(length1, length2);

        double [] sum = new double[larger_length];

        for(int i=0; i<larger_length; i++)
        {
            if(i<length1 && i< length2)
            {
                sum[i]=polynomial.coefficients[i]+this.coefficients[i];
            }
            else if (i>=length1)
            {
                sum[i] = this.coefficients[i];
            }
            else if(i>=length2)
            {
                sum[i]=polynomial.coefficients[i];
            }
        }

        Polynomial new_Polynomial = new Polynomial(sum);
        return new_Polynomial;
    }

    public double evaluate(double x){

        double result = 0;
        int length = this.coefficients.length;
        for(int i=0; i<length; i++)
        {
            double a = this.coefficients[i];
            result = result + a * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x)==0;
    }
}
