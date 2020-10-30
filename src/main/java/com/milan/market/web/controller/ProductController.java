package com.milan.market.web.controller;

import com.milan.market.domain.Product;
import com.milan.market.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    public List<Product> getAll() {
        return this.productService.getAll();
    }

    public Optional<Product> getProduct( int productId ) {
        return this.productService.getProduct( productId );
    }

    public Optional<List<Product>> getByCategory( int categoryId ) {
        return this.productService.getByCategory( categoryId );
    }

    public Product save( Product product) {
        return this.productService.save( product );
    }

    public boolean delete( int productId ) {
        return this.productService.delete( productId );
    }
}
