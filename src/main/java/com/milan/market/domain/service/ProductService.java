package com.milan.market.domain.service;

import com.milan.market.domain.Product;
import com.milan.market.domain.repositoty.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return this.productRepository.getAll();
    }

    public Optional<Product> getProduct( int productId ) {
        return this.productRepository.getProduct( productId );
    }

    public Optional<List<Product>> getByCategory( int categoryId ) {
        return this.productRepository.getByCategory( categoryId );
    }

    public Product save(Product product) {
        return this.productRepository.save( product );
    }

    public boolean delete( int productId ) {

        return getProduct( productId ).map( product -> {
            productRepository.delete( productId );
            return true;
        }).orElse( false );

        // El método opcional es propio de los Optional. Esta es otra forma
        /*
        if ( getProduct(productId).isPresent() ) {
            productRepository.delete( productId );
            return true;
        } else {
            return false;
        }
         */
    }

}
