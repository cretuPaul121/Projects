import java.util.Map;

import testClasses.Informatician;
import testClasses.Person;

public class TestDbConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Persistance p = new Persistance("test6");
		
		Person persoana = new Person("Cretu paul", 23);
		Informatician informatician = new Informatician("Laslau Andrei",true,305.69,false);
		
		p.saveToDb(persoana);
		p.saveToDb(informatician);
		
		
		
	}

}
