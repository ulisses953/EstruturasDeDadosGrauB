import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faculdade.frau.b.Model.User;
import com.faculdade.frau.b.Model.Avl.Node;
import com.faculdade.frau.b.service.ServiceAVL;
import com.faculdade.frau.b.service.ServiceUser;

public class TestServiceUser {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceUser.class);

    private final String path = "../main/resources/nomes.csv";

    /**
     * Testa o método getUserByFileCSV da classe ServiceUser
     * 
     * @throws IOException
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testGetUserByFileCSV() throws IOException {
        // Cria um arquivo CSV temporário para teste
        logger.info("Iniciando teste getUserByFileCSV");

        String testFile = "test_users.csv";
        FileWriter writer = new FileWriter(testFile);
        writer.write("12345678900,1234567,Joao,2000/01/01,Sao Paulo\n");
        writer.write("98765432100,7654321,Maria,1995/05/10,Rio de Janeiro\n");
        writer.close();

        ServiceUser serviceUser = new ServiceUser();
        int count = serviceUser.getUserByFileCSV(testFile, ',', false);

        assertEquals(2, count);

        // Opcional: deletar o arquivo após o teste
        new java.io.File(testFile).delete();
    }

    /**
     * Testa o método getUserByFileCSV da classe ServiceUser com arquivo vazio
     * 
     * @throws IOException
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testGetUserByFileCSVEmpty() throws IOException {
        // Cria um arquivo CSV temporário vazio para teste
        String testFile = "test_empty_users.csv";
        FileWriter writer = new FileWriter(testFile);
        writer.close();

        ServiceUser serviceUser = new ServiceUser();
        int count = serviceUser.getUserByFileCSV(testFile, ',', false);

        assertEquals(0, count);

        // Opcional: deletar o arquivo após o teste
        new java.io.File(testFile).delete();
    }

    /**
     * Testa o método getUserByFileCSV da classe ServiceUser com arquivo inexistente
     * 
     * @throws IOException
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testGetUserByFileCSVNotFound() throws IOException {
        // Cria um arquivo CSV temporário para teste
        String testFile = "test_not_found_users.csv";

        ServiceUser serviceUser = new ServiceUser();
        int count = serviceUser.getUserByFileCSV(testFile, ',', false);

        assertEquals(0, count);

        // Opcional: deletar o arquivo após o teste
        new java.io.File(testFile).delete();
    }

    /**
     * Testa o método getUserByFileCSV da classe ServiceUser com arquivo inválido
     * 
     * @throws IOException
     * @author Ulisses
     * @since 1.0
     */
    @Test
    public void testGetUserByFileCSVInvalid() throws IOException {
        // Cria um arquivo CSV temporário para teste
        String testFile = "test_invalid_users.csv";
        FileWriter writer = new FileWriter(testFile);
        writer.write("12345678900,1234567,Joao,2000/01/01,Sao Paulo\n");
        writer.write("98765432100,7654321,Maria,1995/05/10,Rio de Janeiro\n");
        writer.write("invalid_line\n");
        writer.close();

        ServiceUser serviceUser = new ServiceUser();
        int count = serviceUser.getUserByFileCSV(testFile, ',', false);

        assertEquals(2, count);

        new java.io.File(testFile).delete();
    }

    @Test
    public void testGetAllDatesBetween_NormalRange() {
        ServiceUser serviceUser = new ServiceUser();
        Calendar d1 = Calendar.getInstance();
        d1.set(2023, Calendar.JANUARY, 1);
        Calendar d2 = Calendar.getInstance();
        d2.set(2023, Calendar.JANUARY, 3);

        List<Calendar> result = serviceUser.getAllDatesBetween(d1, d2);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).get(Calendar.DAY_OF_MONTH));
        assertEquals(2, result.get(1).get(Calendar.DAY_OF_MONTH));
        assertEquals(3, result.get(2).get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetAllDatesBetween_SameDay() {
        ServiceUser serviceUser = new ServiceUser();
        Calendar d1 = Calendar.getInstance();
        d1.set(2023, Calendar.JANUARY, 1);

        List<Calendar> result = serviceUser.getAllDatesBetween(d1, d1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetAllDatesBetween_NullDates() {
        ServiceUser serviceUser = new ServiceUser();
        Calendar d1 = null;
        Calendar d2 = Calendar.getInstance();
        d2.set(2023, Calendar.JANUARY, 1);

        List<Calendar> result = serviceUser.getAllDatesBetween(d1, d2);
        assertTrue(result.isEmpty());

        result = serviceUser.getAllDatesBetween(d2, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllDatesBetween_Date1AfterDate2() {
        ServiceUser serviceUser = new ServiceUser();
        Calendar d1 = Calendar.getInstance();
        d1.set(2023, Calendar.JANUARY, 3);
        Calendar d2 = Calendar.getInstance();
        d2.set(2023, Calendar.JANUARY, 1);

        List<Calendar> result = serviceUser.getAllDatesBetween(d1, d2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllDatesBetween_FullNovember() {
        ServiceUser serviceUser = new ServiceUser();

        Calendar start = Calendar.getInstance();

        start.set(2023, Calendar.NOVEMBER, 1); // mês 10 = novembro

        Calendar end = Calendar.getInstance();

        end.set(2023, Calendar.NOVEMBER, 30);

        List<Calendar> result = serviceUser.getAllDatesBetween(start, end);

        assertEquals(30, result.size());
        assertEquals(1, result.get(0).get(Calendar.DAY_OF_MONTH));
        assertEquals(30, result.get(result.size() - 1).get(Calendar.DAY_OF_MONTH));

        for (int i = 0; i < 30; i++) {
            assertEquals(i + 1, result.get(i).get(Calendar.DAY_OF_MONTH));
            assertEquals(Calendar.NOVEMBER, result.get(i).get(Calendar.MONTH));
        }
    }

    @Test
    public void testdata(){
        ArrayList<User> users = new ArrayList<>();

        Calendar d1 = Calendar.getInstance();
        d1.set(2001, Calendar.JANUARY, 1);
        Calendar d2 = Calendar.getInstance();
        d2.set(2001, Calendar.MAY, 10);
        Calendar d3 = Calendar.getInstance();
        d3.set(2001, Calendar.MAY, 29);

        User u1 = new User(12345678900L, 1234567L, "Joao", d1, "Sao Paulo");
        User u2 = new User(98765432100L, 7654321L, "Maria", d2, "Rio de Janeiro");
        User u3 = new User(38470245012L, 7916164950L, "Marcelo Dias", d3, "Santa Vitória do Palmar");

        users.add(u1);
        users.add(u2);
        users.add(u3);

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setUsers(users);

        serviceUser.insertDateAvl();

        List<Calendar> result = serviceUser.getAllDatesBetween(d1, d3);

        assertEquals(1, result.get(0).get(Calendar.DAY_OF_MONTH), "Primeiro dia deve ser 01/01/2001");
        assertEquals(29, result.get(result.size() - 1).get(Calendar.DAY_OF_MONTH), "Último dia deve ser 29/05/2001");
        assertEquals(Calendar.MAY, result.get(result.size() - 1).get(Calendar.MONTH), "Último mês deve ser Maio");
        assertEquals(2001, result.get(result.size() - 1).get(Calendar.YEAR), "Último ano deve ser 2001");   
    }

    @Test
    public void testInsertCalendar_DatasIguaisAcumulaPonteiros() {
        ServiceAVL<Calendar> avl = new ServiceAVL<>();

        Calendar data1 = Calendar.getInstance();
        data1.set(2023, Calendar.NOVEMBER, 30, 0, 0, 0);
        data1.set(Calendar.MILLISECOND, 0);

        Calendar data2 = Calendar.getInstance();
        data2.set(2023, Calendar.NOVEMBER, 30, 12, 30, 45); // mesma data, hora diferente
        data2.set(Calendar.MILLISECOND, 0);

        avl.insert(data1, 1);
        avl.insert(data2, 2);

        Node<Calendar> node = avl.findNode(data1);

        assertNotNull(node, "O nó da data deve existir");
        assertEquals(2, node.getPointer().size(), "Deve haver dois ponteiros para a mesma data");
        assertTrue(node.getPointer().contains(1), "Deve conter o ponteiro 1");
        assertTrue(node.getPointer().contains(2), "Deve conter o ponteiro 2");
    }

}
