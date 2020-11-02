package com.milan.market.persistence;

import com.milan.market.domain.Product;
import com.milan.market.domain.repositoty.ProductRepository;
import com.milan.market.persistence.crud.ProductoCrudRepository;
import com.milan.market.persistence.entity.Producto;
import com.milan.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) this.productoCrudRepository.findAll();
        return this.mapper.toProducts( productos );
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = this.productoCrudRepository.findByIdCategoriaOrderByNombreAsc( categoryId );
        List<Product> products = this.mapper.toProducts( productos );
        return Optional.of( products );
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = this.productoCrudRepository.findByCantidadStockLessThanAndEstado( quantity, true );
        return productos.map( prods -> mapper.toProducts( prods ));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return this.productoCrudRepository.findById( productId ).map( producto -> mapper.toProduct( producto ) );
    }

    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto( product );
        return this.mapper.toProduct( this.productoCrudRepository.save( producto ));
    }

    @Override
    public void delete( int productId ) {
        this.productoCrudRepository.deleteById( productId );
    }

}
