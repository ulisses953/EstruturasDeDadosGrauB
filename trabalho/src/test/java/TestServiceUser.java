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
