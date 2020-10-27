package com.milan.market.persistence.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "compras_productos")
public class CompraProducto {

     @EmbeddedId
     private CompraProductoPK id;

     private Integer cantidad;
     private Double total;
     private Boolean estado;

}
