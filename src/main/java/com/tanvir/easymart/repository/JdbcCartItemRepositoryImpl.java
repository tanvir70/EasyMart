package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.CartItem;
import com.tanvir.easymart.exceptions.CartNotFoundException;
import com.tanvir.easymart.exceptions.OptimisticLockingFailureException;
import com.tanvir.easymart.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCartItemRepositoryImpl implements CartItemRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCartItemRepositoryImpl.class);

    private DataSource dataSource = ConnectionPool.getInstance().getDataSource();

    private final static String INSERT_CART_ITEM = "INSERT INTO cart_item (" +
            "quantity, " +
            "price, " +
            "product_id, " +
            "version, " +
            "date_created, " +
            "date_last_updated) " +
            "VALUES (?,?,?,?,?,?)";

    public static final String UPDATE_CART_ITEM = "UPDATE cart_item " +
            "SET quantity = ?, " +
            "price = ?, " +
            "version = ?, " +
            "date_last_updated = ? WHERE id = ? ";

    public static final String SELECT_CART_ITEM = "SELECT id, " +
            "quantity, " +
            "price, " +
            "product_id, " +
            "version, " +
            "date_created, " +
            "date_last_updated, " +
            "cart_id " +
            "FROM cart_item WHERE id = ? ";



    @Override
    public CartItem save(CartItem cartItem) {
        try {
            var connection = dataSource.getConnection();
            var preparedStatement = connection.prepareStatement(INSERT_CART_ITEM, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cartItem.getQuantity());
            preparedStatement.setBigDecimal(2, cartItem.getPrice());
            preparedStatement.setLong(3, cartItem.getProduct().getId());
            preparedStatement.setLong(4, 0L); //set version
            preparedStatement.setTimestamp(5, Timestamp.valueOf(cartItem.getDateCreated()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(cartItem.getDateLastUpdated()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to save cart item, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long cartItemId = generatedKeys.getLong(1);
                    cartItem.setId(cartItemId);
                    return cartItem;
                } else {
                    throw new SQLException("Failed to save cart item, no ID obtained.");
                }

            }
        } catch (SQLException e) {
            LOGGER.info("Unable to insert cart item in database: {}", cartItem, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public CartItem update(CartItem cartItem) {
        cartItem.setVersion(cartItem.getVersion() + 1);
        var cartItemToUpdate = findOne(cartItem.getId()).orElseThrow(() -> new CartNotFoundException(
                "Cart item not found by Id, + "
                        + cartItem.getId()));
        if (cartItem.getVersion() <= (cartItemToUpdate.getVersion())) {
            throw new OptimisticLockingFailureException(
                    "CartItem is already updated by another request");
        }
        cartItemToUpdate.setDateLastUpdated(LocalDateTime.now());
        cartItemToUpdate.setVersion(cartItem.getVersion());
        cartItemToUpdate.setProduct(cartItem.getProduct());
        cartItemToUpdate.setQuantity(cartItem.getQuantity());
        cartItemToUpdate.setPrice(cartItem.getPrice());
        try (var connection = dataSource.getConnection();
             var preparedStatement
                     = connection.prepareStatement(UPDATE_CART_ITEM)) {
            preparedStatement.setInt(1,
                    cartItemToUpdate.getQuantity());
            preparedStatement.setBigDecimal(2,
                    cartItemToUpdate.getPrice());
            preparedStatement.setLong(3,
                    cartItemToUpdate.getVersion());
            preparedStatement.setTimestamp(4,
                    Timestamp.valueOf(
                            cartItemToUpdate.getDateLastUpdated()));
            preparedStatement.setLong(5, cartItemToUpdate.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Unable to update cart time: {}",
                    cartItem, e);
            throw new RuntimeException(
                    "Unable to update cartItem", e);
        }
        return cartItemToUpdate;
    }


    private Optional<CartItem> findOne (Long id){
            try (var connection = dataSource.getConnection();
                 var preparedStatement
                         = connection.prepareStatement(SELECT_CART_ITEM)) {
                var resultSet = preparedStatement.executeQuery();
                List<CartItem> cartItems = extractCartItems(resultSet);
                if (cartItems.size() > 0) {
                    return Optional.of(cartItems.get(0));
                }
            } catch (SQLException e) {
                LOGGER.error("Unable to find cartItem by id: {}", id, e);
                throw new RuntimeException("Unable to find cartItem", e);
            }
            return Optional.empty();
        }


    private List<CartItem> extractCartItems(ResultSet resultSet)
            throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        while (resultSet.next()) {
            var cartItem = new CartItem();
            cartItem.setId(resultSet.getLong("id"));
            cartItem.setQuantity(resultSet.getInt("quantity"));
            cartItem.setPrice(resultSet.getBigDecimal("price"));
            cartItem.setVersion(resultSet.getLong("version"));
            cartItem.setDateCreated(
                    resultSet.getTimestamp("date_created")
                            .toLocalDateTime());
            cartItem.setDateLastUpdated(
                    resultSet.getTimestamp("date_last_updated")
                            .toLocalDateTime());
            cartItems.add(cartItem);
        }
        return cartItems;


    }

    public void remove(CartItem cartItem) {

    }

}
