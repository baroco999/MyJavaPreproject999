import java.util.Scanner;

public class StringCalculator {
   
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.next());
        }
        StringAnalyzer sa = new StringAnalyzer(new String(sb));
        try {
            sa.compute();
		} catch (NumberFormatException e) {
	        System.out.println(e.getMessage());
		}
        System.out.println(sa.toString());
        sc.close();
    }
}

class StringAnalyzer {

    private String str;
    private String s1 = "", s2 = "", result = "";
    private char sign;
    private int num = 0;

    public StringAnalyzer (String s) {
        str = s;
    }

    void compute() throws NumberFormatException {
        if (str.charAt(0)!= '\"') {
            throw new NumberFormatException("Invalid argument format");
        }
        String[] strArray = str.split("\"");
        s1 = strArray[1];
        sign = strArray[2].charAt(0);
        if (strArray.length == 3) {
            num = (int) Double.parseDouble(strArray[2].substring(1));
            if((num < 1)|(num > 10)) {
                throw new NumberFormatException("Invalid number format");
            }
        } else {
            s2 = strArray[3];
        }
        
        
        if (num == 0) {
            result = new StC().go(s1, s2, sign);
        } else {
            result = new StC().go(s1, num, sign);
        }
    }

    public String toString() {
        return result;
    }
}

class StC {
    StringBuilder r;
    StringBuffer t1;
    StringBuffer t2;
    
    String go(String s1, String s2, char sign) throws NumberFormatException {
            r = new StringBuilder();
            r.append('\"');
            t1 = new StringBuffer(s1);
            t2 = new StringBuffer(s2);
            if (t1.length() > 10) t1.delete(10, t1.length());
            if (t2.length() > 10) t2.delete(10, t2.length());
            switch (sign) {
                case '+':
                    r.append(t1).append(t2);
                    break;
                case '-':
                    if (s1.endsWith(s2)) {
                        r.append(s1.substring(0, (s1.length() - s2.length())));
                    } else {
                        r.append(t1);
                    }
                    break;
                default: throw new NumberFormatException("Unsupported operation");
            }
            r.append('\"');
            if (r.length() > 40) {
                r.delete(40, r.length());
                r.append(new char[]{'.', '.', '.'});
            }
            return new String (r);
    }

    String go(String s1, int num, char sign) throws NumberFormatException {
            r = new StringBuilder();
            r.append('\"');
            t1 = new StringBuffer(s1);
            if (t1.length() > 10) t1.delete(10, t1.length());
            switch (sign) {
                case '*':
                    int i = 1;
                    while (i <= num) {
                        r.append(t1);
                        i++;
                    }
                    break;
                case '/':
                    int re = t1.length() / num;
                    r.append(t1.substring(0, re));
                    break;
                default: throw new NumberFormatException("Unsupported operation");
            }
            r.append('\"');
            if (r.length() > 40) {
                r.delete(40, r.length());
                r.append(new char[]{'.', '.', '.'});
            }
            return new String (r);
    }
}
