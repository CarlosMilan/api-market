package com.milan.market.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "compras_productos")
public class CompraProducto {

     @EmbeddedId
     private CompraProductoPK id;

     private Integer cantidad;
     private Double total;
     private Boolean estado;

     @ManyToOne
     @MapsId("idCompra") // Cuando se cuarde la compra de los productos, mediante esta anotacion se sabrá cual es la pk
     @JoinColumn(name = "id_compra", insertable = false, updatable = false)
     private Compra compra;

     @ManyToOne
     @JoinColumn(name = "id_producto", insertable = false, updatable = false)
     private Producto producto;

     public CompraProductoPK getId() {
          return id;
     }

     public void setId(CompraProductoPK id) {
          this.id = id;
     }

     public Integer getCantidad() {
          return cantidad;
     }

     public void setCantidad(Integer cantidad) {
          this.cantidad = cantidad;
     }

     public Double getTotal() {
          return total;
     }

     public void setTotal(Double total) {
          this.total = total;
     }

     public Boolean getEstado() {
          return estado;
     }

     public void setEstado(Boolean estado) {
          this.estado = estado;
     }

     public Compra getCompra() {
          return compra;
     }

     public void setCompra(Compra compra) {
          this.compra = compra;
     }

     public Producto getProducto() {
          return producto;
     }

     public void setProducto(Producto producto) {
          this.producto = producto;
     }
}
