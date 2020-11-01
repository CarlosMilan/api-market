package com.milan.market.persistence.mapper;

import com.milan.market.domain.PurchaseItem;
import com.milan.market.persistence.entity.CompraProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface PurchaseItemMapper {

    @Mappings({
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "total", target = "total"), //Este no es necesario porque ambos se llaman igual
            @Mapping(source = "estado", target = "active")
    })
    PurchaseItem toPurchaseItem( CompraProducto compraProducto );


    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "producto", ignore = true), // Como tengo que mapear un producto, incluyo su mapper en uses
            @Mapping(target = "id.idCompra", ignore = true)
    })
    CompraProducto toCompraProducto( PurchaseItem purchaseItem);
}
