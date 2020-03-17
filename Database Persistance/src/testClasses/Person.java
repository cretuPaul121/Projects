package testClasses;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Person {

	private String completeName;
	private int age;
	private double money;

	public Person(String completeName, int age) {
		this.completeName = completeName;
		this.age = age;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [completeName=" + completeName + ", age=" + age + "]";
	}

	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Person p1 = new Person("Cretu Paul", 23);
		Informatician inf = new Informatician("Cretu paul",true,345,false);
		
		dinamicGetter(p1);
		System.out.println();
		dinamicGetter(inf);
		
		
	}
	
	public static void dinamicGetter(Object o) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field[] atrributes = o.getClass().getDeclaredFields();

		Map<String, String> map = new HashMap<>();
		
		for (Field f : atrributes) {
			String variableName = f.getName();
			String variableType = f.getType().getSimpleName();

			map.put(variableName, variableType);
		}

		
		for (Map.Entry entry : map.entrySet()) {

			Field f = o.getClass().getDeclaredField("" + entry.getKey());
			f.setAccessible(true);

			switch (map.get(entry.getKey())) {

				case "double": {
					double value = (double)f.get(o);
					//insert in database
					System.out.println(value);
				}
					break;
	
				case "int": {
					int value = (int)f.get(o);
					//insert in database
					System.out.println(value);
				}
					break;
	
				case "String": {
					String value = (String)f.get(o);
					//insert in database
					System.out.println(value);
				}
					break;
				
				case "boolean":{
					boolean value = (boolean)f.get(o);
					//insert in database
					System.out.println(value);
				} 
					break;
					
			}
			

		}
		
	}

}
