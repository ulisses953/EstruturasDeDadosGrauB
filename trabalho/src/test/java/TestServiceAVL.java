import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faculdade.frau.b.service.ServiceAVL;

public class TestServiceAVL {
    private static final Logger logger = LoggerFactory.getLogger(TestServiceAVL.class);



    /**
     * Testa o método insert da classe ServiceAVL
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsert() {
        ServiceAVL<Integer> serviceAVLInteger = new ServiceAVL<>();


        serviceAVLInteger.insert(10, 1);
        serviceAVLInteger.insert(20, 2);
        serviceAVLInteger.insert(30, 3);
        serviceAVLInteger.insert(40, 4);
        serviceAVLInteger.insert(50, 5);
        serviceAVLInteger.insert(25, 6);

        // Verifica se o nó foi inserido corretamente
        assertEquals(30, serviceAVLInteger.getRoot().getKey());
        assertEquals(20, serviceAVLInteger.getRoot().getLeft().getKey());
        assertEquals(40, serviceAVLInteger.getRoot().getRight().getKey());
        assertEquals(10, serviceAVLInteger.getRoot().getLeft().getLeft().getKey());
        assertEquals(25, serviceAVLInteger.getRoot().getLeft().getRight().getKey());
        assertEquals(50, serviceAVLInteger.getRoot().getRight().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores duplicados
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertDuplicate() {
        ServiceAVL<Integer> serviceAVLInteger = new ServiceAVL<>();


        serviceAVLInteger.insert(10, 1);
        serviceAVLInteger.insert(20, 2);
        serviceAVLInteger.insert(10, 3); // Tentativa de inserir um valor duplicado

        // Verifica se o nó foi inserido corretamente
        assertEquals(10, serviceAVLInteger.getRoot().getKey());
        assertEquals(20, serviceAVLInteger.getRoot().getRight().getKey());
    }

    /**
     * Testa o método insert da classe ServiceAVL com valores negativos
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertNegative() {
        ServiceAVL<Integer> serviceAVLInteger = new ServiceAVL<>();


        serviceAVLInteger.insert(-10, 1);
        serviceAVLInteger.insert(-20, 2);
        serviceAVLInteger.insert(-30, 3);

        // Verifica se o nó foi inserido corretamente
        assertEquals(-20, serviceAVLInteger.getRoot().getKey());
        assertEquals(-30, serviceAVLInteger.getRoot().getLeft().getKey());
        assertEquals(-10, serviceAVLInteger.getRoot().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores nulos
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertNull() {
        ServiceAVL<Integer> serviceAVLInteger = new ServiceAVL<>();


        serviceAVLInteger.insert(null, 1);
        serviceAVLInteger.insert(20, 2);
        serviceAVLInteger.insert(null, 3); // Tentativa de inserir um valor nulo

        // Verifica se o nó foi inserido corretamente
        assertEquals(20, serviceAVLInteger.getRoot().getKey());
        assertEquals(null, serviceAVLInteger.getRoot().getLeft());
        assertEquals(null, serviceAVLInteger.getRoot().getRight());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores iguais
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertEqual() {
        ServiceAVL<Integer> serviceAVLInteger = new ServiceAVL<>();


        serviceAVLInteger.insert(10, 1);
        serviceAVLInteger.insert(20, 2);
        serviceAVLInteger.insert(10, 3); // Tentativa de inserir um valor igual

        // Verifica se o nó foi inserido corretamente
        assertEquals(10, serviceAVLInteger.getRoot().getKey());
        assertEquals(20, serviceAVLInteger.getRoot().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de string
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertString() {
        ServiceAVL<String> serviceAVLString = new ServiceAVL<>();


        serviceAVLString.insert("banana", 1);
        serviceAVLString.insert("maçã", 2);
        serviceAVLString.insert("laranja", 3);

        // Verifica se o nó foi inserido corretamente
        assertEquals("laranja", serviceAVLString.getRoot().getKey());
        assertEquals("banana", serviceAVLString.getRoot().getLeft().getKey());
        assertEquals("maçã", serviceAVLString.getRoot().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de string iguais
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertStringEqual() {
        ServiceAVL<String> serviceAVLString = new ServiceAVL<>();


        serviceAVLString.insert("banana", 1);
        serviceAVLString.insert("maçã", 2);
        serviceAVLString.insert("banana", 3); // Tentativa de inserir um valor igual

        // Verifica se o nó foi inserido corretamente
        assertEquals("banana", serviceAVLString.getRoot().getKey());
        assertEquals("maçã", serviceAVLString.getRoot().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de string nulos
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertStringNull() {
        ServiceAVL<String> serviceAVLString = new ServiceAVL<>();


        serviceAVLString.insert(null, 1);
        serviceAVLString.insert("maçã", 2);
        serviceAVLString.insert(null, 3); // Tentativa de inserir um valor nulo

        // Verifica se o nó foi inserido corretamente
        assertEquals("maçã", serviceAVLString.getRoot().getKey());
        assertEquals(null, serviceAVLString.getRoot().getLeft());
        assertEquals(null, serviceAVLString.getRoot().getRight());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de string vazios
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertStringEmpty() {
        ServiceAVL<String> serviceAVLString = new ServiceAVL<>();


        serviceAVLString.insert("", 1);
        serviceAVLString.insert("maçã", 2);
        serviceAVLString.insert("", 3); // Tentativa de inserir um valor vazio

        // Verifica se o nó foi inserido corretamente
        assertEquals("maçã", serviceAVLString.getRoot().getKey());

    }

    /**
     * Testa o método insert da classe ServiceAVL com valores de string vazios e nulos
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertStringEmptyNull() {
        ServiceAVL<String> serviceAVLString = new ServiceAVL<>();


        serviceAVLString.insert("", 1);
        serviceAVLString.insert(null, 2);
        serviceAVLString.insert("", 3); // Tentativa de inserir um valor vazio

        // Verifica se o nó foi inserido corretamente
        assertThrows(NullPointerException.class, () -> {
            serviceAVLString.getRoot().getKey();
        });
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de long
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertLong() {
        ServiceAVL<Long> serviceAVLLong = new ServiceAVL<>();


        serviceAVLLong.insert(100L, 1);
        serviceAVLLong.insert(200L, 2);
        serviceAVLLong.insert(300L, 3);

        // Verifica se o nó foi inserido corretamente
        assertEquals(200L, serviceAVLLong.getRoot().getKey());
        assertEquals(100L, serviceAVLLong.getRoot().getLeft().getKey());
        assertEquals(300L, serviceAVLLong.getRoot().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de long iguais
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertLongEqual() {
        ServiceAVL<Long> serviceAVLLong = new ServiceAVL<>();


        serviceAVLLong.insert(100L, 1);
        serviceAVLLong.insert(200L, 2);
        serviceAVLLong.insert(100L, 3); // Tentativa de inserir um valor igual

        // Verifica se o nó foi inserido corretamente
        assertEquals(100L, serviceAVLLong.getRoot().getKey());
        assertEquals(200L, serviceAVLLong.getRoot().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de long nulos
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertLongNull() {
        ServiceAVL<Long> serviceAVLLong = new ServiceAVL<>();

        serviceAVLLong.insert(null, 1);
        serviceAVLLong.insert(200L, 2);
        serviceAVLLong.insert(null, 3); // Tentativa de inserir um valor nulo

        // Verifica se o nó foi inserido corretamente
        assertEquals(200L, serviceAVLLong.getRoot().getKey());
        assertEquals(null, serviceAVLLong.getRoot().getLeft());
        assertEquals(null, serviceAVLLong.getRoot().getRight());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de long 0
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertLongEmpty() {
        ServiceAVL<Long> serviceAVLLong = new ServiceAVL<>();

        serviceAVLLong.insert(0L, 1);
        serviceAVLLong.insert(200L, 2);
        serviceAVLLong.insert(0L, 3); // Tentativa de inserir um valor vazio

        // Verifica se o nó foi inserido corretamente

        assertEquals(0L, serviceAVLLong.getRoot().getKey());
        assertEquals(200L, serviceAVLLong.getRoot().getRight().getKey());


    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de date
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertDate() {
            ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();


        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1);
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, Calendar.FEBRUARY, 1);
        Calendar date3 = Calendar.getInstance();
        date3.set(2023, Calendar.MARCH, 1);

        serviceAVLCalendar.insert(date1, 1);
        serviceAVLCalendar.insert(date2, 2);
        serviceAVLCalendar.insert(date3, 3);

        // Verifica se o nó foi inserido corretamente
        assertEquals(date2, serviceAVLCalendar.getRoot().getKey());
        assertEquals(date1, serviceAVLCalendar.getRoot().getLeft().getKey());
        assertEquals(date3, serviceAVLCalendar.getRoot().getRight().getKey());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de date iguais
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertDateEqual() {
            ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();


        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1); // 01/01/2023

        Calendar date2 = Calendar.getInstance();
        date2.set(2022, Calendar.FEBRUARY, 1); // 01/02/2022

        Calendar date3 = Calendar.getInstance();
        date3.set(2023, Calendar.JANUARY, 1); // 01/01/2021

        serviceAVLCalendar.insert(date1, 1);
        serviceAVLCalendar.insert(date2, 2);
        serviceAVLCalendar.insert(date3, 3);

        logger.info("Data 1: " + date1.getTime());
        logger.info("Data 2: " + date2.getTime());
        logger.info("Data 3: " + date3.getTime());

        logger.info("Raiz: " + serviceAVLCalendar.getRoot().getKey().getTime());
        logger.info("Esquerda: " + serviceAVLCalendar.getRoot().getLeft().getKey().getTime());
        // logger.info("Direita: " + serviceAVLCalendar.getRoot().getRight().getKey().getTime());

        // Verifica se o nó foi inserido corretamente
        assertEquals(date1, serviceAVLCalendar.getRoot().getKey());
        assertEquals(date2, serviceAVLCalendar.getRoot().getLeft().getKey());
        assertEquals(null, serviceAVLCalendar.getRoot().getRight()); // Não deve haver nó à direita}
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de date nulos
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertDateNull() {
            ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();


        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1); // 01/01/2023

        serviceAVLCalendar.insert(null, 1);
        serviceAVLCalendar.insert(date1, 2);
        serviceAVLCalendar.insert(null, 3); // Tentativa de inserir um valor nulo

        // Verifica se o nó foi inserido corretamente
        assertEquals(date1, serviceAVLCalendar.getRoot().getKey());
        assertEquals(null, serviceAVLCalendar.getRoot().getLeft());
        assertEquals(null, serviceAVLCalendar.getRoot().getRight());
    }
    /**
     * Testa o método insert da classe ServiceAVL com valores de date vazios
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testInsertDateEmpty() {
            ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();

        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1); // 01/01/2023

        serviceAVLCalendar.insert(date1, 1);
        serviceAVLCalendar.insert(null, 2);
        serviceAVLCalendar.insert(date1, 3); // Tentativa de inserir um valor vazio

        // Verifica se o nó foi inserido corretamente
        assertEquals(date1, serviceAVLCalendar.getRoot().getKey());
        assertEquals(null, serviceAVLCalendar.getRoot().getLeft());
        assertEquals(null, serviceAVLCalendar.getRoot().getRight());
    }

    /**
     * Testa o método findNode da classe ServiceAVL.
     * Garante que a busca por um nó existente retorna o nó correto,
     * e que a busca por um valor não inserido retorna null.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testfindNodeAVLInteger() {
        ServiceAVL<Integer> serviceAVLInteger = new ServiceAVL<>();

        
        serviceAVLInteger.insert(10, 1);
        serviceAVLInteger.insert(20, 2);
        serviceAVLInteger.insert(30, 3);

        // Verifica se o nó foi encontrado corretamente
        assertEquals(20, serviceAVLInteger.findNode(20).getKey());
        assertEquals(null, serviceAVLInteger.findNode(40)); // Valor não inserido
    }

    /**
     * Testa o método findNode da classe ServiceAVL com valores de string.
     * Garante que a busca por um nó existente retorna o nó correto,
     * e que a busca por um valor não inserido retorna null.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testfindNodeAVLString() {
        ServiceAVL<String> serviceAVLString = new ServiceAVL<>();

        
        serviceAVLString.insert("banana", 1);
        serviceAVLString.insert("maçã", 2);
        serviceAVLString.insert("laranja", 3);

        // Verifica se o nó foi encontrado corretamente
        assertEquals("maçã", serviceAVLString.findNode("maçã").getKey());
        assertEquals(null, serviceAVLString.findNode("uva")); // Valor não inserido
    }
    /**
     * Testa o método findNode da classe ServiceAVL com valores de long.
     * Garante que a busca por um nó existente retorna o nó correto,
     * e que a busca por um valor não inserido retorna null.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testfindNodeAVLLong() {
        ServiceAVL<Long> serviceAVLLong = new ServiceAVL<>();

        
        serviceAVLLong.insert(100L, 1);
        serviceAVLLong.insert(200L, 2);
        serviceAVLLong.insert(300L, 3);

        // Verifica se o nó foi encontrado corretamente
        assertEquals(200L, serviceAVLLong.findNode(200L).getKey());
        assertEquals(null, serviceAVLLong.findNode(400L)); // Valor não inserido
    }
    /**
     * Testa o método findNode da classe ServiceAVL com valores de date.
     * Garante que a busca por um nó existente retorna o nó correto,
     * e que a busca por um valor não inserido retorna null.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testfindNodeAVLDate() {
        ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();

        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1); // 01/01/2023
        Calendar date2 = Calendar.getInstance();
        date2.set(2023, Calendar.FEBRUARY, 1); // 01/02/2023
        Calendar date3 = Calendar.getInstance();
        date3.set(2023, Calendar.MARCH, 1); // 01/03/2023

        serviceAVLCalendar.insert(date1, 1);
        serviceAVLCalendar.insert(date2, 2);
        serviceAVLCalendar.insert(date3, 3);

        // Verifica se o nó foi encontrado corretamente
        assertEquals(date2, serviceAVLCalendar.findNode(date2).getKey());
        assertEquals(null, serviceAVLCalendar.findNode(Calendar.getInstance())); // Valor não inserido
    }
    /**
     * Testa o método findNode da classe ServiceAVL com valores de date iguais.
     * Garante que a busca por um nó existente retorna o nó correto,
     * e que a busca por um valor não inserido retorna null.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testfindNodeAVLDateEqual() {
        ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();

        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1); // 01/01/2023
        Calendar date2 = Calendar.getInstance();
        date2.set(2022, Calendar.FEBRUARY, 1); // 01/02/2022

        serviceAVLCalendar.insert(date1, 1);
        serviceAVLCalendar.insert(date2, 2);
        serviceAVLCalendar.insert(date1, 3); // Tentativa de inserir um valor igual

        // Verifica se o nó foi encontrado corretamente
        assertEquals(date1, serviceAVLCalendar.findNode(date1).getKey());
        assertEquals(null, serviceAVLCalendar.findNode(Calendar.getInstance())); // Valor não inserido
    }
    /**
     * Testa o método findNode da classe ServiceAVL com valores de date nulos.
     * Garante que a busca por um nó existente retorna o nó correto,
     * e que a busca por um valor não inserido retorna null.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testfindNodeAVLDateNull() {
        ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();

        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1); // 01/01/2023

        serviceAVLCalendar.insert(null, 1);
        serviceAVLCalendar.insert(date1, 2);
        serviceAVLCalendar.insert(null, 3); // Tentativa de inserir um valor nulo

        // Verifica se o nó foi encontrado corretamente
        assertEquals(date1, serviceAVLCalendar.findNode(date1).getKey());
        assertEquals(null, serviceAVLCalendar.findNode(null)); // Valor nulo inserido
    }
    /**
     * Testa o método findNode da classe ServiceAVL com valores de date vazios.
     * Garante que a busca por um nó existente retorna o nó correto,
     * e que a busca por um valor não inserido retorna null.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testfindNodeAVLDateEmpty() {
        ServiceAVL<Calendar> serviceAVLCalendar = new ServiceAVL<>();

        Calendar date1 = Calendar.getInstance();
        date1.set(2023, Calendar.JANUARY, 1); // 01/01/2023

        serviceAVLCalendar.insert(date1, 1);
        serviceAVLCalendar.insert(null, 2);
        serviceAVLCalendar.insert(date1, 3); // Tentativa de inserir um valor vazio

        // Verifica se o nó foi encontrado corretamente
        assertEquals(date1, serviceAVLCalendar.findNode(date1).getKey());
        assertEquals(null, serviceAVLCalendar.findNode(null)); // Valor nulo inserido
    }
    /**
     * Testa o método getRoot da classe ServiceAVL.
     * Garante que o nó raiz é retornado corretamente.
     * 
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testGetRoot() {
        ServiceAVL<Integer> serviceAVLInteger = new ServiceAVL<>();

        
        serviceAVLInteger.insert(10, 1);
        serviceAVLInteger.insert(20, 2);
        serviceAVLInteger.insert(30, 3);

        // Verifica se o nó raiz é retornado corretamente
        assertEquals(20, serviceAVLInteger.getRoot().getKey());
        assertEquals(10, serviceAVLInteger.getRoot().getLeft().getKey());
        assertEquals(30, serviceAVLInteger.getRoot().getRight().getKey());
    }

    

}