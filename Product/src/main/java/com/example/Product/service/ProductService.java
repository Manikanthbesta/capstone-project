package com.example.Product.service;

import com.example.Product.model.Product;
import com.example.Product.dto.ProductDTO;
import com.example.Product.dto.ProductDTO.VendorDTO;
import com.example.Product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private static final String VENDOR_SERVICE_URL = "http://localhost:8084/vendors/";

    // Create a product without sequence service
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product)
                .doOnError(e -> logger.error("Error creating product", e))  // Log error
                .doOnSuccess(p -> logger.info("Product created successfully: {}", p)); // Log success
    }

    // Get products by vendorId
    public Flux<ProductDTO> getProductByVendorid(String vendorId) {
        return productRepository.findByVendorId(vendorId)
                .flatMap(product -> fetchVendorDetails(product.getVendorId())
                        .map(vendorDTO -> convertToDTO(product, vendorDTO)))
                .doOnError(e -> logger.error("Error fetching products by vendorId: " + vendorId, e))  // Log error
                .doOnComplete(() -> logger.info("Fetched products by vendorId: {}", vendorId));  // Log completion
    }

    // Get products by category
    public Flux<ProductDTO> getProductBycategory(String category) {
        return productRepository.findByCategory(category)
                .flatMap(product -> fetchVendorDetails(product.getVendorId())
                        .map(vendorDTO -> convertToDTO(product, vendorDTO)))
                .doOnError(e -> logger.error("Error fetching products by category: " + category, e))  // Log error
                .doOnComplete(() -> logger.info("Fetched products by category: {}", category));  // Log completion
    }

    // Get all products
    public Flux<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .flatMap(product -> fetchVendorDetails(product.getVendorId())
                        .map(vendorDTO -> convertToDTO(product, vendorDTO)))
                .doOnError(e -> logger.error("Error fetching all products", e))  // Log error
                .doOnComplete(() -> logger.info("Fetched all products"));  // Log completion
    }

    // Get product by ID
    public Mono<ProductDTO> getProductById(String id) {
        return productRepository.findById(id)
                .flatMap(product -> fetchVendorDetails(product.getVendorId())
                        .map(vendorDTO -> convertToDTO(product, vendorDTO)))
                .doOnError(e -> logger.error("Error fetching product by id: " + id, e))  // Log error
                .doOnSuccess(productDTO -> logger.info("Fetched product by id: {}", id));  // Log success
    }

    // Update product details
    public Mono<Product> updateProduct(String id, Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    // Update fields but retain existing reviews
                    if (product.getVendorId() != null) existingProduct.setVendorId(product.getVendorId());
                    if (product.getProductName() != null) existingProduct.setProductName(product.getProductName());
                    if (product.getDescription() != null) existingProduct.setDescription(product.getDescription());
                    if (product.getPrice() != null) existingProduct.setPrice(product.getPrice());
                    if (product.getCategory() != null) existingProduct.setCategory(product.getCategory());
                    if (product.getProductImageUrl() != null) existingProduct.setProductImageUrl(product.getProductImageUrl());

                    return productRepository.save(existingProduct);
                })
                .doOnError(e -> logger.error("Error updating product with id: " + id, e))  // Log error
                .doOnSuccess(p -> logger.info("Product updated successfully with id: {}", id));  // Log success
    }

    // Delete product by ID
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id)
                .doOnError(e -> logger.error("Error deleting product with id: " + id, e))  // Log error
                .doOnSuccess(v -> logger.info("Product deleted successfully with id: {}", id));  // Log success
    }

    // Fetch vendor details from the Vendor Service
    private Mono<VendorDTO> fetchVendorDetails(String vendorId) {
        return webClientBuilder.build()
                .get()
                .uri(VENDOR_SERVICE_URL + vendorId)
                .retrieve()
                .bodyToMono(VendorDTO.class)
                .doOnError(e -> logger.error("Error fetching vendor details for vendorId: " + vendorId, e)) // Log error
                .onErrorReturn(new VendorDTO());  // Return empty VendorDTO in case of error
    }

    // Convert Product and VendorDTO to ProductDTO
    private ProductDTO convertToDTO(Product product, VendorDTO vendorDTO) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        productDTO.setProductImageUrl(product.getProductImageUrl());

        // Ensure vendor is not null before setting
        productDTO.setVendor(vendorDTO != null ? vendorDTO : new VendorDTO());  // Default empty vendor if null
        return productDTO;
    }

}
