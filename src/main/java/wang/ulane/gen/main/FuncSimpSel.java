package wang.ulane.gen.main;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FuncSimpSel {
	
	private boolean flag = false;
	private List<String> whereFields;
	private List<String> selectFields;

}
