package chatbot;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper //@MappScon
@Repository //@ComponetScan
public interface PizzaMapper {
	int insertPizza(PizzaDTO dto);

}
