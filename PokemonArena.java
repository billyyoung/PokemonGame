// The "PokemonArena" class.
/*
public class PokemonArena
{
    
    public static void main (String[] args)
    {
		Pokemon []poke=new Pokemon[9];
		int damage;
	    int winner=-1;
		
							// name, HP, damage, type, weakness, resistance
		poke[0]=new Pokemon("Squirtle", 40, 10, "water", "fire", "");   
		poke[1]=new Pokemon("Staryu", 40, 20, "water", "electric", "");   
		poke[2]=new Pokemon("Onix", 90, 10, "rock", "water", "");   
		poke[3]=new Pokemon("Magmar", 70, 20, "fire", "water", "");   
		poke[4]=new Pokemon("Charmander", 50, 10,"fire", "water", "");   
		poke[5]=new Pokemon("Rhyhorn", 60, 20, "rock", "seed", "electric");   
		poke[6]=new Pokemon("Bulbasaur", 50, 10, "seed", "fire", "");   
		poke[7]=new Pokemon("Pikachu", 40, 10, "electric", "rock", "water");   
		poke[8]=new Pokemon("Voltorb", 40, 10, "electric", "rock", "");
	       
		while(winner == -1)
		{
		    int p1,p2;
		    
		    p1 = (int)(Math.random()*9);
		    p2 = (int)(Math.random()*9);
		    
		    if(poke[p1].active() && poke[p2].active() && p1 != p2)
		    {
				System.out.println("I choose you: " + poke[p1].getName());
				System.out.println("Take that: " + poke[p2].getName());
				damage = poke[p1].attack(poke[p2]);
				poke[p2].loseLife(damage);
				damage = poke[p2].attack(poke[p1]);
				poke[p1].loseLife(damage);                   
		    }
		    winner = findWinner(poke);
		}
		System.out.println("WINNER : " + poke[winner].getName());
    } // main method
    
    public static int findWinner(Pokemon [] poke)
    {
		int numactive=0;
		int isactive=0;
		for(int i=0;i<poke.length;i++)
		{
		    if(poke[i].active())
		    {
				numactive++;
				isactive=i;
		    }
		}
		if(numactive <= 1)
		    return isactive;
		else
		    return -1;
	}
} // PokemonArena class
*/