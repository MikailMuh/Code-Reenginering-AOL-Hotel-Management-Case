package afterRefactor.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Singleroom implements Serializable
{
	public String name;
	public String contact;
	public String gender;   
	public ArrayList<Food> food =new ArrayList<>();

   
    public Singleroom()
    {
        this.name="";
    }
    public Singleroom(String name,String contact,String gender)
    {
        this.name=name;
        this.contact=contact;
        this.gender=gender;
    }
}
