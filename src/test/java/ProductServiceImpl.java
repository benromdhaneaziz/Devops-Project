import tn.esprit.devops_project.services.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Stock stock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stock = new Stock();
        stock.setIdStock(1L);

        product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Sample Product");
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setStock(stock);
    }

    @Test
    void testAddProduct() {
        Long stockId = 1L;

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.addProduct(product, stockId);

        assertNotNull(savedProduct);
        assertEquals(product.getTitle(), savedProduct.getTitle());
        verify(stockRepository, times(1)).findById(stockId);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testRetrieveProduct() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.retrieveProduct(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getIdProduct());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testRetrieveAllProduct() {
        List<Product> products = Arrays.asList(product, new Product());

        when(productRepository.findAll()).thenReturn(products);

        List<Product> allProducts = productService.retreiveAllProduct();

        assertEquals(2, allProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveProductByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> products = Arrays.asList(product);

        when(productRepository.findByCategory(category)).thenReturn(products);

        List<Product> productsByCategory = productService.retrieveProductByCategory(category);

        assertEquals(1, productsByCategory.size());
        assertEquals(category, productsByCategory.get(0).getCategory());
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testRetrieveProductStock() {
        Long stockId = 1L;
        List<Product> products = Arrays.asList(product);

        when(productRepository.findByStockIdStock(stockId)).thenReturn(products);

        List<Product> productsByStock = productService.retreiveProductStock(stockId);

        assertEquals(1, productsByStock.size());
        assertEquals(stockId, productsByStock.get(0).getStock().getIdStock());
        verify(productRepository, times(1)).findByStockIdStock(stockId);
    }
}
