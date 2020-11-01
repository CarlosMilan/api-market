package com.milan.market.persistence;

import com.milan.market.domain.Purchase;
import com.milan.market.domain.repositoty.PurchaseRepository;
import com.milan.market.persistence.crud.CompraCrudRepository;
import com.milan.market.persistence.entity.Compra;
import com.milan.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases( (List<Compra>) compraCrudRepository.findAll() );
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente( clientId )
                .map( compras -> mapper.toPurchases( compras ));
    }

    /**
     * Primero debemos mapear purchase a compra, en ese mapeo, los items de purchase pasan a ser productos de Compra
     * de esta manera, ya sabemos en nuestro Entity compra cuales son los productos de la misma. Posteriomente
     * debemos recorrer esos productos (compraProducto) para indicarles que pertenecen a la compra correspondiente
     * para lo cual debemos recorrer con el forEach y asignarselas. Para este proposito se agregan las notaciones
     * necesarias para que este procedimiento sea en cascada
     * @param purchase
     * @return
     */
    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra( purchase );
        compra.getProductos().forEach( producto -> producto.setCompra( compra ) );

        return mapper.toPurchase( compraCrudRepository.save( compra ) );
    }
}
