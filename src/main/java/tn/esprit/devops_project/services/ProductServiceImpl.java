package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.services.Iservices.IProductService;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Override
    public Product addProduct(Product product, Long idStock) {
        logger.info("Adding product: {} to stock with ID: {}", product.getTitle(), idStock);
        Stock stock = stockRepository.findById(idStock)
                .orElseThrow(() -> new NullPointerException("Stock not found for ID: " + idStock));
        product.setStock(stock);
        Product savedProduct = productRepository.save(product);
        logger.debug("Product added successfully with ID: {}", savedProduct.getIdProduct());
        return savedProduct;
    }

    @Override
    public Product retrieveProduct(Long id) {
        logger.info("Retrieving product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Product not found for ID: " + id));
        logger.debug("Product retrieved: {}", product);
        return product;
    }

    @Override
    public List<Product> retreiveAllProduct() {
        logger.info("Retrieving all products");
        List<Product> products = productRepository.findAll();
        logger.debug("Total products retrieved: {}", products.size());
        return products;
    }

    @Override
    public List<Product> retrieveProductByCategory(ProductCategory category) {
        logger.info("Retrieving products by category: {}", category);
        List<Product> products = productRepository.findByCategory(category);
        logger.debug("Total products retrieved in category {}: {}", category, products.size());
        return products;
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        logger.debug("Product deleted successfully with ID: {}", id);
    }

    @Override
    public List<Product> retreiveProductStock(Long id) {
        logger.info("Retrieving products for stock with ID: {}", id);
        List<Product> products = productRepository.findByStockIdStock(id);
        logger.debug("Total products retrieved for stock ID {}: {}", id, products.size());
        return products;
    }
}
