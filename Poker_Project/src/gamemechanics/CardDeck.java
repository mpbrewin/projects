package gamemechanics;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
	private List<Card> cards;
	private final static int size = 52;
	private int index = 0;
	
	public CardDeck(){
		cards = new ArrayList<Card>(size);
		for (int i = 1; i < 5; i++){
			for (int j = 2; j < 15; j++){
				cards.add(new Card(j,i));
			}
		}
		shuffleDeck();
	}
	
	public Card drawCard(){
		index++;
		if (index == size) 
			index = 0;
		return cards.get(index);
	}
	
	public void shuffleDeck(){
		Collections.shuffle(cards);
		index = 0;
	}
}
