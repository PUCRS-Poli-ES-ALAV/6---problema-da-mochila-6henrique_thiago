public class ExercicioFib {

    public static int fiboRec (int n) {
        if (n <= 1) return n;
        int a = fiboRec(n -1);
        int b = fiboRec(n-2);
        return a + b;
    }

    public static int fibo(int n) {
        int[] f = new int[n+1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f[n];
    }

    public static int memoizedFibo(int[] f, int n) {
        for (int i = 0; i <= n; i++) {
            f[i] = -1;
        }
        return lookupFibo(f, n);
    }

    public static int lookupFibo(int[] f, int n) {
        if (f[n] >= 0) return f[n];
        if (n <= 1) f[n] = n;
        else {
            f[n] = lookupFibo(f, n - 1) + lookupFibo(f, n - 2);
        }
        return f[n];
    }

    public static void main(String[] args) {
        int[] vals = {4,8,16,32};
        for (int val : vals) {
            System.out.println(fiboRec(val));
            System.out.println(fibo(val));
            int[] f = new int[val + 1];
            System.out.println(memoizedFibo(f, val));
        }
    }
    }
