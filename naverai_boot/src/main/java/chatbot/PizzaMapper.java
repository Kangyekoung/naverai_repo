package chatbot;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper //@MapperScan
@Repository //@ComponentScan
public interface PizzaMapper {
	int insertPizza(PizzaDTO dto);
}
