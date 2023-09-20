
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PainguainzprojetofinalSuite {

    @Test
    public void testconfirmarInterativoV() {
        System.out.println("confirmarInterativoV");
        int contadorParametros = 1; // confirmar se sao estes os parametros a receber
        boolean serInterativo = painguainzProjetoFinal.confirmarInterativo(contadorParametros);
        assertTrue(serInterativo);
    }

    @Test
    public void testconfirmarInterativoF() {
        System.out.println("confirmarInterativoF");
        int contadorParametros = 5; //por exemplo
        boolean serInterativo = painguainzProjetoFinal.confirmarInterativo(contadorParametros);
        assertFalse(serInterativo);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testconfirmarNaoInterativoV() {
        System.out.println("confirmarNaoInterativoV");
        int contadorParametros = 6; // confirmar se sao estes os parametros a receber
        boolean confirmarNaoInterativo = painguainzProjetoFinal.confirmarNaoInterativo(contadorParametros);
        assertTrue(confirmarNaoInterativo);
    }

    @Test
    public void testconfirmarNaoInterativoF() {
        System.out.println("confirmarNaoInterativoF");
        int contadorParametros = 1; //por exemplo
        boolean confirmarNaoInterativo = painguainzProjetoFinal.confirmarNaoInterativo(contadorParametros);
        assertFalse(confirmarNaoInterativo);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testcontarParametrosI() {
        System.out.println("contarParametrosI");
        String[] args = new String[1];
        int contadorParametros = painguainzProjetoFinal.contarParametros(args);
        assertEquals(1, contadorParametros);
    }

    @Test
    public void testcontarParametrosNI() {
        System.out.println("contarParametrosNI");
        String[] args = new String[6];
        int contadorParametros = painguainzProjetoFinal.contarParametros(args);
        assertEquals(6, contadorParametros);
    }

    @Test
    public void testcontarParametrosInsufP() {
        System.out.println("contarParametrosInsufP");
        String[] args = new String[5]; //Por exemplo
        int contadorParametros = painguainzProjetoFinal.contarParametros(args);
        assertEquals(5, contadorParametros);
    }

    @Test
    public void testcontarParametrosNull() {
        System.out.println("contarParametrosNull");
        String[] args = null;
        int contadorParametros = painguainzProjetoFinal.contarParametros(args);
        assertEquals(0, contadorParametros);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    @Test
    public void confirmarParametroPeriodoV() {
        System.out.println("confirmarParametroPeriodoV");
        int periodosComoParametro = 11;
        boolean confirmacaoParametroPeriodo = painguainzProjetoFinal.confirmarParametroPeriodo(periodosComoParametro);
        assertTrue(confirmacaoParametroPeriodo);
    }

    @Test
    public void confirmarParametroPeriodoF() {
        System.out.println("confirmarParametroPeriodoF");
        int periodosComoParametro = 10; //Por exemplo
        boolean confirmacaoParametroPeriodo = painguainzProjetoFinal.confirmarParametroPeriodo(periodosComoParametro);
        assertFalse(confirmacaoParametroPeriodo);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void confirmacaoMomentoPrevisaoV1() {
        System.out.println("confirmacaoMomentoPrevisaoV1");
        String[] parametrosMomentoPrevisao = new String[3];
        int periodosComoParametro = 14; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoMomentoPrevisao(parametrosMomentoPrevisao, periodosComoParametro);
        assertTrue(confirmacao);
    }

    @Test
    public void confirmacaoMomentoPrevisaoV2() {
        System.out.println("confirmacaoMomentoPrevisaoV2");
        String[] parametrosMomentoPrevisao = new String[2];
        int periodosComoParametro = 3;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoMomentoPrevisao(parametrosMomentoPrevisao, periodosComoParametro);
        assertTrue(confirmacao);
    }

    @Test
    public void confirmacaoMomentoPrevisaoV3() {
        System.out.println("confirmacaoMomentoPrevisaoV3");
        String[] parametrosMomentoPrevisao = new String[1];
        int periodosComoParametro = 4;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoMomentoPrevisao(parametrosMomentoPrevisao, periodosComoParametro);
        assertTrue(confirmacao);
    }

    @Test
    public void confirmacaoMomentoPrevisaoF1() {
        System.out.println("confirmacaoMomentoPrevisaoF1");
        String[] parametrosMomentoPrevisao = new String[2]; //Por exemplo
        int periodosComoParametro = 11; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoMomentoPrevisao(parametrosMomentoPrevisao, periodosComoParametro);
        assertFalse(confirmacao);
    }

    @Test
    public void confirmacaoMomentoPrevisaoF2() {
        System.out.println("confirmacaoMomentoPrevisaoF2");
        String[] parametrosMomentoPrevisao = new String[3]; //Por exemplo
        int periodosComoParametro = 20; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoMomentoPrevisao(parametrosMomentoPrevisao, periodosComoParametro);
        assertFalse(confirmacao);
    }

    @Test
    public void confirmacaoMomentoPrevisaoNull() {
        System.out.println("confirmacaoMomentoPrevisaoNull");
        String[] parametrosMomentoPrevisao = null;
        int periodosComoParametro = 20; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoMomentoPrevisao(parametrosMomentoPrevisao, periodosComoParametro);
        assertFalse(confirmacao);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void confirmacaoModeloV() {
        System.out.println("confirmacaoModeloV");
        int tipoMediaParametro = 1; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoModelo(tipoMediaParametro);
        assertTrue(confirmacao);
    }

    @Test
    public void confirmacaoModeloF() {
        System.out.println("confirmacaoModeloF");
        int tipoMediaParametro = 3; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoModelo(tipoMediaParametro);
        assertFalse(confirmacao);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    @Test
    public void confirmacaoTipoOrdenacaoV() {
        System.out.println("confirmacaoTipoOrdenacaoV");
        int tipoOrdenacaoParametro = 1; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoTipoOrdenacao(tipoOrdenacaoParametro);
        assertTrue(confirmacao);
    }

    @Test
    public void confirmacaoTipoOrdenacaoF() {
        System.out.println("confirmacaoTipoOrdenacaoF");
        int tipoOrdenacaoParametro = 3; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoTipoOrdenacao(tipoOrdenacaoParametro);
        assertFalse(confirmacao);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    @Test
    public void confirmacaoConstanteV() {
        System.out.println("confirmacaoConstanteV");
        double constante = 0.5; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoConstante(constante);
        assertTrue(confirmacao);
    }

    @Test
    public void confirmacaoConstanteF() {
        System.out.println("confirmacaoConstanteF");
        double constante = 2; //Por exemplo
        boolean confirmacao = painguainzProjetoFinal.confirmacaoConstante(constante);
        assertFalse(confirmacao);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void confirmacaoFuncionarV() {
        System.out.println("confirmacaoFuncionarV");
        boolean confirmarMomentoPrevisao = true;
        boolean confirmarModelo = true;
        boolean confirmarPeriodos = true;
        boolean confirmarTipoOrdenacao = true;
        boolean confirmarConstante = true;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
        assertTrue(confirmacao);
    }

    @Test
    public void confirmacaoFuncionarF1() {
        System.out.println("confirmacaoFuncionarF1");
        boolean confirmarMomentoPrevisao = false;
        boolean confirmarModelo = true;
        boolean confirmarPeriodos = true;
        boolean confirmarTipoOrdenacao = true;
        boolean confirmarConstante = true;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
        assertFalse(confirmacao);
    }

    @Test
    public void confirmacaoFuncionarF2() {
        System.out.println("confirmacaoFuncionarF2");
        boolean confirmarMomentoPrevisao = true;
        boolean confirmarModelo = false;
        boolean confirmarPeriodos = true;
        boolean confirmarTipoOrdenacao = true;
        boolean confirmarConstante = true;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
        assertFalse(confirmacao);
    }

    @Test
    public void confirmacaoFuncionarF3() {
        System.out.println("confirmacaoFuncionarF3");
        boolean confirmarMomentoPrevisao = true;
        boolean confirmarModelo = true;
        boolean confirmarPeriodos = false;
        boolean confirmarTipoOrdenacao = true;
        boolean confirmarConstante = true;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
        assertFalse(confirmacao);
    }

    @Test
    public void confirmacaoFuncionarF4() {
        System.out.println("confirmacaoFuncionarF4");
        boolean confirmarMomentoPrevisao = true;
        boolean confirmarModelo = true;
        boolean confirmarPeriodos = true;
        boolean confirmarTipoOrdenacao = false;
        boolean confirmarConstante = true;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
        assertFalse(confirmacao);
    }

    @Test
    public void confirmacaoFuncionarF5() {
        System.out.println("confirmacaoFuncionarF5");
        boolean confirmarMomentoPrevisao = true;
        boolean confirmarModelo = true;
        boolean confirmarPeriodos = true;
        boolean confirmarTipoOrdenacao = true;
        boolean confirmarConstante = false;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
        assertFalse(confirmacao);
    }

    @Test
    public void confirmacaoFuncionarF6() {
        System.out.println("confirmacaoFuncionarF6");
        boolean confirmarMomentoPrevisao = false;
        boolean confirmarModelo = false;
        boolean confirmarPeriodos = false;
        boolean confirmarTipoOrdenacao = false;
        boolean confirmarConstante = false;
        boolean confirmacao = painguainzProjetoFinal.confirmacaoFuncionar(confirmarMomentoPrevisao, confirmarModelo, confirmarPeriodos, confirmarTipoOrdenacao, confirmarConstante);
        assertFalse(confirmacao);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void Mediar1() {
        System.out.println("Mediar1");
        int tipoMediaParametro = 1;
        String tipo = painguainzProjetoFinal.Mediar(tipoMediaParametro);
        assertEquals("MediaSimples", tipo);
    }

    @Test
    public void Mediar2() {
        System.out.println("Mediar2");
        int tipoMediaParametro = 2;
        String tipo = painguainzProjetoFinal.Mediar(tipoMediaParametro);
        assertEquals("MediaPesada", tipo);
    }

    @Test
    public void Mediar3() {
        System.out.println("Mediar3");
        int tipoMediaParametro = 3; //Por exemplo
        String tipo = painguainzProjetoFinal.Mediar(tipoMediaParametro);
        assertEquals(null, tipo);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  

    @Test
    public void Periodar1() {
        System.out.println("Periodar1");
        int periodosComoParametro = 11;
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals("manha", periodo);
    }

    @Test
    public void Periodar2() {
        System.out.println("Periodar2");
        int periodosComoParametro = 12;
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals("tarde", periodo);
    }

    @Test
    public void Periodar3() {
        System.out.println("Periodar3");
        int periodosComoParametro = 13;
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals("noite", periodo);
    }

    @Test
    public void Periodar4() {
        System.out.println("Periodar4");
        int periodosComoParametro = 14;
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals("madrugada", periodo);
    }

    @Test
    public void Periodar5() {
        System.out.println("Periodar5");
        int periodosComoParametro = 2;
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals("diario", periodo);
    }

    @Test
    public void Periodar6() {
        System.out.println("Periodar6");
        int periodosComoParametro = 3;
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals("mensal", periodo);
    }

    @Test
    public void Periodar7() {
        System.out.println("Periodar7");
        int periodosComoParametro = 4;
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals("anual", periodo);
    }

    @Test
    public void PeriodarNull() {
        System.out.println("PeriodarNull");
        int periodosComoParametro = 20; //Por exemplo
        String periodo = painguainzProjetoFinal.Periodar(periodosComoParametro);
        assertEquals(null, periodo);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void ordenacao1() {
        System.out.println("ordenacao1");
        int tipoOrdenacaoParametro = 4; //Por exemplo 
        String tipo = painguainzProjetoFinal.ordenacao(tipoOrdenacaoParametro);
        assertEquals(null, tipo);
    }

    @Test
    public void ordenacao2() {
        System.out.println("ordenacao2");
        int tipoOrdenacaoParametro = 1;
        String tipo = painguainzProjetoFinal.ordenacao(tipoOrdenacaoParametro);
        assertEquals("crescente", tipo);
    }

    @Test
    public void ordenacao3() {
        System.out.println("ordenacao3");
        int tipoOrdenacaoParametro = 2;
        String tipo = painguainzProjetoFinal.ordenacao(tipoOrdenacaoParametro);
        assertEquals("decrescente", tipo);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void confirmarParametroTipoPeriodoV1() {
        System.out.println("confirmarParametroTipoPeriodoV1");
        String tipoPeriodo = "manha";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV2() {
        System.out.println("confirmarParametroTipoPeriodoV2");
        String tipoPeriodo = "tarde";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV3() {
        System.out.println("confirmarParametroTipoPeriodoV3");
        String tipoPeriodo = "noite";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV4() {
        System.out.println("confirmarParametroTipoPeriodoV4");
        String tipoPeriodo = "madrugada";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV5() {
        System.out.println("confirmarParametroTipoPeriodoV5");
        String tipoPeriodo = "diario";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV6() {
        System.out.println("confirmarParametroTipoPeriodoV6");
        String tipoPeriodo = "mensal";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV7() {
        System.out.println("confirmarParametroTipoPeriodoV7");
        String tipoPeriodo = "anual";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV8() {
        System.out.println("confirmarParametroTipoPeriodoV8");
        String tipoPeriodo = "manhã";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoV9() {
        System.out.println("confirmarParametroTipoPeriodoV9");
        String tipoPeriodo = "diário";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoPeriodoF1() {
        System.out.println("confirmarParametroTipoPeriodoF1");
        String tipoPeriodo = "random"; //Por exemplo 
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoPeriodo(tipoPeriodo);
        assertFalse(confirmar);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void confirmarParametroTipoMediaV1() {
        System.out.println("confirmarParametroTipoMediaV1");
        String tipoMedia = "Móvel Simples";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoMedia(tipoMedia);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoMediaV2() {
        System.out.println("confirmarParametroTipoMediaV2");
        String tipoMedia = "Movel Simples";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoMedia(tipoMedia);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoMediaV3() {
        System.out.println("confirmarParametroTipoMediaV3");
        String tipoMedia = "Móvel Exponencial Pesada";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoMedia(tipoMedia);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoMediaV4() {
        System.out.println("confirmarParametroTipoMediaV4");
        String tipoMedia = "Movel Exponencial Pesada";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoMedia(tipoMedia);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoMediaF() {
        System.out.println("confirmarParametroTipoMediaF");
        String tipoMedia = "Random"; //Por exemplo 
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoMedia(tipoMedia);
        assertFalse(confirmar);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void confirmarParametroTipoOrdenacaoV1() {
        System.out.println("confirmarParametroTipoOrdenacaoV1");
        String tipoOrdenacao = "crescente";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoOrdenacao(tipoOrdenacao);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoOrdenacaoV2() {
        System.out.println("confirmarParametroTipoOrdenacaoV2");
        String tipoOrdenacao = "decrescente";
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoOrdenacao(tipoOrdenacao);
        assertTrue(confirmar);
    }

    @Test
    public void confirmarParametroTipoOrdenacaoF() {
        System.out.println("confirmarParametroTipoOrdenacaoF");
        String tipoOrdenacao = "random"; //Por exemplo
        boolean confirmar = painguainzProjetoFinal.confirmarParametroTipoOrdenacao(tipoOrdenacao);
        assertFalse(confirmar);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void mergeSortCrescente1() {
        System.out.println("mergeSortCrescente1");
        int[] resultadoExperado = {1, 5, 12, 20};
        int[] consumo = {12, 5, 20, 1};
        int inicio = 0;
        int fim = 3;
        painguainzProjetoFinal.mergeSortCrescente(consumo, inicio, fim);
        assertArrayEquals(resultadoExperado, consumo);
    }

    @Test
    public void mergeSortCrescente2() {
        System.out.println("mergeSortCrescente2");
        int[] resultadoExperado = {1, 4, 5, 14, 16, 17, 20, 50};
        int[] consumo = {14, 16, 4, 5, 50, 1, 17, 20};
        int inicio = 0;
        int fim = 7;
        painguainzProjetoFinal.mergeSortCrescente(consumo, inicio, fim);
        assertArrayEquals(resultadoExperado, consumo);
    }

    @Test
    public void mergeSortCrescenteNull() {
        System.out.println("mergeSortCrescenteNull");
        int[] resultadoExperado = {};
        int[] consumo = {};
        int inicio = 0;
        int fim = 0;
        painguainzProjetoFinal.mergeSortCrescente(consumo, inicio, fim);
        assertArrayEquals(resultadoExperado, consumo);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void mergeSortDecrescente1() {
        System.out.println("mergeSortDecrescente1");
        int[] resultadoExperado = {20, 12, 5, 1};
        int[] consumo = {12, 5, 20, 1};
        int inicio = 0;
        int fim = 3;
        painguainzProjetoFinal.mergeSortDecrescente(consumo, inicio, fim);
        assertArrayEquals(resultadoExperado, consumo);
    }

    @Test
    public void mergeSortDecrescente2() {
        System.out.println("mergeSortDecrescente2");
        int[] resultadoExperado = {50, 20, 17, 16, 14, 5, 4, 1};
        int[] consumo = {14, 16, 4, 5, 50, 1, 17, 20};
        int inicio = 0;
        int fim = 7;
        painguainzProjetoFinal.mergeSortDecrescente(consumo, inicio, fim);
        assertArrayEquals(resultadoExperado, consumo);
    }

    @Test
    public void mergeSortDecrescenteNull() {
        System.out.println("mergeSortDecrescenteNull");
        int[] resultadoExperado = {};
        int[] consumo = {};
        int inicio = 0;
        int fim = 0;
        painguainzProjetoFinal.mergeSortDecrescente(consumo, inicio, fim);
        assertArrayEquals(resultadoExperado, consumo);
    }
}
