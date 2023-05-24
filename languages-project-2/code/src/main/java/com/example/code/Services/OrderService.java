package com.example.code.Services;

import com.example.code.Models.Order;
import com.example.code.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Saves the provided Order object to the database.
     *
     * @param order The Order object to be saved.
     * @return The saved Order object.
     */
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Deletes an Order entry by its scientific name.
     *
     * @param ScientificName The scientific name of the Order to be deleted.
     */
    public void deleteByScientificName(String ScientificName) {
        orderRepository.deleteByScientificName(ScientificName);
    }

    /**
     * Updates an Order entry with the specified parameters.
     *
     * @param id The ID of the Order to be updated.
     * @param scientificName The updated scientific name.
     * @param author The updated author.
     * @param publicationYear The updated publication year.
     * @param ancestor The updated ancestor.
     * @param collectingMethod The updated collecting method.
     * @return The number of rows affected by the update.
     */
    public int update(Integer id, String scientificName, String author, Integer publicationYear, int ancestor, String collectingMethod) {
        return orderRepository.update(scientificName, author, publicationYear, id, ancestor, collectingMethod);
    }

    /**
     * Retrieves an Order entry by its ID.
     *
     * @param id The ID of the Order to be retrieved.
     * @return The Order object with the specified ID.
     */
    public Order findById(int id) {
        return orderRepository.findById(id);
    }

    /**
     * Retrieves all Order entries from the database.
     *
     * @return A list of all Order objects.
     */
    public List<Order> findAll() {
        return orderRepository.findAllOrders();
    }
}
