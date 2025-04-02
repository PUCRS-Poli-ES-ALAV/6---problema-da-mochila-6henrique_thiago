public class CodigoThiago {
    static long recursiveCalls = 0;
    static long iterations = 0;
    static long instructions = 0;

    public static int fiboRec(int n) {
        recursiveCalls++;
        if (n <= 1) {
            instructions += 2;
            return n;
        }
        instructions += 4;
        int a = fiboRec(n - 1);
        int b = fiboRec(n - 2);
        instructions += 1;
        return a + b;
    }

    public static int fibo(int n) {
        instructions = 0;
        if (n <= 1) {
            instructions += 2;
            return n;
        }
        
        int[] f = new int[n + 1];
        f[0] = 0;
        f[1] = 1;
        instructions += 4;
        
        iterations = 0;
        for (int i = 2; i <= n; i++) {
            iterations++;
            f[i] = f[i - 1] + f[i - 2];
            instructions += 5;
        }
        instructions += 2;
        return f[n];
    }

    public static int memoizedFibo(int[] f, int n) {
        instructions = 0;
        iterations = 0;
        for (int i = 0; i <= n; i++) {
            iterations++;
            f[i] = -1;
            instructions += 4;
        }
        instructions += 2;
        return lookupFibo(f, n);
    }

    public static int lookupFibo(int[] f, int n) {
        recursiveCalls++;
        if (f[n] >= 0) {
            instructions += 2;
            return f[n];
        }
        if (n <= 1) {
            f[n] = n;
            instructions += 3;
        } else {
            instructions += 3;
            f[n] = lookupFibo(f, n - 1) + lookupFibo(f, n - 2);
        }
        instructions += 1;
        return f[n];
    }

    public static void main(String[] args) {
        int[] smallVals = {4, 8, 16, 32};
        int[] largeVals = {128, 1000, 10000};
        
        System.out.println("Resultados para valores pequenos:");
        printTableHeader();
        for (int val : smallVals) {
            testAlgorithms(val);
        }
        
        System.out.println("\nResultados para valores grandes (apenas versões iterativa e memoization):");
        printTableHeader();
        for (int val : largeVals) {
            testLargeAlgorithms(val);
        }
    }
    
    private static void printTableHeader() {
        System.out.printf("%-10s | %-10s | %-15s | %-15s | %-15s | %-15s%n",
                "n", "Algoritmo", "Resultado", "Chamadas/Iterações", "Instruções", "Tempo (ns)");
        System.out.println("-------------------------------------------------------------------------------------------");
    }
    
    private static void testAlgorithms(int n) {
        
        recursiveCalls = 0;
        instructions = 0;
        long start = System.nanoTime();
        int result = fiboRec(n);
        long duration = System.nanoTime() - start;
        System.out.printf("%-10d | %-10s | %-15d | %-15d | %-15d | %-15d%n",
                n, "Recursivo", result, recursiveCalls, instructions, duration);
        
        iterations = 0;
        instructions = 0;
        start = System.nanoTime();
        result = fibo(n);
        duration = System.nanoTime() - start;
        System.out.printf("%-10d | %-10s | %-15d | %-15d | %-15d | %-15d%n",
                n, "Iterativo", result, iterations, instructions, duration);
        
        recursiveCalls = 0;
        instructions = 0;
        int[] f = new int[n + 1];
        start = System.nanoTime();
        result = memoizedFibo(f, n);
        duration = System.nanoTime() - start;
        System.out.printf("%-10d | %-10s | %-15d | %-15d | %-15d | %-15d%n",
                n, "Memoization", result, recursiveCalls, instructions, duration);
    }
    
    private static void testLargeAlgorithms(int n) {
    
        iterations = 0;
        instructions = 0;
        long start = System.nanoTime();
        int result = fibo(n);
        long duration = System.nanoTime() - start;
        System.out.printf("%-10d | %-10s | %-15d | %-15d | %-15d | %-15d%n",
                n, "Iterativo", result, iterations, instructions, duration);
        
        if (n <= 10000) {
            try {
                recursiveCalls = 0;
                instructions = 0;
                int[] f = new int[n + 1];
                start = System.nanoTime();
                result = memoizedFibo(f, n);
                duration = System.nanoTime() - start;
                System.out.printf("%-10d | %-10s | %-15d | %-15d | %-15d | %-15d%n",
                        n, "Memoization", result, recursiveCalls, instructions, duration);
            } catch (StackOverflowError e) {
                System.out.printf("%-10d | %-10s | %-15s | %-15s | %-15s | %-15s%n",
                        n, "Memoization", "StackOverflow", "N/A", "N/A", "N/A");
            }
        }
    }
}