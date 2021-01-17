/*****************************************************************c******************o*******v******id********
 * File: ProductSerializer.java
 * Course materials (20F) CST 8277
 * @author Mike Norman
 *
 * Note: students do NOT need to change anything in this class
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.algonquincollege.cst8277.models.ProductPojo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
@Path(CUSTOMER_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductSerializer extends StdSerializer<Set<ProductPojo>> implements Serializable {
    private static final long serialVersionUID = 1L;

    public ProductSerializer() {
        this(null);
    }

    public ProductSerializer(Class<Set<ProductPojo>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<ProductPojo> originalProducts, JsonGenerator generator, SerializerProvider provider)
        throws IOException {
        
        Set<ProductPojo> hollowProducts = new HashSet<>();
        for (ProductPojo originalProduct : originalProducts) {
            // create a 'hollow' copy of the original Product entity
            ProductPojo hollowP = new ProductPojo();
            hollowP.setId(originalProduct.getId());
            hollowP.setDescription(originalProduct.getDescription());
            hollowP.setCreatedDate(originalProduct.getCreatedDate());
            hollowP.setUpdatedDate(originalProduct.getUpdatedDate());
            hollowP.setVersion(originalProduct.getVersion());
            hollowP.setSerialNo(originalProduct.getSerialNo());
            hollowP.setStores(null);
            hollowProducts.add(hollowP);
        }
        generator.writeObject(hollowProducts);
    }
}