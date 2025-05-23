import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.faculdade.frau.b.service.ServiceUser;

public class TesteServiceUser {

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
        String testFile = "test_users.csv";
        FileWriter writer = new FileWriter(testFile);
        writer.write("12345678900,1234567,Joao,2000-01-01,Sao Paulo\n");
        writer.write("98765432100,7654321,Maria,1995-05-10,Rio de Janeiro\n");
        writer.close();

        ServiceUser serviceUser = new ServiceUser();
        int count = serviceUser.getUserByFileCSV(testFile);

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
        int count = serviceUser.getUserByFileCSV(testFile);

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
        int count = serviceUser.getUserByFileCSV(testFile);

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
        writer.write("12345678900,1234567,Joao,2000-01-01,Sao Paulo\n");
        writer.write("98765432100,7654321,Maria,1995-05-10,Rio de Janeiro\n");
        writer.write("invalid_line\n");
        writer.close();

        ServiceUser serviceUser = new ServiceUser();
        int count = serviceUser.getUserByFileCSV(testFile);

        assertEquals(2, count);

        // Opcional: deletar o arquivo após o teste
        new java.io.File(testFile).delete();
    }
    

}
