
import java.util.ArrayList;
import java.util.List;
import testClasses.Person;

public class TestDbConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Persistance p = new Persistance("multipleValues");
//		
//		
//		Person persoana = new Person("Cretu paul", 23);
//		Informatician informatician = new Informatician("Laslau Andrei",true,305.69,false);
//		
//		
//		p.saveToDb(persoana);
//		p.saveToDb(informatician);
		
		
		List<Person> list = new ArrayList<>();
		
		list.add(new Person("Cretu Paul", 23));
		list.add(new Person("Badita George", 22));
		list.add(new Person("Glodeanu Alexandru", 24));
		list.add(new Person("Benosu", 22));
		
		for(int i=0;i< list.size();i++) {
			p.saveToDb(list.get(i));
		}
		
		
	}

}
