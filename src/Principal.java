/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplinas: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 * 
 * Filtro de Mediana com complexidade linear utilizando o select aleatório
 *
 * Seja A uma matriz de inteiros positivos com m linhas e n colunas, e sejam p e q dois inteiros
 * positivos ímpares. Dada um par de índices (i,j), com p/2 <= i < m-p/2 e q/2 <= j < n-q/2, a
 * vizinhança de tamanho p x q em torno de (i,j) é a submatriz de A com p linhas, q colunas e centro
 * em (i,j). Essa submatriz é denotada por Ai,j .
 * Por exemplo, dada a seguinte matriz 5 x 5
 * A = 
 *      9  4  5  0  8
 *     10  3  2  1  7
 *      9  1  6  3 15
 *      0  3  8 10  1
 *      1 16  9 12  7
 *
 * a vizinhança 3 x 3 em torno de (1,1) é a submatriz
 * A1,1 =
 *      9  4  5
 *      10 3  2
 *      9  1  6
 *
 * Note que a vizinhança não está definida para os pares de índices muito próximos 
 * à "borda" da matriz (por exemplo, em (0; 0)).
 * 
 * Filtro da mediana é uma transformação bastante usada para suavizar ruído do tipo impulsivo em
 * sinais e imagens digitais.
 * Uma imagem digital pode ser representada por uma matriz. Dada uma matriz A de inteiros
 * positivos, com m linhas e n colunas, e dados dois inteiros positivos e ímpares p e q, o filtro da mediana
 * produz uma matriz transformada M, com as mesmas dimensões que A, definida da seguinte maneira:
 * para cada par de índices (i,j), o elemento M(i,j) da matriz transformada é a 
 * mediana dos elementos de Aij (a vizinhança p x q em torno de (i,j)).
 * No caso do exemplo anterior, os núumeros em torno de (1,1) são 9,4,5,10,3,2,9,1,6. Logo,
 * M(1,1) = 5. Quando a vizinhança de uma par de índices (i,j) não estiver bem definida, usaremos a
 * convenção M(i,j) = 0.
 *
 * No caso da matriz A acima, o resultado do filtro da mediana com uma vizinhança 3x3 é 
 * a seguinte matriz:
 * M =
 *      0 0 0 0 0
 *      0 5 3 5 0
 *      0 3 3 6 0
 *      0 6 8 8 0
 *      0 0 0 0 0
 *
 * Atenção:
 * Vetor em java inicia em 0, os algoritmos consideram início em 1.
 * A subtração de -1 ocorre somente no local de acesso ao vetor ou matriz 
 * para manter a compatibilidade entre os algoritmos.
 *
 */

/**
 * @author Osmar de Oliveira Braz Junior
 */
public class Principal {

    /**
     * O piso (= floor) de um número real x é o resultado do arredondamento de x
     * para baixo. 
     * 
     * Em outras palavras, o piso de x é o único número inteiro i
     * tal que i<=x<i+1. Ex. O piso de 3.9 é 3
     *
     * Em java pode ser utilizando Math.floor(double)
     *
     * @param x Número real a ser calculado o piso.
     * @return um valor inteiro com o piso de x.
     */
    public static int piso(double x) {
        //Pego a parte inteira de x
        int parteInteira = (int) x;
        //Pego a parte fracionária de x
        double parteFracionaria = x - parteInteira;
        //Retorno x subtraindo a parte fracionária 
        return (int) (x - parteFracionaria);
    }

    /**
     * Seleciona um número aleatorio no intervalo de início a fim.
     *
     * @param inicio Início do intervalo do número aleatório
     * @param fim Fim do intervalo do número aleatório
     */
    public static int aleatorio(int inicio, int fim) {
        return (int) Math.ceil(Math.random() * (fim - inicio + 1)) - 1 + inicio;
    }

    /**
     * Realiza a troca de posição de dois elementos do vetor.
     *
     * @param A Vetor que contem os dados
     * @param i Primeira posição de troca
     * @param j Segunda posição de troca
     */
    public static void troca(int[] A, int i, int j) {
        int aux = A[i - 1];
        A[i - 1] = A[j - 1];
        A[j - 1] = aux;
    }

    /**
     * Particione encontra o pivô.
     *
     * Complexidade de tempo Theta(n). T(n) = Theta(2n + 4) + O(2n) = Theta(n).
     *
     * @param A Vetor com os dados
     * @param p Início do vetor
     * @param r Fim do vetor
     * @return o pivo da troca
     */
    public static int particione(int A[], int p, int r) {
        //x é o "pivô"
        int x = A[r - 1];                       //Theta(1)
        int i = p - 1;                          //Theta(1)
        for (int j = p; j <= r - 1; j++) {      //Theta(n)
            if (A[j - 1] <= x) {                //Theta(n)
                i = i + 1;                      //O(n)
                troca(A, i, j);                 //O(n)
            }
        }
        troca(A, i + 1, r);                     //Theta(1)
        return i + 1;                           //Theta(1)
    }

    /**
     * Particione aleatório encontra o pivo de forma aleatória.
     *
     * Slide 91 da aula 01/09/2017.
     *
     * @param A Vetor com os dados
     * @param p Início do vetor
     * @param r Fim do vetor
     * @return o pivô da troca
     */
    public static int particioneAleatorio(int A[], int p, int r) {
        //i é o "pivô"
        int j = aleatorio(p, r);              //Theta(1)          
        troca(A, j, r);                       //Theta(1)
        return particione(A, p, r);           //Theta(n)
    }

