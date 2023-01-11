package lesson23.teamWork.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private  int id;
    private  String name_product;
    private int price_product;
    private String country_product;
    private String manufacturer;
    private int manufacturer_emp_count;
    private  String manufacturer_address;



}
