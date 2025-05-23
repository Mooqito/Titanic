package model;

import model.Product.Product;
import model.Product.ReadAllproduct;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ReadproductTest {

    @Test
    public void Readproduct() {
        List<Product> products = ReadAllproduct.Readproduct();
        Product first = products.get(0);

        assertTrue(products.size()>0);

        assertEquals("tv",first.getTitle());
    }
}
