

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.FileTerminal;
import com.panayotis.gnuplot.terminal.GNUPlotTerminal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class painguainzProjetoFinal {

    static int MAXIMO_DIAS = 26304;
    static int MAXIMO_COLUNAS = 3;

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        boolean programaAFuncionar;
        String ficheiro;
        int periodosComoParametro;
        int tipoMediaParametro = 0;
        int tipoOrdenacaoParametro;
        double constante = 0;
        int ano = 0, mes = 0, dia = 0;

        Scanner ler = new Scanner(System.in);
        int inicio = 0;

        int contadorParametros = contarParametros(args);
        boolean interativo = confirmarInterativo(contadorParametros);
        boolean naoInterativo = confirmarNaoInterativo(contadorParametros);

        int[][] data = new int[MAXIMO_DIAS][MAXIMO_COLUNAS];
        int[][] hora = new int[MAXIMO_DIAS][MAXIMO_COLUNAS];
        int[] consumo = new int[MAXIMO_DIAS];
        int contador = 0;
        double alpha = 0;
        String[] itensPrevisao = new String[3];

        if (interativo == true) {

            contador = leituraDoFicheiro(data, hora, consumo, contador, args);
            double[] filtragemMediaSimples = new double[contador];
            double[] filtragemPesada = new double[contador];
            int fim = contador - 1;
            int meio = ((inicio + fim) / 2);
            menuInterativo();
            int referencianum = 0;
            String tipoPeriodo = null;
            String tipoMedia = null;
            String tipoOrdenacao = null;
            selecionarOpção(tipoPeriodo, tipoMedia, tipoOrdenacao, ano, mes, dia, contador, alpha, data, hora, consumo, interativo, naoInterativo, itensPrevisao, inicio, meio, fim, tipoMediaParametro, filtragemMediaSimples, filtragemPesada, referencianum);
            creditos();

        } else if (naoInterativo == true) {

            periodosComoParametro = Integer.parseInt(args[1]);
            tipoMediaParametro = Integer.parseInt(args[2]);
            tipoOrdenacaoParametro = Integer.parseInt(args[3]);
            constante = Double.parseDouble(args[4]);
            itensPrevisao = args[5].split("-");
            if (itensPrevisao.length == 1) {
                ano = Integer.parseInt(itensPrevisao[0]);
            } else if (itensPrevisao.length == 2) {
                ano = Integer.parseInt(itensPrevisao[0]);
                mes = Integer.parseInt(itensPrevisao[1]);
            } else if (itensPrevisao.length == 3) {
                ano = Integer.parseInt(itensPrevisao[0]);
                mes = Integer.parseInt(itensPrevisao[1]);
                dia = Integer.parseInt(itensPrevisao[2]);
            }

            boolean confirmarMomentoPrevisao = confirmacaoMomentoPrevisao(itensPrevisao, periodosComoParametro);
            boolean confirmarModelo = confirmacaoModelo(tipoMediaParametro);
            boolean confirmarPeriodos = confirmarParametroPeriodo(periodosComoParametro);
            boolean confirmarTipoOrdenacao = confirmacaoTipoOrdenacao(tipoOrdenacaoParametro);
            boolean confirmarConstante = confirmacaoConstante(constante);
            programaAFuncionar = confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
            if (programaAFuncionar == false) {
                System.out.println("O programa não pode iniciar, devido a erros nos parametros!");

            } else {
                int referencianum = 0;
                double[] filtragemMediaSimples = new double[contador];
                double[] filtragemPesada = new double[contador];
                String tipoMedia = Mediar(tipoMediaParametro);
                String tipoPeriodo = Periodar(periodosComoParametro);
                String tipoOrdenacao = ordenacao(tipoOrdenacaoParametro);
                contador = leituraDoFicheiro(data, hora, consumo, contador, args);
                analiseTemporal(tipoPeriodo, itensPrevisao, contador, interativo, naoInterativo, consumo, hora);
                int fim = contador - 1;
                int meio = ((inicio + fim) / 2);
                numeroObservacoes(contador, consumo);
                ordenacao1(consumo, inicio, fim, interativo, naoInterativo, tipoOrdenacao);
                String opcaoMedia = tipoMedia;
                if (tipoMedia.trim().compareToIgnoreCase("simples") == 0) {
                    mediaSimples4(consumo, contador, filtragemMediaSimples);

                } else if (tipoMedia.trim().compareToIgnoreCase("pesada") == 0) {
                    mediaExpPesada(consumo, contador, referencianum, alpha, filtragemPesada);
                }
                

            }
        }
    }

    public static void menuInterativo() {

        System.out.println("                             --------------------------------------------------------------");
        System.out.println("                                                Análise de Séries Temporais             ");
        System.out.println("                                                            --.--                       ");
        System.out.println("                                               Bem-vindo ao modo interativo!            ");
        System.out.println("                             --------------------------------------------------------------");
        System.out.println();
        System.out.println(" -------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println(" A partir de agora pode selecionar qualquer uma das seguintes funcionalidades e ver o seu resultado!");
        System.out.println(" Experimente uma das seguintes:");
        System.out.println();
        System.out.print("-");
        System.out.println(" 1 - Analisar series temporais em diferentes resoluções temporais.");
        System.out.print("-");
        System.out.println(" 2 - Calcular o número de observações que ocorrem num conjunto de intervalos.");
        System.out.print("-");
        System.out.println(" 3 - Ordenar os valores da série temporal por ordem crescente ou decrescente.");
        System.out.print("-");
        System.out.println(" 4 - Implementar os modelos de Média Móvel Simples e Média Móvel Exponencialmente Pesada.");
        System.out.print("-");
        System.out.println(" 5 - Calcular o Erro Médio Absoluto (MAE) que se comete ao utilizar um modelo para representar a série observada");
        System.out.print("-");
        System.out.println(" 6 - Prever o valor de uma observação futura utilizando um dos modelos escolhidos.");
        System.out.print("-");
        System.out.println(" -------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public static void selecionarOpção(String tipoPeriodo, String tipoMedia, String tipoOrdenacao, int ano, int mes, int dia, int contador, double alpha, int[][] data, int[][] hora, int[] consumo, boolean interativo, boolean naoInterativo, String[] itensPrevisao, int inicio, int meio, int fim, int tipoMediaParametro, double[] filtragemMediaSimples, double[] filtragemPesada, int referencianum) throws FileNotFoundException {
        Scanner ler = new Scanner(System.in);
        String referencia = null;
        System.out.print("Selecione uma opção: ");
        System.out.println("");
        String opcao = ler.nextLine();
        while (opcao.trim().compareToIgnoreCase("Fim") != 0) {

            switch (opcao) {
                case "1": {
                    analiseTemporal(tipoPeriodo, itensPrevisao, contador, interativo, naoInterativo, consumo, hora);

                    break;

                }
                case "2": {
                    numeroObservacoes(contador, consumo);
                    break;

                }
                case "3": {
                    ordenacao1(consumo, inicio, fim, interativo, naoInterativo, tipoOrdenacao);

                    break;

                }
                case "4": {

                    if (interativo == true) {
                        System.out.println("Introduza o tipo de media que deseja!(Simples, Pesada)");
                        String opcaoMedia;
                        opcaoMedia = ler.nextLine();
                        while (opcaoMedia.trim().compareToIgnoreCase("Simples") != 0 && opcaoMedia.trim().compareToIgnoreCase("Pesada") != 0) {
                            System.out.println("Tipo de média inválido!");
                            System.out.println("Introduza o tipo de media que deseja!(Simples, Pesada)");
                            opcaoMedia = ler.nextLine();

                        }
                        if (opcaoMedia.trim().compareToIgnoreCase("simples") == 0) {
                            mediaSimples4(consumo, contador, filtragemMediaSimples);
                        } else if (opcaoMedia.trim().compareToIgnoreCase("pesada") == 0) {
                            mediaExpPesada(consumo, contador, referencianum, alpha, filtragemPesada);

                        }
                    } else if (naoInterativo == true) {
                        String opcaoMedia = tipoMedia;
                        if (tipoMedia.trim().compareToIgnoreCase("simples") == 0) {
                            mediaSimples4(consumo, contador, filtragemMediaSimples);

                        } else if (tipoMedia.trim().compareToIgnoreCase("pesada") == 0) {
                            mediaExpPesada(consumo, contador, referencianum, alpha, filtragemPesada);
                        }
                    }
                    break;

                }

                case "5": {
                    erroAbsoluto(contador, consumo, interativo, naoInterativo, filtragemMediaSimples);

                    break;

                }
                case "6": {
                    calculoAuxMediaSimples(referencia, contador, consumo, tipoMediaParametro, alpha, filtragemPesada, filtragemMediaSimples, referencianum);

                    break;

                }

                default: {
                    System.out.println("Opção invalida!");
                }
            }
            System.out.println("Selecione uma opção:");
            opcao = ler.nextLine();
        }
    }

    public static void creditos() {
        System.out.println("                 ➕ Obrigado por usar o nosso programa! Espero ter ajudado em tudo! Até à próxima! ➕   ️️️                   ");
        System.out.println();
        System.out.println("  ➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕➕");
        System.out.println();
        System.out.println("                                            ➕ --------------------------- ➕");
        System.out.println("                                           ➕          CRÉDITOS           ➕");
        System.out.println("                                          ➕                             ➕");
        System.out.println("                                         ➕  José Soares, nº1190782     ➕");
        System.out.println("                                        ➕                             ➕");
        System.out.println("                                       ➕  Lourenço Melo, nº1190811   ➕");
        System.out.println("                                      ➕                             ➕");
        System.out.println("                                     ➕   Diogo Silva, nº1180611    ➕");
        System.out.println("                                    ➕                             ➕");
        System.out.println("                                   ➕   João Beires, nº1190718    ➕");
        System.out.println("                                  ➕                             ➕");
        System.out.println("                                 ➕  João Ferreira, nº1190708   ➕");
        System.out.println("                                ➕ --------------------------- ➕");
    }

    public static boolean confirmarInterativo(int contadorParametros) {
        boolean serInterativo = false;
        if (contadorParametros == 1) {     // confirmar se sao estes os parametros a receber
            serInterativo = true;
        }
        return serInterativo;
    }

    public static boolean confirmarNaoInterativo(int contadorParametros) {
        boolean confirmarNaoInterativo = false;
        if (contadorParametros == 6) {    // confirmar se sao estes os parametros a receber
            confirmarNaoInterativo = true;
        }
        return confirmarNaoInterativo;
    }

    public static int contarParametros(String[] args) {
        int contadorParametros = 0;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                contadorParametros++;
            }
            if (contadorParametros != 1 && contadorParametros != 6) {
                System.out.println("Não foram inseridos número de parâmetros suficientes!");
            }
        } else {
            System.out.println("Não foram inseridos número de parâmetros suficientes!");
        }
        return contadorParametros;
    }

    public static boolean confirmarParametroPeriodo(int periodosComoParametro) {
        boolean confirmacaoParametroPeriodo = true;
        if (periodosComoParametro != 11 && periodosComoParametro != 12 && periodosComoParametro != 13 && periodosComoParametro != 14 && periodosComoParametro != 2 && periodosComoParametro != 3 && periodosComoParametro != 4) {
            confirmacaoParametroPeriodo = false;
            System.out.println("O parametro resolução que inseriu não é permitido");
        }
        return confirmacaoParametroPeriodo;
    }

    public static boolean confirmacaoMomentoPrevisao(String[] parametrosMomentoPrevisao, int periodosComoParametro) {
        boolean confirmacao = false;
        if (periodosComoParametro == 11 && parametrosMomentoPrevisao.length == 3) {
            confirmacao = true;
        } else if (periodosComoParametro == 12 && parametrosMomentoPrevisao.length == 3) {
            confirmacao = true;
        } else if (periodosComoParametro == 13 && parametrosMomentoPrevisao.length == 3) {
            confirmacao = true;
        } else if (periodosComoParametro == 14 && parametrosMomentoPrevisao.length == 3) {
            confirmacao = true;
        } else if (periodosComoParametro == 2 && parametrosMomentoPrevisao.length == 3) {
            confirmacao = true;
        } else if (periodosComoParametro == 3 && parametrosMomentoPrevisao.length == 2) {
            confirmacao = true;
        } else if (periodosComoParametro == 4 && parametrosMomentoPrevisao.length == 1) {
            confirmacao = true;
        } else {
            System.out.println("O Parâmetro \"momentoPrevisao\" não foi inserido corretamente!");
        }
        return confirmacao;
    }

    public static boolean confirmacaoModelo(int tipoMediaParametro) {
        boolean confirmacao = false;
        if (tipoMediaParametro == 1 || tipoMediaParametro == 2) {
            confirmacao = true;
        } else {
            System.out.println("O parametro \"modelo\" foi incorretamente inserido");
        }
        return confirmacao;
    }

    public static boolean confirmacaoTipoOrdenacao(int tipoOrdenacaoParametro) {
        boolean confirmacao = false;
        if (tipoOrdenacaoParametro == 1 || tipoOrdenacaoParametro == 2) {
            confirmacao = true;
        } else {
            System.out.println("O parametro \"tipodeOrdenacao\" foi inserido de forma incorreta!");
        }
        return confirmacao;
    }

    public static boolean confirmacaoConstante(double constante) {
        boolean confirmacao = false;
        if (constante >= 0 && constante <= 1) {
            confirmacao = true;
        } else {
            System.out.println("O parametro \"parModelo\" foi inserido de forma incorreta!");
        }
        return confirmacao;
    }

    public static boolean confirmacaoFuncionar(boolean confirmarMomentoPrevisao, boolean confirmarModelo, boolean confirmarPeriodos, boolean confirmarTipoOrdenacao, boolean confirmarConstante) {
        boolean confirmacao = false;
        if ((confirmarMomentoPrevisao == true) && (confirmarModelo == true) && (confirmarPeriodos == true) && (confirmarTipoOrdenacao == true) && (confirmarConstante == true)) {
            confirmacao = true;
        }
        return confirmacao;

    }

    public static int leituraDoFicheiro(int[][] data, int[][] hora, int[] consumo, int contador, String[] args) throws FileNotFoundException {
        Scanner ler = new Scanner(new File(args[0]));
        int i = 0;
        String linha = ler.nextLine();
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            String[] itens = linha.split(" ");
            linha = itens[0];
            String[] informacoes = linha.split("-");
            data[i][0] = Integer.parseInt(informacoes[0]);
            data[i][1] = Integer.parseInt(informacoes[1]);
            data[i][2] = Integer.parseInt(informacoes[2]);
            linha = itens[1];
            String[] informacoes2 = linha.split(",");
            consumo[i] = Integer.parseInt(informacoes2[1]);
            linha = informacoes2[0];
            String[] informacoes3 = linha.split(":");
            hora[i][0] = Integer.parseInt(informacoes3[0]);
            hora[i][1] = Integer.parseInt(informacoes3[1]);
            hora[i][2] = Integer.parseInt(informacoes3[2]);
            i++;
        }
        ler.close();
        System.out.println("O ficheiro foi lido com sucesso!");
        return i;
    }

    public static int[] mergeCrescente(int[] consumo, int inicio, int meio, int fim) {
        int[] temp = new int[fim - inicio + 1];
        int i = inicio, j = meio + 1, k = 0;
        while (i <= meio && j <= fim) {
            if (consumo[i] <= consumo[j]) {
                temp[k] = consumo[i];
                k += 1;
                i += 1;
            } else {
                temp[k] = consumo[j];
                k += 1;
                j += 1;
            }
        }
        while (i <= meio) {
            temp[k] = consumo[i];
            k += 1;
            i += 1;
        }
        while (j <= fim) {
            temp[k] = consumo[j];
            k += 1;
            j += 1;
        }
        for (i = inicio; i <= fim; i += 1) {
            consumo[i] = temp[i - inicio];
        }
        return consumo;
    }

    public static int[] mergeDecrescente(int[] consumo, int inicio, int meio, int fim) {
        int[] temp = new int[fim - inicio + 1];
        int i = inicio, j = meio + 1, k = 0;
        while (i <= meio && j <= fim) {
            if (consumo[i] >= consumo[j]) {
                temp[k] = consumo[i];
                k += 1;
                i += 1;
            } else {
                temp[k] = consumo[j];
                k += 1;
                j += 1;
            }
        }
        while (i <= meio) {
            temp[k] = consumo[i];
            k += 1;
            i += 1;
        }
        while (j <= fim) {
            temp[k] = consumo[j];
            k += 1;
            j += 1;
        }
        for (i = inicio; i <= fim; i += 1) {
            consumo[i] = temp[i - inicio];
        }
        return consumo;
    }

    //*************************************************************************************************************************************************************************
    public static void ordenacao1(int[] consumo, int inicio, int fim, boolean interativo, boolean naoInterativo, String tipoOrdenacao) {
        String resposta = null;
        Scanner in = new Scanner(System.in);
        if (interativo == true) {
            boolean aceite = false;
            while (aceite == false) {
                System.out.println("Insira o tipo de ordenação que pretende. (crescente, decrescente)");
                resposta = in.nextLine();

                if (resposta.trim().compareTo("crescente") == 0 || resposta.trim().compareTo("decrescente") == 0) {
                    aceite = true;
                } else {
                    System.out.println("Tipo de ordenação inválido!");
                }
            }
            if ((resposta.trim()).equalsIgnoreCase("crescente")) {
                mergeSortCrescente(consumo, inicio, fim);
            } else {
                if ((resposta.trim()).equalsIgnoreCase("decrescente")) {
                    mergeSortDecrescente(consumo, inicio, fim);
                }
            }
        } else if (naoInterativo == true) {
            resposta = tipoOrdenacao;
            if ((resposta.trim()).equalsIgnoreCase("crescente")) {
                mergeSortCrescente(consumo, inicio, fim);
            } else {
                if ((resposta.trim()).equalsIgnoreCase("decrescente")) {
                    mergeSortDecrescente(consumo, inicio, fim);
                }
            }

        }

    }

    public static void mergeSortCrescente(int[] consumo, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSortCrescente(consumo, inicio, meio);
            mergeSortCrescente(consumo, meio + 1, fim);
            mergeCrescente(consumo, inicio, meio, fim);
        }
    }

    public static void mergeSortDecrescente(int[] consumo, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSortDecrescente(consumo, inicio, meio);
            mergeSortDecrescente(consumo, meio + 1, fim);
            mergeDecrescente(consumo, inicio, meio, fim);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String Mediar(int tipoMediaParametro) {
        String tipo = null;
        if (tipoMediaParametro == 1) {
            tipo = "MediaSimples";
        } else if (tipoMediaParametro == 2) {
            tipo = "MediaPesada";
        }
        return tipo;
    }

    public static String Periodar(int periodosComoParametro) {
        String periodo = null;
        if (periodosComoParametro == 11) {
            periodo = "manha";
        } else if (periodosComoParametro == 12) {
            periodo = "tarde";
        } else if (periodosComoParametro == 13) {
            periodo = "noite";
        } else if (periodosComoParametro == 14) {
            periodo = "madrugada";
        } else if (periodosComoParametro == 2) {
            periodo = "diario";
        } else if (periodosComoParametro == 3) {
            periodo = "mensal";
        } else if (periodosComoParametro == 4) {
            periodo = "anual";
        }
        return periodo;

    }

    public static String ordenacao(int tipoOrdenacaoParametro) {
        String tipo = null;
        if (tipoOrdenacaoParametro == 1) {
            tipo = "crescente";

        } else if (tipoOrdenacaoParametro == 2) {
            tipo = "decrescente";
        }
        return tipo;
    }

    public static void analiseTemporal(String tipoPeriodo, String[] itensPrevisao, int contador, boolean interativo, boolean naoInterativo, int[] consumo, int[][] hora) throws FileNotFoundException {
        Scanner ler = new Scanner(System.in);
        if (interativo == true) {
            boolean aceite = false;
            while (aceite == false) {
                System.out.println("Em que resolução deseja analisar a série Temporal? )");
                System.out.println("Comandos: (Manhã ou Tarde ou Noite ou Madrugada ou diário ou mensal ou anual");
                tipoPeriodo = ler.nextLine();
                aceite = confirmarParametroTipoPeriodo(tipoPeriodo);

            }
            fazerGrafico(tipoPeriodo, contador, consumo, hora);

        } else if (naoInterativo == true) {
            fazerGrafico(tipoPeriodo, contador, consumo, hora);

        }

    }

    public static boolean confirmarParametroTipoPeriodo(String tipoPeriodo) {
        boolean confirmar = false;
        if (tipoPeriodo.trim().compareToIgnoreCase("manha") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("manhã") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("tarde") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("noite") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("madrugada") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("diario") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("diário") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("mensal") == 0) {
            confirmar = true;
        } else if (tipoPeriodo.trim().compareToIgnoreCase("anual") == 0) {
            confirmar = true;
        } else {
            System.out.println("O parametro foi Inserido com Erro!");
        }
        return confirmar;

    }

    public static boolean confirmarParametroTipoMedia(String tipoMedia) {
        boolean confirmar = false;
        if (tipoMedia.compareToIgnoreCase("Móvel Simples") == 0) {
            confirmar = true;
        } else if (tipoMedia.compareToIgnoreCase("Movel Simples") == 0) {
            confirmar = true;
        } else if (tipoMedia.compareToIgnoreCase("Móvel Exponencial Pesada") == 0) {
            confirmar = true;
        } else if (tipoMedia.compareToIgnoreCase("Movel Exponencial Pesada") == 0) {
            confirmar = true;
        } else {
            System.out.println("O parametro foi inserido com Erro!");
        }
        return confirmar;
    }

    public static boolean confirmarParametroTipoOrdenacao(String tipoOrdenacao) {
        boolean confirmar = true;
        if (tipoOrdenacao.trim().compareToIgnoreCase("crescente") == 0) {
            confirmar = true;
        } else if (tipoOrdenacao.trim().compareToIgnoreCase("decrescente") == 0) {
            confirmar = true;
        } else {
            System.out.println("O parametro foi inserido com Erro!");
        }
        return confirmar;
    }

    public static void fazerGrafico(String tipoPeriodo, int contador, int[] consumo, int[][] hora) throws FileNotFoundException {
        Scanner ler = new Scanner(System.in);
        JavaPlot p = new JavaPlot();
        tipoPeriodo = tipoPeriodo.toLowerCase();
        switch (tipoPeriodo) {

            case "manha": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {
                    if (hora[i][0] >= 6 && hora[i][0] < 12 && hora[i][0] != 0) {
                        consumonovo[i] = consumo[i];
                        contador1++;
                    }
                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle(" Consumo de manhãs");
                s.setPlotStyle(myPlotStyle);

                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();

                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisManha.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }

                break;

            }

            case "manhã": {

                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {
                    if (hora[i][0] >= 6 && hora[i][0] < 12 && hora[i][0] != 0) {
                        consumonovo[i] = consumo[i];
                        contador1++;
                    }
                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo de manhãs");
                s.setPlotStyle(myPlotStyle);

                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisManha.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }
                break;
            }

            case "tarde": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {
                    if (hora[i][0] >= 12 && hora[i][0] < 18 && hora[i][0] != 0) {
                        consumonovo[i] = consumo[i];
                        contador1++;
                    }
                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo tarde");
                s.setPlotStyle(myPlotStyle);

                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisTarde.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + "," + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }
                break;
            }

            case "noite": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {
                    if (hora[i][0] >= 18 && hora[i][0] != 0) {
                        consumonovo[i] = consumo[i];
                        contador1++;
                    }
                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo noite");
                s.setPlotStyle(myPlotStyle);
                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();

                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisNoite.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }
                break;
            }

            case "madrugada": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {
                    if (hora[i][0] >= 0 && hora[i][0] < 6 && hora[i][0] != 0) {
                        consumonovo[i] = consumo[i];
                        contador1++;
                    }
                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                
                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo madrugada");
                s.setPlotStyle(myPlotStyle);
                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisMadrugada.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }
                break;
            }

            case "diario": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {

                    consumonovo[i] = consumo[i];
                    contador1++;

                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo diario");
                s.setPlotStyle(myPlotStyle);
                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisDiario.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }
                break;
            }

            case "diário": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {

                    consumonovo[i] = consumo[i];
                    contador1++;

                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo diário");
                s.setPlotStyle(myPlotStyle);

                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisDiario.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }
                break;
            }

            case "mensal": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {

                    consumonovo[i] = consumo[i];
                    contador1++;

                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo mensal");
                s.setPlotStyle(myPlotStyle);

                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();
                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisMensal.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }
                break;
            }

            case "anual": {
                int[] consumonovo = new int[MAXIMO_DIAS];
                int contador1 = 0;
                for (int i = 0; i < contador; i++) {

                    consumonovo[i] = consumo[i];
                    contador1++;

                }
                double tab[][] = new double[consumonovo.length][10];
                for (int i = 0; i < consumonovo.length; i++) {
                    tab[i][0] = i;
                    tab[i][1] = consumonovo[i];

                }
                PlotStyle myPlotStyle = new PlotStyle();
                myPlotStyle.setStyle(Style.LINESPOINTS);
                myPlotStyle.setLineWidth(1);

                myPlotStyle.setLineType(NamedPlotColor.BLUE);
                myPlotStyle.setPointType(7);
                myPlotStyle.setPointSize(1);
                DataSetPlot s = new DataSetPlot(tab);
                s.setTitle("Consumo ano");
                s.setPlotStyle(myPlotStyle);

                p.addPlot(s);
                p.newGraph();
                p.plot();
                System.out.println("Pretende guardar o grafico?");
                String resposta = ler.nextLine();
                while (resposta.trim().compareToIgnoreCase("não") != 0 && resposta.trim().compareToIgnoreCase("sim") != 0 && resposta.trim().compareToIgnoreCase("nao") != 0) {
                    System.out.println("Resposta Invalida!");
                    System.out.println("Pretende guardar o grafico?");
                    resposta = ler.nextLine();
                }
                if (resposta.trim().compareToIgnoreCase("sim") == 0) {
                    System.out.println("Pretende guardar em que formato?");
                    String respostaFormato = ler.nextLine();
                    while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                        System.out.println("O formato que inseriu não existe!");
                        System.out.println("Pretende guardar em que formato?");
                        respostaFormato = ler.nextLine();

                    }
                    if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                        PrintWriter escrever = new PrintWriter(new File("AnalisesTemporaisAnual.csv"));
                        for (int i = 0; i < tab.length; i++) {
                            escrever.println(tab[i][0] + " , " + tab[i][1]);
                        }
                        escrever.close();
                        System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
                    } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                        GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\analiseTemporalManha.png");
                        p.setTerminal(ficheiro);
                        System.out.println("O ficheiro foi guardado com sucesso!");

                    }

                }

                break;

            }

        }

    }

    public static void mediaSimples(int[] consumo, int contador, int referencianum) {
        double consumoaux, resultado, resultadofinal = 0;
        double[] previsao = new double[referencianum + contador];
        for (int i = 0; i < (contador + referencianum); i++) {
            consumoaux = consumo[i];
            resultado = ((1 / contador) * consumoaux);
            resultadofinal = resultadofinal + resultado;
            previsao[i] = resultadofinal;
        }
    }

    public static void mediaSimples4(int[] consumo, int contador, double[] filtragemMediaSimples) throws FileNotFoundException {

        Scanner ler = new Scanner(System.in);
        JavaPlot p = new JavaPlot();
        double consumoaux, resultado, resultadofinal = 0;
        for (int i = 0; i < (contador); i++) {
            consumoaux = consumo[i];
            resultado = ((1 / contador) * consumoaux);
            resultadofinal = resultadofinal + resultado;
            filtragemMediaSimples[i] = resultadofinal;
        }
        double tab[][] = new double[3340][contador];
        for (int i = 0; i < filtragemMediaSimples.length; i++) {
            tab[i][0] = i;
            tab[i][1] = filtragemMediaSimples[i];

        }
        PlotStyle myPlotStyle = new PlotStyle();
        myPlotStyle.setStyle(Style.LINESPOINTS);
        myPlotStyle.setLineWidth(1);

        myPlotStyle.setLineType(NamedPlotColor.BLUE);
        myPlotStyle.setPointType(7);
        myPlotStyle.setPointSize(1);
        DataSetPlot s = new DataSetPlot(tab);
        s.setTitle("Media Simples");
        s.setPlotStyle(myPlotStyle);

        p.addPlot(s);
        p.newGraph();
        p.plot();
        String guardar;
        System.out.println("Pretende guardar o grafico?");
        guardar = ler.nextLine();
        while (guardar.trim().compareToIgnoreCase("sim") != 0 && guardar.trim().compareToIgnoreCase("nao") != 0 && guardar.trim().compareToIgnoreCase("não") != 0) {
            System.out.println("Resposta Invalida!");
            System.out.println("Pretende guardar o grafico?");
            guardar = ler.nextLine();
        }
        if (guardar.trim().compareToIgnoreCase("sim") == 0) {
            System.out.println("Pretende guardar em que formato?");
            String respostaFormato = ler.nextLine();
            while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                System.out.println("O formato que inseriu não existe!");
                System.out.println("Pretende guardar em que formato?");
                respostaFormato = ler.nextLine();

            }
            if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                PrintWriter escrever = new PrintWriter(new File("MediaSimples.csv"));
                for (int i = 0; i < tab.length; i++) {
                    escrever.println(tab[i][0] + " , " + tab[i][1]);
                }
                escrever.close();
                System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
            } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\mediaSimples.png");
                p.setTerminal(ficheiro);
                System.out.println("O ficheiro foi guardado com sucesso!");

            }

        }

    }

    public static void mediaExpPesada(int[] consumo, int contador, int referencianum, double alpha, double[] filtragemPesada) throws FileNotFoundException {
        Scanner ler = new Scanner(System.in);
        JavaPlot p = new JavaPlot();

        double consumoaux, resultado, resultadofinal = 0, consumo1;
        consumo1 = consumo[0];
        for (int i = 0; i < (contador); i++) {
            consumoaux = consumo[i];
            if (i == 0) {
                filtragemPesada[0] = consumo1;
            } else {
                consumo1 = filtragemPesada[i - 1];
                resultado = (alpha * consumoaux + (1 - alpha) * consumo1);
                resultadofinal = resultadofinal + resultado;
                filtragemPesada[i] = resultadofinal;
            }
        }
        double tab[][] = new double[3340][contador];

        for (int i = 0; i < filtragemPesada.length; i++) {
            tab[i][0] = i;
            tab[i][1] = filtragemPesada[i];

        }
        PlotStyle myPlotStyle = new PlotStyle();
        myPlotStyle.setStyle(Style.LINESPOINTS);
        myPlotStyle.setLineWidth(1);

        myPlotStyle.setLineType(NamedPlotColor.BLUE);
        myPlotStyle.setPointType(7);
        myPlotStyle.setPointSize(1);
        DataSetPlot s = new DataSetPlot(tab);
        s.setTitle("Media Simples");
        s.setPlotStyle(myPlotStyle);

        p.addPlot(s);
        p.newGraph();
        p.plot();
        String guardar;
        System.out.println("Pretende guardar o grafico?");
        guardar = ler.nextLine();
        while (guardar.trim().compareToIgnoreCase("sim") != 0 && guardar.trim().compareToIgnoreCase("nao") != 0 && guardar.trim().compareToIgnoreCase("não") != 0) {
            System.out.println("Resposta Invalida!");
            System.out.println("Pretende guardar o grafico?");
            guardar = ler.nextLine();
        }
        if (guardar.trim().compareToIgnoreCase("sim") == 0) {
            System.out.println("Pretende guardar em que formato?");
            String respostaFormato = ler.nextLine();
            while ((respostaFormato.trim().compareToIgnoreCase("csv") != 0) && (respostaFormato.trim().compareToIgnoreCase("png") != 0)) {
                System.out.println("O formato que inseriu não existe!");
                System.out.println("Pretende guardar em que formato?");
                respostaFormato = ler.nextLine();

            }
            if (respostaFormato.trim().compareToIgnoreCase("csv") == 0) {
                PrintWriter escrever = new PrintWriter(new File("MediaPesada.csv"));
                for (int i = 0; i < tab.length; i++) {
                    escrever.println(tab[i][0] + " , " + tab[i][1]);
                }
                escrever.close();
                System.out.println("O Ficheiro com as Analises Temporais foi Criado com sucesso!");
            } else if (respostaFormato.trim().compareToIgnoreCase("png") == 0) {
                GNUPlotTerminal ficheiro = new FileTerminal("png", "C:\\Users\\diogo\\OneDrive\\Documentos\\NetBeansProjects\\painguainzProjetoFinal\\dist\\MediaPesada.png");
                p.setTerminal(ficheiro);
                System.out.println("O ficheiro foi guardado com sucesso!");

            }

        }
    }

    private static void erroAbsoluto(int contador, int[] consumo, boolean interativo, boolean naoInterativo, double[] filtragemMediaSimples) {
        double[] erroabs = new double[contador];
        double previsaoaux = 0, erroabsoluto = 0;
        int consumoaux = 0;
        for (int i = 0; i < (contador); i++) {
            filtragemMediaSimples[i] = previsaoaux;
            consumo[i] = consumoaux;
            erroabsoluto = ((previsaoaux - consumoaux) / (contador));
            erroabs[i] = erroabsoluto;
        }
    }

    public static void numeroObservacoes(int contador, int[] consumo) {
        int soma = 0, observacoes1 = 0, observacoes2 = 0, observacoes3 = 0;
        for (int i = 0; i < contador; i++) {
            soma = soma + consumo[i];
        }
        double media = soma / contador;
        for (int j = 0; j < contador; j++) {
            if (consumo[j] < (media - (0.2 * media))) {
                observacoes1++;
            } else {
                if (consumo[j] >= (media - (0.2 * media)) && consumo[j] < (media + (0.2 * media))) {
                    observacoes2++;
                } else {
                    observacoes3++;
                }
            }
        }
        System.out.println("O número de observações realizadas no 1º intervalo foi: " + observacoes1 + ".");
        System.out.println("O número de observações realizadas no 2º intervalo foi: " + observacoes2 + ".");
        System.out.println("O número de observações realizadas no 3º intervalo foi: " + observacoes3 + ".");
    }

    private static void calculoAuxMediaSimples(String referencia, int contador, int[] consumo, int tipoMediaParametro, double alpha, double[] filtragemPesada, double[] filtragemMediaSimples, int referencianum) {
        referencianum = 0;
        if (referencia.equals("ano")) {
            referencianum = 8760;
        } else {
            if (referencia.equals("mês")) {
                referencianum = 720;
                if (tipoMediaParametro == 1) {
                    mediaSimples1(consumo, contador, referencianum);
                } else {
                    if (tipoMediaParametro == 2) {
                        mediaExpPesada1(consumo, contador, referencianum, alpha);
                    }
                }
            } else {
                if (referencia.equals("dia")) {
                    referencianum = 24;
                    if (tipoMediaParametro == 1) {
                        mediaSimples1(consumo, contador, referencianum);
                    } else {
                        if (tipoMediaParametro == 2) {
                            mediaExpPesada1(consumo, contador, referencianum, alpha);
                        }
                    }
                }
            }
        }
    }

    private static void calcularTempo(String[] itensPrevisao, int contador, int[][] data, int[] consumo, int tipoMediaParametro, double alpha, double[] filtragemPesada, double[] filtragemMediaSimples, int referencianum) {
        String referencia = null;
        if (itensPrevisao.length == 1) {
            if (contador >= 8760) {
                referencia = "ano";
                calculoAuxMediaSimples(referencia, contador, consumo, tipoMediaParametro, alpha, filtragemPesada, filtragemMediaSimples, referencianum);
            }
        } else if (itensPrevisao.length == 2) {
            //Verifica se tem pelo menos 28 dias
            if (contador > 672) {
                //Verifica se o ficheiro tem um mes de 30 ou 31 dias ou um mes de fevereiro com 28 ou« 29 dias
                if ((data[720][2] == 30) || (data[744][2] == 31) || ((data[672][2] == 28) && (data[672][1] == 2)) || ((data[696][2] == 29) && (data[696][1] == 2))) {
                    referencia = "mês";
                    calculoAuxMediaSimples(referencia, contador, consumo, tipoMediaParametro, alpha, filtragemPesada, filtragemMediaSimples, referencianum);
                }
            }
        } else if (itensPrevisao.length == 3) {
            if (contador > 24) {
                referencia = "dia";
                calculoAuxMediaSimples(referencia, contador, consumo, tipoMediaParametro, alpha, filtragemPesada, filtragemMediaSimples, referencianum);
            }
        }
    }

    private static void mediaSimples1(int[] consumo, int contador, int referencianum) {
        double consumoaux, resultado, resultadofinal = 0;
        double[] previsao = new double[referencianum + contador];
        for (int i = 0; i < (contador + referencianum); i++) {
            consumoaux = consumo[i];
            resultado = ((1 / contador) * consumoaux);
            resultadofinal = resultadofinal + resultado;
            previsao[i] = resultadofinal;
        }
    }
    //************************************************************************************************************************************************************************************************

    private static void mediaExpPesada1(int[] consumo, int contador, int referencianum, double alpha) {
        double[] previsao = new double[referencianum + contador];
        double consumoaux, resultado, resultadofinal = 0, consumo1;
        consumo1 = consumo[0];
        for (int i = 0; i < (contador + referencianum); i++) {
            consumoaux = consumo[i];
            if (i == 0) {
                previsao[0] = consumo1;
            } else {
                consumo1 = previsao[i - 1];
                resultado = (alpha * consumoaux + (1 - alpha) * consumo1);
                resultadofinal = resultadofinal + resultado;
                previsao[i] = resultadofinal;
            }
        }
    }

}
