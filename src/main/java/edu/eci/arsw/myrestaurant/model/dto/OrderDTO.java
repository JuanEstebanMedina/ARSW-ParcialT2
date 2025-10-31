package edu.eci.arsw.myrestaurant.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.eci.arsw.myrestaurant.model.Order;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OrderDTO {

    private Map<String, Integer> orderAmountsMap;
    private Integer totalBill;
    private int tableNumber;
    
    public Map<String, Integer> getOrderAmountsMap() {
        return orderAmountsMap;
    }

    public void setOrderAmountsMap(Map<String, Integer> orderAmountsMap) {
        this.orderAmountsMap = orderAmountsMap;
    }



    public OrderDTO() {
    }

    public OrderDTO(Order order, Integer totalBill) {
        orderAmountsMap = new ConcurrentHashMap<>();
        orderAmountsMap = order.getOrderAmountsMap();
        this.tableNumber = order.getTableNumber();
        this.totalBill = totalBill;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTotalBill(Integer totalBill) {
        this.totalBill = totalBill;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void addDish(String p, int amount) {
        if (!orderAmountsMap.containsKey(p)) {
            orderAmountsMap.put(p, amount);
        } else {
            int previousAmount = orderAmountsMap.get(p);
            orderAmountsMap.put(p, previousAmount + amount);
        }
    }

    @JsonIgnore
    public Set<String> getOrderedDishes() {
        return orderAmountsMap.keySet();
    }

    public int getDishOrderedAmount(String p) {
        if (!orderAmountsMap.containsKey(p)) {
            return 0;
        } else {
            return orderAmountsMap.get(p);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Table " + tableNumber + "\n");
        getOrderedDishes().forEach((p) -> {
            sb.append(p).append(" x ").append(orderAmountsMap.get(p)).append("\n");
        });
        return sb.toString();

    }

}
