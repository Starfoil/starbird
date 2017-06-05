package main;

public interface State {
	
	public void perform(GameManager gameManager, GameInstance gameInstance);
}
