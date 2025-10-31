/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.model.dto.OrderDTO;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
public class OrdersAPIController {

    @Autowired
    RestaurantOrderServices ros;

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {

        try {
            Set<Integer> tables = ros.getTablesWithOrders();
            StringBuilder sb = new StringBuilder();
            Map<Integer, String> ordersByTable = new Hashtable<>();
            List<Order> orders = new ArrayList<>();
            List<OrderDTO> ordersDTO = new ArrayList<>();
            for (Integer i : tables) {
                Integer total = ros.calculateTableBill(i);
                Order order = ros.getTableOrder(i);
                orders.add(order);
                sb.append(order.toString());
                sb.append("TotalBill: " + total);
                String template = "{Order: "+ order.toString() + "TotalBill: " + total + "}";
                ordersByTable.put(i, template);
                ordersDTO.add(new OrderDTO(order, total));
                // ordersByTable.put(i, template);
                // ordersByTable.put(order, "TotalBill: " + total);
                // ordersByTable.put(i, ros.getTableOrder(i));

            }
            return ResponseEntity.ok(ordersDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
