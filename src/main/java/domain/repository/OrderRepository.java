package domain.repository;

import domain.model.Order;
import java.util.List;
import java.util.Optional;

/**
 * Interface para operações de persistência e consulta de pedidos.
 */
public interface OrderRepository {
    /**
     * Salva ou atualiza um pedido.
     * @param order Pedido a ser salvo ou atualizado
     */
    void save(Order order);

    /**
     * Busca um pedido pelo seu ID.
     * @param id Identificador do pedido
     * @return Optional contendo o pedido, se encontrado
     */
    Optional<Order> findById(String id);

    /**
     * Retorna todos os pedidos cadastrados.
     * @return Lista de pedidos
     */
    List<Order> findAll();

    /**
     * Remove um pedido pelo seu ID.
     * @param id Identificador do pedido a ser removido
     */
    void delete(String id);

    /**
     * Verifica se existe um pedido com o ID informado.
     * @param id Identificador do pedido
     * @return true se existir, false caso contrário
     */
    boolean existsById(String id);
}