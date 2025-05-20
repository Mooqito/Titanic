package model;

import controller.product.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

public class ProductInputTest {

    @Test
    public void TestProductInput() {

        String FakeInput = String.join("\n", "1001","shampo","25000","head wash","behdashti","mosleh","1","bantaclin");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(FakeInput.getBytes());
        System.setIn(inputStream);

        ProductInput productInput = new ProductInput();
        Product product  = productInput.ProductInputList();

        assertEquals(1001,product.getId());
        assertEquals("shampo", product.getTitle());
        assertEquals(25000, product.getPrice());
        assertEquals("head wash", product.getDescription());
        assertEquals("behdashti", product.getCategory().getTitle());
        assertEquals("mosleh", product.getBrand().getTitle());
        assertEquals(1, product.getProviders().size());
        assertEquals("bantaclin", product.getProviders().get(0).getTitle());

    }
}
