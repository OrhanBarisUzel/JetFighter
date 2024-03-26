


public class Player{

	private int	id;			
	private String name;	
	private String pass;	
	private int score;		


	public Player(){
		super();
	}
	public Player(int id,String name, String pass, int score){
		super();
		this.id=id;
		this.name = name;
		this.pass = pass;
		this.score = score;
	}

	//
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getPass(){
		return pass;
	}
	public void setPass(String pass){
		this.pass = pass;
	}
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score = score;
	}
	@Override
	public String toString() {
		return "Player [id:" + id + ", name:" + name + ", pass:" + pass + ", score:" + score + "]";
	}
}