    /**
     * Recebe um vetor A[1...n] e devolve o valor do i-ésimo menor elemento de
     * A.
     *
     * A complexidade de tempo no pior caso n = r - p + 1 T(n) = max{T(k-1),
     * T(n-k)} + Theta(n) T(n) = Theta(n^2)
     *
     * A complexidade para o caso médio é Theta(n)
     *
     * @param A Vetor com os valores
     * @param p Posição inicial do vetor
     * @param r Posição final do vetor
     * @param i Posição desejada do vetor
     * @return Um valor do elemento da posição i do vetor
     */
    public static int selectAleatorio(int A[], int p, int r, int i) {
        if (p == r) {                                       //Theta(1)
            return A[p - 1];                                //O(1)
        }
        int q = particioneAleatorio(A, p, r);               //Theta(n)                
        int k = q - p + 1;                                  //Theta(n)
        if (i == k) { //O valor do pivô é a resposta        //Theta(n)
            return A[q - 1];                                //O(1)   
        } else {
            if (i < k) {                                    //O(1)   
                return selectAleatorio(A, p, q - 1, i);     //T(k-1)  
            } else {
                return selectAleatorio(A, q + 1, r, i - k); //T(n-k)
            }
        }
    }

    /**
     * Cálculo da Mediana com complexidade Linear.
     *
     * Utilizando o select aleatório com complexidade Theta(n)
     *
     * @param A Vetor com os dados
     * @param p Início do Vetor
     * @param r Quantidade de elementos do vetor
     * @return A posição da mediana em A
     */
    private static int medianaN(int A[], int p, int r) {
        //Calcula a quantidade de elementos de p até r
        //p = 1 e r = 5 = 5 - 1 + 1 = 5 elementos
        //p = 6 e r = 10 = 10 - 6 + 1 = 5 elementos
        int n = r - p + 1;
        //Calcula a posição da mediana em relação a quantidade de elementos do intervalo de p até r
        int m = piso(n / 2.0);
        selectAleatorio(A, p, r, m); //Particiona todo o vetor coloca a media na posição m        
        if (n % 2 == 1) {
            //Quantidade ímpar 
            //Início do vetor mais a mediana
            //p = 6 e r = 10 = 10 - 6 + 1 = 5 elementos
            //m = piso(5 / 2) = 2
            //mediana = 6 + 2 = 8
            return (p + m);
        } else {
            //Quantidade par 
            //Início do vetor mais a mediana
            //p = 5 e r = 10 = 10 - 5 + 1= 6 elementos
            //m = piso(6 / 2) = 3
            //mediana = 6 + 3 - 1 = 7
            return (p + m - 1);
        }
    }

     /**
     * Imprime na saída padrão a matriz.
     * 
     * @param X Uma matriz a ser exibida
     */
    public static void imprimir(int[][] X) {
        int ti = X.length; //Colunas
        int tj = X[0].length; //Linhas
        for (int i = 0; i < ti; i++) {
            for (int j = 0; j < tj; j++) {
                System.out.printf("%d \t", X[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * Filtro de Mediana.
     * 
     * @param A Matriz a ser aplicada o filtro de mediana.
     * @param p Largura da submatriz da vizinhança
     * @param q Altura da submatriz da vizinhança
     * @return Uma matriz com a aplicação do filtro de p x q
     */
    private static int[][] filtro(int A[][], int p, int q) {
        //Matriz de retorno
        int M [][] = new int[A[0].length][A[0].length];        
        int tamanho = A[0].length;
        //Percorre a matriz A
        for (int i = (p / 2); i < (tamanho - (p / 2)); i++) {
            for (int j = (q / 2); j < (tamanho - (q / 2)); j++) {
                //Contador de elementos incluidos na vetor
                int cont = 1;
                //Vetor para receber os elementos da vizinhaça da matriz 
                int V[] = new int[p * q];
                //Copia os elementos do intervalo da vizinhaça
                for (int k = i - (p / 2); k <= i + (p / 2); k++) {
                    //Calcula o início do intervalo
                    int inicio = j - (q / 2);
                    //Calcula o fim do intervalo
                    int fim = j + (q / 2) + 1;
                    //Adiciona os elementos da vizinhaça no vetor V
                    for (int elemento = inicio; elemento < fim; elemento++) {
                        V[cont-1] = A[k][elemento];
                        cont = cont + 1;
                    }
                }                
                System.out.print(" M[" + i + "]= ");
                for (int jj = 0; jj < (p * q); jj++) {
                    System.out.printf("%2d ", V[jj]);
                }
                //Localiza a mediana do vetor V
                int t = medianaN(V, 1, V.length);
                System.out.println(" = Mediana:" + V[t-1]);
                
                //Atribui na posição
                M[i][j] = V[t-1];
            }
        }
        return M;
    }   
    
    public static void main(String args[]) {

        //Matriz de dados
        int A[][] = { { 9,4,5,0,8}, 
                      {10,3,2,1,7}, 
                       {9,1,6,3,15},
                       {0,3,8,10,1},
                       {1,16,9,12,7}};
                
        //Quantidade de elementos
        int r = 5;

        System.out.println(">>> Filtro de Mediana com complexidade linear <<<");
        System.out.println("Matriz A antes: ");
        imprimir(A);
        System.out.println();

        //3 e 3 são a vizinhaça do filtro
	int M[][] = filtro(A, 3, 3);
        
        System.out.println();
        System.out.println("Matriz M depois: ");
        imprimir(M);
    }
}