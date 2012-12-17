package joni.json.jackson;

import java.io.IOException;
import java.io.InputStream;

import joni.json.jackson.beans.Employee;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jonatan Ivanov
 */
public class Main {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream("/employee.json");

        ObjectMapper mapper = new ObjectMapper();
        Employee employee = mapper.readValue(inputStream, Employee.class);

        System.out.println(employee);
        mapper.writeValue(System.out, employee);
    }
}
