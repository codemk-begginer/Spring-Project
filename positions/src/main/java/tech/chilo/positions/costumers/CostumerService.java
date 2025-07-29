package tech.chilo.positions.costumers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostumerService {

   private final static String FIND_ALL ="SELECT * FROM customers";

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Costumer> costumerRowMapper =
            ((rs, name) -> new Costumer(rs.getInt("id"),
                    rs.getString("email")) );


    public CostumerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Costumer> search(){
       return this.jdbcTemplate.query(FIND_ALL,costumerRowMapper);
    }
}
